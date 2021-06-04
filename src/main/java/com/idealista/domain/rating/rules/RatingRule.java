package com.idealista.domain.rating.rules;

import com.idealista.domain.ad.AdVO;

public interface RatingRule {

    int getScore(AdVO adVO);
}
