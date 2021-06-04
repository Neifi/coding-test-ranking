package com.idealista.infrastructure.persistence;

import com.idealista.domain.picture.PictureVO;
import com.idealista.domain.repository.PictureRepository;
import com.idealista.infrastructure.mapper.Mapper;
import com.idealista.infrastructure.mapper.impl.PictureEntityMapper;
import com.idealista.infrastructure.persistence.model.Picture;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@NoArgsConstructor
public class InMemoryPictureRepository implements PictureRepository {

    private final InMemoryDB inMemoryDB = InMemoryDB.getInstance();
    private Mapper<Picture,PictureVO> mapper = new PictureEntityMapper();

    @Override
    public List<PictureVO> findAll() {
        return this.inMemoryDB
                .getPictures()
                .stream()
                .map(mapper::mapToSource)
                .collect(Collectors.toList());
    }

    @Override
    public List<PictureVO> findAll(List<Integer> ids) {
        return inMemoryDB
                .getPictures()
                .stream()
                .filter(pic -> ids.stream()
                .anyMatch(id -> pic.getId() == id))
                .map(mapper::mapToSource)
                .collect(Collectors.toList());

    }

    @Override
    public PictureVO findById(Integer id) {
        return this.inMemoryDB.
                getPictures()
                .stream()
                .filter(picture -> picture.getId() == id)
                .map(mapper::mapToSource)
                .collect(Collectors.toList()).get(0);
    }


}
