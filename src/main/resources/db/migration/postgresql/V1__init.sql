create table contact(
    id serial,
    name varchar(255),
    commercialPhone varchar(14),
	homePhone varchar(14),
	cellPhone varchar(14),
	commercialEmail varchar(100),
	personalEmail varchar(100),
	dateOfBirth date,
	favorite boolean,
primary key(id));