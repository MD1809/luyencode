package com.finalproject;

/**
 * ContinuousSignal
 */

public class ContinuousSignal implements Signal{
    private double amplitude;
    private double frequency;
    private double period;
    private double wavelength;
    
    public ContinuousSignal(double amplitude, double frequency, double period, double wavelength){
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.period = period;
        this.wavelength = wavelength;
    }

    public void displayInfo(){
        System.out.println("Continuous Signal Information:");
        System.out.println("Amplitude: " + amplitude);
        System.out.println("Frequency: " + frequency);
        System.out.println("Period: " + period);
        System.out.println("Wavelength: " + wavelength);
    }
}