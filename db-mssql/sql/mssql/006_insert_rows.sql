USE test_database;

INSERT INTO test_schema.type_value (
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
    'MSSQL (Кириллица тест)',
    @@VERSION,
    'A',
    CAST('1990-01-31' AS date),
    CAST('10:30:59' AS time),
    CAST('1990-01-31 10:30:59' AS datetime2),
    127,
    32767,
    2147483647,
    99999999999999999.99,
    1,
    CAST('1b6b2e07-78dc-43f5-9d94-bd77304a545c' AS uniqueidentifier),
    (SELECT BulkColumn FROM OPENROWSET(BULK '/var/opt/mssql/1.jpg', SINGLE_BLOB) AS blob)
),
(
    'MSSQL (Кириллица тест)',
    @@VERSION,
    'A',
    GETDATE(),
    CONVERT(TIME, GETDATE()),
    GETDATE(),
    1,
    100,
    1000,
    1234.56,
    0,
    NEWID(),
    (SELECT BulkColumn FROM OPENROWSET(BULK '/var/opt/mssql/1.jpg', SINGLE_BLOB) AS blob)
);