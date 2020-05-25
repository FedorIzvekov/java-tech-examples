package com.fedorizvekov.caching.service;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.caching.model.entity.CachedData;

public interface CacheService {

    Optional<CachedData> simpleFindById(long id);

    List<CachedData> simpleFindAll();

}
