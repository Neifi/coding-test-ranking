package com.idealista.infrastructure.api;

import com.idealista.application.services.RankingService;
import com.idealista.domain.ad.AdVO;
import com.idealista.infrastructure.mapper.Mapper;
import com.idealista.infrastructure.mapper.impl.PublicAdMapper;
import com.idealista.infrastructure.mapper.impl.QualityAdMapper;
import com.idealista.infrastructure.services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/ad")
public class AdsController {


    @Autowired
    RankingService rankingService;

    @Autowired
    public AdService adsService;

    Mapper<QualityAd, AdVO> qualityAdMapper = new QualityAdMapper();
    Mapper<PublicAd, AdVO> publicAdMapper = new PublicAdMapper();

    @GetMapping("/quality")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        List<AdVO> irelevantAds = rankingService.getIrelevantAds();

        if (irelevantAds.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(
                irelevantAds
                        .stream().map(qualityAdMapper::mapToDestination)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/public")
    public ResponseEntity<List<PublicAd>> publicListing() {
        List<AdVO> relevantAds = rankingService.getRelevantAds();

        if (relevantAds.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(
                relevantAds
                        .stream().map(publicAdMapper::mapToDestination)
                        .collect(Collectors.toList()));
    }

    @PostMapping("/calculate")
    public ResponseEntity<Void> calculateScore() {
        adsService.performAdRank();
        return ResponseEntity.ok().build();
    }
}
