CREATE TABLE IF NOT EXISTS sport
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  description VARCHAR (2000)
);

CREATE TABLE IF NOT EXISTS horse
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    sex         CHAR NOT NULL,
    dateofbirth DATE    NOT NULL,
    description VARCHAR(2000),
    favsportid  BIGINT NULL,
    parent1id BIGINT NULL,
    parent2id BIGINT NULL,

    foreign key (favsportid) references sport (id)
);



ALTER TABLE horse
    ADD FOREIGN KEY (parent1id) REFERENCES horse (id);

ALTER TABLE horse
    ADD FOREIGN KEY (parent2id) REFERENCES horse (id);
