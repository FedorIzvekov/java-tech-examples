USE test_database;

DROP TABLE IF EXISTS type_value;

CREATE TABLE type_value (
    long_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier',
    database_name VARCHAR(100) NOT NULL,
    string_value VARCHAR(255) NOT NULL,
    char_value CHAR(1) NOT NULL,
    local_date_value DATE NOT NULL,
    local_time_value TIME NOT NULL,
    local_date_time_value DATETIME NOT NULL,
    byte_value TINYINT NOT NULL,
    short_value SMALLINT NOT NULL,
    integer_value INT NOT NULL,
    big_decimal_value DECIMAL(19,2) NOT NULL,
    boolean_value BIT(1) NOT NULL,
    uuid_value CHAR(36) NOT NULL,
    blob_value LONGBLOB,
    PRIMARY KEY (long_id),
    CHECK (boolean_value IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;