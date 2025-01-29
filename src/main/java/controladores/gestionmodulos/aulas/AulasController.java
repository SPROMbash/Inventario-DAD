package controladores.gestionmodulos.aulas;

import DAO.AulaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.AbrirConfiguracion;
import modelo.Aula;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AulasController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private Aula aulaSeleccionada;

    @FXML
    private TextField txtNumeracion;
    @FXML
    private TextField txtDescripcion;

    @FXML
    private TableView<Aula> tablaDeAulas;
    @FXML
    private TableColumn<Aula, Long> columnaIdAula;
    @FXML
    private TableColumn<Aula, String> columnaNumeracion;
    @FXML
    private TableColumn<Aula, String> columnaDescripcion;
    @FXML
    private TableColumn<Aula, String> columnaIp;
    @FXML
    private Button botonLimpiar;

    @FXML
    public void initialize() {
        columnaIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        columnaNumeracion.setCellValueFactory(new PropertyValueFactory<>("numeracion"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaIp.setCellValueFactory(new PropertyValueFactory<>("direccionIp"));
        cargarAulas();

        txtNumeracion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarNumeracion();
        });

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        tablaDeAulas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            aulaSeleccionada = newValue;
        });

        botonLimpiar.setOnAction(event ->
                limpiar()
        );
    }

    private void cargarAulas() {
        List<Aula> aulas = aulaDAO.getTodos();
        tablaDeAulas.getItems().setAll(aulas);
    }

    private void filtrarNumeracion() {
        if (!txtNumeracion.getText().isEmpty()) {
            List<Aula> aulas = aulaDAO.getPorNumeracion(txtNumeracion.getText());
            tablaDeAulas.getItems().clear();
            tablaDeAulas.getItems().setAll(aulas);
        } else {
            cargarAulas();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Aula> aulas = aulaDAO.getPorDescripcion(txtDescripcion.getText());
            tablaDeAulas.getItems().clear();
            tablaDeAulas.getItems().setAll(aulas);
        } else {
            cargarAulas();
        }
    }

    public void crearAula(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/aulas/crearAula", "Crear aulas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificarAula(MouseEvent mouseEvent) {
        try {
            if (aulaSeleccionada != null) {
                App.setUserData(aulaSeleccionada);
                App.setRoot("/com/example/inventario_hib/gestionmodulos/aulas/modificarAula", "Modificar aulas");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un aula para poder modificarla.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarAula(MouseEvent mouseEvent) {
        try {
            if (aulaSeleccionada != null) {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setTitle("Eliminar aula");
                alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar el aula?");
                alertConfirmation.setContentText("Se eliminará el aula seleccionada.");
                alertConfirmation.showAndWait();
                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {
                    if (aulaDAO.delete(aulaSeleccionada)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Aula eliminada.");
                        alert.setContentText("El aula seleccionada ha sido eliminada correctamente.");
                        alert.showAndWait();
                        cargarAulas();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar el aula.");
                        alert.setContentText("Ha ocurrido un error al intentar eliminar el aula seleccionada.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un aula para poder modificarla.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void abrirConfiguracion(){
        AbrirConfiguracion.abrir();
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
            App.setRoot("/com/example/inventario_hib/gestionmodulos/gestionModulos", "Gestión de los distintos módulos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        txtNumeracion.clear();
        txtDescripcion.clear();
        cargarAulas();
    }
}
