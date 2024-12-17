package com.cgvsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;  // Используем javafx.scene.control.Label
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));



        Scene scene = new Scene(viewport);
        stage.setMinWidth(1600);
        stage.setMinHeight(900);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());
        scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());


        stage.setTitle("3DViewer");
        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch();
    }
}