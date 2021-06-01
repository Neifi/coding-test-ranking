package com.idealista.domain.rating.rules;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.ad.TypologyVO;

public class CompletenessRule {

    public void check(AdVO ad) {
        computeApartmentCompleteness(ad);
        computeChaletCompleteness(ad);
        computeGarageCompleteness(ad);
    }

    private void computeGarageCompleteness(AdVO ad) {
        if(TypologyVO.GARAJE.equals(ad.getTypology())) {
            if (ad.getPictures() != null && ad.getHouseSize().value() > 0
            ) {
                ad.getScore().increase(40);
            }
        }
    }

    private void computeChaletCompleteness(AdVO ad) {
        if(TypologyVO.CHALET.equals(ad.getTypology())) {
            if (ad.getDescription().getWords().size() > 0
                    && !ad.getPictures().isEmpty()
                    && !ad.getHouseSize().isEmpty()
                    && !ad.getGardenSize().isEmpty()
            ) {
                ad.getScore().increase(40);
            }
        }
    }

    private void computeApartmentCompleteness(AdVO ad) {
        if(TypologyVO.PISO.equals(ad.getTypology())) {
            if (checkCompleteness(ad)) {
                ad.getScore().increase(40);
            }
        }
    }

    private boolean checkCompleteness(AdVO ad) {
        boolean isComplete = !ad.getDescription().isEmpty()
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

}
