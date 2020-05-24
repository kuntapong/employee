# Spring-boot Employee App

## Database
The application uses H2 in memory database

## How to run
* Maven install
```
./mvnw clean install
```
* Run Application
```
./mvnw spring-boot:run
```

* Run Application
## Token Authentication
Make a post request to
```
/api/v1/auth/login

Request Body
{
  "username": "admin1",
  "password": "password1"
}
```
Login with 
username: "admin1"
password: "password1"
to get an access token.

And then pass the token to header "app-auth" on employees enpoints

## Unit tests and Integreation test
* Using Maven
```
./mvnw test
```
* And You can run the unit tests and integration test using your IDE.

## Swagger UI
http://localhost:8080/swagger-ui.html
