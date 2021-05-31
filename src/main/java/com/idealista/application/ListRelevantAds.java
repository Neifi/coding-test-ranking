package com.idealista.application;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.repository.AdRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ListRelevantAds {

    private final AdRepository adRepository;

    public ListRelevantAds(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public List<AdVO> getAdsInOrder() {

        List<AdVO> adVOList = adRepository.findAll().stream()
                .sorted(Comparator
                        .comparingInt(ad -> -ad.getScore().value()))
                .collect(Collectors.toList());
        return adVOList;
    }
}
