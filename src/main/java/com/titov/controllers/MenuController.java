package com.titov.controllers;

import com.titov.datalayer.SettingsData;
import com.titov.resourcer.ConfigurationManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MenuController {
    public ImageView imageViewGame;
    public ImageView imageViewRules;
    public ImageView imageViewSettings;
    public ImageView imageViewExit;
    public Button btGame;
    public Button btRules;
    public Button btSettings;
    public Button btExit;

    @FXML
    public void initialize() {

        btGame.setText(ConfigurationManager.getProperty("menu.button.game.text.name"));
        btRules.setText(ConfigurationManager.getProperty("menu.button.rules.text.name"));
        btSettings.setText(ConfigurationManager.getProperty("menu.button.settings.text.name"));
        btExit.setText(ConfigurationManager.getProperty("menu.button.exit.text.name"));
    }

    public void onClickSwitchToSceneSettings(ActionEvent event) {
        SoundManager.playButtonSound();
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.menu.settings"));
    }


    public void btGameOnMouseEntered(MouseEvent mouseDragEvent) {
        imageViewGame.setVisible(true);
    }

    public void btGameOnMouseExited(MouseEvent mouseDragEvent) {
        imageViewGame.setVisible(false);
    }

    public void btRulesOnMouseEntered(MouseEvent mouseEvent) {
        imageViewRules.setVisible(true);
    }

    public void btRulesOnMouseExited(MouseEvent mouseEvent) {
        imageViewRules.setVisible(false);
    }

    public void btSettingsOnMouseEntered(MouseEvent mouseEvent) {
        imageViewSettings.setVisible(true);
    }

    public void btSettingsOnMouseExited(MouseEvent mouseEvent) {
        imageViewSettings.setVisible(false);
    }

    public void onClickExitApplication(ActionEvent actionEvent) {
        SettingsData.refreshFileSettings();
        Platform.exit();
        System.exit(0);
    }

    public void btExitOnMouseEntered(MouseEvent mouseEvent) {
        imageViewExit.setVisible(true);
    }

    public void btExitOnMouseExited(MouseEvent mouseEvent) {
        imageViewExit.setVisible(false);
    }

    public void btGameOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.game.teams"));
    }

    public void btnRulesOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.menu.rules"));
    }
}
