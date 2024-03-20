package com.titov.filetools;
import com.titov.data.Settings;
import com.titov.resourcer.ConfigurationManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    private static final String ABSOLUTE_FILE_PATH = ConfigurationManager.getProperty("file.path.absolute");
    private FileManager() {
    }

    // Сохранение объекта в файл
    public static void saveToFile(Object object, String filePath) {
        filePath = ABSOLUTE_FILE_PATH + filePath;
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
            oos.writeObject(object);
            System.out.println("Объект успешно сохранен в файл: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Загрузка объекта из файла
    public static Object loadFromFile(String filePath) {
        filePath = ABSOLUTE_FILE_PATH + filePath;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)))) {
            Object object = ois.readObject();
            System.out.println("Объект успешно загружен из файла: " + filePath);
            return object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



}
