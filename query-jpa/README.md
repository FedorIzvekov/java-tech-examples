# Project Query JPA

This project demonstrates using JPA (Java Persistence API) in Spring Boot setup.

## Table of Contents

* [Return to main README.md](../README.md#project-java-tech-examples)
* [Building and Running](#building-and-running)
* [Checking](#checking)
* [Stopping](#stopping)

## Building and Running

1. Navigate to the project directory:

```
cd <projects_directory>/java-tech-examples/query-jpa
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
java -jar ./target/query-jpa-1.0.0-SNAPSHOT.jar
```

## Checking

Create new user:

```
curl -X POST -H "Content-Type: application/json" \
-d '{ "firstName":"Boris","lastName":"Blade","contacts":[{"type":"EMAIL","value":"secondEmail@email.com","confirmationCode":"111","status":"NOT_CONFIRMED"},{"type":"PHONE","value":"1112223344","confirmationCode":"111","status":"NOT_CONFIRMED"}] }' \
http://localhost:8080/users
```

Get all users:

```
curl -X GET http://localhost:8080/users
```

Get user by id:

```
curl -X GET http://localhost:8080/user/1
```

Update user:

```
curl -X PUT -H "Content-Type: application/json" \
-d '{ "firstName":"Boris","lastName":"Razor","contacts":[{"type":"EMAIL","value":"newemail@email.com","confirmationCode":"111","status":"NOT_CONFIRMED"}] }' \
http://localhost:8080/user/1
```

Delete user:

```
curl -X DELETE http://localhost:8080/user/1
```

## Stopping

1. Shutdown the application:

```

curl -v -X POST http://localhost:8080/actuator/shutdown

```

2. Stop docker compose:

```

docker compose down

```
