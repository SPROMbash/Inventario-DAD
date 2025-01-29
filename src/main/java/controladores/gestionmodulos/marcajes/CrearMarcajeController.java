package controladores.gestionmodulos.marcajes;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrearMarcajeController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();
    @FXML
    private ComboBox<Long> comboBoxIdAulas;
    @FXML
    private ComboBox<Long> comboBoxIdProductos;
    @FXML
    private ToggleButton toggleButtonEntrada;
    @FXML
    private ToggleButton toggleButtonSalida;

    @FXML
    public void initialize() {
        comboBoxIdAulas.getItems().setAll(obtenerIdAulas());
        comboBoxIdProductos.getItems().setAll(obtenerIdProductos());
        ToggleGroup tipoMarcajeGroup = new ToggleGroup();
        toggleButtonEntrada.setToggleGroup(tipoMarcajeGroup);
        toggleButtonSalida.setToggleGroup(tipoMarcajeGroup);
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/marcajes", "Marcajes");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void crearMarcaje(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            Marcaje marcaje = new Marcaje();
            marcaje.setIdProducto(productoDAO.obtenerPorId(comboBoxIdProductos.getValue()));
            marcaje.setIdAula(aulaDAO.obtenerPorId(comboBoxIdAulas.getValue()));
            marcaje.setTipo(toggleButtonEntrada.isSelected());
            if (marcajeDAO.create(marcaje)) {
                MostrarAlerta.AlertaExitosa("Creación exitosa", "Marcaje creado", "Se ha creado el marcaje correctamente");
                limpiar();
            } else {
                MostrarAlerta.AlertaError("Error", "Error al intentar crear el marcaje", "No se ha podido crear el marcaje");
            }
        }
    }

    @FXML
    private void limpiar() {
        comboBoxIdProductos.getSelectionModel().clearSelection();
        comboBoxIdAulas.getSelectionModel().clearSelection();
        toggleButtonEntrada.setSelected(false);
        toggleButtonSalida.setSelected(false);
    }

    private boolean comprobarCampos() {
        if (comboBoxIdAulas.getSelectionModel().isEmpty() || comboBoxIdProductos.getSelectionModel().isEmpty() || (!toggleButtonEntrada.isSelected() && !toggleButtonSalida.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Crear Marcaje");
            alert.setHeaderText("Error al crear el marcaje.");
            alert.setContentText("Debes rellenar todos los campos para poder crear el marcaje.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
