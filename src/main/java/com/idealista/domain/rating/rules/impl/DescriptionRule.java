package com.idealista.domain.rating.rules.impl;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.ad.Typology;
import com.idealista.domain.rating.rules.RatingRule;

import java.util.List;

public class DescriptionRule implements RatingRule {

    private final int HAS_DESCRIPTION_VALUE = 5;
    private final int DESCRIPTION_BETWEEN_RANGE_APARTMENT_VALUE = 10;
    private final int DESCRIPTION_GREATER_THAN_RANGE_APARTMENT_VALUE = 30;
    private final int DESCRIPTION_GREATER_THAN_RANGE_CHALET_VALUE = 20;
    private final int KEYWORD_MACHT_VALUE = 5;
    final int INITIAL_RANGE = 20;
    final int END_RANGE = 49;

    private int totalScore = 0;

    @Override
    public int getScore(Ad ad) {

        if (!ad.getDescription().isEmpty()) {
            List<String> descriptionWords = ad.getDescription().getWords();
            int descriptionLength = ad.getDescription().getWords().size();


            totalScore += HAS_DESCRIPTION_VALUE;

            if (Typology.FLAT.equals(ad.getTypology()) && descriptionWordSizeIsBetween(descriptionLength, INITIAL_RANGE, END_RANGE)) {
                totalScore += DESCRIPTION_BETWEEN_RANGE_APARTMENT_VALUE;

            }

            if (Typology.FLAT.equals(ad.getTypology()) && descriptionWordSizeIsGreaterThan(descriptionLength, 50)) {
                totalScore += DESCRIPTION_GREATER_THAN_RANGE_APARTMENT_VALUE;
            }

            if (Typology.CHALET.equals(ad.getTypology()) && descriptionWordSizeIsGreaterThan(descriptionLength, 51)) {
                totalScore += DESCRIPTION_GREATER_THAN_RANGE_CHALET_VALUE;
            }

            //TODO keyword repository?
            addDescriptionKeywordPoints(descriptionWords);

            return totalScore;
        }

        return totalScore;
    }

    private void addDescriptionKeywordPoints(List<String> descriptionWords) {
        long words = getDescriptionKeywords(descriptionWords);

        totalScore += KEYWORD_MACHT_VALUE * words;
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
