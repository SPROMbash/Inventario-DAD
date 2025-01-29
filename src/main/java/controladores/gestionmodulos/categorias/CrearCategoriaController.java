package controladores.gestionmodulos.categorias;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.Categoria;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CrearCategoriaController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private RadioButton rbActivada;
    @FXML
    private RadioButton rbDesactivada;

    @FXML
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        buttonLimpiar.setOnAction(event ->
                limpiar(event)
        );
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        AbrirAccesibilidad.abrir();
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        AbrirAyuda.abrir();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/categorias", "Categorías");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            boolean estado = rbActivada.isSelected();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = new Categoria(txtNombre.getText(), txtDescripcion.getText(), estado);
            if (categoriaDAO.create(categoria)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Categoría creada");
                alert.setHeaderText("Categoría creada correctamente.");
                alert.setContentText("Nombre: " + txtNombre.getText() +
                        "\nDescripción: " + txtDescripcion.getText() +
                        "\nEstado: " + (estado ? "Activado" : "Desactivado"));
                alert.showAndWait();
                limpiar(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear la categoría");
                alert.setHeaderText("Ha ocurrido un error al crear la categoría.");
                alert.showAndWait();
            }
        }
    }

    private boolean comprobarCampos() {
        if (txtNombre.getText().isEmpty() && txtDescripcion.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void limpiar(ActionEvent event) {
        txtNombre.clear();
        txtDescripcion.clear();
        rbActivada.setSelected(true);
        rbDesactivada.setSelected(false);
    }
}
