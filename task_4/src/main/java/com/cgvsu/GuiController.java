package com.cgvsu;

import com.cgvsu.SetModels.ModelManager;
import com.cgvsu.deletevertex.PolygonRemover;
import com.cgvsu.deletevertex.DeleteVertex;
import com.cgvsu.math.typesMatrix.Matrix4f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.ModelTransformer;
import com.cgvsu.model.TranslationModel;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objreader.ObjWriter;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.cgvsu.math.typesVectors.Vector3f;


public class GuiController {
    private static double[][] zBuffer;

    final private float TRANSLATION = 0.5F;



    //Поля для управления мышкой

    private double startX;
    private double startY;

    private Timeline timeline;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private TabPane settingsTab;

    @FXML
    private Button showSettingsButton;

    private Model oldModel = null;

    private Model triangulatedModel = null;

    private ModelManager modelManager = new ModelManager();


    //кнопки моделей
    public AnchorPane modelPane;
    private List<Button> addedButtonsModel = new ArrayList<>();
    private List<CheckBox> checkBoxesTexture = new ArrayList<>();
    private List<CheckBox> checkBoxesLighting = new ArrayList<>();
    private List<CheckBox> checkBoxesGrid = new ArrayList<>();
    //кнопки удаления моделей
    private List<Button> deletedButtonsModel = new ArrayList<>();

    //кнопочки для камеры
    public AnchorPane cameraPane;
    private List<Button> addedButtonsCamera = new ArrayList<>();
    private List<Button> deletedButtonsCamera = new ArrayList<>();


    //Для перемещения изменения масштаба и тд модели
    public TextField Sx;
    public TextField Sy;
    public TextField Sz;
    public TextField Tx;
    public TextField Ty;
    public TextField Tz;
    public TextField Rx;
    public TextField Ry;
    public TextField Rz;
    public Button convert;



    //для добавления камеры
    public TextField eyeX;
    public TextField targetX;
    public TextField eyeY;
    public TextField targetY;
    public TextField eyeZ;
    public TextField targetZ;



    private List<Camera> camerasList = new ArrayList<>();
    private Camera curCamera;


    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        addNewCamera(new Vector3f(0, 0, 100), new Vector3f(0, 0, 0));
        curCamera = camerasList.get(0);
        addCameraButtons();

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();
            zBuffer = new double[(int) width][(int) height];
            for (double[] doubles : zBuffer) {
                Arrays.fill(doubles, Double.POSITIVE_INFINITY);
            }

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            curCamera.setAspectRatio((float) (width / height));
            canvas.setOnMousePressed(this::handleMousePressed);
            canvas.setOnMouseDragged(this::handleMouseDragged);
            canvas.setOnScroll(this::mouseCameraZoom);

