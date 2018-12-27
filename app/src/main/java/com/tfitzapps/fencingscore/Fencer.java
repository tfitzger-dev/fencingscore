package com.tfitzapps.fencingscore;

/**
 * Created by fitzt on 10/26/2018.
 */

public class Fencer {
    boolean carded;
    int score;

    public boolean isCarded() {
        return carded;
    }

    public void setCarded() {
        this.carded = true;
    }

    public int getScore(){
        return score;
    }

    public void addPoint(){
        score++;
    }
}
