CREATE LOGIN test_user WITH PASSWORD = 'TestUser#123';
GO

USE test_database;
GO

CREATE USER test_user FOR LOGIN test_user;
GO

ALTER LOGIN test_user WITH DEFAULT_DATABASE = test_database;
GO

ALTER ROLE db_datareader ADD MEMBER test_user;
GO

ALTER ROLE db_datawriter ADD MEMBER test_user;
GO