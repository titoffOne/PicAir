package com.titov.data;

import java.io.Serializable;

public class Settings implements Serializable {
    private String penColor;
    private int penWidth;
    private boolean sounds;
    private String language;

    public Settings(String penColor, int penWidth, boolean sounds, String language) {
        this.penColor = penColor;
        this.penWidth = penWidth;
        this.sounds = sounds;
        this.language = language;
    }

    public Settings() {
        this.penColor = "#30ff00";
        this.penWidth = 6;
        this.sounds = true;
        this.language = "Русский";
    }

    public String getPenColor() {
        return penColor;
    }

    public int getPenWidth() {
        return penWidth;
    }

    public boolean isSounds() {
        return sounds;
    }

    public String getLanguage() {
        return language;
    }

    public void setPenColor(String penColor) {
        this.penColor = penColor;
    }

    public void setPenWidth(int penWidth) {
        this.penWidth = penWidth;
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    @Override
    public String toString() {
        return "Settings{" +
                "penColor='" + penColor + '\'' +
                ", penWidth='" + penWidth + '\'' +
                ", sounds='" + sounds + '\'' +
                ", language=" + language +
                '}';
    }
}
