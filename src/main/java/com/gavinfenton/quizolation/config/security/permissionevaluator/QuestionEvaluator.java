package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionEvaluator implements Evaluator<Question> {

    public boolean hasPermission(AppUser appUser, Question Question, String permission) {
        return hasPermission(appUser, Question.getId(), permission);
    }

    public boolean hasPermission(AppUser appUser, Long QuestionId, String permission) {
        return false;
    }

}
