package modelo;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;

public class AbrirConfiguracion {
    public static void abrir(){
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/configuracion", "Configuración");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
