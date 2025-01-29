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

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("dashboard", "Productos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
