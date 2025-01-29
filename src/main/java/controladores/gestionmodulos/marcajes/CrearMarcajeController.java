package controladores.gestionmodulos.marcajes;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import modelo.Aula;
import modelo.Marcaje;
import modelo.Producto;

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
    private RadioButton radioButtonEntrada;
    @FXML
    private RadioButton radioButtonSalida;

    @FXML
    public void initialize() {
        comboBoxIdAulas.getItems().setAll(obtenerIdAulas());
        comboBoxIdProductos.getItems().setAll(obtenerIdProductos());
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad");
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
            marcaje.setTipo(radioButtonEntrada.isSelected());
            if (marcajeDAO.create(marcaje)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Marcaje Creado");
                alert.setHeaderText("Marcaje creado correctamente.");
                alert.setContentText("ID del Aula: " + comboBoxIdAulas.getValue() +
                        "\nID del Producto: " + comboBoxIdProductos.getValue() +
                        "\nTipo: " + (radioButtonEntrada.isSelected() ? "Entrada" : "Salida"));
                alert.showAndWait();
                limpiar();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear el marcaje");
                alert.setHeaderText("Ha ocurrido un error al crear el marcaje.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void limpiar() {
        comboBoxIdProductos.getSelectionModel().clearSelection();
        comboBoxIdAulas.getSelectionModel().clearSelection();
        radioButtonEntrada.setSelected(false);
        radioButtonSalida.setSelected(false);
    }

    private boolean comprobarCampos() {
        if (comboBoxIdAulas.getSelectionModel().isEmpty() || comboBoxIdProductos.getSelectionModel().isEmpty() || (!radioButtonEntrada.isSelected() && !radioButtonSalida.isSelected())) {
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
