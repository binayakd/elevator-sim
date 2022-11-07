package me.binayak.service;

import me.binayak.dto.InputData;
import me.binayak.dto.ElevatorRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    public InputData readInputDataFromFile(String filePath) throws FileNotFoundException {

        InputData inputData = new InputData();
        Scanner scanner;
        if (filePath != null) {
            scanner = new Scanner(new File(filePath));
        } else {
            // read default file if filepath not provided
            String defaultFilePath = "ElevatorConfig.txt";
            scanner = new Scanner(new File(defaultFilePath));
        }
        // read simulation time
        inputData.setSimulationTime(Integer.parseInt(scanner.nextLine()));
        // read simulation rate
        inputData.setSimulationRate(Integer.parseInt(scanner.nextLine()));

        // read the passenger information line by line
        while (scanner.hasNextLine()) {
            String[] s = scanner.nextLine().split("\\s+");
            inputData.getElevatorRequestList().add(
                    new ElevatorRequest(
                            // current floor, set to 0 if the string values is G
                            s[0].equalsIgnoreCase("G") ? 0 : Integer.parseInt(s[0]),
                            // Destination floor
                            s[1].equalsIgnoreCase("G") ? 0 : Integer.parseInt(s[1]),
                            // number of passengers
                            Integer.parseInt(s[2])
                    )
            );
        }
        scanner.close();

        return inputData;
    }

}
