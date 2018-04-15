USE test_database;

DROP SEQUENCE IF EXISTS test_schema.type_value_long_id_seq;

CREATE SEQUENCE test_schema.type_value_long_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO CYCLE;