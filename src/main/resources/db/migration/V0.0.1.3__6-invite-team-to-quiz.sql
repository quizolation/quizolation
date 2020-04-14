ALTER TABLE quiz_teams
    ADD UNIQUE (quiz_id, team_id);