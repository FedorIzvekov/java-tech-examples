--create database
CREATE DATABASE [$(DatabaseName)] COLLATE Latin1_General_100_CI_AS_SC_UTF8;
GO

USE [$(DatabaseName)];
GO

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO


--create schema
CREATE SCHEMA [$(SchemaName)];
GO


--create user
CREATE LOGIN [$(LoginName)] WITH PASSWORD = '$(Password)';
GO

CREATE USER [$(LoginName)] FOR LOGIN [$(LoginName)];
GO

ALTER LOGIN [$(LoginName)] WITH DEFAULT_DATABASE = [$(DatabaseName)];
GO

ALTER ROLE db_datareader ADD MEMBER [$(LoginName)];
GO

ALTER ROLE db_datawriter ADD MEMBER [$(LoginName)];
GO

ALTER ROLE db_ddladmin ADD MEMBER [$(LoginName)];
GO

ALTER USER [$(LoginName)] WITH DEFAULT_SCHEMA = [$(SchemaName)];
GO