package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.service.QuizService;
import com.gavinfenton.quizolation.service.QuizTeamService;
import org.springframework.stereotype.Component;

@Component
public class QuizEvaluator implements Evaluator<Quiz> {

    private final QuizService quizService;
    private final QuizTeamService quizTeamService;

    public QuizEvaluator(QuizService quizService, QuizTeamService quizTeamService) {
        this.quizService = quizService;
        this.quizTeamService = quizTeamService;
    }

    public boolean hasPermission(AppUser appUser, Quiz quiz, String permission) {
        return hasPermission(appUser, quiz.getId(), permission);
    }

    public boolean hasPermission(AppUser appUser, Long quizId, String permission) {
        switch (permission) {
            case "CREATE":
                return true;
            case "READ":
                return isQuizMaster(appUser, quizId) || isQuizTeamMember(appUser, quizId);
            case "UPDATE":
            case "DELETE":
                return isQuizMaster(appUser, quizId);
            default:
                return false;
        }
    }

    private boolean isQuizMaster(AppUser appUser, Long quizId) {
        return quizService.getQuiz(quizId).getMasterId().equals(appUser.getId());
    }

    private boolean isQuizTeamMember(AppUser appUser, Long quizId) {
        return quizTeamService.isQuizTeamMember(quizId, appUser.getId());
    }

}
