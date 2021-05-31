package com.idealista.application;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.ad.TypologyVO;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.QualityVO;

public class ComputeAdPoints {

    public void compute(AdVO ad) {
        computeImageScore(ad);
        computeDescriptionScore(ad);
    }

    private void computeDescriptionScore(AdVO ad) {
        int descriptionLength = ad.getDescription().getWords().size();

        if (descriptionLength > 0) {

            int initialSize = 20;
            int endSize = 49;
            ad.getScore().increase(5);

            if (TypologyVO.PISO.equals(ad.getTypology()) && descriptionSize(descriptionLength, initialSize, endSize)) {
                ad.getScore().increase(10);

            }
            if (TypologyVO.PISO.equals(ad.getTypology()) && descriptionTextSizeIsGreaterThan(descriptionLength, 50)) {
                ad.getScore().increase(30);
            }

            if (TypologyVO.CHALET.equals(ad.getTypology()) && descriptionTextSizeIsGreaterThan(descriptionLength, 51)) {
                ad.getScore().increase(20);
            }

            //TODO keyword repository?
            long words = ad.getDescription().getWords().stream().filter(w -> w.equalsIgnoreCase("Luminoso") ||
                            w.equalsIgnoreCase("Nuevo")
                            || w.equalsIgnoreCase("Céntrico")
                            || w.equalsIgnoreCase("Reformado")
                            || w.equalsIgnoreCase("Ático")).count();

            ad.getScore().increase(5 * (int)words);


        }
    }

    private boolean descriptionTextSizeIsGreaterThan(int descriptionLength, int maxSize) {

        return descriptionLength >= maxSize;
    }

    private boolean descriptionSize(int descriptionLength, int initialSize, int endSize) {
        return descriptionLength >= initialSize && descriptionLength <= endSize;
    }

    private void computeImageScore(AdVO ad) {
        if (ad.getPictures() == null) {
            ad.getScore().decrease(10);
            return;
        }
        for (PictureVO picture : ad.getPictures()) {
            if (QualityVO.HD.equals(picture.getQuality())) {
                ad.getScore().increase(20);
            } else {
                ad.getScore().increase(10);

            }
        }
    }
}
