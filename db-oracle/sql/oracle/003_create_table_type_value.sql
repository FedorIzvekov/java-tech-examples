ALTER SESSION SET CONTAINER=test_database;
ALTER SESSION SET CURRENT_SCHEMA=TEST_USER;

DROP TABLE IF EXISTS TYPE_VALUE;

CREATE TABLE TYPE_VALUE (
    LONG_ID NUMBER(19) DEFAULT TYPE_VALUE_LONG_ID_SEQ.NEXTVAL PRIMARY KEY,
    DATABASE_NAME VARCHAR2(100) NOT NULL,
    STRING_VALUE VARCHAR2(255) NOT NULL,
    CHAR_VALUE CHAR(1) NOT NULL,
    LOCAL_DATE_VALUE DATE NOT NULL,
    LOCAL_TIME_VALUE TIMESTAMP NOT NULL,
    LOCAL_DATE_TIME_VALUE TIMESTAMP NOT NULL,
    BYTE_VALUE NUMBER(3) NOT NULL,
    SHORT_VALUE NUMBER(5) NOT NULL,
    INTEGER_VALUE NUMBER(10) NOT NULL,
    BIG_DECIMAL_VALUE NUMBER(19,2) NOT NULL,
    BOOLEAN_VALUE NUMBER(1) NOT NULL,
    UUID_VALUE RAW(16) NOT NULL,
    BLOB_VALUE BLOB
);