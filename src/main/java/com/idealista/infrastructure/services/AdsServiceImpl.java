package com.idealista.infrastructure.services;

import com.idealista.application.services.RankingService;
import com.idealista.domain.ad.Ad;
import com.idealista.domain.repository.AdRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@NoArgsConstructor
@Service
public class AdsServiceImpl implements AdService{

    @Autowired
    private AdRepository repository;
    @Autowired
    private RankingService rankingService;

    @Override
    public void performAdRank(){
        List<Ad> all = repository.findAll();
        rankingService.calculateScore(all);
        repository.saveAll(all);
    }
}
