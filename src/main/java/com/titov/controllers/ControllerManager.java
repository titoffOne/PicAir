package com.titov.controllers;

import com.titov.resourcer.ConfigurationManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ControllerManager {
    public static Stage primaryStage;
    private static Stage modalStage;

    public static void initializeStage(Stage primaryStage) throws IOException {
        ControllerManager.primaryStage = primaryStage;
        ControllerManager.setDefaultStageSettings();
        ControllerManager.primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private static void setDefaultStageSettings() {
        // Установкаа заголовка окна
        primaryStage.setTitle(ConfigurationManager.getProperty("application.text.name"));

        Image icon = new Image(ConfigurationManager.getProperty("application.path.logo"));
        primaryStage.getIcons().add(icon);

        // Запрет масштабирования окна
        primaryStage.setResizable(false);

        // Отключаем декорации окна (заголовок, рамка и т.д.)
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }

    public static void setMainStageSettings() {
        // Установка максимизированного состояния
        primaryStage.setMaximized(true);

        // Устанавливаем свойства для полноэкранного режима
        primaryStage.setFullScreen(true);

        // Пустая строка для скрытия подсказки о выходе из полноэкранного режима
        primaryStage.setFullScreenExitHint("");
    }

    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(ControllerManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(ControllerManager.class.getResource("/styles/style.css").toExternalForm());
            ControllerManager.primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openModalWindow(String fxmlPath) {
        Stage parentStage = ControllerManager.primaryStage;
        try {
            // Загрузка FXML-файла
            FXMLLoader loader = new FXMLLoader(ControllerManager.class.getResource(fxmlPath));
            // Загрузка корневого элемента из FXML
            Parent root = loader.load();

            // Создание нового модального окна
            modalStage = new Stage(StageStyle.UTILITY);
            modalStage.initOwner(parentStage);

            // Отключаем декорации окна (заголовок, рамка и т.д.)
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);

            // Установка сцены для модального окна
            Scene modalScene = new Scene(root);
            modalScene.setFill(Color.TRANSPARENT);
            modalStage.setScene(modalScene);

            // Установка сцены по центру экрана
            modalStage.centerOnScreen();
            // Показ модального окна и блокировка основного окна
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок при загрузке FXML
        }
    }

    public static void closeModalWindow() {
        if (modalStage != null) {
            modalStage.close();
        }
    }
}
