# School registration application

School registration system where you can do the following operations:

## Student
- Add a student
- Update a student
- Delete a student
- Get a student by its student id

## Course
- Add a course
- Update an existing course
- Delete a course
- Get a course by its code
- Get all registered courses

## Registration
- Add student to an existing course
- Get students registered in a particular course by course code
- Get courses registered to a particular student by student id
- Get courses without students
- Get courses without students

## API Documentation
For further information about the above operations, API can be found [here](https://localhost:8080/swagger-ui.html) based on OpenAPI 3 spec

## Requirements

- Java 11
- Docker
- Gradle
- MySQL 8
- Docker Compose

## Setup project

The following are the needed steps to be able to run locally the project in your favorite IDE

### 1 - Start a local database

For local development, there is a containerized mysql db setup with the appropriate ports. The spring boot application
is also configured to use this container when running locally. To start the local DB container, execute :

```bash
./scripts/build_db_container.sh
```
### 2 - Build/Compile

The gradle build command will run all tests, which need a running database. So please ensure you have a
local db running first. The project when runs for the first time, will create automatically DB structure leaning on
Flyway schema management to accomplish that task

``` bash
MYSQL_URL=localhost:3307 MYSQL_DB_NAME=school-registration-db MYSQL_USERNAME=root MYSQL_PASS=mysqlpass ./gradlew build
```

``` bash
# Compiles all Java src and test files
MYSQL_URL=localhost:3307 MYSQL_DB_NAME=school-registration-db MYSQL_USERNAME=root MYSQL_PASS=mysqlpass ./gradlew compileTestJava
```
### 3 - Start the service
``` bash
MYSQL_URL=localhost:3307 MYSQL_DB_NAME=school-registration-db MYSQL_USERNAME=root MYSQL_PASS=mysqlpass ./gradlew bootRun
```
Once the server starts, navigate to [Swagger API](https://localhost:8080/swagger-ui.html) to view API docs for your service

To debug the service locally, you can start the service with the following command and then attach a remote debugger from your IDE

``` bash
MYSQL_URL=localhost:3307 MYSQL_DB_NAME=school-registration-db MYSQL_USERNAME=root MYSQL_PASS=mysqlpass ./gradlew bootRun --debug-jvm
```

