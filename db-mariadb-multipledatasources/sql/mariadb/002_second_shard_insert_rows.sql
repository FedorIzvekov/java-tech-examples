USE multiple_data_sources;

INSERT INTO type_value (
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
    'MARIADB second shard',
    VERSION(),
    'B',
    CURRENT_DATE(),
    CURRENT_TIME(),
    CURRENT_TIMESTAMP(),
    2,
    200,
    2000,
    5678.90,
    0,
    UUID(),
    LOAD_FILE('/var/lib/mysql/1.jpg')
 );