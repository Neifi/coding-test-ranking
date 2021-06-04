package com.idealista.application.services;

import com.idealista.domain.ad.AdVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankingService {

    List<AdVO> getRelevantAds();
    List<AdVO> getIrelevantAds();
    void calculateScore(List<AdVO> ads);
}
