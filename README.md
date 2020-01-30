# resttestAPI
Contact control API

This project was generated using:

SpringBoot
Lombok
Mockito
PostgreSQL
H2
Swagger
Docker

Setup
Create a PortgreSQL schema named 'resttest'.

From the Terminal: 
Clone the repo https://github.com/RebecaTeodoro/resttestAPI

Need to configure database username, password and url according to your database server in application.properties file.
I use PostgreSQL in this project.

Resources URL

Contact POST and PUT example
Headers{ Context-Type: application/json, Accept: application/json } both for POST and PUT request
POST Resource URL => http://localhost:8080/contact

Body Content JSON example:
{
	"id": "null",
	"name": "Testando 345",
	"commercialPhone": "1212-1212",
	"homePhone": "1212-1212",
	"cellPhone": "1212-1212",
	"commercialEmail": "teste@teste.com.br",
	"personalEmail": "teste@teste.com.br",
	"dateOfBirth": "10-04-1989",
	"favorite": true
}




