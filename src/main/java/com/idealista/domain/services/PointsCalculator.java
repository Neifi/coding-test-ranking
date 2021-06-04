package com.idealista.domain.services;

import com.idealista.domain.ad.AdVO;
import org.springframework.stereotype.Component;


@Component
public interface PointsCalculator {

    AdVO calculate(AdVO ad);
}
