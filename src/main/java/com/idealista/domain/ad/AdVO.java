package com.idealista.domain.ad;

import com.idealista.domain.picture.PictureVO;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class AdVO {

    private Integer id;
    private TypologyVO typology;
    private DescriptionVO description;
    private List<PictureVO> pictures;
    private HouseSizeVO houseSize;
    private GardenSizeVO gardenSize;
    private ScoreVO score;
    private DateVO irrelevantSince;

    public AdVO() {}

    public AdVO(Integer id, TypologyVO typology, DescriptionVO description, HouseSizeVO houseSize, GardenSizeVO gardenSize, ScoreVO score, DateVO irrelevantSince, PictureVO ...pictures) {
        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictures = Arrays.asList(pictures);
        this.houseSize = houseSize;
        this.gardenSize = gardenSize;
        this.score = score;
        this.irrelevantSince = irrelevantSince;

    }
    public AdVO(Integer id, TypologyVO typology, DescriptionVO description, HouseSizeVO houseSize, GardenSizeVO gardenSize, ScoreVO score, DateVO irrelevantSince) {
        this.id = id;
        this.typology = typology;
        this.description = description;
        this.houseSize = houseSize;
        this.gardenSize = gardenSize;
        this.score = score;
        this.irrelevantSince = irrelevantSince;

    }

    public AdVO(Integer id, TypologyVO typology, DescriptionVO description, HouseSizeVO houseSize, ScoreVO score, DateVO irrelevantSince, PictureVO ...pictures) {
        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictures = Arrays.asList(pictures);
        this.houseSize = houseSize;
        this.score = score;
        this.irrelevantSince = irrelevantSince;

    }


}
