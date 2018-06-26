# Project Database Oracle

This project demonstrates using [Oracle](https://db-engines.com/en/system/Oracle) in Spring Boot setup.

## Table of Contents

* [Return to main README.md](../README.md#project-java-tech-examples)
* [Building and Running](#building-and-running)
* [Checking](#checking)
* [Stopping](#stopping)

## Building and Running

1. Navigate to the project directory:

```
cd <projects_directory>/java-tech-examples/db-oracle
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
java -jar ./target/db-oracle-1.0.0-SNAPSHOT.jar
```

## Checking

### JPA

```
curl -v http://localhost:8085/jpa/rows/count
curl -v http://localhost:8085/jpa/row/1
curl -v http://localhost:8085/jpa/rows
```

### JDBC

```
curl -v http://localhost:8085/jdbc/rows/count
curl -v http://localhost:8085/jdbc/row/1
curl -v http://localhost:8085/jdbc/rows
```

## Stopping

1. Shutdown the application:

```
curl -v -X POST http://localhost:8085/actuator/shutdown
```

2. Stop docker compose:

```
docker compose down
```