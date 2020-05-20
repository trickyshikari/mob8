package com.example.lab3;

public class Result {
    public int time;
    public int trueAns;
    public int falseAns;

    public Result(){

    }

    public Result(int time, int trueAns, int falseAns){
        this.time = time;
        this.trueAns = trueAns;
        this.falseAns = falseAns;
    }

    public int getTime(){ return time;}

    public int getTrueAns(){ return trueAns;}

    public int getFalseAns(){ return falseAns;}

}
