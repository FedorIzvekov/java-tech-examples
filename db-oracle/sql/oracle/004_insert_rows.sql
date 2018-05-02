ALTER SESSION SET CONTAINER=test_database;
ALTER SESSION SET CURRENT_SCHEMA=TEST_USER;

 INSERT INTO type_value (
     database_name,
     string_value,
     char_value,
     local_date_value,
     local_time_value,
     local_date_time_value,
     byte_value,
     short_value,
     integer_value,
     big_decimal_value,
     boolean_value,
     uuid_value,
     blob_value
 )
 VALUES (
    'ORACLE',
    (SELECT BANNER FROM v$version),
    'A',
    CURRENT_DATE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1,
    100,
    1000,
    1234.56,
    1,
    SYS_GUID(),
    EMPTY_BLOB()
 );