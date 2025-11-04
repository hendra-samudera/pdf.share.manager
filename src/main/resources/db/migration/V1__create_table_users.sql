CREATE TABLE users (
    username VARCHAR(128) PRIMARY KEY,
    password VARCHAR(128) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(128) NOT NULL,
    authority VARCHAR(128) NOT NULL,
    CONSTRAINT authorities_unique UNIQUE (username, authority),
    CONSTRAINT authorities_fk1 FOREIGN KEY (username) REFERENCES users (username)
);
