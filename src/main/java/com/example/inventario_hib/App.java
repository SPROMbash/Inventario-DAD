package com.example.inventario_hib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("dashboard"), 950, 600);
        stage.setTitle("Programa de gestión de inventario");
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/styles/tabla.css").toExternalForm());
        stage.show();
    }

    public static void setRoot(String fxmlPath, String title) throws IOException {
        scene.setRoot(loadFXML(fxmlPath));
        Stage stage = (Stage) scene.getWindow();
        stage.sizeToScene();
        stage.setTitle(title);
    }

    private static Parent loadFXML(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setUserData(Object objeto) {
        Parent root = scene.getRoot();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setUserData(objeto);
    }

    public static Object getUserData() {
        Parent root = scene.getRoot();
        Stage stage = (Stage) root.getScene().getWindow();
        return stage.getUserData();
    }

    public static void main(String[] args) {
        launch();
    }
}