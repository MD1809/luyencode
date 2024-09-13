package com.finalproject;

/**
 * Radar
 */
public class Radar {
    public double Signal_analysis(int n){
        if(n >= 0 && n <= 15){
            return 1 - (double)n/15;
        }else{
            return 0;
        }
    }
}