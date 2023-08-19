# knacklab-adaptive-example

## How to clone git repo

```
https://full-access:glpat-Qv5FvMoMxjfvWxjnXpgs@gitlab.com/jon-spring-labs/knacklab-adaptive-example.git
```

## Spring Boot Technical Stack

```
- OpenJDK 1.8 8u362-b09
- Spring Boot v2.7.8
- Using Spring Boot WebFlux
```

## Docker

```
 docker build --build-arg JAR_FILE=/target/*.jar -t my-app:latest .
 docker run -it -p 8080:8080 --rm --name test-app my-app:latest --spring.profiles.active=dev
