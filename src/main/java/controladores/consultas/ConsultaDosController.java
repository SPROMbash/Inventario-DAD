package controladores.consultas;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaDosController {
    private static final AulaDAO aulaDAO = new AulaDAO();
    private static final MarcajeDAO marcajeDAO = new MarcajeDAO();
    private Aula aulaSeleccionada;

    @FXML
    private ComboBox<Long> comboBoxIdAula;
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
    private DatePicker FechaInicio;
    @FXML
    private DatePicker FechaFinal;

    @FXML
    public void initialize() {
        columnaIdMarcaje.setCellValueFactory(new PropertyValueFactory<>("idMarcaje"));
        columnaIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        comboBoxIdAula.getItems().setAll(obtenerIdAulas());

        comboBoxIdAula.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (comboBoxIdAula.getValue() != null) {
                aulaSeleccionada = aulaDAO.obtenerPorId(comboBoxIdAula.getValue());
                filtrarEntreFechas();
            }
        });

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
    }

    private List<Long> obtenerIdAulas() {
        List<Aula> productos = aulaDAO.getTodos();
        List<Long> idAulas = new ArrayList<>();
        for (Aula aula : productos) {
            idAulas.add(aula.getIdAula());
        }
        return idAulas;
    }

    private void filtrarEntreFechas() {
        if (comprobarFechas() && comprobarInicioYFin()) {
            LocalDate inicio = FechaInicio.getValue();
            LocalDate fin = FechaFinal.getValue();
            List<Marcaje> marcajes = marcajeDAO.obtenerMarcajesDeAulaEntreFechas(aulaSeleccionada, inicio, fin);
            tablaDeMarcajes.getItems().setAll(marcajes);
        }
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/consultasModulos", "CONSULTAS PARA LOS DIFERENTES MÓDULOS");
        } catch (IOException e) {
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

    public void abrirConfiguracion(){
        AbrirConfiguracion.abrir();
    }

    @FXML
    private void limpiar() {
        comboBoxIdAula.setValue(null);
        FechaInicio.setValue(null);
        FechaFinal.setValue(null);
        tablaDeMarcajes.getItems().clear();
    }

    private boolean comprobarLista() {
        if (comboBoxIdAula.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la selección de aula");
            alert.setContentText("Por favor, selecciona un aula de la lista desplegable.");
            alert.showAndWait();
            return false;
        }
        return true;
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
