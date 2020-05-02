package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.service.RoundService;
import org.springframework.stereotype.Component;

@Component
public class RoundEvaluator implements Evaluator<Round> {

    private final RoundService roundService;

    public RoundEvaluator(RoundService roundService) {
        this.roundService = roundService;
    }

    public boolean hasPermission(AppUser appUser, Round round, String permission) {
        return hasPermission(appUser, round.getId(), permission);
    }

    public boolean hasPermission(AppUser appUser, Long roundId, String permission) {
        switch (permission) {
            case "CREATE":
            case "UPDATE":
            case "DELETE":
                return isQuizMaster(appUser, roundId);
            case "READ":
                return isQuizMaster(appUser, roundId) || isQuizTeamMember(appUser, roundId);
            default:
                return false;
        }
    }

    private boolean isQuizMaster(AppUser appUser, Long roundId) {
        return roundService.isMasterOfRelatedQuiz(roundId, appUser.getId());
    }

    private boolean isQuizTeamMember(AppUser appUser, Long roundId) {
        return roundService.isTeamMemberOfRelatedQuiz(roundId, appUser.getId());
    }

}
