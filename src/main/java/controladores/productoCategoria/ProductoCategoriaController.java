package controladores.productoCategoria;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.ProductoCategoria;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
    private TableColumn<ProductoCategoria, Long> columnaId;
    @FXML
    private TableColumn<ProductoCategoria, Long> columnaIdCategoria;
    @FXML
    private TableColumn<ProductoCategoria, Long> columnaIdProducto;

    @FXML
    public void initialize() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idProductoCategoria"));
        columnaIdCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
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
            App.setRoot("com/example/inventario_hib/gestionmodulos/gestionModulos", "Gestión de los distintos módulos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setRoot("gestionmodulos/productoCategoria/crearProductoCategoria", "Crear Producto por Categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setRoot("gestionmodulos/productoCategoria/modificarProductoCategoria", "Modificar Producto por Categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarProductoCategoria(MouseEvent mouseEvent) {
        try {
            if (productoCategoriaSeleccionado != null) {
                Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmacion.setTitle("Eliminar relación");
                alertConfirmacion.setHeaderText("¿Está seguro de que desea eliminar la relación seleccionada?");
                alertConfirmacion.setContentText("Esta acción no se puede deshacer.");
                if (alertConfirmacion.showAndWait().get() != ButtonType.OK) {
                    return;
                } else {
                    if (productoCategoriaDAO.delete(productoCategoriaSeleccionado)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Relación eliminada");
                        alert.setContentText("La relación ha sido eliminada correctamente.");
                        alert.showAndWait();
                        cargarProductoCategorias();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar la relación");
                        alert.setContentText("Ha ocurrido un error al eliminar la relación.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al eliminar la relación");
                alert.setContentText("Debes seleccionar una relación para poder eliminarla.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        productoCategoriaSeleccionado = null;
        tablaDeProductoCategoria.getItems().clear();
        txtIdCategoria.clear();
        txtIdProducto.clear();
        cargarProductoCategorias();
    }
}
