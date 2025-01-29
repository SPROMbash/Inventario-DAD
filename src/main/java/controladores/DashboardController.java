package controladores;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.AbrirConfiguracion;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DashboardController {

    public void abrirAccesibilidad() {
        AbrirAccesibilidad.abrir();
    }

    public void abrirConfiguracion() {
        AbrirConfiguracion.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void salirDelPrograma(ActionEvent actionEvent) {
        System.exit(0);
    }


    public void mostrarMensajeNoDisponible(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Función no disponible");
        alert.setHeaderText(null);
        alert.setContentText("Esta función estará disponible en versiones posteriores.");
        alert.showAndWait();
    }

    public void abrirGestionModulos(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/gestionModulos", "Gestión de los Módulos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirConsultasModulos(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/consultasModulos", "Consultas de Módulos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta1(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/uno", "Marcajes de un Producto entre dos fechas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta2(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/dos", "Marcajes de un Aula entre dos fechas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta3(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/tres", "Marcajes de un Aula y de un Producto");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta4(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/cuatro", "Productos sin categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta5(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/cinco", "Número de productos por categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}