package com.idealista.application;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.ad.TypologyVO;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.QualityVO;

public class ComputeAdPoints {

    public void compute(AdVO ad) {

    }

    public void computeCompleteness(AdVO ad) {
        if(TypologyVO.PISO.equals(ad.getTypology())) {
            if (checkCompleteness(ad)) {
                ad.getScore().increase(40);
            }
        }
        if(TypologyVO.CHALET.equals(ad.getTypology())) {
            if (ad.getDescription().getWords().size() > 0
                    && !ad.getPictures().isEmpty()
                    && !ad.getHouseSize().isEmpty()
                    && !ad.getGardenSize().isEmpty()
            ) {
                ad.getScore().increase(40);
            }
        }
        if(TypologyVO.GARAJE.equals(ad.getTypology())) {
            if (ad.getPictures() != null && ad.getHouseSize().value() > 0
            ) {
                ad.getScore().increase(40);
            }
        }
    }

    private boolean checkCompleteness(AdVO ad) {
        boolean isComplete = !ad.getDescription().getWords().isEmpty()
                    && !ad.getPictures().isEmpty()
                    && !ad.getHouseSize().isEmpty();


        if(TypologyVO.CHALET.equals(ad.getTypology())){
            return isComplete && !ad.getGardenSize().isEmpty();
        }

        if(TypologyVO.GARAJE.equals(ad.getTypology())){
            return ad.getPictures().isEmpty() && !ad.getHouseSize().isEmpty();
        }

        return isComplete;
    }

    public void computeDescriptionScore(AdVO ad) {
        int descriptionLength = ad.getDescription().getWords().size();

        if (descriptionLength > 0) {

            int initialSize = 20;
            int endSize = 49;
            ad.getScore().increase(5);

            if (TypologyVO.PISO.equals(ad.getTypology()) && descriptionWordSizeIsBetween(descriptionLength, initialSize, endSize)) {
                ad.getScore().increase(10);

            }
            if (TypologyVO.PISO.equals(ad.getTypology()) && descriptionWordSizeIsGreaterThan(descriptionLength, 50)) {
                ad.getScore().increase(30);
            }

            if (TypologyVO.CHALET.equals(ad.getTypology()) && descriptionWordSizeIsGreaterThan(descriptionLength, 51)) {
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

    private boolean descriptionWordSizeIsGreaterThan(int descriptionLength, int maxSize) {

        return descriptionLength >= maxSize;
    }

    private boolean descriptionWordSizeIsBetween(int descriptionLength, int initialSize, int endSize) {
        return descriptionLength >= initialSize && descriptionLength <= endSize;
    }

    public void computeImageScore(AdVO ad) {
        if (ad.getPictures().isEmpty()) {
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
