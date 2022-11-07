package me.binayak.service;

public class SimulationClock {

    private static int currentTime;

    //constructor
    public SimulationClock() {
        currentTime = 0;
    }

    //increment the currentTime
    public static void tick() {
        currentTime++;
    }

    //getter method for current time
    public static int getTime() {
        return currentTime;
    }
}
