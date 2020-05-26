CREATE EXTENSION IF NOT EXISTS pg_stat_statements;

CREATE SCHEMA IF NOT EXISTS cache_schema;

ALTER EXTENSION pg_stat_statements SET SCHEMA cache_schema;

SET search_path TO cache_schema, public;