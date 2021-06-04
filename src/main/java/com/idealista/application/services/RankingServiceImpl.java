package com.idealista.application.services;


import com.idealista.application.ListIrrelevantAds;
import com.idealista.application.ListRelevantAds;
import com.idealista.domain.ad.Ad;
import com.idealista.domain.services.PointsCalculator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class RankingServiceImpl implements RankingService{

    @Autowired
    private PointsCalculator pointsCalculator;

    @Autowired
    private ListRelevantAds listRelevantAds;

    @Autowired
    private ListIrrelevantAds listIrrelevantAds;

    @Override
    public List<Ad> getRelevantAds() {
        return listRelevantAds.getAdsInOrder();
    }

    @Override
    public List<Ad> getIrelevantAds() {
        return listIrrelevantAds.getAds();
    }

    @Override
    public void calculateScore(List<Ad> ads) {
        for (Ad ad:ads) {
            pointsCalculator.calculate(ad);
        }
    }


}
