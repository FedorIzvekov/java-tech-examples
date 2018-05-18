USE test_database;

DROP TABLE IF EXISTS type_value;

CREATE TABLE type_value (
    long_id Int64,
    database_name String,
    string_value String,
    char_value FixedString(1),
    local_date_value Date,
    local_time_value DateTime,
    local_date_time_value DateTime,
    byte_value Int8,
    short_value Int16,
    integer_value Int32,
    big_decimal_value Decimal(18, 4),
    boolean_value UInt8,
    uuid_value UUID
) ENGINE = MergeTree()
ORDER BY long_id;
