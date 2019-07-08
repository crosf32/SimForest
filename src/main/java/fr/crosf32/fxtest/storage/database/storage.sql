CREATE TABLE Article (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    price int NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE User (
    id int NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    game_played int NOT NULL,
    game_won int NOT NULL,
    PRIMARY KEY (id)
);