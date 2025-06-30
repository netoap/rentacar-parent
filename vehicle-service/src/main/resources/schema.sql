CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    available BOOLEAN NOT NULL,
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    price_per_day NUMERIC(10, 2) NOT NULL
);
