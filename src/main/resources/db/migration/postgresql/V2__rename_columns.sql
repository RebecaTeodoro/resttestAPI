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

alter table contact rename commercialPhone to commercial_phone;
alter table contact rename homePhone to home_phone;
alter table contact rename cellPhone to cell_phone;
alter table contact rename commercialEmail to commercial_email;
alter table contact rename personalEmail to personal_email;
alter table contact rename dateOfBirth to date_of_birth;

