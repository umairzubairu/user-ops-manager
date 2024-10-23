# User-Ops-Manager

UserOpsManager: As name suggesting management of user operation with API-driven actions. The service provides
following operations for managing user data. It allows to interact with user resources through RESTful APIs.
This document guide you to develop the understanding that how you can interact with endpoints, their functionality, and
the expected request and response formats.

- Basic User Operations (CRUD with Extended Functionality): The API provides typical CRUD operations.
    - Create: We can create a single user or list of users in one go.
    - Read: We can retrieve a specific user by passing the id or all users.
    - Update: We can update a specific user by id.
    - Delete: We can delete a specific user by id. Currently implement soft deletes (where user data is flagged as
      deleted but not physically removed), allowing for
      easy recovery of users.
- Audit Logs: Every create/update/delete action generate an audit trail for tracking changes in user data over
  time.

Note: Duplication validation check exist in create and update flow.

## Base URL

```
http://localhost:8282/api/v1/user
```

All endpoints provided in this API should be appended to the base URL mentioned above. We can change the port in yaml if
required.

## Endpoints

### Retrieve a User

```
GET /{id}
```

This endpoint retrieves information about a specific user identified by their `id`.
The response will include details such as the id, firstname, and lastname information.

#### Example Request

```
GET /1
```

#### Example Response

if the record is found it will get the required data with 200 status code, if the record doesn't exist it will return
404 status code.

```json
{
  "id": 1,
  "firstName": "Tom",
  "lastName": "Jerry"
}
```

If we pass a id which doesn't exist.

```json
{
  "code": "404",
  "message": "User with id 4 does not exist."
}
```

### Create a User

```
POST /
```

we can create a new user by passing the required data in the request body using a post request. You need to provide the
user's details in the request body, including their first name and last name. If any required information is missing it
will generate a validation error due to missing data.

#### Example Request

```json
{
  "firstName": "John",
  "lastName": "Wick"
}
```

#### Example Response

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Wick"
}
```

if the same data already exist

```json
{
  "code": "409",
  "message": "User with First Name John and Last Name Wick already exist."
}
```

In case last name is missing in the request body.

```json
{
  "code": "400",
  "message": "Invalid request",
  "fieldErrors": [
    {
      "code": "NotBlank",
      "field": "lastName",
      "message": "Last name is a mandatory field."
    }
  ]
}
```

### Bulk Creation of Users

```
POST /all
```

This endpoint allows bulk creation of users in a single request. It performs duplicate checks on full names before
saving.

Example Request

```json
[
  {
    "firstName": "Alice",
    "lastName": "Johnson"
  },
  {
    "firstName": "Bob",
    "lastName": "Smith"
  }
]
```

#### Example Response

if the duplicate record is found it will generate duplicate exception or it will create the required data in DB if there
is no duplication.

```json
[
  {
    "id": 2,
    "firstName": "Alice",
    "lastName": "Johnson"
  },
  {
    "id": 3,
    "firstName": "Bob",
    "lastName": "Smith"
  }
]
```

### Update a User

```
PUT /{id}
```

This endpoint allows updating an existing user’s details. The id of the user should be provided in the path, and the
updated fields should be passed in the request body.

Example Request

```
PUT /1
```

Example Request

```json
{
  "firstName": "Thomas",
  "lastName": "Edison"
}
```

Example Response

```json
{
  "id": 1,
  "firstName": "Thomas",
  "lastName": "Edison"
}
```

If the ID doesn't exist:

```json
{
  "code": "404",
  "message": "User with id 1 does not exist."
}
```

Duplicate record:

```json
{
  "code": "409",
  "message": "User with First Name Thomas and Last Name Edison already exist."
}
```

### Delete a User

```
DELETE /{id}
```

This endpoint allows you to delete a user resource identified by their `id`.

#### Example Request

```
DELETE /1
```

#### Example Response

```
User deleted Successfully with id.
```

## Error Handling

In case of errors, the API will respond with appropriate HTTP status codes and error messages. Here are some common
error cases:

- `404 Not Found`: When a user with the specified `id` does not exist.
- `400 Bad Request`: When the request is invalid or missing required parameters.
- `409 Conflict`: When a duplicate found which indicates that the request could not be processed because of the
  conflict.

## Authentication and Authorization

Currently, User Service API is using basic authentication to access the endpoints. Please refer to the application.yml
file to get the credentials information.

## Swagger-UI

```
{server-url}/swagger-ui/index.html
```

We have added swagger-ui as well to interact with API. Please use the above URL to access.

## Prerequisite

Java 17 is required in order to run this project.

-----------------------------------------------------------------

# Technical guidelines

### Switching to MySQL Database

If we plan to switch the database from the in memory configuration to MySQL for data storage in this project,
we will need to perform the following steps:

1. Uncomment the MySQL Dependency from the pom.xml.

For example, in pom.xml, find the following block:

```xml
<!-- Uncomment the following lines if switching to MySQL after commenting above lines -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

Remove the comment tags to activate the MySQL dependency so that Maven can include the MySQL driver in the project.

2. Modify the application.yml to Use MySQL configure as data source.

Example configuration for MySQL:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database_name?useSSL=false&serverTimezone=UTC
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
```

- url: Update the url to point to your MySQL database instance. Replace your_database_name with your actual database
  name.
- username and password: Fill in with the correct MySQL credentials.
- dialect: Use the correct Hibernate dialect for MySQL (e.g., MySQL8Dialect for MySQL 8.x).

3. Database Creation
   Ensure that the MySQL database you referenced in the application.yml (your_database_name) exists. If not, you can
   create it by logging into MySQL and executing:

```sql
CREATE DATABASE your_database_name;
```

4. Restart the Application
   After making these changes, restart your application. Spring Boot will automatically connect to the MySQL database
   and apply any necessary schema changes based on your entity definitions and JPA configuration.

With these steps, your application will start using MySQL as its primary database for data storage, but do remove
in-memory (H2 from pom and yml setting with above) setting

### TODO

There are some action items that are draft as initial version in [TODO.md](/TODO.md). Will try to complete them in the free time. 