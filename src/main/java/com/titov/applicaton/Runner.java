package com.titov.applicaton;

import com.titov.controllers.ControllerManager;
import com.titov.resourcer.ConfigurationManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Runner extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ControllerManager.initializeStage(primaryStage);
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.loading"));
    }
}
