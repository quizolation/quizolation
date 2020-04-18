CREATE TABLE app_user
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    name     VARCHAR(32)           NOT NULL,
    username VARCHAR(16)           NOT NULL UNIQUE,
    email    VARCHAR(254)          NOT NULL UNIQUE,
    password VARCHAR(256)          NOT NULL
);

ALTER TABLE quiz
    ADD COLUMN master_id BIGINT REFERENCES app_user (id) ON DELETE CASCADE;

CREATE TABLE app_users_teams
(
    app_user_id BIGINT REFERENCES app_user (id) ON DELETE CASCADE,
    team_id     BIGINT REFERENCES team (id) ON DELETE CASCADE
);