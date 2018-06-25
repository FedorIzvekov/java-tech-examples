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
    'ORACLE (Кириллица тест)',
    (SELECT BANNER FROM v$version),
    'A',
    TO_DATE('1990-01-31', 'YYYY-MM-DD'),
    TO_TIMESTAMP('1990-01-31 10:30:59', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('1990-01-31 10:30:59', 'YYYY-MM-DD HH24:MI:SS'),
    127,
    32767,
    2147483647,
    99999999999999999.99,
    1,
    HEXTORAW(REPLACE('1b6b2e07-78dc-43f5-9d94-bd77304a545c', '-', '')),
    EMPTY_BLOB()
 ),
 (
    'ORACLE (Кириллица тест)',
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