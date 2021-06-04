package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.UrlVO;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QualityAdMapper implements Mapper<QualityAd,AdVO> {

    @Override
    public QualityAd mapToDestination(AdVO source) {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setId(source.getId());
        qualityAd.setTypology(source.getTypology().name());
        qualityAd.setDescription(source.getDescription().getText());
        qualityAd.setPictureUrls(source.getPictures().stream()
                .map(PictureVO::getUrl)
                .map(UrlVO::value)
                .collect(Collectors.toList()));
        qualityAd.setHouseSize(source.getHouseSize().value());
        qualityAd.setGardenSize(source.getGardenSize().value());
        qualityAd.setScore(source.getScore().value());
        qualityAd.setIrrelevantSince(source.getIrrelevantSince().getDate());
        return qualityAd;
    }

    @Override
    public AdVO mapToSource(QualityAd source) {
        return null;
    }
}