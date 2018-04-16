DROP DATABASE IF EXISTS javaProjekt;

CREATE DATABASE javaprojekt ENCODING = 'UTF8';

--Connect to database
\c javaprojekt

--Customer table
CREATE TABLE Customer(id serial primary key not null,
					First_Name varchar(30) not null,
					Last_Name varchar(30) not null,
					Birth date not null,
					Sex char(1) not null,
					Tel_Number varchar(12),
					City varchar(25),
					Address text);

--Account table
CREATE TABLE Account(id serial primary key not null,
					Customer_ID int  REFERENCES Customer(id) not null,
					Cash numeric,
					Username varchar(25) not null,
					Password varchar(30) not null);

ALTER TABLE Account ALTER COLUMN Cash SET DEFAULT 0;


--Transaction table
CREATE TABLE Transaction(id serial primary key not null,
						Account_ID int REFERENCES Account(id) not null,
						T_Time timestamp,
						Event_ID int not null);
ALTER TABLE Transaction ALTER COLUMN T_Time SET DEFAULT CURRENT_TIMESTAMP;


--Event table
CREATE TABLE Event(id serial primary key not null,
					Location varchar(25) not null,
					E_Start timestamp,
					Length int,
					Height int,
					Insurance boolean);
ALTER TABLE Event ALTER COLUMN E_Start SET DEFAULT CURRENT_TIMESTAMP;


--Customer_Event (Binding table)
CREATE TABLE Customer_Event(id serial primary key not null,
							Event_ID int REFERENCES Event(id) not null,
							Cust_ID int REFERENCES Customer(id) not null,
							Specification boolean);
ALTER TABLE Customer_Event ALTER COLUMN Specification SET DEFAULT false;

--Specification table
CREATE TABLE Specification(id serial primary key not null,
							Customer_Event_ID int REFERENCES Customer_Event(id) not null,
							Duration time,
							Rank int not null,
							Length int not null,
							Finished boolean);