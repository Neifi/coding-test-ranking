package com.idealista.domain.ad;

import com.idealista.domain.picture.PictureVO;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class AdVO {

    private final int INVALID_THRESHOLD = 40;

    private Integer id;
    private Typology typology;
    private DescriptionVO description;
    private List<PictureVO> pictures = new ArrayList<>();
    private HouseSizeVO houseSize;
    private GardenSizeVO gardenSize;
    private Score score;
    private IrrelevantSinceVO irrelevantSince;

    public AdVO(Integer id, Typology typology, DescriptionVO description, HouseSizeVO houseSize, GardenSizeVO gardenSize, Score score, List<PictureVO> pictures) {

        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictures = pictures;
        this.houseSize = houseSize;
        this.gardenSize = gardenSize;
        this.score = score;
        this.irrelevantSince = new IrrelevantSinceVO();
    }

    public AdVO(Integer id, Typology typology, DescriptionVO description, HouseSizeVO houseSize, Score score, List<PictureVO> pictures) {

        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictures = pictures;
        this.houseSize = houseSize;
        this.gardenSize = new GardenSizeVO(0);
        this.score = score;
        this.irrelevantSince = new IrrelevantSinceVO();

    }

    public AdVO(Integer id, Typology typology, HouseSizeVO houseSize, Score score, List<PictureVO> pictures) {

        this.id = id;
        this.typology = typology;
        this.description = new DescriptionVO("");
        this.pictures = pictures;
        this.houseSize = houseSize;
        this.gardenSize = new GardenSizeVO(0);
        this.score = score;
        this.irrelevantSince = new IrrelevantSinceVO();

    }

    public void setIrrelevantSince(IrrelevantSinceVO irrelevantSince) {
        this.irrelevantSince = irrelevantSince;
    }

    public boolean isRelevant() {
        return score.value() > INVALID_THRESHOLD;
    }

    public boolean isComplete() {
        if (Typology.GARAGE.equals(this.getTypology())) {
            return isGarageComplete();
        }
        if (Typology.CHALET.equals(this.getTypology())) {
            return isChaletComplete();
        }
        if (Typology.FLAT.equals(this.getTypology())) {
            return isApartmentComplete();
        }

        return false;
    }

    private boolean isGarageComplete() {
        return !this.getPictures().isEmpty() && !this.getHouseSize().isEmpty();

    }

    private boolean isChaletComplete() {
        return !this.getDescription().isEmpty()
                && !this.getPictures().isEmpty()
                && !this.getHouseSize().isEmpty()
                && !this.getGardenSize().isEmpty();
    }

    private boolean isApartmentComplete() {
        return !this.getDescription().isEmpty()
                && !this.getPictures().isEmpty()
                && !this.getHouseSize().isEmpty();

    }

}
