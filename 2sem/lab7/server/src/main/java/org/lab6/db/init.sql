BEGIN;

CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(40) UNIQUE NOT NULL,
   password VARCHAR(96) NOT NULL,
   salt VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS coordinates (
   id SERIAL PRIMARY KEY,
   x DOUBLE PRECISION NOT NULL,
   y BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS location (
    id SERIAL PRIMARY KEY,
    x REAL NOT NULL,
    y REAL NOT NULL,
    z BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birthday TIMESTAMP WITH TIME ZONE,
    height REAL CHECK (height > 0),
    weight BIGINT CHECK (weight > 0),
    location_id INTEGER,
    FOREIGN KEY (location_id) REFERENCES location(id)
    );

CREATE TYPE color AS ENUM ('RED', 'YELLOW', 'ORANGE');

CREATE TYPE dragon_character AS ENUM ('CUNNING', 'WISE', 'CHAOTIC_EVIL');

CREATE TABLE IF NOT EXISTS dragon (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinates_id INTEGER NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    age INTEGER NOT NULL CHECK (age > 0),
    speaking BOOLEAN,
    color color,
    character dragon_character,
    killer_id INTEGER,
    FOREIGN KEY (coordinates_id) REFERENCES coordinates(id),
    FOREIGN KEY (killer_id) REFERENCES person(id)
    );

END;