package com.idealista.application.services;


import com.idealista.application.ListIrrelevantAds;
import com.idealista.application.ListRelevantAds;
import com.idealista.domain.ad.AdVO;
import com.idealista.domain.services.PointsCalculator;
import com.idealista.domain.services.PointsCalculatorImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<AdVO> getRelevantAds() {
        return listRelevantAds.getAdsInOrder();
    }

    @Override
    public List<AdVO> getIrelevantAds() {
        return listIrrelevantAds.getAds();
    }

    @Override
    public void calculateScore(List<AdVO> ads) {
        for (AdVO ad:ads) {
            pointsCalculator.calculate(ad);
        }
    }


}
