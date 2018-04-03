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
    blob_value,
    oid_value
)
VALUES (
    'POSTGRESQL',
    version(),
    'A',
    CURRENT_DATE,
    CURRENT_TIME,
    CURRENT_TIMESTAMP,
    1,
    100,
    1000,
    1234.56,
    true,
    gen_random_uuid(),
    pg_read_binary_file('/var/lib/postgresql/data/1.jpg'),
    lo_import('/var/lib/postgresql/data/1.jpg')
);


--  Postgresql save large object to pg_largeobject
--  Get large object from postgresql:
--  SELECT lo_export(type_value.oid_value, '/var/lib/postgresql/data/2.jpg') FROM type_value WHERE oid_value = 16394;