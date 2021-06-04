package com.idealista.application.services;

import com.idealista.domain.ad.Ad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankingService {

    List<Ad> getRelevantAds();
    List<Ad> getIrelevantAds();
    void calculateScore(List<Ad> ads);
}
