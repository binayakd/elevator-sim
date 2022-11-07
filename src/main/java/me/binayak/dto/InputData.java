package me.binayak.dto;

import java.util.ArrayList;
import java.util.List;

public class InputData {

    private int simulationTime;
    private int simulationRate;

    private List<ElevatorRequest> elevatorRequestList = new ArrayList<>();

    public int getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
    }

    public int getSimulationRate() {
        return simulationRate;
    }

    public void setSimulationRate(int simulationRate) {
        this.simulationRate = simulationRate;
    }

    public List<ElevatorRequest> getElevatorRequestList() {
        return elevatorRequestList;
    }

    public void setElevatorRequestList(List<ElevatorRequest> elevatorRequestList) {
        this.elevatorRequestList = elevatorRequestList;
    }
}
