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
import modelo.*;

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
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/productoCategoria/productoCategoria", "Producto por Categoría");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProductoCategoria(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            productoCategoriaSeleccionado = productoCategoriaDAO.getPorIdProductoYIdCategoria(productoSeleccionado.getIdProducto(), categoriaSeleccionada.getIdCategoria());
            if (productoCategoriaSeleccionado != null) {
                if (productoCategoriaDAO.delete(productoCategoriaSeleccionado)) {
                    MostrarAlerta.AlertaExitosa("Información", "Enlace eliminado", "El enlace entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + " ha sido eliminado correctamente.");
                } else {
                    MostrarAlerta.AlertaError("Error", "Error", "Ha ocurrido un error al intentar eliminar la relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + ".");
                }
            } else {
                productoCategoriaSeleccionado = new ProductoCategoria();
                productoCategoriaSeleccionado.setProducto(productoSeleccionado);
                productoCategoriaSeleccionado.setCategoria(categoriaSeleccionada);
                if (productoCategoriaDAO.create(productoCategoriaSeleccionado)) {
                    MostrarAlerta.AlertaExitosa("Información", "Enlace creado", "El enlace entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + " ha sido creado correctamente.");
                } else {
                    MostrarAlerta.AlertaError("Error", "Error", "Ha ocurrido un error al intentar crear el enlace entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + ".");
                }
            }
        }
    }

    private boolean comprobarCampos() {
        if (cbIdCategorias.getValue() != null && cbIdProductos.getValue() != null) {
            return true;
        } else {
           MostrarAlerta.AlertaError("Error", "Error", "Debes seleccionar un producto y una categoría para poder añadir el producto a la categoría.");
            return false;
        }
    }
}
