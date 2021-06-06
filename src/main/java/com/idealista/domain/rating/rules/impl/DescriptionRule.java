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
    private final int MAX_LENGTH = 50;
    private final int INITIAL_RANGE = 20;
    private final int END_RANGE = 49;
    private List<String> descriptionWords;
    private int descriptionLength;
    private int totalScore = 0;

    @Override
    public int getScore(Ad ad) {
        if (!ad.getDescription().isEmpty()) {
            totalScore += HAS_DESCRIPTION_VALUE;

            getWords(ad);
            getDescriptionLength(ad);
            addFlatScore(ad);
            addChaletScore(ad);
            addDescriptionKeywordScore();

            return totalScore;
        }

        return totalScore;
    }

    private void getWords(Ad ad) {
        this.descriptionWords = ad.getDescription().getWords();
    }

    private void getDescriptionLength(Ad ad) {
        this.descriptionLength = ad.getDescription().getWords().size();
    }


    private void addFlatScore(Ad ad) {
        boolean isFlat = Typology.FLAT.equals(ad.getTypology());

        if (isFlat && isWordLengthGreaterThanMaxSize(MAX_LENGTH)) {
            totalScore += DESCRIPTION_GREATER_THAN_RANGE_APARTMENT_VALUE;
        }
        if (isFlat && isDescLengthInRange(INITIAL_RANGE, END_RANGE)) {
            totalScore += DESCRIPTION_BETWEEN_RANGE_APARTMENT_VALUE;
        }
    }
    private boolean isDescLengthInRange( int initalRange, int endRange) {
        return descriptionLength >= initalRange && descriptionLength <= endRange;
    }

    private void addChaletScore(Ad ad) {
        boolean isChalet = Typology.CHALET.equals(ad.getTypology());
        if (isChalet && isWordLengthGreaterThanMaxSize(MAX_LENGTH+1)) {
            totalScore += DESCRIPTION_GREATER_THAN_RANGE_CHALET_VALUE;
        }
    }

    private boolean isWordLengthGreaterThanMaxSize(int maxSize) {

        return descriptionLength >= maxSize;
    }
    private void addDescriptionKeywordScore() {
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


}
