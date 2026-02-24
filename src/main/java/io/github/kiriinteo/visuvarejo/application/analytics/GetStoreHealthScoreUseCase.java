package io.github.kiriinteo.visuvarejo.application.analytics;

import org.springframework.stereotype.Service;

@Service
public class GetStoreHealthScoreUseCase {

    public int execute(double trend, double averageVolatility, double revenueConcentration) {

        int score = 100;

        // impacto da tendência de crescimento
        if (trend < 0) score -= 20;
        if (trend < -20) score -= 20;

        // impacto da volatilidade média
        if (averageVolatility > 1000) score -= 15;

        // impacto da concentração de receita
        if (revenueConcentration > 50) score -= 20;

        if (score < 0) score = 0;

        return score;
    }
}