package com.finalproject;

public class Main {
    public static void main(String[] args) {
        Signal discreteSignal = new DiscreteSignal(5.0, 1000.0, 0.01, 0.3);
        Signal continuousSignal = new ContinuousSignal(3.5, 2000.0, 0.05, 0.15);

        discreteSignal.displayInfo();
        System.out.println();
        continuousSignal.displayInfo();
        System.out.println();

        Radar radar = new Radar();
        System.out.println("Signal X(4) = " + radar.Signal_analysis(4));

    }
}