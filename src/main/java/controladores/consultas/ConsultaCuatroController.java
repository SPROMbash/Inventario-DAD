package controladores.consultas;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.Producto;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConsultaCuatroController {
    private static final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Long> colIdProducto;
    @FXML
    private TableColumn<Producto, String> colDescripcion;
    @FXML
    private TableColumn<Producto, String> colEAN13;
    @FXML
    private TableColumn<Producto, Boolean> colKeyRFID;

    @FXML
    public void initialize() {
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEAN13.setCellValueFactory(new PropertyValueFactory<>("EAN13"));
        colKeyRFID.setCellValueFactory(new PropertyValueFactory<>("keyRFID"));
        cargarProductos();
    }

    private void cargarProductos() {
        List<Producto> productos = productoDAO.getProductosSinCategorias();
        tablaProductos.getItems().setAll(productos);
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
}
