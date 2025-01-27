package controladores.productoCategoria;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import modelo.Categoria;
import modelo.Producto;
import modelo.ProductoCategoria;

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
    private ComboBox<Long> cbIdCategorias;
    @FXML
    private ComboBox<Long> cbIdProductos;

    @FXML
    public void initialize() {
        cbIdCategorias.getItems().setAll(obtenerIdCategorias());
        cbIdProductos.getItems().setAll(obtenerIdProductos(true));

        cbIdCategorias.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cbIdProductos.getItems().setAll(obtenerIdProductos(false));
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
            List<Producto> productos = productoDAO.getProductosNoEnCategoria(cbIdCategorias.getValue());
            for (Producto producto : productos) {
                idProductos.add(producto.getIdProducto());
            }
        }
        return idProductos;
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("accesibilidad", "Accesibilidad.");
        } catch (IOException e) {
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

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            Producto producto = productoDAO.obtenerPorId(cbIdProductos.getValue());
            Categoria categoria = categoriaDAO.obtenerPorId(cbIdCategorias.getValue());
            ProductoCategoria productoCategoria = new ProductoCategoria(producto, categoria);
            if (productoCategoriaDAO.create(productoCategoria)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Producto añadido a categoría");
                alert.setContentText("El producto se ha añadido a la categoría correctamente." +
                        "Id de categoría: " + cbIdCategorias.getValue() + "\n" +
                        "Id de producto: " + cbIdProductos.getValue());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al añadir producto a categoría");
                alert.setContentText("Ha ocurrido un error al añadir el producto a la categoría.");
                alert.showAndWait();
            }
        }
    }

    private boolean comprobarCampos() {
        if (cbIdCategorias.getValue() == null || cbIdProductos.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al añadir producto a categoría");
            alert.setContentText("Debes seleccionar una categoría y un producto para poder añadirlo a la categoría.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
