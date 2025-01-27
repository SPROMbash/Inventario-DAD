package controladores;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DashboardController {

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirConfiguracion(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/configuracion", "Configuración");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        try {
            String rutaAyuda = "C:/Users/tecen/Documents/HelpNDoc/Output/chm/AyudaApp.chm";

            File archivoAyuda = new File(rutaAyuda);

            if (archivoAyuda.exists()) {
                Desktop.getDesktop().open(archivoAyuda);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Archivo de ayuda no encontrado");
                alert.setContentText("No se pudo encontrar el archivo de ayuda en la ruta especificada:\n" + rutaAyuda);
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al abrir la ayuda");
            alert.setContentText("Ocurrió un error al intentar abrir el archivo de ayuda. Por favor, inténtelo de nuevo.");
            alert.showAndWait();
        }
    }

    public void salirDelPrograma(ActionEvent actionEvent) {
        System.exit(0);
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
}