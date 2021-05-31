package com.idealista.infrastructure.persistence;

import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.repository.PictureRepository;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryPictureRepository implements PictureRepository {

    private final InMemoryDB inMemoryDB = InMemoryDB.getInstance();

    @Override
    public List<PictureVO> findAll() {
        return this.inMemoryDB.getPictures();
    }

    @Override
    public PictureVO findById(Integer id) {
       // return inMemoryDB.getPictures().stream().filter(ad -> ad.getId().equals(id)).collect(Collectors.toList()).get(0);
        return null;
    }
}
