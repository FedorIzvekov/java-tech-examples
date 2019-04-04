# Project StateMachine

This project demonstrates using StateMachine in Spring Boot setup.

## Table of Contents

* [Return to main README.md](../README.md#project-java-tech-examples)
* [Building and Running](#building-and-running)
* [Checking](#checking)
* [Stopping](#stopping)

## Building and Running

1. Navigate to the project directory:

```
cd <projects_directory>/java-tech-examples/statemachine
```

2. Build the project:

```
mvn clean install
```

3. Run the application:

```
java -jar ./target/statemachine-1.0.0-SNAPSHOT.jar
```

## Checking

```
curl -X GET http://localhost:8088/registration/state/1
```

```
curl -X POST "http://localhost:8088/registration/event/1?event=NEW_USER_SAVED"
```

```
curl -X POST "http://localhost:8088/registration/event/1?event=EMAIL_CONFIRMED"
```

```
curl -X POST "http://localhost:8088/registration/event/1?event=PHONE_CONFIRMED"
```

## Stopping

Shutdown the application:

```
curl -v -X POST http://localhost:8088/actuator/shutdown
```
