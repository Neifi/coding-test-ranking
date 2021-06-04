package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.ad.*;
import com.idealista.domain.picture.PictureVO;
import com.idealista.infrastructure.mapper.Mapper;
import com.idealista.infrastructure.persistence.model.Ad;

import java.util.List;
import java.util.stream.Collectors;

public class AdEntityMapper implements Mapper<Ad,AdVO> {

    private List<PictureVO> pictures;

    @Override
    public Ad mapToDestination(AdVO source) {
        return Ad.builder()
                .id(source.getId())
                .typology(source.getTypology().name())
                .description(source.getDescription().getText())
                .pictureUrls(source.getPictures().stream()
                        .map(PictureVO::getId)
                        .collect(Collectors.toList()))
                .houseSize(source.getHouseSize().value())
                .gardenSize(source.getGardenSize().value())
                .score(source.getScore().value())
                .irrelevantSince(source.getIrrelevantSince().getDate())
                .build();
    }

    @Override
    public AdVO mapToSource(Ad source) {

        AdVO adVO = new AdVO(
                source.getId(),
                Typology.valueOf(source.getTypology()),
                new DescriptionVO(source.getDescription()),
                new HouseSizeVO(source.getHouseSize()),
                new GardenSizeVO(source.getGardenSize()),
                new Score(source.getScore()),
                pictures

        );
        return adVO;
    }



    public void setPictures(List<PictureVO> pictures) {
        this.pictures = pictures;
    }
}
