# resttestAPI

## Contact control API

This project was generated using:

- [SpringBoot](https://spring.io/projects/spring-boot)
- [Lombok](https://projectlombok.org/)
- [Mockito](https://site.mockito.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [H2](https://www.h2database.com/html/main.html)
- [Swagger](https://swagger.io/)
- [Docker](https://www.docker.com/)

## Setup
> Create a PortgreSQL schema named 'resttest'.

## From the Terminal 
Clone the repo https://github.com/RebecaTeodoro/resttestAPI

Need to configure database username, password and url according to your database server in application.properties file.
I use PostgreSQL in this project.



## Resources URL

| Http Method | Resource URL                    | Description                                                                                              | Body Content JSON example                                                                                                                                                                                                                                                 |
|-------------|---------------------------------|----------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST        | {{url}}/contact                 | Adds a new contact. The data must be transferred in the body of the request in JSON format.              | {"id": "null","name": "Testando 345","commercialPhone": "1212-1212","homePhone": "1212-1212","cellPhone": "1212-1212","commercialEmail": "teste@teste.com.br","personalEmail": "teste@teste.com.br","dateOfBirth": "10-04-1989","favorite": true }           |
| GET         | {{url}}/contact/3               | Returns the contact of ID 3                                                                              |                                                                                                                                                                                                                                                              |
| GET         | {{url}}/contact/                | Returns a list of all contacts                                                                           |                                                                                                                                                                                                                                                              |
| GET         | {{url}}/contact/page/0          | Returns a list of all contacts in PAGE format                                                            |                                                                                                                                                                                                                                                              |
| GET         | {{url}}/contact/name/namesearch | Returns a list of contacts that have the string "namesearch"                                             |                                                                                                                                                                                                                                                              |
| PUT         | {{url}}/contact/                | Updates the contact. The data must be transferred in the body of the request in JSON format.             | {"id": "3","name test update": "Testando 345","commercialPhone": "1212-1212","homePhone": "1212-1212","cellPhone": "1212-1212","commercialEmail": "teste@teste.com.br","personalEmail": "teste@teste.com.br","dateOfBirth": "10-04-1989","favorite": true }  |
| PUT         | {{url}}/contact/updateFavorite/ | Updates the contact's favorite field. The data must be passed in the body of the request in JSON format. | {"id": "3","name test update": "Testando 345","commercialPhone": "1212-1212","homePhone": "1212-1212","cellPhone": "1212-1212","commercialEmail": "teste@teste.com.br","personalEmail": "teste@teste.com.br","dateOfBirth": "10-04-1989","favorite": false } |
| DELETE      | {{url}}/contact/2               | Deletes the contact from ID 2.                                                                           |                                                                                                                                                                                                                                                              |


### Application Swagger documentation:
http://localhost:8080/swagger-ui.html

### Postman file at the root of the project:
ResttestAPI.postman_collection.json

### Docker repositories (but with errors. If you know the solution, contact me):
- [resttest](https://hub.docker.com/repository/docker/rebecateodoro/resttest)
- [postgres-resttest](https://hub.docker.com/repository/docker/rebecateodoro/postgres-resttest)




