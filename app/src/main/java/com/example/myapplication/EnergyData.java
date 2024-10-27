package com.example.myapplication;

public class EnergyData {
    private String electricity;
    private String gas;
    private String water;

    // Empty constructor required for Firebase
    public EnergyData() {
    }

    public EnergyData(String electricity, String gas, String water) {
        this.electricity = electricity;
        this.gas = gas;
        this.water = water;
    }

    // Getters
    public String getElectricity() {
        return electricity;
    }

    public String getGas() {
        return gas;
    }

    public String getWater() {
        return water;
    }

    // Setters (if needed)
    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public void setWater(String water) {
        this.water = water;
    }
}
