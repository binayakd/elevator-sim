package me.binayak;


import me.binayak.dto.InputData;
import me.binayak.service.FileReader;
import me.binayak.service.SimulationManager;

import java.io.FileNotFoundException;

public class Main {

    private static final FileReader fileReader = new FileReader();
    private static final SimulationManager simulationManager = new SimulationManager();

    public static void main( String[] args ) throws FileNotFoundException, InterruptedException {
        System.out.println("Starting Simulation");

        System.out.println("Reading input file");
        InputData inputData;
        if (args.length > 0 ) {
            inputData = fileReader.readInputDataFromFile(args[0]);
        } else {
            inputData = fileReader.readInputDataFromFile(null);
        }


        simulationManager.startSimulation(inputData);
    }
}
