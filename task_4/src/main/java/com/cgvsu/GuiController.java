package com.cgvsu;

import com.cgvsu.SetModels.ModelManager;
import com.cgvsu.deletevertex.DeleteVertex;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.model.FindNormals;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriterClass;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class GuiController {
    private static double[][] zBuffer;

    final private float TRANSLATION = 0.5F;
    private Timeline timeline;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model oldModel = null;

    private Model triangulatedModel = null;

    private boolean triangulated = false;

    private ModelManager modelManager = new ModelManager();

    private boolean polyGrid = false;

    private boolean coloring = true;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);


    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            zBuffer = new double[(int) width][(int) height];
            for (int i = 0; i < zBuffer.length; i++) {
                for (int j = 0; j < zBuffer[i].length; j++) {
                    zBuffer[i][j] = Double.POSITIVE_INFINITY;
                }
            }

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (modelManager != null) {
                for (Model model: modelManager.getModels()) {
                    canvas.getGraphicsContext2D().setStroke(Color.WHITE);
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, model, (int) width, (int) height,
                            zBuffer, polyGrid, coloring);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            oldModel = ObjReader.read(fileContent);
            triangulatedModel = ObjReader.read(fileContent);
            // Триангуляция и расчёт нормалей
            triangulatedModel.triangulate();
            modelManager.addModel(oldModel);
            modelManager.setActiveModel(oldModel);
        } catch (IOException exception) {
            showError("Ошибка чтения файла", "Не удалось прочитать файл"+ exception.getMessage());
        } /*catch (InvalidFileFormatException exception) {
            showError("Некорректный файл", "Файл имеет неправильный формат"+ exception.getMessage());
        }*/ catch (Exception exception) {
            showError("Неизвестная ошибка", "Произошла неизвестная ошибка" + exception.getMessage());
        }
    }
    @FXML
    public void saveModel(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Объектные файлы", "*.obj"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            String filename = file.getAbsolutePath();
            // Создаем экземпляр ObjWriterClass для записи модели
            ObjWriterClass objWriter = new ObjWriterClass();
            objWriter.write(modelManager.getActiveModel(), filename);  // Сохраняем модель

            System.out.println("Модель сохранена в файл: " + filename);
        }
        else {
            showError("Ошибка сохранения","Нет модели");
        }
    }
    // Метод для отображения сообщения об ошибке
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void delvertex(ActionEvent actionEvent){
        // Создаем диалог для ввода списка вершин
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Удалить вершины");
        dialog.setHeaderText("Введите ID вершин для удаления (через запятую):");
        dialog.setContentText("Вершины:");

        // Отображаем диалог и ждем ответа
        String result = dialog.showAndWait().orElse("");

        if (!result.isEmpty()) {
            // Разделяем введенные данные на список вершин и удаляем лишние пробелы
            String[] verticesArray = result.split(",");
            List<Integer> verticesToDelete = new ArrayList<>();

            // Преобразуем строки в целые числа и добавляем в список
            for (String vertexStr : verticesArray) {
                try {
                    verticesToDelete.add(Integer.parseInt(vertexStr.trim())); // парсим строку в Integer
                } catch (NumberFormatException e) {
                    showError("Ошибка ввода", "Некоторые элементы не являются целыми числами.");
                    return; // Выход из метода, если был неправильный ввод
                }
            }
            boolean flag1 = askForFlag("Удалять нормали?");
            boolean flag2 = askForFlag("Удалять текстурные вершины?");
            // Создаем экземпляр DeleteVertex для удаления вершин

            DeleteVertex.deleteVertex(modelManager.getActiveModel(),verticesToDelete,flag1,flag2) ;
            // Удаляем вершины
            activeModelnull();
        } else {
            showError("Ошибка", "Вы не ввели ни одной вершины.");
        }
    }
    private void activeModelnull(){
        if(modelManager.getActiveModel().getVertices().isEmpty()){
            System.out.println(1);
            modelManager.delModels(modelManager.getActiveModel());
            if (!modelManager.getModels().isEmpty()){
                modelManager.setActiveModel(modelManager.getModels().get(modelManager.getModels().size()-1));
            }
        }
    }
    // Метод для запроса флажка true/false для каждой вершины
    private boolean askForFlag(String headerText) {
        // Создаем диалог для ввода флажка (true/false)
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Да", "Да", "Нет");
        dialog.setTitle("Выбор флажка");
        dialog.setHeaderText(headerText);
        dialog.setContentText("Выберите флажок:");

        // Отображаем диалог и ждем ответа
        String result = dialog.showAndWait().orElse("false");

        return result.equals("true");
    }





    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    public void setRenderStyleToColorFill(ActionEvent actionEvent) {
        coloring = !coloring;
    }

    public void switchPolygonalGrid(ActionEvent actionEvent) {
        polyGrid = !polyGrid;
    }
    @FXML
    public void onModelSelectionChanged(ActionEvent actionEvent) {
    }


    public void chooseModel(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Какую модель выбрать?");
        dialog.setHeaderText("Введите ID модели:");
        dialog.setContentText("Модель:");

        // Отображаем диалог и ждем ответа
        String result = dialog.showAndWait().orElse("");

        if (!result.isEmpty()) {
            try {
                modelManager.setActiveModel(modelManager.getModels().get(Integer.parseInt(result)-1));
            } catch (NumberFormatException e) {
                showError("Ошибка ввода", "Элемент не является целым числом.");
            }
            catch (IndexOutOfBoundsException e){
                showError("Ошибка выбора модели","Индекса такой модели нет");
            }
        }
    }

    public void DeleteModel(ActionEvent actionEvent) {
        modelManager.delModels(modelManager.getActiveModel());
        if (!modelManager.getModels().isEmpty()){
            modelManager.setActiveModel(modelManager.getModels().get(modelManager.getModels().size()-1));
        }
        else{
            modelManager.setActiveModel(null);
        }
    }

    public void triangulation(ActionEvent actionEvent) {
        if (triangulated){
            modelManager.delModels(triangulatedModel);
            modelManager.addModel(oldModel);
            modelManager.setActiveModel(oldModel);
            triangulated = false;
        } else {
            modelManager.delModels(oldModel);
            modelManager.addModel(triangulatedModel);
            modelManager.setActiveModel(triangulatedModel);
            triangulated = true;
        }
    }

    public void lightning(ActionEvent actionEvent) {
    }

    public void texture(ActionEvent actionEvent) {
    }
}