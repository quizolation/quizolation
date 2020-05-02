package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.config.annotation.CastTargetTo;
import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.service.QuizService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.gavinfenton.quizolation.config.security.permissionevaluator.PermissionLevel.*;

@Component
@CastTargetTo(Quiz.class)
public class QuizEvaluator implements Evaluator<Quiz> {

    private final QuizService quizService;

    public QuizEvaluator(QuizService quizService) {
        this.quizService = quizService;
    }

    public boolean hasPermission(AppUser appUser, Quiz targetDomainObject, String permission) {
        return hasPermission(appUser, targetDomainObject.getId(), permission);
    }

    public boolean hasPermission(AppUser appUser, Long targetId, String permission) {
        switch (permission) {
            case "CREATE":
                return true;
            case "READ":
            case "WRITE":
                return isQuizMaster(appUser, targetId);
            default:
                return false;
        }
    }

    private boolean isQuizMaster(AppUser appUser, Long targetId) {
        return quizService.getQuiz(targetId).getMasterId().equals(appUser.getId());
    }

}
