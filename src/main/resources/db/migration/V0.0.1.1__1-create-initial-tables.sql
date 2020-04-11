CREATE TABLE quiz (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE round (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(32) NOT NULL,
    description VARCHAR(256) NOT NULL,
    quiz_id BIGINT NOT NULL REFERENCES quiz (id)
);

CREATE TABLE question (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    question VARCHAR(512) NOT NULL,
    answer VARCHAR(256) NOT NULL,
    points INTEGER NOT NULL CHECK (points > 0),
    round_id BIGINT NOT NULL REFERENCES round (id)
);

CREATE TABLE team (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(32)
);

CREATE TABLE guess (
    team_id BIGINT NOT NULL REFERENCES team (id),
    question_id BIGINT NOT NULL REFERENCES question (id),
    guess VARCHAR(256),
    points INTEGER
);

CREATE TABLE quiz_teams (
    quiz_id BIGINT NOT NULL REFERENCES quiz (id) ,
    team_id BIGINT NOT NULL REFERENCES team (id)
);