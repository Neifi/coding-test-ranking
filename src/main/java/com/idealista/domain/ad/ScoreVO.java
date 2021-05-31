package com.idealista.domain.ad;

public class ScoreVO {

    private int score;

    public ScoreVO(int score) {
        if(score < 0){
            throw new RuntimeException("score cannot be less than zero");
        }
        this.score = score;
    }

    public int value() {
        return score;
    }

    public void decrease(int value) {
        if(score > value) {
            this.score -= value;
        }
    }

    public void increase(int value) {
        if(value < 0){
            throw new RuntimeException("value cannot be less than zero");
        }
        this.score += score;
    }
}
