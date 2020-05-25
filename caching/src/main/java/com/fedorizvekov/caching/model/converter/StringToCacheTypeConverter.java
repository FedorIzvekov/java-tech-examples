package com.fedorizvekov.caching.model.converter;

import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCacheTypeConverter implements Converter<String, CacheType> {

    @Override
    public CacheType convert(String source) {
        return CacheType.valueOf(source.toUpperCase());
    }

}
