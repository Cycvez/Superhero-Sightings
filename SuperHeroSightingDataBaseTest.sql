drop database if exists SuperHeroSightingTest;

create database SuperHeroSightingTest;

use SuperHeroSightingTest;

create table Supers(
	ID_Super int primary key auto_increment,
    `Name` varchar(50) not null,
    SuperPower varchar(50) not null,
    `Description` varchar(280) not null,
    Hero_Villain tinyint not null
);

create table Organization(
	ID_Organization int primary key auto_increment,
	`Name` varchar(50) not null,
    `Description` varchar(280) not null,
    Address varchar (280) not null,
    ContactInfo varchar(280) not null
);

create table Members(
	primary key (ID_Super, ID_Organization),
	ID_Super int not null,
    ID_Organization int not null,
	foreign key (ID_Super) references Supers(ID_Super),
    foreign key (ID_Organization) references Organization(ID_Organization)
);

create table Location(
	ID_Location int primary key auto_increment,
	`Name` varchar(50) not null,
    `Description` varchar(280) not null,
    Address varchar (280) not null,
    Latitude Decimal(10,8) not null,
    Longitude Decimal(11,8) not null
);

create table Sighting(
	ID_Sighting int primary key auto_increment,
    ID_Location int not null,
	`Date` TIMESTAMP not null,
     foreign key (ID_Location) references Location(ID_Location)
);

create table Super_Sightings(
	primary key (ID_Super, ID_Sighting),
	ID_Super int not null,
    ID_Sighting int not null,
	foreign key (ID_Super) references Supers(ID_Super),
    foreign key (ID_Sighting) references Sighting(ID_Sighting)
);
