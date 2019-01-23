# parkandrest-kotlin
Backend application made in spring-boot 2 and kotlin for educational purposes. All application features description is available in client application [parkandrest-ui](https://github.com/pokemzok/parkandrest-ui#application-features). 
Application is fully tested on windows 10. Application consists of three microservices:  
* Parkingmanagement - handles all parking related activities
* Security - handles users management and Oauth 2.0 authorization.
* Timemanagement - handles time manipulation (should be deployed only for development or testing purposes).

# What can you find in this application
* Rabbitmq publisher/subscribers implementation (publish event on one exchange, subscribe to it by many queues)
* Oauth 2.0 Server implementation
* Kotlin + Spring Boot 2 project setup
* Flyway configuration
* RESTful architecture 
* Spock testing
* Microservices with kotlin 
* PostgreSQL
* Docker compose 

# Prerequisites before run
* Java Runtime Environment 11 
* Window 10 operation system
* Docker
* Maven 3.6.0

# How to run
You should not encounter any errors during any of those steps. 
## First run
1. Make sure docker is running
2. Share your local drives in docker (necessary for rabbitMQ container)
3. Run [docker-up.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/docker/docker-up.bat)
4. Run [docker-rabbit-config.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/docker/docker-rabbit-config.bat)
5. Run [build.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/run/build.bat)
6. Create parkandrest database (connect to postgresql database and execute [create-db.sql](https://github.com/pokemzok/parkandrest-kotlin/blob/master/run/create-db.sql) )
7. Run [init-db.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/run/init-db.bat)
8. Run [dev-deploy.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/run/dev-deploy.bat) 
## Regular run
1. Make sure that docker is running
2. Make sure that all three containers are running
2. Run [build.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/run/build.bat)
3. Run [dev-deploy.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/run/dev-deploy.bat)   

# Docker
Application uses docker containers. To delete the containers run [docker-down.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/docker/docker-down.bat).

# What if I don't like docker?
Then you should manually install and configure [PostgreSQL](https://www.postgresql.org) and [RabbitMQ](https://www.rabbitmq.com). 

# Flyway
Appplication uses flyway to maintain database state. To migrate database run [migrate.bat](https://github.com/pokemzok/parkandrest-kotlin/blob/master/run/migrate.bat).
 
# Project structure
* database - flyway migrations for database state management
* docker - docker related scripts
* exception-api - library used by parkingmanagement and security in order to manage exception
* parkingmanagement-api - data transfer objects and exceptions for parking management
* parkingmanagement-core - parking management business layer 
* parkingmanagement-web - REST API and Spring configurations for parking management
* security-api - data transfer objects and exceptions for security 
* security-core - spring security configurations (Oauth 2.0), user management business layer
* security-web - REST API and Spring configurations for security
* timemanagement - application time management features
* timemanagement-web - REST API and Spring configurations for time management 

# REST API information (default host - localhost)
## Parking management
| URL                                                         | Description                                                                                                                          |Required authority to access |
|-------------------------------------------------------------| -------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| host:8080/parkingmanagement/account/monitor/financialReport | Returns financial report for requested parking and date.                                                                             |OWNER                        |
| host:8080/parkingmanagement/parkingmeter/checkParkingSpace  | Checks parking meter (has it started and when) for requested parking space.                                                          |DRIVER                       |
| host:8080/parkingmanagement/parkingmeter/start              | Starts parking meter for requested parking space and vehicle.                                                                        |DRIVER                       |
| host:8080/parkingmanagement/parkingmeter/stop               | Stops parking meter for vehicle on requested parking space and returns calculated charge.                                            |DRIVER                       |
| host:8080/parkingmanagement/parkingmeter/checkVehicle       | Checks parking meter (has it started and when) for requested vehicle.                                                                |OPERATOR                     |
| host:8080/parkingmanagement/parkingmeter/parkingSpaces      | Returns filtered parking spaces list which would answer questions like "Is parking space free? What vehicles are currently parked?". |OPERATOR                     |
## Security
| URL                                                         | Description                                                       |Required authority to access|
|-------------------------------------------------------------| -------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| host:8082/security/oauth/token          | Allows user to login, return response with JWT tokens (access and refresh) and authorities string. The token is necessary to access restricted endpoints.|  None             |
| host:8082/security/revoke/token         | Allows user to logout (removes created access and refresh tokens from database).      | Any authority                                                                        |
| host:8082/security/users/sign-up        | Registers new user to system.                                                         | ADMIN                                                                                |
| host:8082/security/users/activate       | Activates user.                                                                       | ADMIN                                                                                |
| host:8082/security/users/deactivate     | Deactivates user. After this he can no longer login and access restricted endpoints.  | ADMIN                                                                                |
| host:8082/security/user/exist           | Checks if user with provided username already exists in database.                     | ADMIN                                                                                |
| host:8082/security/users                | Returns filtered system users list.                                                   | ADMIN                                                                                |
## Time management
| URL                                                         | Description                                                                          |Required authority to access|
|-------------------------------------------------------------| -------------------------------------------------------------------------------------|----------------------------|
| host:8081/timemanagement/time/increment                     | Increments system time by hours. Should only be available enabled during development.| None                       |
| host:8081/timemanagement/time/restore                       | Restores system time to current value. Should only be available during development.  | None                       |

# How to test
 Get the client application [here](https://github.com/pokemzok/parkandrest-ui) and then just run it simultaneously with this application.
 
# Code statistics
| Language      | Total Lines   | Source Code Lines | Source Code Lines [%] | Comment Lines | Comment Lines [%] | Blank Lines | Blank Lines [%] |
| ------------- | ------------- |------------------ | --------------------- | ------------- | ----------------- | ----------- | --------------- |
| Kotlin        | 3395          | 2581              | 76%                   | 204           | 6%                | 610         | 18%             |
| Groovy        | 1357          | 1154              | 85%                   | 70            | 5%                | 133         | 10%             |

# What is planed in future:
* add logging (ELK stack)
* add service discovery and api-gateway
* db per microservice 
* swagger docs
* pom refactor (make child poms self sustaining)
* full linux support 
