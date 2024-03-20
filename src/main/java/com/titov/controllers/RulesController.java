package com.titov.controllers;

import com.titov.resourcer.ConfigurationManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class RulesController {

    public Label labelRulesOne;
    public Label labelRulesTwo;
    public Label labelRulesThree;
    public Label labelRulesFour;
    public ScrollPane scrollPane;
    public Button btOk;
    public Label labelTitleOne;
    public Label labelTitleTwo;
    public Label labelTitleThree;
    public Label labelTitleFour;

    @FXML
    public void initialize() {
        setTextForElement();
        Platform.runLater(() -> scrollPane.setVvalue(0.0));
    }

    private void setTextForElement() {
        labelTitleOne.setText(ConfigurationManager.getProperty("text.rules.title.one"));
        labelTitleTwo.setText(ConfigurationManager.getProperty("text.rules.title.two"));
        labelTitleThree.setText(ConfigurationManager.getProperty("text.rules.title.three"));
        labelTitleFour.setText(ConfigurationManager.getProperty("text.rules.title.four"));

        labelRulesOne.setText(ConfigurationManager.getProperty("text.rules.preparing.for.game"));
        labelRulesTwo.setText(ConfigurationManager.getProperty("text.rules.start.game"));
        labelRulesThree.setText(ConfigurationManager.getProperty("text.rules.drawing.rules"));
        labelRulesFour.setText(ConfigurationManager.getProperty("text.rules.determinate.winner"));

        btOk.setText(ConfigurationManager.getProperty("text.rules.button.ok"));
    }

    public void btnOkOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.menu"));
    }
}
