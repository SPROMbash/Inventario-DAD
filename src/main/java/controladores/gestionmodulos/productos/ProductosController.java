package controladores.gestionmodulos.productos;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Producto;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProductosController {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private Producto productoSeleccionado;

    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtRFID;

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
    private Button botonLimpiar;

    @FXML
    public void initialize() {
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaEAN13.setCellValueFactory(new PropertyValueFactory<>("EAN13"));
        columnaKeyRFID.setCellValueFactory(new PropertyValueFactory<>("keyRFID"));
        cargarProductos();

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        txtRFID.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarRFID();
        });

        botonLimpiar.setOnAction(event -> {
            limpiar();
        });

        tablaDeProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            productoSeleccionado = newValue;
        });
    }

    private void cargarProductos() {
        List<Producto> productos = productoDAO.getTodos();
        tablaDeProductos.getItems().setAll(productos);
    }

    private void filtrarRFID() {
        if (!txtRFID.getText().isEmpty()) {
            List<Producto> productos = productoDAO.getPorRFID(txtRFID.getText());
            tablaDeProductos.getItems().clear();
            tablaDeProductos.getItems().setAll(productos);
        } else {
            cargarProductos();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Producto> productos = productoDAO.getPorDescripcion(txtDescripcion.getText());
            tablaDeProductos.getItems().clear();
            tablaDeProductos.getItems().setAll(productos);
        } else {
            cargarProductos();
        }
    }

    public void abrirConfiguracion(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/configuracion", "Configuración");
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

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void crearProducto(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/productos/crearProducto", "Crear Producto");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProducto(MouseEvent mouseEvent) {
        try {
            if (productoSeleccionado != null) {
                App.setUserData(productoSeleccionado);
                App.setRoot("/com/example/inventario_hib/gestionmodulos/productos/modificarProducto", "Modificar Producto");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al modificar producto", "Debes seleccionar un producto para poder modificarlo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error inesperado", "Error", "Ocurrió un error inesperado al intentar modificar el producto.");
        }
    }

    public void eliminarProducto(MouseEvent mouseEvent) {
        try {
            if (productoSeleccionado != null) {
                Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmacion.setTitle("Confirmación");
                alertConfirmacion.setHeaderText("¿Estás seguro de que deseas eliminar el producto?");
                alertConfirmacion.setContentText("Se eliminará el producto seleccionado.");

                if (alertConfirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                    return; // Si el usuario cancela, no pasa nada más
                }

                if (productoDAO.delete(productoSeleccionado)) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto eliminado", "El producto ha sido eliminado correctamente.");
                    cargarProductos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al eliminar producto", "No se ha podido eliminar el producto seleccionado.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al eliminar producto", "Debes seleccionar un producto para eliminarlo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error inesperado", "Error", "Ocurrió un error inesperado al intentar eliminar el producto.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public void mostrarMensajeNoDisponible(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Función no disponible");
        alert.setHeaderText(null);
        alert.setContentText("Esta función estará disponible en versiones posteriores.");
        alert.showAndWait();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/gestionModulos", "Gestión de los distintos módulos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        txtDescripcion.clear();
        txtRFID.clear();
        cargarProductos();
    }
}
