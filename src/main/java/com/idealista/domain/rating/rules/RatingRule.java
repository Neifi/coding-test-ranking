package com.idealista.domain.rating.rules;

import com.idealista.domain.ad.Ad;

public interface RatingRule {

    int getScore(Ad ad);
}
