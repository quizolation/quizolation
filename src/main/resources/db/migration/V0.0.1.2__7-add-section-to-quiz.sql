ALTER TABLE round
    DROP CONSTRAINT round_quiz_id_fkey,
    ADD CONSTRAINT round_quiz_id_fkey FOREIGN KEY (quiz_id) REFERENCES quiz (id) ON DELETE CASCADE;

ALTER TABLE question
    DROP CONSTRAINT question_round_id_fkey,
    ADD CONSTRAINT question_round_id_fkey FOREIGN KEY (round_id) REFERENCES round (id) ON DELETE CASCADE;

ALTER TABLE guess
    DROP CONSTRAINT guess_team_id_fkey,
    DROP CONSTRAINT guess_question_id_fkey,
    ADD CONSTRAINT guess_team_id_fkey FOREIGN KEY (team_id) REFERENCES team (id) ON DELETE CASCADE,
    ADD CONSTRAINT guess_question_id_fkey FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE;

ALTER TABLE quiz_teams
    DROP CONSTRAINT quiz_teams_quiz_id_fkey,
    DROP CONSTRAINT quiz_teams_team_id_fkey,
    ADD CONSTRAINT quiz_teams_quiz_id_fkey FOREIGN KEY (quiz_id) REFERENCES quiz (id) ON DELETE CASCADE,
    ADD CONSTRAINT quiz_teams_team_id_fkey FOREIGN KEY (team_id) REFERENCES team (id) ON DELETE CASCADE;