package controladores.gestionmodulos.categorias;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CategoriasController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

    @FXML
    private TextField txtNombreCategoria;
    @FXML
    private TextField txtDescripcion;

    @FXML
    private TableView<Categoria> tablaDeCategorias;
    @FXML
    private TableColumn<Categoria, Long> columnaIdCategoria;
    @FXML
    private TableColumn<Categoria, String> columnaNombre;
    @FXML
    private TableColumn<Categoria, String> columnaDescripcion;
    @FXML
    private TableColumn<Categoria, Boolean> columnaEstado;

    @FXML
    public void initialize() {
        columnaIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cargarCategorias();

        txtNombreCategoria.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarNombre();
        });

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        tablaDeCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoriaSeleccionada = newValue;
        });
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaDeCategorias.getItems().setAll(categorias);
    }

    private void filtrarNombre() {
        if (!txtNombreCategoria.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorNombre(txtNombreCategoria.getText());
            tablaDeCategorias.getItems().clear();
            tablaDeCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorDescripcion(txtDescripcion.getText());
            tablaDeCategorias.getItems().clear();
            tablaDeCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
        }
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/gestionModulos", "GESTIÓN DE LOS DISTINTOS MÓDULOS");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirConfiguracion(){
        AbrirConfiguracion.abrir();
    }

    public void mostrarMensajeNoDisponible(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Función no disponible");
        alert.setHeaderText(null);
        alert.setContentText("Esta función estará disponible en versiones posteriores.");
        alert.showAndWait();
    }

    public void crearCategoria(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/crearCategoria", "CREAR CATEGORÍA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificarCategoria(MouseEvent mouseEvent) {
        try {
            if (categoriaSeleccionada != null) {
                App.setUserData(categoriaSeleccionada);
                App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/modificarCategoria", "MODIFICAR CATEGORÍA");
            } else {
                MostrarAlerta.AlertaError("Error", "Error", "Debes seleccionar una categoria para poder modificarla");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoria(MouseEvent mouseEvent) {
        try {
            if (categoriaSeleccionada != null) {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setTitle("Eliminar categoría");
                alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar la categoría?");
                alertConfirmation.showAndWait();

                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {
                    if (categoriaDAO.delete(categoriaSeleccionada)) {
                        MostrarAlerta.AlertaExitosa("Eliminación exitosa", "Categoría eliminada", "Se ha eliminado la categoría correctamente.");
                        cargarCategorias();
                    } else {
                        MostrarAlerta.AlertaError("Error", "Error al intentar eliminar la categoría", "No se ha podido eliminar la categoría");
                    }
                }
            } else {
                MostrarAlerta.AlertaError("Error", "Error", "Debes seleccionar una categoria para poder eliminarla");
            }
            App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/eliminarCategoria", "Eliminar categorías");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar() {
        txtNombreCategoria.clear();
        txtDescripcion.clear();
        cargarCategorias();
    }
}
