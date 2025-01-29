package controladores.consultas;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.AbrirConfiguracion;
import modelo.Producto;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConsultaCincoController {
    private static final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private TableView<Object[]> tablaDeNumeroDeProductos;
    @FXML
    private TableColumn<Object[], String> columnaCategorias;
    @FXML
    private TableColumn<Object[], Long> columnaNumeroProductos;

    @FXML
    public void initialize() {
        columnaCategorias.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[0]));
        columnaNumeroProductos.setCellValueFactory(cellData -> new SimpleLongProperty((Long) cellData.getValue()[1]).asObject());
        cargarProductos();
    }

    private void cargarProductos() {
        List<Object[]> productos = productoDAO.getNumeroProductoPorCategoria();
        tablaDeNumeroDeProductos.getItems().setAll(productos);
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
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

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/consultas/consultasModulos", "CONSULTAS PARA LOS DIFERENTES MÓDULOS");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
