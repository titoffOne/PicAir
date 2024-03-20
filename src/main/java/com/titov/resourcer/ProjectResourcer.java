package com.titov.resourcer;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Providing access to text resources.
 * <p>
 * Using initialization-on-demand, thread-safety, and dependency injection.
 * <p>
 * History:
 * <p>
 * 1.0 - The first version.
 * <p>
 * 2.0 - Added initialization-on-demand and thread-safety.
 * <p>
 * 2.1 - Added the getInstance method for dependency injection.
 *
 * @version 2.1
 */
public class ProjectResourcer implements Resourcer {
    public static final String MISSING_PARAMETER_VALUE_MESSAGE_FORMAT = "Value for parameter \"%s\" is missing";

    public static final String DEFAULT_PROPERTY_NAME = "constants.text";
    public static final String ENGLISH_PROPERTY_NAME = "constants.text_en";
    private static String basename = DEFAULT_PROPERTY_NAME;

    private ProjectResourcer() {
    }

    public static void setBasename(String basename) {
        ProjectResourcer.basename = basename;
    }

    public static Resourcer getInstance() {
        return ResourcerHolder.resourcer;
    }

    private static void reinitialize() {
        synchronized (ProjectResourcer.class) {
            if ((ProjectResourcer.isBasenameChanged())
                    || (ProjectResourcer.isLocaleChanged())) {
                ResourcerHolder.basename = ProjectResourcer.basename;
                ResourcerHolder.resources = ResourceBundle.getBundle(
                        ResourcerHolder.basename, Locale.getDefault());
            }
        }
    }

    private static boolean isLocaleChanged() {
        Locale systemLocale = Locale.getDefault();
        Locale resourcerLocale = ResourcerHolder.resources.getLocale();

        return (!systemLocale.equals(resourcerLocale));
    }

    private static boolean isBasenameChanged() {
        return (!ProjectResourcer.basename.equals(ResourcerHolder.basename));
    }

    @Override
    public String getString(String resourceKey) {
        ProjectResourcer.reinitialize();
        ResourceBundle resources = ResourcerHolder.resources;
        String resourceValue;

        try {
            resourceValue = resources.getString(resourceKey);
        } catch (MissingResourceException e) {
            resourceValue = String.format(
                    ProjectResourcer.MISSING_PARAMETER_VALUE_MESSAGE_FORMAT,
                    resourceKey);
        }

        return resourceValue;
    }

    private static class ResourcerHolder {
        private static String basename = ProjectResourcer.basename;
        private static ResourceBundle resources = ResourceBundle.getBundle(
                ProjectResourcer.basename, Locale.getDefault());
        private static Resourcer resourcer = new ProjectResourcer();
    }
}
