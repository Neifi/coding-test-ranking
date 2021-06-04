package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.picture.UrlVO;
import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.mapper.Mapper;

import java.util.stream.Collectors;

public class PublicAdMapper implements Mapper<PublicAd, Ad> {

    @Override
    public PublicAd mapToDestination(Ad source) {
        PublicAd qualityAd = new PublicAd();
        qualityAd.setId(source.getId());
        qualityAd.setTypology(source.getTypology().name());
        qualityAd.setDescription(source.getDescription().getText());
        qualityAd.setPictureUrls(source.getPictures().stream()
                .map(PictureVO::getUrl)
                .map(UrlVO::value)
                .collect(Collectors.toList()));
        qualityAd.setHouseSize(source.getHouseSize().value());
        qualityAd.setGardenSize(source.getGardenSize().value());
        return qualityAd;
    }

    @Override
    public Ad mapToSource(PublicAd source) {
        return null;
    }
}
