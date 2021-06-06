package com.idealista.application;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.repository.AdRepository;
import com.idealista.domain.services.PointsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculateAdScore {

    @Autowired
    PointsCalculator pointsCalculator;

    public void calculate(Ad ad) {
        pointsCalculator.calculate(ad);
    }
}
