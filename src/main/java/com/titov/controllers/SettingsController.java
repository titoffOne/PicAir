package com.titov.controllers;

import com.titov.datalayer.SettingsData;
import com.titov.resourcer.ConfigurationManager;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;

public class SettingsController {
    private static final String LANGUAGE_RUSSIAN = "Русский";
    private static final String LANGUAGE_ENGLISH = "English";
    private static final String FORMAT_STRING_WIDTH = "%.0f";
    private static final String FORMAT_STRING_COLOR = "#%02X%02X%02X";

    public ColorPicker colorPicker;
    public Slider slider;
    public Circle circle;
    public Text textPenThickness;
    public CheckBox checkBoxMusic;
    public ChoiceBox<String> choiceBoxLanguage;
    public Text textPenColor;
    public Text textLanguage;
    public Text textSounds;
    public Text textPenWidth;
    public Button btSaveSettings;


    @FXML
    public void initialize() {
        setTextForElement();
        this.addChoiceBoxData();
        this.sliderListener();
        this.readInformationFromSettingsFile();
        this.setCircleFill();
    }

    private void setTextForElement() {
        textPenColor.setText(ConfigurationManager.getProperty("text.settings.pen.color"));
        textPenWidth.setText(ConfigurationManager.getProperty("text.settings.pen.width"));
        textSounds.setText(ConfigurationManager.getProperty("text.settings.sounds"));
        textLanguage.setText(ConfigurationManager.getProperty("text.settings.language"));
        btSaveSettings.setText(ConfigurationManager.getProperty("text.settings.button.save"));
    }

    private void addChoiceBoxData() {
        // Заполнение ChoiceBox элементами
        choiceBoxLanguage.getItems().addAll(LANGUAGE_RUSSIAN, LANGUAGE_ENGLISH);
    }

    public void btSaveSettingsOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();

        SettingsData.setPenColor(String.valueOf(convertColorToHex(colorPicker.getValue())));
        SettingsData.setPenWidth((int) slider.getValue());
        SettingsData.setSounds(checkBoxMusic.isSelected());
        SettingsData.setLanguage(choiceBoxLanguage.getValue());

        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.menu"));
    }

    public static String convertColorToHex(Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);

        // Формирование строки в формате #RRGGBB
        return String.format(SettingsController.FORMAT_STRING_COLOR, red, green, blue);
    }

    private void sliderListener() {
        slider.setBlockIncrement(1); // Установка шага в 1
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                circle.setRadius(newValue.doubleValue());
                textPenThickness.setText(String.format(FORMAT_STRING_WIDTH, newValue.doubleValue()));
            }
        });
    }

    private void setCircleFill() {
        Color selectedColor = colorPicker.getValue();
        circle.setFill(selectedColor);
    }

    public void colorPickerOnAction(ActionEvent actionEvent) {
        this.setCircleFill();
    }

    public void checkBoxMusicOnAction(ActionEvent actionEvent) {
        SoundManager.ifMusicPlay = checkBoxMusic.isSelected();
    }

    @FXML
    private void choiceBoxLanguageOnAction(ActionEvent actionEvent) {
        String selectedLanguage = choiceBoxLanguage.getValue();
        ConfigurationManager.setLanguage(LANGUAGE_RUSSIAN.equals(selectedLanguage));
        setTextForElement();
    }

    private void readInformationFromSettingsFile() {
        colorPicker.setValue(Color.valueOf(SettingsData.PEN_COLOR));
        slider.setValue(SettingsData.PEN_WIDTH);
        textPenThickness.setText(String.valueOf(SettingsData.PEN_WIDTH));
        circle.setRadius(SettingsData.PEN_WIDTH);
        checkBoxMusic.setSelected(SettingsData.SOUNDS);
        choiceBoxLanguage.setValue(SettingsData.LANGUAGE);
    }
}
