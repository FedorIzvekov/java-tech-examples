package com.fedorizvekov.caching.service;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.caching.model.entity.CachedData;

public interface CacheService {

    Optional<CachedData> caffeineFindById(long id);

    List<CachedData> caffeineFindAll();

    Optional<CachedData> couchbaseFindById(long id);

    List<CachedData> couchbaseFindAll();

    Optional<CachedData> redisFindById(long id);

    List<CachedData> redisFindAll();

    Optional<CachedData> simpleFindById(long id);

    List<CachedData> simpleFindAll();

}
