package com.titov.controllers.game;

import com.titov.controllers.ControllerManager;
import com.titov.controllers.SoundManager;
import com.titov.data.Game;
import com.titov.datalayer.GameData;
import com.titov.resourcer.ConfigurationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class TeamsController {
    public ToggleGroup groupColorTeamOne;
    public ToggleGroup groupColorTeamTwo;
    public Text textTeamOne;
    public Text textTeamTwo;
    public TextField textFieldTeamOne;
    public TextField textFieldTeamTwo;
    public Text textTimerValue;
    public Text textRoundValue;


    private final int MAX_TIMER_VALUE = 180;
    private final int MIN_TIMER_VALUE = 30;
    private final int MIN_ROUND_VALUE = 1;
    private final int MAX_ROUND_VALUE = 5;
    public Text textTimer;
    public Text textRound;
    public Button btPlay;


    private String colorTeamOne = GameData.getColorTeamOne();
    private String colorTeamTwo = GameData.getColorTeamTwo();
    private int timerValue = GameData.getTimerValue();
    private int roundValue = GameData.getRoundValue();

    @FXML
    public void initialize() {
        setTextForElement();
        setElementSettings();
        this.handleRadioButtonAction(groupColorTeamOne, groupColorTeamTwo);
        this.convertToUpperCase();
    }

    private void setTextForElement() {
        textTeamOne.setText(ConfigurationManager.getProperty("text.game.teams.team.one"));
        textTeamTwo.setText(ConfigurationManager.getProperty("text.game.teams.team.two"));
        textTimer.setText(ConfigurationManager.getProperty("text.game.teams.timer"));
        textRound.setText(ConfigurationManager.getProperty("text.game.teams.round"));
        btPlay.setText(ConfigurationManager.getProperty("text.game.teams.button.play"));
    }

    private void setElementSettings() {
        textFieldTeamOne.setText(GameData.getNameTeamOne());
        textFieldTeamTwo.setText(GameData.getNameTeamTwo());
        textTeamOne.setStyle("-fx-fill: " + GameData.getColorTeamOne() + ";");
        textFieldTeamOne.setStyle("-fx-border-color: " + GameData.getColorTeamOne() + ";");
        textTeamTwo.setStyle("-fx-fill: " + GameData.getColorTeamTwo() + ";");
        textFieldTeamTwo.setStyle("-fx-border-color: " + GameData.getColorTeamTwo() + ";");

        int newIndexOne = getIndexForRadioButton(GameData.getColorTeamOne());
        groupColorTeamOne.getToggles().get(newIndexOne).setSelected(true);
        int newIndexTwo = getIndexForRadioButton(GameData.getColorTeamTwo());
        groupColorTeamTwo.getToggles().get(newIndexTwo).setSelected(true);

        textTimerValue.setText(String.valueOf(GameData.getTimerValue()));
        textRoundValue.setText(String.valueOf(GameData.getRoundValue()));
    }

    private void convertToUpperCase() {
        // Установка фильтра ввода
        textFieldTeamOne.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            // Преобразование введенного символа в верхний регистр
            String newText = textFieldTeamOne.getText() + event.getCharacter().toUpperCase();
            textFieldTeamOne.setText(newText);

            // Установка курсора в конец текста
            textFieldTeamOne.positionCaret(newText.length());

            // Подавление события
            event.consume();
        });

        // Установка фильтра ввода
        textFieldTeamTwo.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            // Преобразование введенного символа в верхний регистр
            String newText = textFieldTeamTwo.getText() + event.getCharacter().toUpperCase();
            textFieldTeamTwo.setText(newText);

            // Установка курсора в конец текста
            textFieldTeamTwo.positionCaret(newText.length());

            // Подавление события
            event.consume();
        });
    }

    public void btPlayOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        GameData.setNameTeamOne(textFieldTeamOne.getText());
        GameData.setColorTeamOne(this.colorTeamOne);
        GameData.setNameTeamTwo(textFieldTeamTwo.getText());
        GameData.setColorTeamTwo(this.colorTeamTwo);
        GameData.setTimerValue(this.timerValue);
        GameData.setRoundValue(this.roundValue);
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.game.process"));
    }

    public void handleRadioButtonLeftOnAction(ActionEvent actionEvent) {
        handleRadioButtonAction(groupColorTeamOne, groupColorTeamTwo);
    }

    public void handleRadioButtonRightOnAction(ActionEvent actionEvent) {
        handleRadioButtonAction(groupColorTeamTwo, groupColorTeamOne);
    }

    private void handleRadioButtonAction(ToggleGroup group1, ToggleGroup group2) {
        int indexTeamOne = group1.getToggles().indexOf(group1.getSelectedToggle());
        int indexTeamTwo = group2.getToggles().indexOf(group2.getSelectedToggle());

        if (indexTeamOne == indexTeamTwo) {
            int newIndex = (indexTeamTwo + 1) % group2.getToggles().size();
            group2.getToggles().get(newIndex).setSelected(true);
            indexTeamTwo = newIndex;
        }

        if (group1 == groupColorTeamOne) {
            this.setColorTeam(indexTeamOne, indexTeamTwo);
        } else {
            this.setColorTeam(indexTeamTwo, indexTeamOne);
        }
    }

    private String getColorForRadioButton(int index) {
        switch (index) {
            case 0:
                return "#47DEFF";
            case 1:
                return "#F44DCF";
            case 2:
                return "#FFED47";
            case 3:
                return "#FF4752";
            default:
                return "#FFFFFF";
        }
    }

    private int getIndexForRadioButton(String color) {
        switch (color) {
            case "#47DEFF":
                return 0;
            case "#F44DCF":
                return 1;
            case "#FFED47":
                return 2;
            case "#FF4752":
                return 3;
            default:
                return 0;
        }
    }

    private void setColorTeam(int indexTeamOne, int indexTeamTwo) {
        String colorOne = getColorForRadioButton(indexTeamOne);
        String colorTwo = getColorForRadioButton(indexTeamTwo);
        this.colorTeamOne = colorOne;
        this.colorTeamTwo = colorTwo;

        textTeamOne.setStyle("-fx-fill: " + colorOne + ";");
        textFieldTeamOne.setStyle("-fx-border-color: " + colorOne + ";");

        textTeamTwo.setStyle("-fx-fill: " + colorTwo + ";");
        textFieldTeamTwo.setStyle("-fx-border-color: " + colorTwo + ";");
    }

    public void btBackOnAction(ActionEvent actionEvent) {
        ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.menu"));
    }


    public void btIncTimer(ActionEvent actionEvent) {
        timerValue = Integer.parseInt(textTimerValue.getText());
        if (timerValue < MAX_TIMER_VALUE) {
            timerValue += 30;
            textTimerValue.setText(String.valueOf(timerValue));
        }
    }

    public void btDecTimer(ActionEvent actionEvent) {
        timerValue = Integer.parseInt(textTimerValue.getText());
        if (timerValue > MIN_TIMER_VALUE) {
            timerValue -= 30;
            textTimerValue.setText(String.valueOf(timerValue));
        }
    }

    public void btIncRound(ActionEvent actionEvent) {
        roundValue = Integer.parseInt(textRoundValue.getText());
        if (roundValue < MAX_ROUND_VALUE) {
            roundValue += 2;
            textRoundValue.setText(String.valueOf(roundValue));
        }
    }

    public void btDecRound(ActionEvent actionEvent) {
        roundValue = Integer.parseInt(textRoundValue.getText());
        if (roundValue > MIN_ROUND_VALUE) {
            roundValue -= 2;
            textRoundValue.setText(String.valueOf(roundValue));
        }
    }
}
