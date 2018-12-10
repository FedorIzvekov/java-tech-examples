USE query_jpa;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier of user',
    first_name VARCHAR(100) NOT NULL COMMENT 'First name',
    last_name VARCHAR(100) DEFAULT NULL COMMENT 'Surname',
    middle_name VARCHAR(100) DEFAULT NULL COMMENT 'Middle name (patronymic)',
    birthdate DATE DEFAULT NULL COMMENT 'Date of birth',
    gender TINYINT(4) NOT NULL COMMENT 'Gender: 0 = undetermined, 1 = male, 2 = female',
    created DATETIME NOT NULL COMMENT 'Date and time of entry into database',
    updated DATETIME NOT NULL COMMENT 'Date and time of last modification in database',
    PRIMARY KEY (user_id),
    CONSTRAINT user_gender CHECK (gender IN (0, 1, 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
