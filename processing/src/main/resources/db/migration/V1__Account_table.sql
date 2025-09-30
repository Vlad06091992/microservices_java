CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE public."accounts"
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    user_id character varying NOT NULL,
    currency_code character varying,
    balance numeric,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public."Accout_table"
    OWNER to java_user;