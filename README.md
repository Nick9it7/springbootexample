# SpringBootExample

Basic project with authentication page and CRUD operation on tables.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Configure

Before running the application you should set database configuration in the `application.properties` file. See an example below:

```$xslt
spring.datasource.url=jdbc:mysql://localhost:3306/springbootdb
spring.datasource.username=root
spring.datasource.password=rootroot
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.spring.SpringBootExample` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Test API

Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to check the available REST API.