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
                MostrarAlerta.AlertaError("Error al abrir la ayuda", "Ocurrió un error al intentar abrir el archivo de ayuda. Por favor, inténtelo de nuevo.", "Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
          MostrarAlerta.AlertaError("Error al abrir la ayuda", "Ocurrió un error al intentar abrir el archivo de ayuda. Por favor, inténtelo de nuevo.", "Error");
        }
    }
}
