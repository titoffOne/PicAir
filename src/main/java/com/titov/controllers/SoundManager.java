package com.titov.controllers;

import com.titov.datalayer.SettingsData;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundManager {
    private static MediaPlayer mediaPlayer;
    private static final String buttonAudioFilePath = "D:\\IT\\IntelliJIDEA\\PicProject\\src\\main\\resources\\sounds\\sound2.mp3";
    private static final String stopTimerAudioFilePath = "D:\\IT\\IntelliJIDEA\\PicProject\\src\\main\\resources\\sounds\\stopTimer.mp3";
    public static boolean ifMusicPlay = SettingsData.SOUNDS;

    public static Media buttonSound = new Media(new File(buttonAudioFilePath).toURI().toString());
    public static Media stopTimerSound = new Media(new File(stopTimerAudioFilePath).toURI().toString());

    private SoundManager() {}


    public static void playButtonSound() {
        mediaPlayer = new MediaPlayer(buttonSound);
        if (ifMusicPlay) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }

            mediaPlayer.play();
        }
    }

    public static void playStopTimerSound() {
        mediaPlayer = new MediaPlayer(stopTimerSound);
        if (ifMusicPlay) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }

            mediaPlayer.play();
        }
    }

    public static void stopSound() {
        mediaPlayer.stop();
    }
}