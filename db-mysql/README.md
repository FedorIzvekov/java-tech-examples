# Project Database MySQL

This project demonstrates using [MySQL](https://db-engines.com/en/system/MySQL) in Spring Boot setup.

## Table of Contents

* [Return to main README.md](../README.md#project-java-tech-examples)
* [Building and Running](#building-and-running)
* [Checking](#checking)
* [Stopping](#stopping)

## Building and Running

1. Navigate to the project directory:

```
cd <projects_directory>/java-tech-examples/db-mysql
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
java -jar ./target/db-mysql-1.0.0-SNAPSHOT.jar
```

## Checking

### JPA

```
curl -v http://localhost:8084/jpa/rows/count
curl -v http://localhost:8084/jpa/row/1
curl -v http://localhost:8084/jpa/rows
```

### JDBC

```
curl -v http://localhost:8084/jdbc/rows/count
curl -v http://localhost:8084/jdbc/row/1
curl -v http://localhost:8084/jdbc/rows
```

### CRITERIA

```
curl -v http://localhost:8084/criteria/rows/count
curl -v http://localhost:8084/criteria/row/1
curl -v http://localhost:8084/criteria/rows
```

## Stopping

1. Shutdown the application:

```
curl -v -X POST http://localhost:8084/actuator/shutdown
```

2. Stop docker compose:

```
docker compose down
```