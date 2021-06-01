package com.idealista.domain.ad;

import com.idealista.domain.picture.PictureVO;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
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
    private IrrelevantSinceVO irrelevantSince;

    public AdVO(Integer id, TypologyVO typology, DescriptionVO description, HouseSizeVO houseSize, GardenSizeVO gardenSize, ScoreVO score, PictureVO ...pictures) {
        if(pictures.length == 0){
            this.pictures = Collections.emptyList();
        }
        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictures = Arrays.asList(pictures);
        this.houseSize = houseSize;
        this.gardenSize = gardenSize;
        this.score = score;

    }
    public AdVO(Integer id, TypologyVO typology, DescriptionVO description, HouseSizeVO houseSize, ScoreVO score, PictureVO ...pictures) {
        if(pictures.length == 0){
            this.pictures = Collections.emptyList();
        }
        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictures = Arrays.asList(pictures);
        this.houseSize = houseSize;
        this.gardenSize = new GardenSizeVO(0L);
        this.score = score;

    }

    public void setIrrelevantSince(IrrelevantSinceVO irrelevantSince) {
        this.irrelevantSince = irrelevantSince;
    }
}
