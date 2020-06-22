package com.fedorizvekov.caching.aop;

import static java.util.Objects.isNull;

import java.util.concurrent.CompletionStage;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Timer.Sample;
import io.micrometer.core.lang.NonNullApi;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
@NonNullApi
@AllArgsConstructor
public class TimedAspect {

    private final MeterRegistry registry;


    @Around("execution (@io.micrometer.core.annotation.Timed * *.*(..))")
    public Object timedMethod(ProceedingJoinPoint pjp) throws Throwable {

        var method = ((MethodSignature) pjp.getSignature()).getMethod();
        var timed = method.getAnnotation(Timed.class);

        if (isNull(timed)) {
            method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
            timed = method.getAnnotation(Timed.class);
        }

        var stopWhenCompleted = CompletionStage.class.isAssignableFrom(method.getReturnType());
        var metricName = timed.value().isEmpty() ? "service.response.seconds" : timed.value();

        return timed.longTask()
                ? processWithLongTaskTimer(pjp, timed, metricName, stopWhenCompleted)
                : processWithTimer(pjp, timed, metricName, stopWhenCompleted);
    }


    private Object processWithTimer(ProceedingJoinPoint pjp, Timed timed, String metricName, boolean stopWhenCompleted) throws Throwable {

        var sample = Timer.start(registry);
        var object = pjp.proceed();
        var args = pjp.getArgs();

        try {

            if (stopWhenCompleted && object instanceof CompletionStage<?>) {
                return ((CompletionStage<?>) object)
                        .whenComplete((result, throwable) -> stopTimer(timed, metricName, sample, args, throwable));
            }

            stopTimer(timed, metricName, sample, args, null);

            return object;

        } catch (Exception exception) {
            stopTimer(timed, metricName, sample, args, exception);
            throw exception;
        }

    }


    private void stopTimer(Timed timed, String metricName, Sample sample, Object[] args, Throwable throwable) {

        var exceptionClass = isNull(throwable) ? "NONE" : throwable.getClass().getSimpleName();

        var builder = Timer.builder(metricName)
                .description(timed.description())
                .tags(timed.extraTags())
                .tags("exception", exceptionClass)
                .publishPercentileHistogram(timed.histogram())
                .publishPercentiles(timed.percentiles());

        if (args.length > 0 && args[0] instanceof CacheType) {
            builder.tags("source", ((CacheType) args[0]).name());
        }

        sample.stop(builder.register(registry));
    }


    private Object processWithLongTaskTimer(ProceedingJoinPoint pjp, Timed timed, String metricName, boolean stopWhenCompleted) throws Throwable {

        var sample = LongTaskTimer.builder(metricName)
                .description(timed.description().isEmpty() ? null : timed.description())
                .tags(timed.extraTags())
                .register(registry)
                .start();

        try {
            var object = pjp.proceed();

            if (stopWhenCompleted && object instanceof CompletionStage) {
                return ((CompletionStage<?>) object)
                        .whenComplete((result, throwable) -> stopLongTimer(sample));
            }
            return object;

        } finally {
            if (!stopWhenCompleted) {
                stopLongTimer(sample);
            }
        }

    }


    private void stopLongTimer(LongTaskTimer.Sample sample) {
        try {
            sample.stop();
        } catch (Exception error) {
            log.error("Error stopping timer", error);
        }
    }

}
