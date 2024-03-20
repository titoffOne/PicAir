package com.titov.datalayer;


import com.titov.data.Settings;
import com.titov.filetools.FileManager;
import com.titov.resourcer.ConfigurationManager;

public class SettingsData {
    private static Settings settings = (Settings) FileManager.loadFromFile(ConfigurationManager.getProperty("file.path.content.settings"));

    public static String PEN_COLOR = settings.getPenColor();
    public static int PEN_WIDTH = settings.getPenWidth();
    public static boolean SOUNDS = settings.isSounds();
    public static String LANGUAGE = settings.getLanguage();

    private SettingsData() {
        if (settings == null) {
            settings = new Settings();
        }
    }

    public static void setPenColor(String penColor) {
        PEN_COLOR = penColor;
    }

    public static void setPenWidth(int penWidth) {
        PEN_WIDTH = penWidth;
    }

    public static void setSounds(boolean sounds) {
        SettingsData.SOUNDS = sounds;
    }

    public static void setLanguage(String language) {
        SettingsData.LANGUAGE = language;
    }

    public static void refreshFileSettings() {
        Settings newSettings = new Settings(PEN_COLOR, PEN_WIDTH, SOUNDS, LANGUAGE);
        FileManager.saveToFile(newSettings, ConfigurationManager.getProperty("file.path.content.settings"));
    }
}
