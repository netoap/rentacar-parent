CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    reservation_id BIGINT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    method VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    date TIMESTAMP NOT NULL
);
