package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.entity.Team;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class EvaluatorFactory {

    private final Map<String, Evaluator<?>> subEvaluators;

    public EvaluatorFactory(QuestionEvaluator questionEvaluator, QuizEvaluator quizEvaluator, RoundEvaluator roundEvaluator, TeamEvaluator teamEvaluator) {
        subEvaluators = Map.of(
                Question.class.getSimpleName(), questionEvaluator,
                Quiz.class.getSimpleName(), quizEvaluator,
                Round.class.getSimpleName(), roundEvaluator,
                Team.class.getSimpleName(), teamEvaluator
        );
    }

    @Bean("evaluator")
    public Map<String, Evaluator<?>> permissionEvaluators() {
        return subEvaluators;
    }

}