            if (modelManager != null) {
                for (Model model: modelManager.getModels()) {
                    canvas.getGraphicsContext2D().setStroke(Color.WHITE);
                    RenderEngine.render(canvas.getGraphicsContext2D(), curCamera, model, (int) width, (int) height,
                            zBuffer);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();

        setupDragAndDrop();
    }

    private void setupDragAndDrop() {
        canvas.setOnDragOver(event -> {
            if (event.getGestureSource() != canvas && event.getDragboard().hasFiles()) {
                File file = event.getDragboard().getFiles().get(0);
                if (file.getName().toLowerCase().endsWith(".obj")) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
            }
            event.consume();
        });

        canvas.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                File file = dragboard.getFiles().get(0);
                if (file.getName().toLowerCase().endsWith(".obj")) {
                    loadObjFile(file);
                } else {
                    showError("Неподдерживаемый файл", "Пожалуйста, выберите файл с расширением .obj");
                }
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }
    private void loadObjFile(File file) {
        Path fileName = Path.of(file.getAbsolutePath());
        try {
            String fileContent = Files.readString(fileName);
            oldModel = ObjReader.read(fileContent);
            triangulatedModel = ObjReader.read(fileContent);

            // Триангуляция и расчёт нормалей
            triangulatedModel.triangulate();
            triangulatedModel.normalize();

            modelManager.addModel(triangulatedModel);
            modelManager.setActiveModel(triangulatedModel);
            addModelButtons(triangulatedModel);
        } catch (IOException exception) {
            showError("Ошибка чтения файла", "Не удалось прочитать файл: " + exception.getMessage());
        } catch (Exception exception) {
            showError("Неизвестная ошибка", "Произошла неизвестная ошибка: " + exception.getMessage());
        }
    }



    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

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
            triangulatedModel.normalize();
            modelManager.addModel(triangulatedModel);
            modelManager.setActiveModel(triangulatedModel);
            addModelButtons(triangulatedModel);
        } catch (IOException exception) {
            showError("Ошибка чтения файла", "Не удалось прочитать файл"+ exception.getMessage());
        }
        catch (Exception exception) {
            showError("Неизвестная ошибка", "Произошла неизвестная ошибка" + exception.getMessage());
        }
    }
    @FXML
    public void saveModel(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Объектные файлы", "*.obj"));
        File file = fileChooser.showSaveDialog(null);

        if (modelManager.getActiveModel()!=null) {
            if (file != null) {
                String filename = file.getAbsolutePath();
                ObjWriter.write(modelManager.getActiveModel(), filename);

                System.out.println("Модель сохранена в файл: " + filename);
            } else {
                showError("Ошибка сохранения", "Введите имя");
            }
        }
        else {
            showError("Ошибка сохранения", "Нет модели");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void delvertex(ActionEvent actionEvent){
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Нельзя удалить что-то в модели, так как модели нет. Загрузите или выберите модель");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Удалить вершины");
        dialog.setHeaderText("Введите ID вершин для удаления (через запятую):");
        dialog.setContentText("Вершины:");

        String result = dialog.showAndWait().orElse("");

        if (!result.isEmpty()) {
            String[] verticesArray = result.split(",");
            List<Integer> verticesToDelete = new ArrayList<>();

            for (String vertexStr : verticesArray) {
                try {
                    verticesToDelete.add(Integer.parseInt(vertexStr.trim()));
                } catch (NumberFormatException e) {
                    showError("Ошибка ввода", "Некоторые элементы не являются целыми числами.");
                    return;
                }
            }
            boolean flag1 = askForFlag("Удалять нормали?");
            boolean flag2 = askForFlag("Удалять текстурные вершины?");

            DeleteVertex.deleteVertex(modelManager.getActiveModel(),verticesToDelete,flag1,flag2) ;
            activeModelnull();
        } else {
            showError("Ошибка", "Вы не ввели ни одной вершины.");
        }
    }
    private void activeModelnull(){
        if (modelManager.getActiveModel().getPolygons().isEmpty() || modelManager.getActiveModel().getVertices().isEmpty()) {
            boolean flag = askForFlag("Модель пуста. Вы хотите её удалить?");
            if (flag) {
                modelManager.delModels(modelManager.getActiveModel());
            }
        }
    }
    // Метод для запроса флажка true/false
    private boolean askForFlag(String headerText) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Да", "Да", "Нет");
        dialog.setTitle("Выбор флажка");
        dialog.setHeaderText(headerText);
        dialog.setContentText("Выберите флажок:");

        String result = dialog.showAndWait().orElse("false");

        return result.equals("true");
    }


    public void lightning(ActionEvent actionEvent) {
    }

    public void texture(ActionEvent actionEvent) {
    }
    public void addNewCamera(Vector3f cameraPos, Vector3f targetPos){
        camerasList.add(new Camera(cameraPos, targetPos, 1.0F, 1, 0.01F, 100));
        curCamera = camerasList.get(camerasList.size() - 1);

    }
    public void deleteCamera(int index){
        // Предполагается что индекс у камер будет начинаться с 1
        index -= 1;
        if (camerasList.size() == 1){
            showError("Ошибка удаления камеры","Если удалить текущую камеру - камер больше не останется!");
            return;
        }
        if (curCamera == camerasList.get(index)){
            if (index != 0) {
                showMessage("Удаление текущей камеры", "Вы перенаправлены на: Камера "+ (index) );
                curCamera = camerasList.get(index - 1);
            }
            else {
                showMessage("Удаление текущей камеры", "Вы перенаправлены на: Камера "+ (index + 1));
                curCamera = camerasList.get(index + 1);
            }
        }

        //переименовываем кнопки
        for (int i = 0; i < addedButtonsCamera.size(); i++) {
            if (i + 1 > index) {
                addedButtonsCamera.get(i).setText("Камера " + i);
            }
        }
        //смещаем координаты
        for (int i = addedButtonsCamera.size() - 1; i >= 1; i--) {
            if (i + 1 > index) {
                addedButtonsCamera.get(i).setLayoutY(addedButtonsCamera.get(i - 1).getLayoutY());

                deletedButtonsCamera.get(i).setLayoutY(deletedButtonsCamera.get(i - 1).getLayoutY());

            }
        }
        camerasList.remove(index);
        cameraPane.getChildren().remove(addedButtonsCamera.get(index));
        cameraPane.getChildren().remove(deletedButtonsCamera.get(index));
        addedButtonsCamera.remove(index);
        deletedButtonsCamera.remove(index);



    }

    public void setCurCamera(int index){
        index -= 1;
        curCamera = camerasList.get(index);
    }

    @FXML
    public void mouseCameraZoom(ScrollEvent scrollEvent) {
        curCamera.mouseCameraZoom(scrollEvent.getDeltaY());
    }

    private void handleMousePressed(MouseEvent mouseEvent) {
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
    }

    @FXML
    public void mouseCameraOrbit(MouseEvent mouseEvent) {
        curCamera.mouseOrbit(startX - mouseEvent.getX(), startY - mouseEvent.getY());
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
    }

    @FXML
    public void mouseCameraMove(MouseEvent mouseEvent) {
        curCamera.mousePan(startX - mouseEvent.getX(), startY - mouseEvent.getY());
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
    }

    private void handleMouseDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // Правая кнопка
            mouseCameraOrbit(mouseEvent);
        } else if (mouseEvent.getButton() == MouseButton.MIDDLE) { // Средняя кнопка
            mouseCameraMove(mouseEvent);
        }
    }

    public void toggleSettings(ActionEvent actionEvent) {
        settingsTab.setVisible(!settingsTab.isVisible());
        String arrow = (settingsTab.isVisible()) ? ">" : "<";
        showSettingsButton.setText(arrow);
        showSettingsButton.setTranslateX(325 - showSettingsButton.getTranslateX());
    }



    public void addModelButtons(Model mesh) {
        Button addButton = new Button("Модель " + (addedButtonsModel.size() + 1));
        addButton.setLayoutY((addedButtonsModel.size() > 0) ?
               checkBoxesLighting.get(checkBoxesLighting.size()-1).getLayoutY() + 50 :
                20);
        addButton.setLayoutX(20);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modelManager.setActiveModel(mesh);
                addButton.setStyle("-fx-background-color: #333;");
                for (Button button: addedButtonsModel) {
                    if (button != addButton) {
                        button.setStyle("-fx-background-color: #3c3f41;");
                    }
                }
            }
        });

        addedButtonsModel.add(addButton);
        for (int i =  0; i < addedButtonsModel.size(); i++){
            if( i == addedButtonsModel.size()-1){
                addedButtonsModel.get(i).setStyle("-fx-background-color: #333;");
            }
            else{
                addedButtonsModel.get(i).setStyle("-fx-background-color: #3c3f41;");
            }
        }

        Button deleteButton = new Button("Удалить");
        deleteButton.setLayoutY(addedButtonsModel.get(addedButtonsModel.size() - 1).getLayoutY());
        deleteButton.setLayoutX(addedButtonsModel.get(addedButtonsModel.size() - 1).getLayoutX() + 85);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteModel(Integer.parseInt(addButton.getText().replace("Модель ", "")));
                for (int i = 0; i < addedButtonsModel.size(); i++){
                    if (modelManager.getModels().get(i) == modelManager.getActiveModel()){
                        addedButtonsModel.get(i).setStyle("-fx-background-color: #333;");
                    }
                }
            }
        });
        deletedButtonsModel.add(deleteButton);



        //Сетка
        CheckBox checkBoxGrid = new CheckBox("Сетка");
        checkBoxGrid.setLayoutY(addedButtonsModel.get(addedButtonsModel.size() - 1).getLayoutY() + 40);
        checkBoxGrid.setLayoutX(20);
        checkBoxGrid.getStyleClass().add("checkbox"); // Применение стиля
        checkBoxGrid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mesh.isActivePolyGrid = !mesh.isActivePolyGrid;
            }
        });
        checkBoxesGrid.add(checkBoxGrid);

        //Тексутры
        CheckBox checkBoxTexture = new CheckBox("Текстура");
        checkBoxTexture.setLayoutY(checkBoxesGrid.get(checkBoxesGrid.size() - 1).getLayoutY() + 20);
        checkBoxTexture.setLayoutX(checkBoxesGrid.get(checkBoxesGrid.size() - 1).getLayoutX());
        checkBoxTexture.getStyleClass().add("checkbox"); // Применение стиля
        checkBoxTexture.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mesh.isActiveTexture){
                    mesh.isActiveTexture = false;
                    return;
                }
                if (mesh.pathTexture != null){
                    mesh.isActiveTexture = true;
                } else {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texture (*.png, *.jpg)", "*.png", "*.jpg"));
                    fileChooser.setTitle("Load Texture");
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

                    File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
                    if (file == null) {
                        return;
                    }

                    Path fileName = Path.of(file.getAbsolutePath());
                    mesh.pathTexture = String.valueOf(fileName);
                    mesh.isActiveTexture = true;
                }
            }
        });
        checkBoxesTexture.add(checkBoxTexture);

        // Освещение
        CheckBox checkBoxLighting = new CheckBox("Освещение");
        checkBoxLighting.setLayoutY(checkBoxesTexture.get(checkBoxesTexture.size() - 1).getLayoutY() + 20);
        checkBoxLighting.setLayoutX(checkBoxesTexture.get(checkBoxesTexture.size() - 1).getLayoutX());
        checkBoxLighting.getStyleClass().add("checkbox"); // Применение стиля
        checkBoxLighting.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mesh.isActiveLighting = !mesh.isActiveLighting;
            }
        });
        checkBoxesLighting.add(checkBoxLighting);



        modelPane.getChildren().add(addButton);
        modelPane.getChildren().add(deleteButton);
        modelPane.getChildren().add(checkBoxGrid);
        modelPane.getChildren().add(checkBoxTexture);
        modelPane.getChildren().add(checkBoxLighting);
    }




    public void deleteModel(int index) {
        if (modelManager.getActiveModel() == modelManager.getModels().get(index - 1)){
            if (index != 1) {
                showMessage("Удаление текущей модели", "Выбрана модель "+ index);
                modelManager.setActiveModel(modelManager.getModels().get(index - 2));
            } else if (modelManager.getModels().size()!=1) {
                showMessage("Удаление текущей модели", "Выбрана модель "+ (index+1));
                modelManager.setActiveModel(modelManager.getModels().get(index ));
            }
            else
            {
                showMessage("Удаление текущей модели", "Нет выбранной модели.");
                modelManager.setActiveModel(null);
            }
        }


        modelManager.delModels(modelManager.getModels().get(index - 1));
        modelPane.getChildren().remove(addedButtonsModel.get(index - 1));
        modelPane.getChildren().remove(deletedButtonsModel.get(index - 1));
        modelPane.getChildren().remove(checkBoxesGrid.get(index - 1));
        modelPane.getChildren().remove(checkBoxesTexture.get(index - 1));
        modelPane.getChildren().remove(checkBoxesLighting.get(index - 1));
        //переименовываем кнопки
        for (int i = 0; i < addedButtonsModel.size(); i++) {
            if (i + 1 > index) {
                addedButtonsModel.get(i).setText("Модель " + i);
            }
        }
        //смещаем координаты
        for (int i = addedButtonsModel.size() - 1; i >= 1; i--) {
            if (i + 1 > index) {
                addedButtonsModel.get(i).setLayoutY(addedButtonsModel.get(i - 1).getLayoutY());

                deletedButtonsModel.get(i).setLayoutY(deletedButtonsModel.get(i - 1).getLayoutY());
                checkBoxesGrid.get(i).setLayoutY(checkBoxesGrid.get(i - 1).getLayoutY());
                checkBoxesTexture.get(i).setLayoutY(checkBoxesTexture.get(i - 1).getLayoutY());
                checkBoxesLighting.get(i).setLayoutY(checkBoxesLighting.get(i - 1).getLayoutY());
            }
        }
        addedButtonsModel.remove(index - 1);
        deletedButtonsModel.remove(index - 1);
        checkBoxesGrid.remove(index - 1);
        checkBoxesTexture.remove(index - 1);
        checkBoxesLighting.remove(index - 1);


    }






    public void addCameraButtons() {
        Button addButton = new Button("Камера " + (addedButtonsCamera.size() + 1));
        addButton.setLayoutY((!addedButtonsCamera.isEmpty()) ?
                addedButtonsCamera.get(addedButtonsCamera.size() - 1).getLayoutY() + 70 :
                245);
        addButton.setLayoutX(20);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setCurCamera(Integer.parseInt(addButton.getText().replace("Камера ", "")));
                addButton.setStyle("-fx-background-color: #333;");
                for (Button button: addedButtonsCamera) {
                    if (button != addButton) {
                        button.setStyle("-fx-background-color: #3c3f41;");
                    }
                }
            }
        });
        addedButtonsCamera.add(addButton);
        for (int i =  0; i < addedButtonsCamera.size(); i++){
            if( i == addedButtonsCamera.size()-1){
                addedButtonsCamera.get(i).setStyle("-fx-background-color: #333;");
            }
            else{
                addedButtonsCamera.get(i).setStyle("-fx-background-color: #3c3f41;");
            }
        }

        Button deleteButton = new Button("Удалить");
        deleteButton.setLayoutY(addedButtonsCamera.get(addedButtonsCamera.size() - 1).getLayoutY());
        deleteButton.setLayoutX(addedButtonsCamera.get(addedButtonsCamera.size() - 1).getLayoutX() + 85);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteCamera(Integer.parseInt(addButton.getText().replace("Камера ", "")));
                for (int i = 0; i < addedButtonsCamera.size(); i++){
                    if (camerasList.get(i) == curCamera){
                        addedButtonsCamera.get(i).setStyle("-fx-background-color: #333;");
                    }
                }
            }
        });
        deletedButtonsCamera.add(deleteButton);



        cameraPane.getChildren().add(addButton);
        cameraPane.getChildren().add(deleteButton);

    }

    //кнопочка добавит камеру тут добавляется камера
    public void createCamera(MouseEvent mouseEvent) {
        Vector3f pos = new Vector3f(Float.parseFloat(eyeX.getText()),
                Float.parseFloat(eyeY.getText()), Float.parseFloat(eyeZ.getText()));
        Vector3f targetPos = new Vector3f(Float.parseFloat(targetX.getText()),
                Float.parseFloat(targetY.getText()), Float.parseFloat(targetZ.getText()));
        addCameraButtons();
        addNewCamera(pos, targetPos);

    }
    //кнопочка преобразовать тут её функция при нажатии
    public void convert(MouseEvent mouseEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Выберите или загрузите модель");
            return;
        }
        if (Objects.equals(Tx.getText(), "") || Objects.equals(Ty.getText(), "") || Objects.equals(Tz.getText(), "")
        || Objects.equals(Sx.getText(), "") || Objects.equals(Sy.getText(), "") || Objects.equals(Sz.getText(), "")
        || Objects.equals(Rx.getText(), "") || Objects.equals(Ry.getText(), "") || Objects.equals(Rz.getText(), "")) {
            showError("Ошибка", "Введите необходимые данные!");
        } else {
            Matrix4f transposeMatrix = ModelTransformer.modelMatrix(
                    Double.parseDouble(Tx.getText()), Double.parseDouble(Ty.getText()), Double.parseDouble(Tz.getText()),
                    Double.parseDouble(Rx.getText()), Double.parseDouble(Ry.getText()), Double.parseDouble(Rz.getText()),
                    Double.parseDouble(Sx.getText()), Double.parseDouble(Sy.getText()), Double.parseDouble(Sz.getText()));
            TranslationModel.move(transposeMatrix, modelManager.getActiveModel());
        }
    }

    //быстрые кнопочки для наташи

    //кнопочка перенести в начало координат
    public void MoveToTheOrigin(ActionEvent actionEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Выберите или загрузите модель");
            return;
        }
        Vector3f center = modelManager.getActiveModel().getCenter().multiplied(-1);
        Matrix4f transposeMatrix = ModelTransformer.translateMatrix(center.getX(), center.getY(), center.getZ());
        TranslationModel.move(transposeMatrix, modelManager.getActiveModel());
    }

    public void Rotate90x(ActionEvent actionEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Выберите или загрузите модель");
            return;
        }
        Matrix4f transposeMatrix = ModelTransformer.rotateMatrix(90, 0, 0);
        TranslationModel.move(transposeMatrix, modelManager.getActiveModel());
    }

    public void Rotate90y(ActionEvent actionEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Выберите или загрузите модель");
            return;
        }
        Matrix4f transposeMatrix = ModelTransformer.rotateMatrix(0, 90, 0);
        TranslationModel.move(transposeMatrix, modelManager.getActiveModel());
    }

    public void Rotate90z(ActionEvent actionEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Выберите или загрузите модель");
            return;
        }
        Matrix4f transposeMatrix = ModelTransformer.rotateMatrix(0, 0, 90);
        TranslationModel.move(transposeMatrix, modelManager.getActiveModel());
    }
    //Увеличить в 2 раза
    public void increase2(ActionEvent actionEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Выберите или загрузите модель");
            return;
        }
        Matrix4f transposeMatrix = ModelTransformer.scaleMatrix(2, 2, 2);
        TranslationModel.move(transposeMatrix, modelManager.getActiveModel());
    }
    //Уменьшить в 2 раза
    public void reduce2(ActionEvent actionEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Выберите или загрузите модель");
            return;
        }
        Matrix4f transposeMatrix = ModelTransformer.scaleMatrix(0.5, 0.5, 0.5);
        TranslationModel.move(transposeMatrix, modelManager.getActiveModel());
    }

    public void DelFace(ActionEvent actionEvent) {
        if (modelManager.getActiveModel()==null){
            showError("Ошибка","Нельзя удалить что-то в модели, так как модели нет. Загрузите или выберите модель");
            return;
        }
        // Создаем диалог для ввода списка вершин
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Удалить Полигон");
        dialog.setHeaderText("Введите ID Полигона для удаления :");
        dialog.setContentText("Полигон:");

        // Отображаем диалог и ждем ответа
        String result = dialog.showAndWait().orElse("");

        if (!result.isEmpty()) {
            // Разделяем введенные данные на список вершин и удаляем лишние пробелы
            String[] facesArray = result.split(",");
            List<Integer> faceToDelete = new ArrayList<>();

            // Преобразуем строки в целые числа и добавляем в список
            for (String vertexStr : facesArray) {
                try {
                    faceToDelete.add(Integer.parseInt(vertexStr.trim())); // парсим строку в Integer
                } catch (NumberFormatException e) {
                    showError("Ошибка ввода", "Некоторые элементы не являются целыми числами.");
                    return; // Выход из метода, если был неправильный ввод
                }
            }
            boolean flag1 = askForFlag("Удалять свободные вершины?");
            boolean flag2 = askForFlag("Удалять свободные нормали?");
            boolean flag3 = askForFlag("Удалять свободные текстурные вершины?");

            PolygonRemover.removePolygons(modelManager.getActiveModel(),faceToDelete,flag1,flag2,flag3) ;
            activeModelnull();
        } else {
            showError("Ошибка", "Вы не ввели ни одного полигона.");
        }
    }


    private void showMessage(String headText, String messageText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headText);
        alert.setContentText(messageText);
        alert.showAndWait();
    }

    public void cleanScene(ActionEvent actionEvent) {
        modelManager.cleanModels();
        addedButtonsModel.clear();
        deletedButtonsModel.clear();
        checkBoxesLighting.clear();
        checkBoxesTexture.clear();
        checkBoxesGrid.clear();
        modelPane.getChildren().clear();
    }
}