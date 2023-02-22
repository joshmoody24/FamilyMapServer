drop table if exists users;
drop table if exists persons;
drop table if exists events;
drop table if exists authtokens;

create table users (
    personID varchar(100) not null primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    email varchar(100) not null,
    firstName varchar(100) not null,
    lastName varchar(100) not null,
    gender char(1) not null,
    constraint unique_username unique (username)
);

create table persons (
    personID varchar(100) not null primary key,
    associatedUsername varchar(100) not null,
    firstName varchar(100) not null,
    lastName varchar(100) not null,
    gender char(1) not null,
    fatherID varchar(100),
    motherID varchar(100),
    spouseID varchar(100)
);

create table events (
    eventID varchar(100) not null primary key,
    associatedUsername varchar(100) not null,
    personID varchar(100) not null,
    latitude float not null,
    longitude float not null,
    country varchar(100) not null,
    city varchar(100) not null,
    eventType varchar(100) not null,
    year int not null
);

create table authtokens (
    authtoken varchar(100) not null primary key,
    username varchar(100) not null
);