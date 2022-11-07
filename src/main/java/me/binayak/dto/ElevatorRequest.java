package me.binayak.dto;

public class ElevatorRequest {
    private int currentFloor;
    private int destinationFloor;
    private int numberOfPassengers;
    private int estimatedTimeOfArrival;

    public ElevatorRequest(int currentFloor, int destinationFloor, int numberOfPassengers) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }

    public void setEstimatedTimeOfArrival(int estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }
}
