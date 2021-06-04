package com.idealista.domain.rating.rules.impl;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.rating.rules.RatingRule;

public class CompletenessRule implements RatingRule {

    private final int MAX_COMPLETENESS_SCORE = 40;

    @Override
    public int getScore(AdVO ad) {
        if(ad.isComplete()) {
            return MAX_COMPLETENESS_SCORE;
        }

        return 0;
    }

}
