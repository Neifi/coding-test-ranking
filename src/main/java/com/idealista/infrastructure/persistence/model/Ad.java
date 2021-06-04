package com.idealista.infrastructure.persistence.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Ad {
    private Integer id;
    private String typology;
    private String description;
    private List<Integer> pictureUrls;
    private Integer houseSize;
    private Integer gardenSize;
    private Integer score;
    private Date irrelevantSince;

    public Ad(Integer id, String typology, String description, List<Integer> pictureUrls, Integer houseSize, Integer gardenSize, Integer score, Date irrelevantSince) {
        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictureUrls = pictureUrls;
        this.houseSize = houseSize;
        this.gardenSize = gardenSize;
        this.score = score;
        this.irrelevantSince = irrelevantSince;
    }

    public Ad(Integer id, String typology, String description, List<Integer> pictureUrls, Integer houseSize, Integer score, Date irrelevantSince) {
        this.id = id;
        this.typology = typology;
        this.description = description;
        this.pictureUrls = pictureUrls;
        this.houseSize = houseSize;
        this.score = score;
        this.irrelevantSince = irrelevantSince;
        this.gardenSize = 0;
    }

    public Ad(Integer id, String typology, List<Integer> pictureUrls, Integer houseSize, Integer gardenSize, Integer score, Date irrelevantSince) {
        this.id = id;
        this.typology = typology;
        this.pictureUrls = pictureUrls;
        this.houseSize = houseSize;
        this.gardenSize = gardenSize;
        this.score = score;
        this.irrelevantSince = irrelevantSince;
        this.gardenSize = 0;
        this.description = "";
    }
}


