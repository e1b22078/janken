CREATE TABLE users (
    id IDENTITY PRIMARY KEY,
    username VARCHAR NOT NULL
);

CREATE TABLE matches (
    id IDENTITY,
    user1 INT NOT NULL,
    user2 INT NOT NULL,
    user1Hand VARCHAR NOT NULL,
    user2Hand VARCHAR NOT NULL
);
