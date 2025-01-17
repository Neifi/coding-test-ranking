package com.idealista.application;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListIrrelevantAds {

    @Autowired
    private AdRepository adRepository;

    public List<Ad> getAds() {
        return adRepository
                .findAll()
                .stream()
                .filter(ad -> !ad.isRelevant())
                .collect(Collectors.toList());
    }
}
