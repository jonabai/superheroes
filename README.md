# SuperHero Manager
This application provides a RESTFUL webservice to manage your superheroes!

## Development

It is developed in Java 1.8, using Maven.

Frameworks used:

- [Spring Boot]: As Spring Boot web site says: "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run". We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need very little Spring configuration."  
- [H2]: Database engine that can work embedded and in server mode; I'm using the in-memory instance, avoiding set up a database server.
- [liquibase]: Liquibase is an open source database-independent library for tracking, managing and applying database schema changes. 
- [lombok]: Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
            Never write another getter or equals method again.   
- [mapstruct]: MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention over configuration approach.
- [Swagger]: I used Swagger to document and test the REST interface. It is accessible via the endpoint http://ec2-18-218-234-13.us-east-2.compute.amazonaws.com:8080/swagger-ui.html


Implementation decisions:
 - I'm using JPA, so it is really simple changing from the configured H2 to another SQL database (Oracle, PostgreSQL, MySQL ...)
 - For mocking services in unit tests, I'm using Mockito
 - Mapstruct it saves a lot of development time, it will generate the mapping class.
 - Lombok: it saves a lot of development time, avoiding you to add getter / setters / equals / etc methods in every entity / dto / pojo.
 - To allow testing the application, I'm including Swagger UI, it will show all the implemented REST API
 - The list of allies of a hero, it is retrieved when the hero is not requested in a list. when the hero DTO instance is inside a list (getAll, allies list of allies), the skills and allies collections are set to null, to avoid sending too much info to the front end and recursive problems :)  
 - The POST call forces to not set the id because we want to generate the id. Front end should not provide the new id (IMHO).
 
You can also fully dockerize this application. To achieve this, first build a docker image of the superhero app by running:

     ./mvnw verify -Pprod dockerfile:build         

 
[Spring Boot]: https://projects.spring.io/spring-boot/
[Liquibase]: http://www.liquibase.org/
[H2]: http://www.h2database.com/html/main.html
[lombok]: https://projectlombok.org/
[mapstruct]: http://mapstruct.org/
[Swagger]: https://swagger.io/
