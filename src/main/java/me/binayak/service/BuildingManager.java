package me.binayak.service;

public class BuildingManager {

    private int numberOfFloors = 11;
    private BuildingFloor[] floors;
    private boolean goUp;

    // Constructor
    public BuildingManager() {
        floors = new BuildingFloor[numberOfFloors];
        for (int i=0; i<numberOfFloors; i++) {
            floors[i] = new BuildingFloor(numberOfFloors);
        }
        goUp = false;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    // Update passenger request by setter method in Building floor
    public synchronized void updatePassengerRequest(int start, int dest, int numOfPassenRequest) {
        floors[start].setPassengerRequests(dest, numOfPassenRequest);
        floors[start].setTotalNumDestRequests(dest, numOfPassenRequest);
    }

    // Update arrived passengers by setter method in Building floor
    public synchronized void updateArrivedPassengers(int floor, int start, int arrivedPassenger) {
        floors[floor].setArrivedPassengers(start, arrivedPassenger);
    }

    // Check if there are passengers request for elevator; return the destination floor if there is, return -1 if no requests.
    public synchronized int checkForPassengerRequests(int elevatorID) {
        for (int floor=0; floor<numberOfFloors; floor++) {
            for (int dest=0; dest<numberOfFloors; dest++) {
                if (floors[floor].getPassengerRequests(dest) > 0 && floors[floor].getApproachingElevator() == -1) {
                    floors[floor].setApproachingElevator(elevatorID);
                    return floor;
                }
            }
        }
        return -1;
    }

    // Return true if the elevator goes up, else goes down
    public synchronized boolean checkUpDown() {
        return goUp;
    }

    // Load passengers in a floor and save their destination information in an array
    public synchronized int[] pickUpPassengers(int floor) {
        int[] destinations = new int[10];
        goUp = false;
        for (int dest=floor; dest<10; dest++) {
            if (floors[floor].getPassengerRequests(dest) > 0) {
                destinations[dest] = floors[floor].getPassengerRequests(dest);
                floors[floor].setPassengerRequests(dest, 0);
                goUp = true;
            }
        }
        if (!goUp) {
            for (int dest=floor; dest>=0; dest--) {
                if (floors[floor].getPassengerRequests(dest) > 0) {
                    destinations[dest] = floors[floor].getPassengerRequests(dest);
                    floors[floor].setPassengerRequests(dest, 0);
                }
            }
        }
        floors[floor].setApproachingElevator(-1);
        return destinations;
    }
}
