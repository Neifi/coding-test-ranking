package com.idealista.domain.services;

import com.idealista.domain.ad.Ad;
import org.springframework.stereotype.Component;


@Component
public interface PointsCalculator {

    void calculate(Ad ad);
}
