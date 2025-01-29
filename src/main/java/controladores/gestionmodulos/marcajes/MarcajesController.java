package controladores.gestionmodulos.marcajes;

import DAO.MarcajeDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MarcajesController {
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();
    private Marcaje marcajeSeleccionado;

    @FXML
    private DatePicker FechaInicio;
    @FXML
    private DatePicker FechaFinal;
    @FXML
    private TableView<Marcaje> tablaDeMarcajes;
    @FXML
    private TableColumn<Marcaje, Long> columnaIdMarcaje;
    @FXML
    private TableColumn<Marcaje, Long> columnaIdAula;
    @FXML
    private TableColumn<Marcaje, Long> columnaIdProducto;
    @FXML
    private TableColumn<Marcaje, Date> columnaFecha;
    @FXML
    private TableColumn<Marcaje, Boolean> columnaTipo;

    @FXML
    public void initialize() {
        columnaIdMarcaje.setCellValueFactory(new PropertyValueFactory<>("idMarcaje"));
        columnaIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cargarMarcajes();

        FechaInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (FechaInicio.getValue() != null && FechaFinal.getValue() != null) {
                if (comprobarInicioYFin()) {
                    filtrarEntreFechas();
                }
            }
        });

        FechaFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
            filtrarEntreFechas();
        });

        tablaDeMarcajes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            marcajeSeleccionado = newValue;
        });
    }

    private void filtrarEntreFechas() {
        if (comprobarFechas() && comprobarInicioYFin()) {
            LocalDate inicio = FechaInicio.getValue();
            LocalDate fin = FechaFinal.getValue();
            List<Marcaje> marcajes = marcajeDAO.obtenerEntreFechas(inicio, fin);
            tablaDeMarcajes.getItems().setAll(marcajes);
        }
    }

    private void cargarMarcajes() {
        List<Marcaje> marcajes = marcajeDAO.getTodos();
        tablaDeMarcajes.getItems().setAll(marcajes);
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

    public void crearMarcaje(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/crearMarcaje", "Crear Marcaje");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarMarcaje(MouseEvent mouseEvent) {
        try {
            if (marcajeSeleccionado != null) {
                App.setUserData(marcajeSeleccionado);
                App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/modificarMarcaje", "Modificar Marcaje");
            } else {
                MostrarAlerta.AlertaError("Error", "Error", "Debes seleccionar un marcaje para poder modificarlo");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarMarcaje(MouseEvent mouseEvent) {
        try {
            if (marcajeSeleccionado != null) {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setTitle("Eliminar marcaje");
                alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar el marcaje?");
                alertConfirmation.showAndWait();
                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {
                    if (marcajeDAO.delete(marcajeSeleccionado)) {
                        MostrarAlerta.AlertaExitosa("Eliminación exitosa", "Marcaje eliminado", "Se ha eliminado el marcaje correctamente.");
                        cargarMarcajes();
                    } else {
                        MostrarAlerta.AlertaError("Error", "Error al intentar eliminar el marcaje", "No se ha podido eliminar el marcaje");
                    }
                }
            } else {
                MostrarAlerta.AlertaError("Error", "Error", "Debes seleccionar un marcaje para poder eliminarlo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar() {
        FechaInicio.setValue(null);
        FechaFinal.setValue(null);
        cargarMarcajes();
    }

    private boolean comprobarFechas() {
        if (FechaInicio.getValue() == null || FechaFinal.getValue() == null) {
            return false;
        }
        return true;
    }

    private boolean comprobarInicioYFin() {
        if (!comprobarFechas()) {
            return false;
        }
        if (FechaInicio.getValue().isAfter(FechaFinal.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el rango de fechas");
            alert.setContentText("La fecha de inicio no puede ser posterior a la fecha final. Por favor, corrige el rango de fechas.");
            alert.showAndWait();
            FechaInicio.setValue(null);
            FechaFinal.setValue(null);
            return false;
        }
        return true;
    }
}
