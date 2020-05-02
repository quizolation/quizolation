package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Round;
import org.springframework.stereotype.Component;

@Component
public class RoundEvaluator implements Evaluator<Round> {

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
        // TODO: implement
        return false;
    }

    private boolean isQuizTeamMember(AppUser appUser, Long roundId) {
        // TODO: implement
        return false;
    }

}
