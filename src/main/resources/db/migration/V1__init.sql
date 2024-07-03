CREATE SCHEMA IF NOT EXISTS aud;

CREATE TABLE IF NOT EXISTS product (
    id                      uuid        not null,
    name                    text        not null,
    type                    text        not null,
    start_date              date        not null,
    end_date                date        not null,
    description             text,
    tariff_id               uuid        not null,
    version                 int         not null    DEFAULT 0,
    last_modified_instant   timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS aud.revinfo (
    rev         int     not null,
    revtstmp    bigint,
    PRIMARY KEY (rev)
);

CREATE SEQUENCE revinfo_seq
	INCREMENT BY 50
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE IF NOT EXISTS aud.product_aud (
    id                      uuid        not null,
    rev                     int         not null    CONSTRAINT rev_product REFERENCES aud.revinfo,
    revtype                 smallint,
    name                    text        not null,
    type                    text        not null,
    start_date              date        not null,
    end_date                date        not null,
    description             text,
    tariff_id               uuid        not null,
    version                 int         not null    DEFAULT 0,
    last_modified_instant   timestamp,
    PRIMARY KEY (id, rev)
);