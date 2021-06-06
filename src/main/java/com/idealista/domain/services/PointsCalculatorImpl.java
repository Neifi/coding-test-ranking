package com.idealista.domain.services;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.ad.IrrelevantSinceVO;
import com.idealista.domain.rating.rules.impl.CompletenessRule;
import com.idealista.domain.rating.rules.impl.DescriptionRule;
import com.idealista.domain.rating.rules.impl.ImageRule;
import com.idealista.domain.rating.rules.RatingRule;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PointsCalculatorImpl implements PointsCalculator {

    private RatingRule ratingRule;


    public void calculateDescriptionPoints(Ad ad) {
        ratingRule = new DescriptionRule();
        ad.getScore().increase(ratingRule.getScore(ad));
    }

    public void calculateCompletenessPoints(Ad ad) {
        ratingRule = new CompletenessRule();
        int score = ratingRule.getScore(ad);
        if (score == 0) {
            ad.getScore().decrease(40);
            return;
        }
        ad.getScore().increase(score);
    }

    public void calculateImagePoints(Ad ad) {
        ratingRule = new ImageRule();
        int imageRuleScore = ratingRule.getScore(ad);
        if (imageRuleScore < 0) {
            ad.getScore().decrease(imageRuleScore * -1);
        } else {
            ad.getScore().increase(imageRuleScore);
        }
    }

    public void setRelevance(Ad ad) {
        if (ad.isRelevant()) {
            ad.setIrrelevantSince(new IrrelevantSinceVO());
            return;
        }
        ad.setIrrelevantSince(new IrrelevantSinceVO(new Date()));

    }

    @Override
    public void calculate(Ad ad) {
        calculateDescriptionPoints(ad);
        calculateImagePoints(ad);
        calculateCompletenessPoints(ad);
        setRelevance(ad);
    }


}
