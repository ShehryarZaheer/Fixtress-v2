package com.example.ehsanullah.loginandregistration.ImageDetection;

/**
 * Created by SZ on 8/30/2017.
 */

public class FaceExpressions {
    String condition;
    double score;

    FaceExpressions(String condition, double score){
        this.condition = condition;
        this.score = score;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
