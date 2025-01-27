package controladores.opcionesMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BotonLimpiarController {
    @FXML
    private Button botonLimpiar;

    private EventHandler<ActionEvent> onAction;

    public void setOnAction(EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
    }

    public void limpiar(ActionEvent actionEvent) {
        if (onAction != null) {
            onAction.handle(actionEvent);
        }
    }
}
