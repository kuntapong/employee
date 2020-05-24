# Spring-boot employee

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

## Swagger UI
http://localhost:8080/swagger-ui.html
