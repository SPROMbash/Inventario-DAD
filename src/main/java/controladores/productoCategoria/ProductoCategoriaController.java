package controladores.productoCategoria;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import com.example.inventario_hib.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductoCategoriaController {
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final ProductoCategoriaDAO productoCategoriaDAO = new ProductoCategoriaDAO();
    private ProductoCategoria productoCategoriaSeleccionado;

    @FXML
    private TextField txtIdCategoria;
    @FXML
    private TextField txtIdProducto;

    @FXML
    private TableView<ProductoCategoria> tablaDeProductoCategoria;
    @FXML
    private TableColumn<ProductoCategoria, Long> columnaIdProductoCategoria;
    @FXML
    private TableColumn<ProductoCategoria, Long> columnaIdCategoria;
    @FXML
    private TableColumn<ProductoCategoria, Long> columnaIdProducto;
    @FXML
    private TableColumn<ProductoCategoria, String> descripcionCategoria;

    @FXML
    public void initialize() {
        columnaIdProductoCategoria.setCellValueFactory(new PropertyValueFactory<>("idProductoCategoria"));
        columnaIdCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        descripcionCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getDescripcion()));

        cargarProductoCategorias();

        txtIdCategoria.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cargarProductoCategorias();
            } else {
                filtrarIdCategoria();
            }
        });

        txtIdProducto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cargarProductoCategorias();
            } else {
                filtrarIdProducto();
            }
        });

        tablaDeProductoCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            productoCategoriaSeleccionado = newValue;
        });
    }

    private void cargarProductoCategorias() {
        List<ProductoCategoria> productoCategorias = productoCategoriaDAO.getTodos();
        tablaDeProductoCategoria.getItems().clear();
        tablaDeProductoCategoria.getItems().setAll(productoCategorias);

    }

    private void filtrarIdCategoria() {
        List<ProductoCategoria> productoCategorias = productoCategoriaDAO.getPorIdCategoria(Long.parseLong(txtIdCategoria.getText()));
        tablaDeProductoCategoria.getItems().clear();
        tablaDeProductoCategoria.getItems().setAll(productoCategorias);

    }

    private void filtrarIdProducto() {
        List<ProductoCategoria> productoCategorias = productoCategoriaDAO.getPorIdProducto(Long.parseLong(txtIdProducto.getText()));
        tablaDeProductoCategoria.getItems().clear();
        tablaDeProductoCategoria.getItems().setAll(productoCategorias);

    }


    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/gestionModulos", "Gestión de los distintos módulos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void anadirProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/productoCategoria/crearProductoCategoria", "Crear Producto por Categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/productoCategoria/modificarProductoCategoria", "Modificar Producto por Categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirConfiguracion(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/configuracion", "Configuración");
        } catch (Exception e) {
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

    public void eliminarProductoCategoria(MouseEvent mouseEvent) {
        try {
            if (productoCategoriaSeleccionado != null) {
                Optional<ButtonType> result = MostrarAlerta.AlertaConfirmacion("Eliminar enlace entre Producto y Categoría", "¿Está seguro de que desea eliminar el enlace seleccionado?");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (productoCategoriaDAO.delete(productoCategoriaSeleccionado)) {
                        MostrarAlerta.AlertaExitosa("Éxito", "Enlace entre Producto y Categoría eliminado", "El enlace ha sido eliminado correctamente.");
                        cargarProductoCategorias();
                    } else {
                        MostrarAlerta.AlertaError("Error", "Error al eliminar el enlace", "No se ha podido eliminar el enlace entre Producto y Categoría.");
                    }
                }
            } else {
                MostrarAlerta.AlertaError("Error", "Error al eliminar el enlace", "Debes seleccionar un enlace entre Producto y Categoría para poder eliminarlo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar() {
        productoCategoriaSeleccionado = null;
        tablaDeProductoCategoria.getItems().clear();
        txtIdCategoria.clear();
        txtIdProducto.clear();
        cargarProductoCategorias();
    }
}
