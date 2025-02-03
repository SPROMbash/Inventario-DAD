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
import modelo.AbrirConfiguracion;
import modelo.Producto;

import java.io.IOException;
import java.util.List;

public class CuartaConsultaController {
    private static final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private TableView<Producto> tablaDeProductos;
    @FXML
    private TableColumn<Producto, Long> columnaIdProducto;
    @FXML
    private TableColumn<Producto, String> columnaDescripcion;
    @FXML
    private TableColumn<Producto, String> columnaEAN13;
    @FXML
    private TableColumn<Producto, Boolean> columnaKeyRFID;

    @FXML
    public void initialize() {
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaEAN13.setCellValueFactory(new PropertyValueFactory<>("EAN13"));
        columnaKeyRFID.setCellValueFactory(new PropertyValueFactory<>("keyRFID"));
        cargarProductos();
    }

    private void cargarProductos() {
        List<Producto> productos = productoDAO.getProductosSinCategorias();
        tablaDeProductos.getItems().setAll(productos);
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
}
