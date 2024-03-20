package com.titov.controllers.game;

import com.titov.controllers.ControllerManager;
import com.titov.controllers.SoundManager;
import com.titov.resourcer.ConfigurationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PauseController {

    public Text textTitle;
    public Button btEnd;
    public Button btResume;

    @FXML
    public void initialize() throws InterruptedException {
        setTextForElement();
    }

    private void setTextForElement() {
        textTitle.setText(ConfigurationManager.getProperty("text.game.pause.title"));
        btEnd.setText(ConfigurationManager.getProperty("text.game.pause.button.stop"));
        btResume.setText(ConfigurationManager.getProperty("text.game.pause.button.resume"));

    }
    public void btResultOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        ProcessController.drawingEnabled = true;
        ProcessController.camera.release();
        ControllerManager.closeModalWindow();
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.game.result"));
    }

    public void btResumeOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        ProcessController.resumeTimer();
        ProcessController.drawingEnabled = true;
        ControllerManager.closeModalWindow();
    }
}
