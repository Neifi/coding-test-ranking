package com.idealista.domain.rating.rules.impl;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.QualityVO;
import com.idealista.domain.rating.rules.RatingRule;

public class ImageRule implements RatingRule {
    private final int HD_IMAGE_POINTS = 20;
    private final int NO_HD_IMAGE_POINTS = 10;
    private final int NO_MAGE_POINTS = 10;


    private int totalPoints = 0;

    public int getScore(AdVO ad) {
        if (ad.getPictures().isEmpty()) {
                totalPoints -= NO_MAGE_POINTS;
            return totalPoints;
        }
        for (PictureVO picture : ad.getPictures()) {
            if (QualityVO.HD.equals(picture.getQuality())) {
                totalPoints += HD_IMAGE_POINTS;
            } else {
                totalPoints += NO_HD_IMAGE_POINTS;
            }
        }

        return totalPoints;
    }
}
