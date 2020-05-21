DROP TABLE IF EXISTS type_value;

CREATE TABLE type_value (
    long_id BIGINT DEFAULT NEXT VALUE FOR type_value_long_id_seq,
    database_name VARCHAR(100) NOT NULL,
    string_value VARCHAR(255) NOT NULL,
    char_value CHAR(1) NOT NULL,
    local_date_value DATE NOT NULL,
    local_time_value TIME NOT NULL,
    local_date_time_value DATETIME2 NOT NULL,
    byte_value TINYINT NOT NULL,
    short_value SMALLINT NOT NULL,
    integer_value INT NOT NULL,
    big_decimal_value NUMERIC(19,2) NOT NULL,
    boolean_value BIT NOT NULL,
    uuid_value UNIQUEIDENTIFIER NOT NULL,
    blob_value VARBINARY(MAX),
    CONSTRAINT type_value_pkey PRIMARY KEY (long_id)
);

EXEC sp_addextendedproperty
    'MS_Description', N'Unique identifier',
    'schema', N'test_schema',
    'table', N'type_value',
    'column', N'long_id';
