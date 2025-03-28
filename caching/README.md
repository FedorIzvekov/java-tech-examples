# Project Caching

This project demonstrates using Caching in Spring Boot setup.

## Table of Contents

* [Return to main README.md](../README.md#project-java-tech-examples)
* [Building and Running](#building-and-running)
* [Checking](#checking)
* [Results](#results)
* [Stopping](#stopping)

## Building and Running

1. Navigate to the project directory:

```
cd <projects_directory>/java-tech-examples/caching
```

2. Build the project:

```
mvn clean install
```

3. Run Docker Compose:

```
docker compose up -d
```

4. Run the application:

```
java -jar ./target/caching-1.0.0-SNAPSHOT.jar
```

## Checking

```
curl -v http://localhost:8089/by/1/from/{caffein, couchbase, hazelcast, redis, simple}
curl -v http://localhost:8089/all/from/{caffein, couchbase, hazelcast, redis, simple}
curl -v http://localhost:8089/stat/statements/by/cached_data
curl -v http://localhost:8089/actuator/prometheus
```

## Results

```
get_all_data_duration_seconds_max{exception="NONE",source="COUCHBASE",} 0.004581458
get_all_data_duration_seconds_max{exception="NONE",source="HAZELCAST",} 0.005465792
get_all_data_duration_seconds_max{exception="NONE",source="REDIS",} 0.00614075
get_all_data_duration_seconds_max{exception="NONE",source="SIMPLE",} 0.00747675
get_all_data_duration_seconds_max{exception="NONE",source="CAFFEINE",} 0.007842875
get_all_data_duration_seconds_max{exception="NONE",source="POSTGRESQL",} 0.022208167
```

## Stopping

1. Shutdown the application:

```
curl -v -X POST http://localhost:8089/actuator/shutdown
```

2. Stop docker compose:

```
docker compose down
```