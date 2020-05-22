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
    uuid_value
--    blob_value,
--    oid_value
)
VALUES (
    'POSTGRESQL (Кириллица тест)',
    version(),
    'A',
    '1990-01-31'::date,
    '10:30:59'::time,
    '1990-01-31 10:30:59'::timestamp,
    127,
    32767,
    2147483647,
    99999999999999999.99,
    true,
    '1b6b2e07-78dc-43f5-9d94-bd77304a545c'::uuid
--    pg_read_binary_file('/var/lib/postgresql/data/1.jpg'),
--    lo_import('/var/lib/postgresql/data/1.jpg')
),
(
    'POSTGRESQL (Кириллица тест)',
    version(),
    'A',
    CURRENT_DATE,
    CURRENT_TIME,
    CURRENT_TIMESTAMP,
    1,
    100,
    1000,
    1234.56,
    false,
    gen_random_uuid()
--    pg_read_binary_file('/var/lib/postgresql/data/1.jpg'),
--    lo_import('/var/lib/postgresql/data/1.jpg')
);


--  Postgresql save large object to pg_largeobject
--  Get large object from postgresql:
--  SELECT lo_export(type_value.oid_value, '/var/lib/postgresql/data/2.jpg') FROM type_value WHERE oid_value = 16394;