package controladores.opcionesMenu;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import modelo.AbrirAyuda;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AccesibilidadController {

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void mostrarMensajeNoDisponible(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Función no disponible");
        alert.setHeaderText(null);
        alert.setContentText("Esta función estará disponible en versiones posteriores.");
        alert.showAndWait();
    }

    public void abrirConfiguracion(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/configuracion", "Configuración");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("dashboard", "PROGRAMA DE GESTIÓN DE INVENTARIO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
