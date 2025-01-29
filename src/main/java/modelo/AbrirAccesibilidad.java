package modelo;

import com.example.inventario_hib.App;

public class AbrirAccesibilidad {

    public static void abrir() {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
