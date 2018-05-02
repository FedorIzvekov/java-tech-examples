ALTER SESSION SET CONTAINER=test_database;

CREATE USER test_user IDENTIFIED BY TestUser#123 QUOTA UNLIMITED ON USERS;

GRANT CONNECT, RESOURCE TO TEST_USER;

SELECT username, account_status FROM dba_users WHERE username = 'TEST_USER';
SELECT grantee, granted_role FROM dba_role_privs WHERE grantee = 'TEST_USER';
