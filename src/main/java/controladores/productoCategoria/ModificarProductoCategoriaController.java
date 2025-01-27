package controladores.productoCategoria;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Categoria;
import modelo.Producto;
import modelo.ProductoCategoria;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModificarProductoCategoriaController {
    private final static CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final static ProductoDAO productoDAO = new ProductoDAO();
    private final static ProductoCategoriaDAO productoCategoriaDAO = new ProductoCategoriaDAO();
    private Categoria categoriaSeleccionada;
    private Producto productoSeleccionado;
    private ProductoCategoria productoCategoriaSeleccionado;

    @FXML
    private TableView<Categoria> tablaCategorias;
    @FXML
    private TableColumn<Categoria, Long> colIdCategoria;
    @FXML
    private TableColumn<Categoria, String> colNombre;
    @FXML
    private TableColumn<Categoria, String> colDescripcion;
    @FXML
    private TableColumn<Categoria, Boolean> colEstado;

    @FXML
    private ComboBox<Long> cbIdCategorias;
    @FXML
    private ComboBox<Long> cbIdProductos;

    @FXML
    public void initialize() {
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cargarCategorias();

        tablaCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoriaSeleccionada = newValue;
            cbIdCategorias.setValue(categoriaSeleccionada.getIdCategoria());
        });

        cbIdCategorias.getItems().setAll(obtenerIdCategorias());
        cbIdProductos.getItems().setAll(obtenerIdProductos());

        cbIdCategorias.setOnAction(event -> {
            categoriaSeleccionada = categoriaDAO.obtenerPorId(cbIdCategorias.getValue());
        });

        cbIdProductos.setOnAction(event -> {
            productoSeleccionado = productoDAO.obtenerPorId(cbIdProductos.getValue());
        });
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaCategorias.getItems().clear();
        tablaCategorias.getItems().setAll(categorias);
    }

    private List<Long> obtenerIdCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        List<Long> idCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            idCategorias.add(categoria.getIdCategoria());
        }
        return idCategorias;
    }

    private List<Long> obtenerIdProductos() {
        List<Producto> productos = productoDAO.getTodos();
        List<Long> idProductos = new ArrayList<>();
        for (Producto producto : productos) {
            idProductos.add(producto.getIdProducto());
        }
        return idProductos;
    }

    public void limpiar(ActionEvent actionEvent) {
        cbIdCategorias.setValue(null);
        cbIdProductos.setValue(null);
    }


    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("accesibilidad", "Accesibilidad");
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
            App.setRoot("gestionmodulos/productoCategoria/productoCategoria", "Producto por Categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            productoCategoriaSeleccionado = productoCategoriaDAO.getPorIdProductoYIdCategoria(productoSeleccionado.getIdProducto(), categoriaSeleccionada.getIdCategoria());
            if (productoCategoriaSeleccionado != null) {
                if (productoCategoriaDAO.delete(productoCategoriaSeleccionado)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Relación eliminada");
                    alert.setContentText("La relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + " ha sido eliminada correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Ha ocurrido un error al intentar eliminar la relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + ".");
                    alert.showAndWait();}
            } else {
                productoCategoriaSeleccionado = new ProductoCategoria();
                productoCategoriaSeleccionado.setProducto(productoSeleccionado);
                productoCategoriaSeleccionado.setCategoria(categoriaSeleccionada);
                if (productoCategoriaDAO.create(productoCategoriaSeleccionado)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Relación creada");
                    alert.setContentText("La relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + " ha sido creada correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Ha ocurrido un error al intentar crear la relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + ".");
                    alert.showAndWait();
                }
            }
        }
    }

    private boolean comprobarCampos() {
        if (cbIdCategorias.getValue() != null && cbIdProductos.getValue() != null) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Debes seleccionar una categoría y un producto para poder modificar la relación.");
            alert.showAndWait();
            return false;
        }
    }
}
