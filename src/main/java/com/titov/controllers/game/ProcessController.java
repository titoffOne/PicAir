package com.titov.controllers.game;

import com.titov.controllers.ControllerManager;
import com.titov.controllers.SoundManager;
import com.titov.datalayer.GameData;
import com.titov.datalayer.SettingsData;
import com.titov.resourcer.ConfigurationManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ProcessController {
    public ImageView imageViewCamera;
    public Label labelMainTimer;
    public Label labelNameTeamOne;
    public Label labelPointsTeamOne;
    public Label labelNameTeamTwo;
    public Label labelPointsTeamTwo;
    public Button btStartTimer;
    public Button btPause;
    public Button btDelete;
    public Label labelInfo;
    public Button btAddPoints;

    private List<Point> points = new ArrayList<>();
    public static VideoCapture camera;
    private static Timeline timeline;
    private static int secondsRemaining;
    private int pointTeamOne = 0;
    private int pointTeamTwo = 0;
    private int currentRound = 1;
    private boolean isTwoTeamsPlayed = false;
    private boolean isCurrentTeamOne = true;
    public static boolean drawingEnabled = true;

    @FXML
    public void initialize() throws InterruptedException {
        setTextForElement();
        setGameSettings();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        startCamera();
        isCurrentTeamOne = chooseRandomTeam();
        setInfoText();
    }

    private void setTextForElement() {
        btStartTimer.setText(ConfigurationManager.getProperty("text.game.process.start.timer"));
        btAddPoints.setText(ConfigurationManager.getProperty("text.game.process.add.points"));
    }

    private void setInfoText() {
        String info;
        if (isCurrentTeamOne) {
            info = String.format(ConfigurationManager.getProperty("text.game.process.info"), currentRound, GameData.getNameTeamOne());
        } else {
            info = String.format(ConfigurationManager.getProperty("text.game.process.info"), currentRound, GameData.getNameTeamTwo());
        }
        labelInfo.setText(info);
    }

    private void updateCurrentRound() {
        isCurrentTeamOne = !isCurrentTeamOne;
        if (isTwoTeamsPlayed) {
            currentRound++;
        } else {
            isTwoTeamsPlayed = true;
        }

    }

    private boolean chooseRandomTeam() {
        // Создаем объект Random для генерации случайных чисел
        Random random = new Random();

        // Генерируем случайное число (0 или 1)
        int randomIndex = random.nextInt(2);

        // Выбираем команду на основе сгенерированного числа
        return randomIndex == 0;
    }

    private void setGameSettings() {
        labelMainTimer.setText(String.valueOf(GameData.getTimerValue()));
        labelNameTeamOne.setText(GameData.getNameTeamOne());
        labelNameTeamOne.setStyle("-fx-background-color: " + GameData.getColorTeamOne() + ";");
        labelNameTeamTwo.setText(GameData.getNameTeamTwo());
        labelNameTeamTwo.setStyle("-fx-background-color: " + GameData.getColorTeamTwo() + ";");
        labelPointsTeamOne.setText("0");
        labelPointsTeamOne.setStyle("-fx-background-color: " + GameData.getColorTeamOne() + ";");
        labelPointsTeamTwo.setText("0");
        labelPointsTeamTwo.setStyle("-fx-background-color: " + GameData.getColorTeamTwo() + ";");
    }

    public void startTimer() {
        stopTimer(); // Остановка текущего таймера, если существует
        secondsRemaining = GameData.getTimerValue(); // Сброс счетчика секунд
        labelMainTimer.setText(String.valueOf(secondsRemaining)); // Обновление отображаемого времени
        labelMainTimer.setTextFill(Color.WHITE);
        btPause.setVisible(true); // Возможно, вы хотите снова показать кнопку паузы
        btDelete.setVisible(true); // Возможно, вы хотите снова показать кнопку удаления

        Duration duration = Duration.seconds(1);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            // Обновление отображаемого времени
            labelMainTimer.setText(String.valueOf(secondsRemaining));

            // Проверка, осталось ли 10 секунд
            if (secondsRemaining <= 10) {
                labelMainTimer.setTextFill(Color.RED);
            }

            // Уменьшение счетчика секунд
            secondsRemaining--;

            // Проверка на окончание времени
            if (secondsRemaining < 0) {
                SoundManager.playStopTimerSound();
                stopTimer(); // Вызываем метод остановки таймера
                labelMainTimer.setTextFill(Color.WHITE);
                labelMainTimer.setText(ConfigurationManager.getProperty("text.game.process.timer"));
                btStartTimer.setVisible(true);
                btAddPoints.setVisible(false);
                btPause.setVisible(false);
                btDelete.setVisible(false);
                updateCurrentRound();
                setInfoText();

                if (currentRound == (GameData.getRoundValue() + 1)) {
                    ProcessController.camera.release();
                    ControllerManager.switchScene(ConfigurationManager.getProperty("fxml.path.game.result"));
                }
            }
        });

        // Создание таймлайна с заданным KeyFrame и циклом выполнения
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Запуск таймлайна
        timeline.play();
    }

    private void stopTimer() {
        if (timeline != null) {
            timeline.stop(); // Остановка таймлайна
        }
    }

    public static void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    public static void resumeTimer() {
        if (timeline != null) {
            timeline.play();
        }
    }

    public void btPauseOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        ProcessController.pauseTimer();
        ProcessController.drawingEnabled = false;
        ControllerManager.openModalWindow(ConfigurationManager.getProperty("fxml.path.game.pause"));
    }

    public void btDeleteOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        points.clear(); // Очистить список точек
    }

    public void startCamera() {
        // Инициализация объекта VideoCapture для работы с камерой (идентификатор 0 - основная камера)
        camera = new VideoCapture(0);

        // Проверка успешности открытия камеры
        if (!camera.isOpened()) {
            System.out.println("Error: Camera not opened");
            System.exit(-1);
        }

        // Инициализация объекта Mat для хранения кадра
        Mat frame = new Mat();

        // Установка параметров отображения изображения
        imageViewCamera.setPreserveRatio(true);
        imageViewCamera.setFitWidth(1600);

        // Запуск нового потока для обработки видеопотока
        new Thread(() -> {
            try {
                while (true) {
                    // Чтение кадра с камеры
                    if (camera.read(frame)) {
                        // Зеркальное отражение кадра
                        Mat flipped = new Mat();
                        Core.flip(frame, flipped, 1);

                        // Получение точки изображения
                        Point point = getPoint(flipped);

                        // Если точка существует и разрешено рисование, добавляем точку в список
                        if (point != null && drawingEnabled) {
                            Platform.runLater(() -> points.add(point));
                        }

                        // Обновление изображения на JavaFX UI
                        Platform.runLater(() -> {
                            Mat drawFrame = flipped.clone();

                            // Рисование линий между точками
                            for (int i = 1; i < points.size(); i++) {
                                Point a = points.get(i);
                                Point b = points.get(i - 1);
                                double dist = Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
                                if (dist < 500) {
                                    Imgproc.line(drawFrame, a, b, webColorToScalar(SettingsData.PEN_COLOR), SettingsData.PEN_WIDTH, Imgproc.MORPH_RECT, 0);
                                }
                            }

                            // Преобразование изображения в объект Image и установка его в ImageView
                            Image image = mat2Image(drawFrame);
                            imageViewCamera.setImage(image);
                        });
                    }
                }
            } finally {
                // Освобождение ресурсов камеры после завершения потока
                camera.release();
            }
        }).start();
    }

    public static Scalar webColorToScalar(String webColor) {
        if (webColor.length() != 7 || webColor.charAt(0) != '#') {
            throw new IllegalArgumentException("Неверный формат веб-цвета");
        }

        try {
            // Извлечь значения красного, зеленого и синего цветов
            int red = Integer.parseInt(webColor.substring(1, 3), 16);
            int green = Integer.parseInt(webColor.substring(3, 5), 16);
            int blue = Integer.parseInt(webColor.substring(5, 7), 16);

            // Создать объект Scalar в порядке BGR
            return new Scalar(blue, green, red);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка при преобразовании веб-цвета", e);
        }
    }

    // Метод для преобразования объекта Mat в объект Image (JavaFX)
    public Image mat2Image(Mat videoMatImage) {
        int cols = videoMatImage.cols();
        int rows = videoMatImage.rows();

        if (cols <= 0 || rows <= 0) {
            return null;
        }

        // Определение типа изображения в зависимости от количества каналов
        int type = videoMatImage.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        int bufferSize = videoMatImage.channels() * cols * rows;

        // Получение данных изображения в виде байтового массива
        byte[] buffer = new byte[bufferSize];
        videoMatImage.get(0, 0, buffer);

        // Создание объекта BufferedImage
        BufferedImage image = new BufferedImage(cols, rows, type);

        // Копирование данных из байтового массива в массив пикселей BufferedImage
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

        // Преобразование BufferedImage в объект Image (JavaFX)
        return SwingFXUtils.toFXImage(image, null);
    }

    // Метод для обнаружения белых областей в изображении в пространстве цветов HSV
    public Mat whiteDetection(Mat hsvImage) {
        Mat threshedImg = new Mat();
        Scalar hsvMin = new Scalar(0, 0, 255);
        Scalar hsvMax = new Scalar(255, 10, 255);
        Core.inRange(hsvImage, hsvMin, hsvMax, threshedImg);
        return threshedImg;
    }

    // Метод для обнаружения зеленых областей в изображении в пространстве цветов HSV
    public Mat greenDetection(Mat hsvImage) {
        Mat threshedImg = new Mat();
        Scalar hsvMin = new Scalar(45, 0, 0);
        Scalar hsvMax = new Scalar(85, 255, 255);
        Core.inRange(hsvImage, hsvMin, hsvMax, threshedImg);
        return threshedImg;
    }

    // Метод для удаления шумов из кадра
    public Mat removeNoise(Mat frame) {
        Mat blurredImage = new Mat();
        Mat hsvImage = new Mat();

        // Размытие изображения
        Imgproc.blur(frame, blurredImage, new Size(5, 5));

        // Преобразование изображения в пространство цветов HSV
        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

        return hsvImage;
    }

    // Метод для применения морфологических операций (эрозия и расширение) к кадру
    public Mat morph(Mat frame) {
        Mat morphOutput = new Mat();

        // Определение элементов для морфологических операций
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(6, 6));
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));

        // Эрозия
        Imgproc.erode(frame, morphOutput, erodeElement);
        Imgproc.erode(morphOutput, morphOutput, erodeElement);

        // Расширение
        Imgproc.dilate(morphOutput, morphOutput, dilateElement);
        Imgproc.dilate(morphOutput, morphOutput, dilateElement);

        return morphOutput;
    }

    // Метод для получения точки изображения на основе обработки кадра
    public Point getPoint(Mat frame) {
        Mat noNoise = removeNoise(frame);
        Mat morphed = morph(noNoise);
        Mat white = whiteDetection(morphed);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        // Поиск контуров белых областей
        Imgproc.findContours(white, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint cont : contours) {
            Rect rect = Imgproc.boundingRect(cont);

            int padding = 20;
            int x = rect.x - padding;
            int y = rect.y - padding;
            int w = rect.width + padding * 2;
            int h = rect.height + padding * 2;

            try {
                // Выделение области интереса (ROI)
                Rect roi = new Rect(x, y, w, h);
                Mat rectCrop = frame.submat(roi);

                // Проверка, является ли объект зеленым
                if (isGreen(rectCrop)) {
                    double centerX = rect.x + 0.5 * rect.width;
                    double centerY = rect.y + 0.5 * rect.height;
                    return new Point(centerX, centerY);
                }
            } catch (CvException e) {
                // Обработка исключения (пустой блок)
            }
        }
        return null;
    }

    // Метод для проверки, является ли объект на изображении зеленым
    public boolean isGreen(Mat frame) {
        Mat noNoise = removeNoise(frame);
        Mat morphed = morph(noNoise);
        Mat green = greenDetection(morphed);
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        // Поиск контуров зеленых областей
        Imgproc.findContours(green, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Возвращение результата проверки: есть ли зеленые контуры на изображении
        return !contours.isEmpty();
    }

    public void btStartTimerOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        startTimer();
        btAddPoints.setVisible(true);
        btPause.setVisible(true);
        btDelete.setVisible(true);
        btDeleteOnAction(actionEvent);
        btStartTimer.setVisible(false);
    }

    public void btAddPointOnAction(ActionEvent actionEvent) {
        SoundManager.playButtonSound();
        addPoints();
    }

    private void addPoints() {
        if (isCurrentTeamOne) {
            pointTeamOne++;
            labelPointsTeamOne.setText(String.valueOf(pointTeamOne));
            GameData.countPointsTeamOne = pointTeamOne;
        } else {
            pointTeamTwo++;
            labelPointsTeamTwo.setText(String.valueOf(pointTeamTwo));
            GameData.countPointsTeamTwo = pointTeamTwo;
        }
    }
}
