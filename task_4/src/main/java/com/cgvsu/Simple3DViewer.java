package com.cgvsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;  // Используем javafx.scene.control.Label
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));

        Scene scene = new Scene(viewport);
        stage.setMinWidth(1600);
        stage.setMinHeight(900);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("3DViewer");
        stage.setScene(scene);
        stage.show();
    }
    private void openSecondWindow() {
        // Создаем второе окно
        Stage secondStage = new Stage();

        // Создаем метку с текстом, который будет отображаться во втором окне
        Label label = new Label("Это второе окно!");

        // Создаем контейнер для второго окна
        StackPane secondaryRoot = new StackPane();
        secondaryRoot.getChildren().add(label);

        // Создаем сцену для второго окна
        Scene secondScene = new Scene(secondaryRoot, 250, 150);

        // Настройка второго окна
        secondStage.setTitle("Второе окно");
        secondStage.setScene(secondScene);
        secondStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}