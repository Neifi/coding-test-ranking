package com.idealista.domain.rating.rules;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.ad.TypologyVO;

import java.util.List;

public class DescriptionRule {

    public void check(AdVO ad) {
        int descriptionLength = ad.getDescription().getWords().size();
        if (descriptionLength > 0) {
            List<String> descriptionWords = ad.getDescription().getWords();

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
            long words = getDescriptionKeywords(descriptionWords);

            ad.getScore().increase(5 * (int)words);


        }
    }

    private long getDescriptionKeywords(List<String> descriptionWords) {
        return descriptionWords.stream().filter(w -> w.equalsIgnoreCase("Luminoso") ||
                w.equalsIgnoreCase("Nuevo")
                || w.equalsIgnoreCase("Céntrico")
                || w.equalsIgnoreCase("Reformado")
                || w.equalsIgnoreCase("Ático")).count();
    }

    private boolean descriptionWordSizeIsGreaterThan(int descriptionLength, int maxSize) {

        return descriptionLength >= maxSize;
    }

    private boolean descriptionWordSizeIsBetween(int descriptionLength, int initialSize, int endSize) {
        return descriptionLength >= initialSize && descriptionLength <= endSize;
    }
}
