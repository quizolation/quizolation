package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.entity.Team;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class EvaluatorFactory {

    private final Map<String, Evaluator<?>> subEvaluators;

    public EvaluatorFactory(TeamEvaluator teamEvaluator, QuizEvaluator quizEvaluator) {
        subEvaluators = Map.of(
                Team.class.getSimpleName(), teamEvaluator,
                Quiz.class.getSimpleName(), quizEvaluator
        );
    }

    @Bean("evaluator")
    public Map<String, Evaluator<?>> permissionEvaluators() {
        return subEvaluators;
    }

}
