CREATE OR REPLACE FUNCTION insert_cached_data() RETURNS void AS $$
DECLARE
    counter INT := 1;
    float_value DOUBLE PRECISION;
BEGIN
    WHILE counter <= 100 LOOP
        float_value := counter::DOUBLE PRECISION;

        INSERT INTO cached_data (
            string_value,
            local_date_value,
            local_time_value,
            local_date_time_value,
            integer_value,
            big_decimal_value,
            boolean_value
        )
        VALUES (
            'Raw ' || counter,
            CURRENT_DATE,
            CURRENT_TIME,
            CURRENT_TIMESTAMP,
            counter,
            float_value,
            false
        );

        counter := counter + 1;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT insert_cached_data();