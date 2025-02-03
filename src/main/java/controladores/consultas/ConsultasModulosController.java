package controladores.consultas;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.AbrirConfiguracion;

import java.io.IOException;

public class ConsultasModulosController {

    public void abirConsulta1(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/primera", "MARCAJES DE UN PRODUCTO ORDENADO POR FECHA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta2(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/segunda", "MARCAJES DE UN AULA ENTRE DOS FECHAS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta3(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/tercera", "MARCAJES DE UN PRODUCTO Y UN AULA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta4(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/cuarta", "PRODUCTOS SIN CATEGORÍA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta5(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/quinta", "CATEGORÍAS CON NÚMERO DE PRODUCTOS EN ELLAS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirAccesibilidad() {
        AbrirAccesibilidad.abrir();
    }

    public void abrirConfiguracion() {
        AbrirConfiguracion.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("dashboard", "PROGRAMA DE GESTIÓN DE INVENTARIO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarMensajeNoDisponible(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Función no disponible");
        alert.setHeaderText(null);
        alert.setContentText("Esta función estará disponible en versiones posteriores.");
        alert.showAndWait();
    }
}
