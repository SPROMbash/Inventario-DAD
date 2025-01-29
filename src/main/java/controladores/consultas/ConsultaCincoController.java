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
import modelo.Producto;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConsultaCincoController {
    private static final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private TableView<Object[]> tablaConsulta;
    @FXML
    private TableColumn<Object[], String> colCategoria;
    @FXML
    private TableColumn<Object[], Long> colNumero;

    @FXML
    public void initialize() {
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[0]));
        colNumero.setCellValueFactory(cellData -> new SimpleLongProperty((Long) cellData.getValue()[1]).asObject());
        cargarProductos();
    }

    private void cargarProductos() {
        List<Object[]> productos = productoDAO.getNumeroProductoPorCategoria();
        tablaConsulta.getItems().setAll(productos);
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad.");
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
            App.setRoot("dashboard", "Programa de gestión de inventario");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
