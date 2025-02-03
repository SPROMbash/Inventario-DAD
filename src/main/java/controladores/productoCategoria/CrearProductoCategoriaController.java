package controladores.productoCategoria;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import modelo.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrearProductoCategoriaController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ProductoCategoriaDAO productoCategoriaDAO = new ProductoCategoriaDAO();

    @FXML
    private ComboBox<Long> comboBoxIdProductos;

    @FXML
    private ComboBox<Long> comboBoxIdCategorias;

    @FXML
    public void initialize() {
        comboBoxIdCategorias.getItems().setAll(obtenerIdCategorias());
        comboBoxIdProductos.getItems().setAll(obtenerIdProductos(true));

        comboBoxIdCategorias.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxIdProductos.getItems().setAll(obtenerIdProductos(false));
            }
        });
    }

    private List<Long> obtenerIdCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        List<Long> idCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            idCategorias.add(categoria.getIdCategoria());
        }
        return idCategorias;
    }

    private List<Long> obtenerIdProductos(boolean primeraVez) {
        List<Long> idProductos = new ArrayList<>();
        if (primeraVez) {
            List<Producto> productos = productoDAO.getTodos();
            for (Producto producto : productos) {
                idProductos.add(producto.getIdProducto());
            }
        } else {
            List<Producto> productos = productoDAO.getProductosNoEnCategoria(comboBoxIdProductos.getValue());
            for (Producto producto : productos) {
                idProductos.add(producto.getIdProducto());
            }
        }
        return idProductos;
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

    public void anadirProductoporCategoria(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            Producto producto = productoDAO.obtenerPorId(comboBoxIdProductos.getValue());
            Categoria categoria = categoriaDAO.obtenerPorId(comboBoxIdProductos.getValue());
            ProductoCategoria productoCategoria = new ProductoCategoria(producto, categoria);
            if (productoCategoriaDAO.create(productoCategoria)) {

                MostrarAlerta.AlertaExitosa("Creación exitosa", "Producto añadido a categoría", "Se ha añadido el producto a la categoría correctamente");
            } else {
                MostrarAlerta.AlertaError("Error", "Error al intentar añadir el producto a la categoría", "No se ha podido añadir el producto a la categoría");
            }
        }
    }

    private boolean comprobarCampos() {
        if (comboBoxIdCategorias.getValue() == null || comboBoxIdProductos.getValue() == null) {
           MostrarAlerta.AlertaError("Error", "Error", "Debes seleccionar un producto y una categoría para poder añadir el producto a la categoría.");
            return false;
        }
        return true;
    }
}
