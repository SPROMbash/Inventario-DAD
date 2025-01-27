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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText("Ayuda - Consulta 4");
            alert.setContentText("En esta ventana puedes consultar los productos que no pertenecen a ninguna categoría." +
                    "\nSi lo deseas, puedes pulsar el botón de 'Volver' para volver a la página inicial.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
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
