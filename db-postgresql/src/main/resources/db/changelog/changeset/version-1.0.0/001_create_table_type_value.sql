DROP TABLE IF EXISTS type_value;

CREATE TABLE type_value (
    long_id BIGSERIAL NOT NULL,
    database_name VARCHAR(100) NOT NULL,
    string_value VARCHAR(255) NOT NULL,
    char_value CHAR(1) NOT NULL,
    local_date_value DATE NOT NULL,
    local_time_value TIME NOT NULL,
    local_date_time_value TIMESTAMP NOT NULL,
    byte_value INT2 NOT NULL,
    short_value INT2 NOT NULL,
    integer_value INT4 NOT NULL,
    big_decimal_value NUMERIC(19,2) NOT NULL,
    boolean_value BOOL NOT NULL,
    uuid_value UUID NOT NULL,
    blob_value BYTEA,
    oid_value OID,
    CONSTRAINT type_value_pkey PRIMARY KEY (long_id)
);

COMMENT ON COLUMN type_value.long_id is 'Unique identifier';