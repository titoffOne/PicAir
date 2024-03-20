package com.titov.data;

public class Game {
    private String nameTeamOne;
    private int countPointsTeamOne;
    private String colorTeamOne;
    private String nameTeamTwo;
    private int countPointsTeamTwo;
    private String colorTeamTwo;
    private int timerValue;
    private int roundValue;

    public Game(String nameTeamOne, String colorTeamOne, String nameTeamTwo, String colorTeamTwo, int timerValue, int roundValue) {
        this.nameTeamOne = nameTeamOne;
        this.colorTeamOne = colorTeamOne;
        this.nameTeamTwo = nameTeamTwo;
        this.colorTeamTwo = colorTeamTwo;
        this.timerValue = timerValue;
        this.roundValue = roundValue;
    }

    public Game(){
        nameTeamOne = "Малевичи";
        countPointsTeamOne = 0;
        colorTeamOne = "#47DEFF";
        nameTeamTwo = "Пикассо";
        countPointsTeamTwo = 0;
        colorTeamTwo = "#FF4752";
        this.timerValue = 60;
        this.roundValue = 3;
    }

    public String getNameTeamOne() {
        return nameTeamOne;
    }

    public void setNameTeamOne(String nameTeamOne) {
        this.nameTeamOne = nameTeamOne;
    }

    public int getCountPointsTeamOne() {
        return countPointsTeamOne;
    }

    public void setCountPointsTeamOne(int countPointsTeamOne) {
        this.countPointsTeamOne = countPointsTeamOne;
    }

    public int getCountPointsTeamTwo() {
        return countPointsTeamTwo;
    }

    public void setCountPointsTeamTwo(int countPointsTeamTwo) {
        this.countPointsTeamTwo = countPointsTeamTwo;
    }

    public String getColorTeamOne() {
        return colorTeamOne;
    }

    public void setColorTeamOne(String colorTeamOne) {
        this.colorTeamOne = colorTeamOne;
    }

    public String getNameTeamTwo() {
        return nameTeamTwo;
    }

    public void setNameTeamTwo(String nameTeamTwo) {
        this.nameTeamTwo = nameTeamTwo;
    }

    public String getColorTeamTwo() {
        return colorTeamTwo;
    }

    public void setColorTeamTwo(String colorTeamTwo) {
        this.colorTeamTwo = colorTeamTwo;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public int getRoundValue() {
        return roundValue;
    }

    public void setRoundValue(int roundValue) {
        this.roundValue = roundValue;
    }
}
