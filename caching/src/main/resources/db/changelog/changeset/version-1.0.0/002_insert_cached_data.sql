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
    'First Raw',
    '1990-01-31'::date,
    '10:30:59'::time,
    '1990-01-31 10:30:59'::timestamp,
    2147483647,
    99999999999999999.99,
    true
),
(
    'Second Raw',
    CURRENT_DATE,
    CURRENT_TIME,
    CURRENT_TIMESTAMP,
    22222,
    234567.890,
    false
);




