USE query_jpa;

DROP TABLE IF EXISTS contact;

CREATE TABLE contact (
    contact_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier of contact',
    user_id BIGINT(20) NOT NULL COMMENT 'Reference to user',
    type TINYINT(4) NOT NULL COMMENT 'Contact type: 0 = email or 1 = phone',
    value VARCHAR(254) NOT NULL COMMENT 'Unique contact value',
    confirmation_code VARCHAR(10) NOT NULL COMMENT 'Contact confirmation code',
    status TINYINT(4) NOT NULL COMMENT 'Contact confirmation status: 0 = confirmed, 1 = not_confirmed, 2 = replaced',
    created DATETIME NOT NULL COMMENT 'Date and time of entry into database',
    updated DATETIME NOT NULL COMMENT 'Date and time of last modification in database',
    PRIMARY KEY (contact_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user (user_id),
    CONSTRAINT contact_value_unique UNIQUE (value),
    CONSTRAINT contact_type CHECK (type IN (0, 1)),
    CONSTRAINT contact_status CHECK (status IN (0, 1, 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
