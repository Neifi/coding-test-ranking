package com.idealista.domain.repository;

import com.idealista.domain.pictureEntity.Picture;

import java.util.List;

public interface PictureRepository {
    List<Picture> findAll();
    List<Picture> findAll(List<Integer> ids);
    Picture findById(Integer id);
}
