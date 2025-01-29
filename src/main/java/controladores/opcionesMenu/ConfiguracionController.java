package controladores.opcionesMenu;

import javafx.event.ActionEvent;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;

public class ConfiguracionController {

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }
}
