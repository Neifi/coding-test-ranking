package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.pictureEntity.Picture;
import com.idealista.domain.pictureEntity.QualityVO;
import com.idealista.domain.pictureEntity.UrlVO;
import com.idealista.infrastructure.mapper.Mapper;
import com.idealista.infrastructure.persistence.model.PictureEntity;

public class PictureEntityMapper implements Mapper<PictureEntity, Picture> {
    @Override
    public PictureEntity mapToDestination(Picture source) {
        return null;
    }

    @Override
    public Picture mapToSource(PictureEntity source) {
        return new Picture(
                source.getId(),
                new UrlVO(source.getUrl()),
                QualityVO.valueOf(source.getQuality())
        );
    }
}
