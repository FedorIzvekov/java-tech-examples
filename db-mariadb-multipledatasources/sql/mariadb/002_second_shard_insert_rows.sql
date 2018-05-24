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
    'MARIADB second shard (Кириллица тест)',
    VERSION(),
    'B',
	STR_TO_DATE('2000-12-01', '%Y-%m-%d'),
    STR_TO_DATE('10:30:59', '%H:%i:%s'),
    STR_TO_DATE('2000-12-01 10:30:59', '%Y-%m-%d %H:%i:%s'),
    2,
    200,
    2000,
    5678.91,
    0,
    UNHEX(REPLACE("afef2790-2dcb-4447-bae3-a34dbed182af", '-', '')),
    LOAD_FILE('/var/lib/mysql/1.jpg')
 ),
 (
    'MARIADB second shard (Кириллица тест)',
    VERSION(),
    'D',
    CURRENT_DATE(),
    CURRENT_TIME(),
    CURRENT_TIMESTAMP(),
    1,
    100,
    1000,
    1234.56,
    1,
    UNHEX(REPLACE(UUID(), '-', '')),
    LOAD_FILE('/var/lib/mysql/1.jpg')
 );