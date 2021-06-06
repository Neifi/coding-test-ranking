package com.idealista.infrastructure.mapper.impl;

import com.idealista.domain.picture.Picture;
import com.idealista.domain.picture.QualityVO;
import com.idealista.domain.picture.UrlVO;
import com.idealista.infrastructure.mapper.Mapper;

public class PictureEntityMapper implements Mapper<com.idealista.infrastructure.persistence.model.Picture, Picture> {
    @Override
    public com.idealista.infrastructure.persistence.model.Picture mapToDestination(Picture source) {
        return null;
    }

    @Override
    public Picture mapToSource(com.idealista.infrastructure.persistence.model.Picture source) {
        return new Picture(
                source.getId(),
                new UrlVO(source.getUrl()),
                QualityVO.valueOf(source.getQuality())
        );
    }
}
