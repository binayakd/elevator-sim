package me.binayak.service;

import me.binayak.dto.ElevatorEvent;
import me.binayak.dto.ElevatorRequest;

import java.util.ArrayList;
import java.util.List;

public class Elevator implements Runnable {

    private final int floorTravelTime = 3;
    private final int elevatorLoadTime = 5;

    private int elevatorId;
    private BuildingManager buildingManager;

    private int currentFloor = 0;
    private int numPassengers = 0;
    List<ElevatorEvent> elevatorEventList = new ArrayList<>();
    int[] destinations ;

    public Elevator(int elevatorID, BuildingManager buildingManager) {
        this.elevatorId = elevatorID;
        this.buildingManager = buildingManager;
        this.destinations = new int[buildingManager.getNumberOfFloors()];
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (numPassengers == 0 && elevatorEventList.isEmpty()) {
                // elevator is empty with no requests, look for more
                int dest = buildingManager.checkForPassengerRequests(elevatorId);
                if (dest != -1) {
                    // Elevator travels to destination floor and load passengers
                    // estimated time to destination = current time + abs(currentFloor - destinationFloor) * floorTravelTime + elevatorLoadTime
                    int estimatedTimeToDestination = SimulationClock.getTime() + Math.abs(currentFloor - dest) * floorTravelTime + elevatorLoadTime;
                    elevatorEventList.add(new ElevatorEvent(dest, estimatedTimeToDestination));

                    System.out.printf("Time: [%d], Elevator: [%d], Current: [%d], To: [%d]%n", SimulationClock.getTime(), elevatorId, currentFloor, dest);
                }
            }
            if (numPassengers == 0 && !elevatorEventList.isEmpty()) {
                // Loading passengers
                ElevatorEvent event = elevatorEventList.get(0);
                if (SimulationClock.getTime() == event.getExpectedArrival()) { // Check if the elevator arrive the destination floor
                    currentFloor = event.getDestination();
                    destinations = buildingManager.pickUpPassengers(currentFloor);
                    if (buildingManager.checkUpDown()) {
                        for (int dest = 0; dest < destinations.length; dest++) { // Going up
                            if (destinations[dest] > 0) {
                                numPassengers += destinations[dest];
                                int estimatedTimeToDestination = SimulationClock.getTime() + Math.abs(currentFloor - dest) * floorTravelTime + elevatorLoadTime * elevatorEventList.size();
                                elevatorEventList.add(new ElevatorEvent(dest, estimatedTimeToDestination));
                                System.out.printf("Time: [%d], Elevator: [%d], Current: [%d], To: [%d], Passengers Loaded: [%d]%n",
                                        SimulationClock.getTime(), elevatorId, currentFloor, dest, destinations[dest]);
                            }
                        }
                    } else {
                        for (int dest = destinations.length - 1; dest>=0; dest--) { // Going down
                            if (destinations[dest] > 0) {
                                numPassengers += destinations[dest];
                                int estimatedTimeToDestination = SimulationClock.getTime() + Math.abs(currentFloor - dest) * floorTravelTime + elevatorLoadTime * elevatorEventList.size();
                                elevatorEventList.add(new ElevatorEvent(dest, estimatedTimeToDestination));
                                System.out.printf("Time: [%d], Elevator: [%d], Current: [%d], To: [%d], Passengers Loaded: [%d]%n",
                                        SimulationClock.getTime(), elevatorId, currentFloor, dest, destinations[dest]);
                            }
                        }
                    }
                    elevatorEventList.remove(0);
                }
            }
            if (numPassengers > 0 && !elevatorEventList.isEmpty()) { // Unloading passengers
                ElevatorEvent event = elevatorEventList.get(0);
                if (SimulationClock.getTime() == event.getExpectedArrival()) {
                    elevatorEventList.remove(0);
                    int arrivedPassengers = destinations[event.getDestination()];
                    buildingManager.updateArrivedPassengers(event.getDestination(), currentFloor, arrivedPassengers);
                    numPassengers -= arrivedPassengers;
                    currentFloor = event.getDestination();

                    System.out.printf("Time: [%d], Elevator: [%d], Current: [%d], Passengers Unloaded: [%d]%n",
                            SimulationClock.getTime(), elevatorId, currentFloor, arrivedPassengers);
                }
            }
        }

    }
}
