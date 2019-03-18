DROP TABLE IF EXISTS contact;

CREATE TABLE contact (
    contact_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES "user" (user_id),
    type SMALLINT NOT NULL CHECK (type IN (0, 1)),
    value VARCHAR(254) NOT NULL UNIQUE,
    confirmation_code VARCHAR(10) NOT NULL,
    status SMALLINT NOT NULL CHECK (status IN (0, 1, 2)),
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL
);

COMMENT ON TABLE contact IS 'Contact table';
COMMENT ON COLUMN contact.contact_id IS 'Unique identifier of contact';
COMMENT ON COLUMN contact.user_id IS 'Reference to user';
COMMENT ON COLUMN contact.type IS 'Contact type: 0 = email, 1 = phone';
COMMENT ON COLUMN contact.value IS 'Unique contact value';
COMMENT ON COLUMN contact.confirmation_code IS 'Contact confirmation code';
COMMENT ON COLUMN contact.status IS 'Contact confirmation status: 0 = confirmed, 1 = not_confirmed, 2 = replaced';
COMMENT ON COLUMN contact.created IS 'Date and time of entry into database';
COMMENT ON COLUMN contact.updated IS 'Date and time of last modification in database';