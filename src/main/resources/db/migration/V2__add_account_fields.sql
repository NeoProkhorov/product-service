ALTER TABLE IF EXISTS product
    ADD COLUMN IF NOT EXISTS account_id bigint;

ALTER TABLE IF EXISTS aud.product_aud
    ADD COLUMN IF NOT EXISTS account_id bigint;