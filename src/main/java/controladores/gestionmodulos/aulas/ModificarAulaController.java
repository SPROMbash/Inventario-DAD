package controladores.gestionmodulos.aulas;

import DAO.AulaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Aula;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModificarAulaController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private Aula aulaSeleccionada;

    @FXML
    private Spinner<Integer> spinnerNumeracion1;
    @FXML
    private Spinner<Integer> spinnerNumeracion2;
    @FXML
    private Spinner<Integer> spinnerNumeracion3;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Spinner<Integer> spinnerIp1;
    @FXML
    private Spinner<Integer> spinnerIp2;
    @FXML
    private Spinner<Integer> spinnerIp3;
    @FXML
    private Spinner<Integer> spinnerIp4;

    @FXML
    private Button botonLimpiar;

    @FXML
    public void initialize() {
        aulaSeleccionada = (Aula) App.getUserData();

        spinnerIp1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));

        spinnerNumeracion1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinnerNumeracion2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinnerNumeracion3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0));

        limpiar();

        botonLimpiar.setOnAction(event -> {
            limpiar();
        });
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad.");
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

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/aulas/aulas", "Aulas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar() {
        txtDescripcion.setText(aulaSeleccionada.getDescripcion());

        String[] ip = aulaSeleccionada.getDireccionIp().split("\\.");
        spinnerIp1.getValueFactory().setValue(Integer.parseInt(ip[0]));
        spinnerIp2.getValueFactory().setValue(Integer.parseInt(ip[1]));
        spinnerIp3.getValueFactory().setValue(Integer.parseInt(ip[2]));
        spinnerIp4.getValueFactory().setValue(Integer.parseInt(ip[3]));

        String[] numeracion = aulaSeleccionada.getNumeracion().split("\\.");
        spinnerNumeracion1.getValueFactory().setValue(Integer.parseInt(numeracion[0]));
        spinnerNumeracion2.getValueFactory().setValue(Integer.parseInt(numeracion[1]));
        spinnerNumeracion3.getValueFactory().setValue(Integer.parseInt(numeracion[2]));
    }

    public void modificarAula(ActionEvent actionEvent) {
        if (comprobarCampos() && comprobarCamposModificados()) {
            aulaSeleccionada.setDescripcion(txtDescripcion.getText());
            aulaSeleccionada.setDireccionIp(getDireccionIp());
            aulaSeleccionada.setNumeracion(getNumeracion());
            if (aulaDAO.update(aulaSeleccionada)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Aula modfiicada");
                alert.setHeaderText("Aula modificada correctamente.");
                alert.setContentText("Descripción: " + txtDescripcion.getText() +
                        "\nDirección IP: " + getDireccionIp() +
                        "\nNumeración: " + spinnerNumeracion1.getValue() + "." + spinnerNumeracion2.getValue() + "." + spinnerNumeracion3.getValue());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modificar Aula");
                alert.setHeaderText("Error al modificar el aula.");
                alert.showAndWait();
            }
        }
    }

    private void establecerSpinnerIp(String[] campos) {
        spinnerIp1.getValueFactory().setValue(Integer.parseInt(campos[0]));
        spinnerIp2.getValueFactory().setValue(Integer.parseInt(campos[1]));
        spinnerIp3.getValueFactory().setValue(Integer.parseInt(campos[2]));
        spinnerIp4.getValueFactory().setValue(Integer.parseInt(campos[3]));
    }
    private void establecerSpinnerNumeracion(String[] campos) {
        spinnerNumeracion1.getValueFactory().setValue(Integer.parseInt(campos[0]));
        spinnerNumeracion2.getValueFactory().setValue(Integer.parseInt(campos[1]));
        spinnerNumeracion3.getValueFactory().setValue(Integer.parseInt(campos[2]));
    }

    private List<Long> obtenerIdAulas() {
        List<Aula> aulas = aulaDAO.getTodos();
        List<Long> ids = new ArrayList<>();
        for (Aula aula : aulas) {
            ids.add(aula.getIdAula());
        }
        return ids;
    }

    private boolean comprobarCampos() {
        if (!txtDescripcion.getText().isEmpty() && comprobarSpinnerIp() && comprobarSpinnerNumeracion()) {
            return true;
        }
        return false;
    }

    private boolean comprobarSpinnerIp() {
        if (spinnerIp1.getValue() != null && spinnerIp2.getValue() != null && spinnerIp3.getValue() != null && spinnerIp4.getValue() != null) {
            String ip = getDireccionIp();
            if (ip.matches("^(?:\\d{1,3}\\.){3}\\d{1,3}$")) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean comprobarSpinnerNumeracion() {
        if (spinnerNumeracion1.getValue() != null && spinnerNumeracion2.getValue() != null && spinnerNumeracion3.getValue() != null) {
            String numeracion = getNumeracion();
            if (numeracion.matches("^\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$")) {
                return true;
            }
            return false;
        }
        return false;
    }

    private String getNumeracion() {
        return spinnerNumeracion1.getValue() + "." + spinnerNumeracion2.getValue() + "." + spinnerNumeracion3.getValue();
    }

    private String getDireccionIp() {
        return spinnerIp1.getValue() + "." + spinnerIp2.getValue() + "." + spinnerIp3.getValue() + "." + spinnerIp4.getValue();
    }

    private boolean comprobarCamposModificados() {
        if (aulaSeleccionada.getDescripcion().equals(txtDescripcion.getText()) &&
                aulaSeleccionada.getDireccionIp().equals(getDireccionIp()) &&
                aulaSeleccionada.getNumeracion().equals(getNumeracion())) {
            return false;
        }
        return true;
    }
}
