package controladores.gestionmodulos;

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

public class GestionModulosController {

    public void abrirAulas(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/aulas/aulas", "Aulas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirProductos(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/productos/productos", "Productos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirMarcajes(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/marcajes", "Marcajes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/productoCategoria/productoCategoria", "Producto por Categoría.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirCategorias(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/categorias", "Categorías.");
        } catch (Exception e) {
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

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("dashboard", "PROGRAMA DE GESTIÓN DE INVENTARIO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirConfiguracion(ActionEvent actionEvent) {
        AbrirConfiguracion.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }
}