package com.fedorizvekov.caching.controller;

import java.util.List;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CachingController {

    private final DataService dataService;


    @GetMapping("/all/from/{cacheType}")
    @ResponseStatus(HttpStatus.OK)
    public List<CachedData> getAllFromCache(@PathVariable CacheType cacheType) {
        return dataService.findAll(cacheType);
    }


    @GetMapping("/by/{id}/from/{cacheType}")
    @ResponseStatus(HttpStatus.OK)
    public CachedData getByIdFromCache(@PathVariable long id, @PathVariable CacheType cacheType) {
        return dataService.findById(cacheType, id);
    }

}
