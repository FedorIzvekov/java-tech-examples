# Project Database MariaDB multiple data sources

This project demonstrates using [MariaDB](https://db-engines.com/en/system/MariaDB) with Multiple Data Sources in a Spring Boot
setup.

## Table of Contents

* [Return to main README.md](../README.md#project-java-tech-examples)
* [Building and Running](#building-and-running)
* [Checking](#checking)
* [Stopping](#stopping)

## Building and Running

1. Navigate to the project directory:

```
cd <projects_directory>/java-tech-examples/db-mariadb-multipledatasources
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
java -jar ./target/db-mariadb-multipledatasources-1.0.0-SNAPSHOT.jar
```

## Checking

```
curl -v http://localhost:8081/first_jpa/count/rows
curl -v http://localhost:8081/second_jpa/count/rows

curl -v http://localhost:8081/first_jpa/row/1
curl -v http://localhost:8081/second_jpa/row/1

curl -v http://localhost:8081/first_jpa/rows
curl -v http://localhost:8081/second_jpa/rows
```

## Stopping

Stop docker compose:

```
docker compose down
```