package controladores.gestionmodulos.marcajes;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import modelo.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModificarMarcajeController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();

    private Marcaje marcajeSeleccionado;

    @FXML
    private ComboBox<Long> comboBoxIdAulas;
    @FXML
    private ComboBox<Long> comboBoxIdProductos;
    @FXML
    private ToggleButton toggleButtonEntrada;
    @FXML
    private ToggleButton toggleButtonSalida;
    @FXML
    private DatePicker FechaMarcaje;

    @FXML
    public void initialize() {

        marcajeSeleccionado = (Marcaje) App.getUserData();
        comboBoxIdProductos.getItems().setAll(obtenerIdProductos());
        comboBoxIdAulas.getItems().setAll(obtenerIdAulas());
        rellenarCampos();
        ToggleGroup tipoMarcajeGroup = new ToggleGroup();
        toggleButtonEntrada.setToggleGroup(tipoMarcajeGroup);
        toggleButtonSalida.setToggleGroup(tipoMarcajeGroup);

    }

    private void rellenarCampos() {
        comboBoxIdAulas.setValue(marcajeSeleccionado.getIdAula().getIdAula());
        comboBoxIdProductos.setValue(marcajeSeleccionado.getIdProducto().getIdProducto());
        FechaMarcaje.setValue(convertirALocalDate(marcajeSeleccionado.getFecha()));
        toggleButtonEntrada.setSelected(marcajeSeleccionado.isTipo());
        toggleButtonSalida.setSelected(!marcajeSeleccionado.isTipo());
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
            App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/marcajes", "Marcajes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarMarcaje(ActionEvent actionEvent) {
        if (comprobarCampos() && comprobarCamposModificados()) {
            Marcaje marcaje = new Marcaje();
            marcaje.setIdAula(new Aula(comboBoxIdAulas.getValue()));
            marcaje.setIdProducto(new Producto(comboBoxIdProductos.getValue()));
            marcaje.setFecha(java.sql.Date.valueOf(FechaMarcaje.getValue()));
            marcaje.setTipo(toggleButtonEntrada.isSelected());
            if (marcajeDAO.update(marcaje)) {
                MostrarAlerta.AlertaExitosa("Modificación exitosa", "Marcaje modificado", "Se ha modificado el marcaje correctamente");
            } else {
                MostrarAlerta.AlertaError("Error", "Error al intentar modificar el marcaje", "No se ha podido modificar el marcaje");
            }
        }
    }

    @FXML
    private void limpiar() {
        comboBoxIdAulas.setValue(marcajeSeleccionado.getIdAula().getIdAula());
        comboBoxIdProductos.setValue(marcajeSeleccionado.getIdProducto().getIdProducto());
        FechaMarcaje.setValue(convertirALocalDate(marcajeSeleccionado.getFecha()));
        toggleButtonEntrada.setSelected(marcajeSeleccionado.isTipo());
        toggleButtonSalida.setSelected(!marcajeSeleccionado.isTipo());
    }

    private boolean comprobarCampos() {
        if (comboBoxIdAulas.getValue() == null || comboBoxIdProductos.getValue() == null || FechaMarcaje.getValue() == null || (!toggleButtonEntrada.isSelected() && !toggleButtonSalida.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error - Campos vacíos");
            alert.setContentText("Debes rellenar todos los campos para poder modificar un marcaje.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean comprobarCamposModificados() {
        if (marcajeSeleccionado.getIdAula().getIdAula() == comboBoxIdAulas.getValue() && marcajeSeleccionado.getIdProducto().getIdProducto() == comboBoxIdProductos.getValue() && marcajeSeleccionado.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(FechaMarcaje.getValue()) && ((marcajeSeleccionado.isTipo() && toggleButtonEntrada.isSelected()) || (!marcajeSeleccionado.isTipo() && toggleButtonSalida.isSelected()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error - Campos no modificados");
            alert.setContentText("Debes modificar al menos un campo para poder modificar un marcaje.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private LocalDate convertirALocalDate(Date fechaAConvertir) {
        return fechaAConvertir.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
