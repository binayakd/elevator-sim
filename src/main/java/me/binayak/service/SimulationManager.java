package me.binayak.service;

import me.binayak.dto.ElevatorRequest;
import me.binayak.dto.InputData;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {

    public void startSimulation(InputData inputData) throws InterruptedException {

        BuildingManager buildingManager = new BuildingManager();
        List<Thread> elevatorThreadList = new ArrayList<>();
        for (int i = 0; i < 3; i ++ ){
            Thread elevatorThread = new Thread(new Elevator(i, buildingManager));
            elevatorThread.start();
            elevatorThreadList.add(elevatorThread);
        }
        for (ElevatorRequest  elevatorRequest: inputData.getElevatorRequestList()) {
            buildingManager.updatePassengerRequest(
                    elevatorRequest.getCurrentFloor(),
                    elevatorRequest.getDestinationFloor(),
                    elevatorRequest.getNumberOfPassengers()
            );
        }
        while (SimulationClock.getTime() < inputData.getSimulationTime()) {
            SimulationClock.tick();
            Thread.sleep(inputData.getSimulationRate());
        }

        for (Thread thread: elevatorThreadList){
            thread.interrupt();
        }
    }
}
