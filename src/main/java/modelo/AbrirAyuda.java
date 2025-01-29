package modelo;

import com.example.inventario_hib.App;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AbrirAyuda {

    public static void abrir() {
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
}
