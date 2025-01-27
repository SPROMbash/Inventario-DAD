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
import modelo.Categoria;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CategoriasController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;

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
    private Button botonLimpiar;

    @FXML
    public void initialize() {
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cargarCategorias();

        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarNombre();
        });

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        botonLimpiar.setOnAction(event -> {
            limpiar();
        });

        tablaCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoriaSeleccionada = newValue;
        });
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaCategorias.getItems().setAll(categorias);
    }

    private void filtrarNombre() {
        if (!txtNombre.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorNombre(txtNombre.getText());
            tablaCategorias.getItems().clear();
            tablaCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorDescripcion(txtDescripcion.getText());
            tablaCategorias.getItems().clear();
            tablaCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
        }
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad.");
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
            App.setRoot("/com/example/inventario_hib/gestionmodulos/gestionModulos", "Gestión de los distintos módulos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearCategoria(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/crearCategoria.fxml", "Crear categorías.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificarCategoria(MouseEvent mouseEvent) {
        try {
            if (categoriaSeleccionada != null) {
                App.setUserData(categoriaSeleccionada);
                App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/modificarCategoria", "Modificar categorías");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Selecciona una categoría para modificarla");
                alert.showAndWait();
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
                alertConfirmation.setContentText("Se eliminará la categoría seleccionada.");
                alertConfirmation.showAndWait();

                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {

                    if (categoriaDAO.delete(categoriaSeleccionada)) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Categoría eliminada.");
                        alert.setContentText("Se ha eliminado correctamente.");
                        alert.showAndWait();
                        cargarCategorias();

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar la categoría.");
                        alert.setContentText("Ha ocurrido un error al intentar eliminar la categoría seleccionada.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error: categoría no seleccionada");
                alert.setContentText("Selecciona una categoría para eliminarla.");
                alert.showAndWait();
            }

            App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/eliminarCategoria", "Eliminar categorías");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        txtNombre.clear();
        txtDescripcion.clear();
        cargarCategorias();
    }
}
