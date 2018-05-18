USE test_database;

INSERT INTO type_value (
    long_id,
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
    uuid_value
) VALUES (
    1,
    'CLICKHOUSE',
    VERSION(),
    'A',
    CURRENT_DATE(),
    CURRENT_TIMESTAMP(),
    CURRENT_TIMESTAMP(),
    127,
    32767,
    2147483647,
    12345.6789,
    1,
    generateUUIDv4()
);
