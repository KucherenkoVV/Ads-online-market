-- liquibase formatted sql

-- changeset KucherenkoVV:1

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(25)        ,
    last_name  VARCHAR(25)        ,
    phone      VARCHAR(13)        ,
    password   VARCHAR(255)       NOT NULL,
    role       VARCHAR(10)        NOT NULL DEFAULT 'USER',
    image      VARCHAR
);

CREATE TABLE ads
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR,
    price       INTEGER      NOT NULL,
    author_id   INT REFERENCES users (id),
    image       VARCHAR
);

CREATE TABLE comment
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    text       TEXT      NOT NULL,
    ads_id     INTEGER REFERENCES ads (id),
    author_id  INTEGER REFERENCES users (id)
);

ALTER TABLE users ADD COLUMN enabled boolean default true;

CREATE TABLE authorities
(
    id SERIAL PRIMARY KEY ,
    username VARCHAR(30) NOT NULL ,
    authority VARCHAR(30) NOT NULL
)


