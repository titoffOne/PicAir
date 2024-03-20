package com.titov.controllers.game;

import com.titov.controllers.ControllerManager;
import com.titov.controllers.SoundManager;
import com.titov.datalayer.GameData;
import com.titov.resourcer.ConfigurationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.opencv.core.Core;

public class ResultController {

    public Label labelResultInfo;
    public Label labelWinner;
    public Label labelNameTeamOne;
    public Label labelNameTeamTwo;
    public Label labelCountPointsTeamOne;
    public Label labelCountPointsTeamTwo;
    public Button btMainMenu;
    public Button btPlay;


    @FXML
    public void initialize() throws InterruptedException {
        setTextForElement();
        setLabelResultInfo();
        determineWinner();
    }

    private void setTextForElement() {
        labelResultInfo.setText(ConfigurationManager.getProperty("text.game.result.winner"));
        btMainMenu.setText(ConfigurationManager.getProperty("text.game.result.button.menu"));
        btPlay.setText(ConfigurationManager.getProperty("text.game.result.button.play"));
    }

    private void resetScores() {
        GameData.countPointsTeamOne = 0;
        GameData.countPointsTeamTwo = 0;
    }

    private void setLabelResultInfo() {
        labelCountPointsTeamOne.setText(String.valueOf(GameData.countPointsTeamOne));
        labelCountPointsTeamTwo.setText(String.valueOf(GameData.countPointsTeamTwo));
        labelNameTeamOne.setText(GameData.getNameTeamOne());
        labelNameTeamTwo.setText(GameData.getNameTeamTwo());
    }

    private void determineWinner() {
        if (GameData.countPointsTeamOne == GameData.countPointsTeamTwo) {
            labelResultInfo.setVisible(false);
            labelWinner.setText(ConfigurationManager.getProperty("text.game.result.not.winner"));
        } else if (GameData.countPointsTeamOne > GameData.countPointsTeamTwo) {
            labelWinner.setText(GameData.getNameTeamOne());
        } else {
            labelWinner.setText(GameData.getNameTeamTwo());
        }
    }

    public void btMainMenuOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        resetScores();
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.menu"));
    }

    public void btPlayOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        resetScores();
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.game.teams"));
    }
}
