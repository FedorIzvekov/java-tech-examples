USE test_database;

INSERT INTO test_schema.type_value (
    database_name,
    string_value,
    char_value,
    local_date_value,
    local_time_value,
    local_date_time_value,
    byte_value, short_value,
    integer_value,
    big_decimal_value,
    boolean_value,
    uuid_value,
    blob_value
)
VALUES (
    'MSSQL',
    @@VERSION,
    'A',
    GETDATE(),
    CONVERT(TIME, GETDATE()),
    GETDATE(),
    1,
    100,
    1000,
    1234.56,
    1,
    NEWID(),
    (SELECT BulkColumn FROM OPENROWSET(BULK '/var/opt/mssql/1.jpg', SINGLE_BLOB) AS blob)
);