package com.idealista.infrastructure.persistence;

import com.idealista.domain.ad.AdVO;
import com.idealista.domain.repository.AdRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class inMemoryAdRepositoryImpl implements AdRepository {
    private final InMemoryDB inMemoryDB = InMemoryDB.getInstance();

    @Override
    public List<AdVO> findAll() {
        return inMemoryDB.getAds();
    }

    @Override
    public AdVO findById(Integer id) {
        return null;
    }

    //TODO crea los métodos que necesites
}
