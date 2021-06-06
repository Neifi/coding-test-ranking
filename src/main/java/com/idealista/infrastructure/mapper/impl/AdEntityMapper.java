package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.ad.*;
import com.idealista.domain.picture.Picture;
import com.idealista.infrastructure.mapper.Mapper;
import com.idealista.infrastructure.persistence.model.AdEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AdEntityMapper implements Mapper<AdEntity, Ad> {

    private List<Picture> pictures;

    @Override
    public AdEntity mapToDestination(Ad source) {
        return AdEntity.builder()
                .id(source.getId())
                .typology(source.getTypology().name())
                .description(source.getDescription().getText())
                .pictureUrls(source.getPictures().stream()
                        .map(Picture::getId)
                        .collect(Collectors.toList()))
                .houseSize(source.getHouseSize().value())
                .gardenSize(source.getGardenSize().value())
                .score(source.getScore().value())
                .irrelevantSince(source.getIrrelevantSince().getDate())
                .build();
    }

    @Override
    public Ad mapToSource(AdEntity source) {

        Ad ad = new Ad(
                source.getId(),
                Typology.valueOf(source.getTypology()),
                new DescriptionVO(source.getDescription()),
                new HouseSizeVO(source.getHouseSize()),
                new GardenSizeVO(source.getGardenSize()),
                new Score(source.getScore()),
                pictures

        );
        ad.setIrrelevantSince(new IrrelevantSinceVO(source.getIrrelevantSince()));
        return ad;
    }



    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
