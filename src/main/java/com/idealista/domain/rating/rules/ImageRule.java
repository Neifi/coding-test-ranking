package com.idealista.domain.rating.rules;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.QualityVO;

public class ImageRule{
    private final int HD_IMAGE_POINTS = 20;
    private final int NO_HD_IMAGE_POINTS = 10;
    private final int NO_MAGE_POINTS = 10;

    public void check(AdVO ad) {
        if (ad.getPictures().isEmpty()) {
            ad.getScore().decrease(NO_MAGE_POINTS);
            return;
        }
        for (PictureVO picture : ad.getPictures()) {
            if (QualityVO.HD.equals(picture.getQuality())) {
                ad.getScore().increase(HD_IMAGE_POINTS);
            } else {
                ad.getScore().increase(NO_HD_IMAGE_POINTS);
            }
        }
    }
}
