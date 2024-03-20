package com.titov.datalayer;

import com.titov.data.Game;

public class GameData {
    public static Game game = new Game();

    public static String nameTeamOne = game.getNameTeamOne();
    public static int countPointsTeamOne = game.getCountPointsTeamOne();
    public static String colorTeamOne = game.getColorTeamOne();
    public static String nameTeamTwo = game.getNameTeamTwo();
    public static int countPointsTeamTwo = game.getCountPointsTeamTwo();
    public static String colorTeamTwo = game.getColorTeamTwo();
    public static int timerValue = game.getTimerValue();
    public static int roundValue = game.getRoundValue();

    private GameData() {
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameData.game = game;
    }

    public static String getNameTeamOne() {
        return nameTeamOne;
    }

    public static void setNameTeamOne(String nameTeamOne) {
        GameData.nameTeamOne = nameTeamOne;
    }

    public static int getCountPointsTeamOne() {
        return countPointsTeamOne;
    }

    public static void setCountPointsTeamOne(int countPointsTeamOne) {
        GameData.countPointsTeamOne = countPointsTeamOne;
    }

    public static String getColorTeamOne() {
        return colorTeamOne;
    }

    public static void setColorTeamOne(String colorTeamOne) {
        GameData.colorTeamOne = colorTeamOne;
    }

    public static String getNameTeamTwo() {
        return nameTeamTwo;
    }

    public static void setNameTeamTwo(String nameTeamTwo) {
        GameData.nameTeamTwo = nameTeamTwo;
    }

    public static int getCountPointsTeamTwo() {
        return countPointsTeamTwo;
    }

    public static void setCountPointsTeamTwo(int countPointsTeamTwo) {
        GameData.countPointsTeamTwo = countPointsTeamTwo;
    }

    public static String getColorTeamTwo() {
        return colorTeamTwo;
    }

    public static void setColorTeamTwo(String colorTeamTwo) {
        GameData.colorTeamTwo = colorTeamTwo;
    }

    public static int getTimerValue() {
        return timerValue;
    }

    public static void setTimerValue(int timerValue) {
        GameData.timerValue = timerValue;
    }

    public static int getRoundValue() {
        return roundValue;
    }

    public static void setRoundValue(int roundValue) {
        GameData.roundValue = roundValue;
    }
}
