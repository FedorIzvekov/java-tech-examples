DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
    user_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    middle_name VARCHAR(100),
    birthdate DATE,
    gender SMALLINT NOT NULL CHECK (gender IN (0, 1, 2)),
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL
);

COMMENT ON TABLE "user" IS 'User table';
COMMENT ON COLUMN "user".user_id IS 'Unique identifier of user';
COMMENT ON COLUMN "user".first_name IS 'First name';
COMMENT ON COLUMN "user".last_name IS 'Surname';
COMMENT ON COLUMN "user".middle_name IS 'Middle name (patronymic)';
COMMENT ON COLUMN "user".birthdate IS 'Date of birth';
COMMENT ON COLUMN "user".gender IS 'Gender: 0 = undetermined, 1 = male, 2 = female';
COMMENT ON COLUMN "user".created IS 'Date and time of entry into database';
COMMENT ON COLUMN "user".updated IS 'Date and time of last modification in database';
