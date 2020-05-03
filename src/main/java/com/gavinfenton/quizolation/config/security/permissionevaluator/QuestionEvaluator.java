package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.service.QuestionService;
import org.springframework.stereotype.Component;

@Component
public class QuestionEvaluator implements Evaluator<Question> {

    private final QuestionService questionService;

    public QuestionEvaluator(QuestionService questionService) {
        this.questionService = questionService;
    }

    public boolean hasPermission(AppUser appUser, Question question, String permission) {
        return hasPermission(appUser, question.getId(), permission);
    }

    public boolean hasPermission(AppUser appUser, Long questionId, String permission) {
        switch (permission) {
            case "CREATE":
            case "UPDATE":
            case "DELETE":
                return isQuizMaster(appUser, questionId);
            case "READ":
                return isQuizMaster(appUser, questionId) || isQuizTeamMember(appUser, questionId);
            default:
                return false;
        }
    }

    private boolean isQuizMaster(AppUser appUser, Long questionId) {
        return questionService.isMasterOfRelatedQuiz(questionId, appUser.getId());
    }

    private boolean isQuizTeamMember(AppUser appUser, Long questionId) {
        return questionService.isTeamMemberOfRelatedQuiz(questionId, appUser.getId());
    }

}