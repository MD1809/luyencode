package com.finalproject;

/**
 * DiscreteSignal
 */
public class DiscreteSignal implements Signal{
    private double amplitude;
    private double frequency;
    private double period;
    private double wavelength;

    public DiscreteSignal(double amplitude, double frequency, double period, double wavelength){
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.period = period;
        this.wavelength = wavelength;
    }

    public void displayInfo(){
        System.out.println("Discrete Signal Information:");
        System.out.println("Amplitude: " + amplitude);
        System.out.println("Frequency: " + frequency);
        System.out.println("Period: " + period);
        System.out.println("Wavelength: " + wavelength);
    }
}