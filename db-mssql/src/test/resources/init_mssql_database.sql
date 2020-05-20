--create database
CREATE DATABASE test_database COLLATE Latin1_General_100_CI_AS_SC_UTF8;

USE test_database;

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;

--create schema
CREATE SCHEMA test_schema;

--create user
CREATE LOGIN test_user WITH PASSWORD = 'TestUser#123';
CREATE USER test_user FOR LOGIN test_user;

ALTER LOGIN test_user WITH DEFAULT_DATABASE = test_database;

ALTER ROLE db_datareader ADD MEMBER test_user;
ALTER ROLE db_datawriter ADD MEMBER test_user;

ALTER USER test_user WITH DEFAULT_SCHEMA = test_schema;