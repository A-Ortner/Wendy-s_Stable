CREATE TABLE IF NOT EXISTS sport
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS horse
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    sex         CHAR NOT NULL,
    dateofbirth OBJECT    NOT NULL,
    description VARCHAR(2000),
    favsportid  BIGINT,

    foreign key (favSportID) references sport (id)
);
