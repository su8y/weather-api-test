package com.example.geomweatherapi.interfaces.rest.model;

public class Weather {
    private int amRainyPercentage;
    private int pmRainyPercentage;
    private String PTV;
    private String amSkyCondition;
    private String pmSkyCondition;
    private int minTemperature;
    private int maxTemperature;

    public int getAmRainyPercentage() {
        return amRainyPercentage;
    }

    public void setAmRainyPercentage(int amRainyPercentage) {
        this.amRainyPercentage = amRainyPercentage;
    }

    public int getPmRainyPercentage() {
        return pmRainyPercentage;
    }

    public void setPmRainyPercentage(int pmRainyPercentage) {
        this.pmRainyPercentage = pmRainyPercentage;
    }

    public String getPTV() {
        return PTV;
    }

    public void setPTV(String PTV) {
        this.PTV = PTV;
    }

    public String getAmSkyCondition() {
        return amSkyCondition;
    }

    public void setAmSkyCondition(String amSkyCondition) {
        this.amSkyCondition = amSkyCondition;
    }

    public String getPmSkyCondition() {
        return pmSkyCondition;
    }

    public void setPmSkyCondition(String pmSkyCondition) {
        this.pmSkyCondition = pmSkyCondition;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
