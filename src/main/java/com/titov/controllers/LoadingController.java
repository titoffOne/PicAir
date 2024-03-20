package com.titov.controllers;

import com.titov.datalayer.SettingsData;
import com.titov.resourcer.ConfigurationManager;
import javafx.animation.*;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController {
    public Text textAppName;
    public Text textLoading;

    private static final String LANGUAGE_RUSSIAN = "Русский";

    public void initialize() {
        ControllerManager.setMainStageSettings();
        ConfigurationManager.setLanguage(LANGUAGE_RUSSIAN.equals(SettingsData.LANGUAGE));
        textLoading.setText(ConfigurationManager.getProperty("text.loading"));

        // Изменение цвета надписи несколько раз
        Timeline colorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(textAppName.fillProperty(), Color.WHITE)),
                new KeyFrame(Duration.seconds(1), new KeyValue(textAppName.fillProperty(), Color.web("#4248D6"))),
                new KeyFrame(Duration.seconds(2), new KeyValue(textAppName.fillProperty(), Color.WHITE)),
                new KeyFrame(Duration.seconds(3), new KeyValue(textAppName.fillProperty(), Color.web("#FF8947"))),
                new KeyFrame(Duration.seconds(4), new KeyValue(textAppName.fillProperty(), Color.WHITE)),
                new KeyFrame(Duration.seconds(5), new KeyValue(textAppName.fillProperty(), Color.web("#F44D7B")))
        );

        // Задержка перед открытием основного окна
        Timeline delayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1))
        );

        colorTimeline.setOnFinished(event -> {
            delayTimeline.setOnFinished(e -> {
//                ControllerManager.setMainStageSettings();
                ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.menu"));
            });

            delayTimeline.play();
        });

        colorTimeline.play();
    }
}
