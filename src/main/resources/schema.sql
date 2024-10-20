CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255)
);

CREATE TABLE matches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user1 INT,
    user2 INT,
    user1Hand VARCHAR(10),
    user2Hand VARCHAR(10)
);
