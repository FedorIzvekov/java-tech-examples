package com.fedorizvekov.caching.config;

import static java.util.Objects.nonNull;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {

        return registry -> registry.config()
                .meterFilter(MeterFilter.deny(tags -> {
                    var uri = tags.getTag("uri");
                    return nonNull(uri) && uri.startsWith("/actuator");
                }));
    }

}
