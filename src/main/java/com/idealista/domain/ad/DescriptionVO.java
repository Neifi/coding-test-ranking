package com.idealista.domain.ad;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class DescriptionVO {

    private String text;
    private int descriptionLength;
    private List<String> words = new ArrayList<>();
    public DescriptionVO() {
        this.descriptionLength = 0;
    }

    public DescriptionVO(String desc) {
        if (desc.length() > 0) {
            this.words = Stream.of(desc)
                    .map(w -> w.split("(?U)[^\\p{Alpha}0-9']+"))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
            this.text = desc;
        }
        this.descriptionLength = desc.length();
    }


}
