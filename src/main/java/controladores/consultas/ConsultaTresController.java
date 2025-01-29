package controladores.consultas;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import DAO.ProductoDAO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaTresController {
    private static final AulaDAO aulaDAO = new AulaDAO();
    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final MarcajeDAO marcajeDAO = new MarcajeDAO();

    private Aula aulaSeleccionada;
    private Producto productoSeleccionado;

    @FXML
    private ComboBox<Long> comboBoxIdProducto;
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
    public void initialize() {
        columnaIdMarcaje.setCellValueFactory(new PropertyValueFactory<>("idMarcaje"));
        columnaIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        comboBoxIdAula.getItems().setAll(obtenerIdAulas());
        comboBoxIdProducto.getItems().setAll(obtenerIdProductos());

        comboBoxIdAula.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (comboBoxIdAula.getValue() != null) {
                aulaSeleccionada = aulaDAO.obtenerPorId(comboBoxIdAula.getValue());
            }
            cargarDatos();
        });

        comboBoxIdProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (comboBoxIdProducto.getValue() != null) {
                productoSeleccionado = productoDAO.obtenerPorId(comboBoxIdProducto.getValue());
            }
            cargarDatos();
        });
    }

    private List<Long> obtenerIdAulas() {
        List<Aula> aulas = aulaDAO.getTodos();
        List<Long> idAulas = new ArrayList<>();
        for (Aula aula : aulas) {
            idAulas.add(aula.getIdAula());
        }
        return idAulas;
    }

    private List<Long> obtenerIdProductos() {
        List<Producto> productos = productoDAO.getTodos();
        List<Long> idProductos = new ArrayList<>();
        for (Producto producto : productos) {
            idProductos.add(producto.getIdProducto());
        }
        return idProductos;
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
        comboBoxIdProducto.setValue(null);
        comboBoxIdAula.setValue(null);
        tablaDeMarcajes.getItems().clear();
    }

    private void cargarDatos() {
        if (aulaSeleccionada != null && productoSeleccionado != null) {
            List<Marcaje> marcajes = marcajeDAO.obtenerMarcajesAulaYProducto(aulaSeleccionada, productoSeleccionado);
            tablaDeMarcajes.getItems().setAll(marcajes);
        }
    }
}
