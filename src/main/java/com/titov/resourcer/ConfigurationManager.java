package com.titov.resourcer;

public class ConfigurationManager {
    private final static Resourcer resource = ProjectResourcer.getInstance();

    // класс извлекает информацию из файла config.properties
    private ConfigurationManager() {
    }

    public static String getProperty(String key) {
        return resource.getString(key);
    }

    public static void setLanguage(boolean isRussian) {
        if (isRussian) {
            ProjectResourcer.setBasename(ProjectResourcer.DEFAULT_PROPERTY_NAME);
        } else {
            ProjectResourcer.setBasename(ProjectResourcer.ENGLISH_PROPERTY_NAME);
        }
    }
}
