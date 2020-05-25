DROP TABLE IF EXISTS cached_data;

CREATE TABLE cached_data (
    id BIGSERIAL NOT NULL,
    string_value VARCHAR(255) NOT NULL,
    local_date_value DATE NOT NULL,
    local_time_value TIME NOT NULL,
    local_date_time_value TIMESTAMP NOT NULL,
    integer_value INT4 NOT NULL,
    big_decimal_value NUMERIC(19,2) NOT NULL,
    boolean_value BOOL NOT NULL,
    CONSTRAINT cashed_data_pkey PRIMARY KEY (id)
);

COMMENT ON COLUMN cached_data.id is 'Unique identifier';