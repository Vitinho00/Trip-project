CREATE TABLE participants (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    trip_id BIGINT,
    FOREIGN KEY (trip_id) REFERENCES trips(id)
);