package com.idealista.application;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListRelevantAds {

    @Autowired
    private AdRepository adRepository;

    public List<Ad> getAdsInOrder() {

        return adRepository
                .findAll().stream()
                .filter(Ad::isRelevant)
                .sorted(Comparator
                        .comparingInt(ad -> -ad.getScore().value()))
                .collect(Collectors.toList());
    }
}
