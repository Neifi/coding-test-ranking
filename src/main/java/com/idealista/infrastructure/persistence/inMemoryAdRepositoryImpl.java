package com.idealista.infrastructure.persistence;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.repository.AdRepository;
import com.idealista.domain.repository.PictureRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class inMemoryAdRepositoryImpl implements AdRepository {
    private final InMemoryDB inMemoryDB = InMemoryDB.getInstance();

    @Override
    public List<AdVO> findAll() {
        return inMemoryDB.getAds();
    }

    @Override
    public AdVO findById(Integer id) {
        return new AdVO();
    }

    //TODO crea los métodos que necesites
}
