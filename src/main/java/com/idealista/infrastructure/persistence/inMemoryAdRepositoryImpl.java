package com.idealista.infrastructure.persistence;

import com.idealista.domain.ad.Ad;
import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.repository.AdRepository;
import com.idealista.domain.repository.PictureRepository;
import com.idealista.infrastructure.mapper.impl.AdEntityMapper;
import com.idealista.infrastructure.persistence.model.AdEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class inMemoryAdRepositoryImpl implements AdRepository {

    private final InMemoryDB inMemoryDB = InMemoryDB.getInstance();

    private final AdEntityMapper mapper = new AdEntityMapper();

    private PictureRepository pictureRepository = new InMemoryPictureRepository();

    @Override
    public List<Ad> findAll() {
        List<PictureVO>pictureVOS = new ArrayList<>();
        List<AdEntity> ads = inMemoryDB.getAd();
        List<Ad> adList = new ArrayList<>();
        for (AdEntity ad:ads) {
         pictureVOS = pictureRepository.findAll(ad.getPictureUrls());
         mapper.setPictures(pictureVOS);
         adList.add(mapper.mapToSource(ad));
        }

        return adList;
    }

    @Override
    public void saveAll(List<Ad> ads) {
        inMemoryDB.saveAll(ads.stream()
                .map(mapper::mapToDestination)
                .collect(Collectors.toList()));
    }

}
