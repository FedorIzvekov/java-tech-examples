# Project Database ClickHouse

This project demonstrates using [ClickHouse](https://db-engines.com/en/system/ClickHouse) in Spring Boot setup.

## Table of Contents

* [Return to main README.md](../README.md#project-java-tech-examples)
* [Building and Running](#building-and-running)
* [Checking](#checking)
* [Stopping](#stopping)

## Building and Running

1. Navigate to the project directory:

```
cd <projects_directory>/java-tech-examples/db-clickhouse
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
java -jar ./target/db-clickhouse-1.0.0-SNAPSHOT.jar
```

## Checking

```
curl -v http://localhost:8086/jpa/row/1
curl -v http://localhost:8086/jpa/rows
```

## Stopping

1. Shutdown the application:

```
curl -v -X POST http://localhost:8086/actuator/shutdown
```

2. Stop docker compose:

```
docker compose down
```