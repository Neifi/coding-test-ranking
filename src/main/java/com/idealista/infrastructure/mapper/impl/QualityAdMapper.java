package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.picture.Picture;
import com.idealista.domain.picture.UrlVO;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QualityAdMapper implements Mapper<QualityAd, Ad> {

    @Override
    public QualityAd mapToDestination(Ad source) {
        QualityAd qualityAd = new QualityAd();
        qualityAd.setId(source.getId());
        qualityAd.setTypology(source.getTypology().name());
        qualityAd.setDescription(source.getDescription().getText());
        qualityAd.setPictureUrls(source.getPictures().stream()
                .map(Picture::getUrl)
                .map(UrlVO::value)
                .collect(Collectors.toList()));
        qualityAd.setHouseSize(source.getHouseSize().value());
        qualityAd.setGardenSize(source.getGardenSize().value());
        qualityAd.setScore(source.getScore().value());
        qualityAd.setIrrelevantSince(source.getIrrelevantSince().getDate());
        return qualityAd;
    }

    @Override
    public Ad mapToSource(QualityAd source) {
        return null;
    }
}
