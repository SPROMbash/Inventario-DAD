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
    private ComboBox<Long> cbIdAulas;
    @FXML
    private ComboBox<Long> cbIdProductos;
    @FXML
    private RadioButton rbEntrada;
    @FXML
    private RadioButton rbSalida;

    @FXML
    public void initialize() {
        cbIdAulas.getItems().setAll(obtenerIdAulas());
        cbIdProductos.getItems().setAll(obtenerIdProductos());
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
            App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/marcajes", "Marcajes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            Marcaje marcaje = new Marcaje();
            marcaje.setIdAula(aulaDAO.obtenerPorId(cbIdAulas.getValue()));
            marcaje.setIdProducto(productoDAO.obtenerPorId(cbIdProductos.getValue()));
            marcaje.setTipo(rbEntrada.isSelected());
            if (marcajeDAO.create(marcaje)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Marcaje Creado");
                alert.setHeaderText("Marcaje creado correctamente.");
                alert.setContentText("ID del Aula: " + cbIdAulas.getValue() +
                        "\nID del Producto: " + cbIdProductos.getValue() +
                        "\nTipo: " + (rbEntrada.isSelected() ? "Entrada" : "Salida"));
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

    private void limpiar() {
        cbIdAulas.getSelectionModel().clearSelection();
        cbIdProductos.getSelectionModel().clearSelection();
        rbEntrada.setSelected(false);
        rbSalida.setSelected(false);
    }

    private boolean comprobarCampos() {
        if (cbIdAulas.getSelectionModel().isEmpty() || cbIdProductos.getSelectionModel().isEmpty() || (!rbEntrada.isSelected() && !rbSalida.isSelected())) {
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
