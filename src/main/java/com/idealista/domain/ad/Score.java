package com.idealista.domain.ad;

public class Score {

    private final int MAX_SCORE = 100;

    private int score = 0;

    public Score(Integer value) {
        if (value==null) return;
        if(value < 0){
            throw new RuntimeException("score cannot be less than zero");
        }
        this.score = value;
    }

    public int value() {
        return score;
    }

    public void decrease(int value) {
        if(score >= value) {
            this.score -= value;
        }
    }

    public void increase(Integer value) {

        if(value < 0){
            throw new RuntimeException("value cannot be less than zero");
        }
        if(score == 100){
            return;
        }
        if(score+value > MAX_SCORE){
            this.score += (value-score);
            return;
        }

        this.score += value;

    }
}
