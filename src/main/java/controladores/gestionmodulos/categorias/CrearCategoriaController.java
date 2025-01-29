package controladores.gestionmodulos.categorias;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.Categoria;
import modelo.MostrarAlerta;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CrearCategoriaController {
    @FXML
    private TextField txtNombreCategoria;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private ToggleButton toggleButtonActivo;
    @FXML
    private ToggleButton toggleButtonDesactivado;

    @FXML
    public void initialize() {

        ToggleGroup EstadoCategoriaGroup = new ToggleGroup();
        toggleButtonActivo.setToggleGroup(EstadoCategoriaGroup);
        toggleButtonDesactivado.setToggleGroup(EstadoCategoriaGroup);
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

    public void crearCategoria(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            boolean estado = toggleButtonActivo.isSelected();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = new Categoria(txtNombreCategoria.getText(), txtDescripcion.getText(), estado);
            if (categoriaDAO.create(categoria)) {
                MostrarAlerta.AlertaExitosa("Creación exitosa", "Categoría creada", "Se ha creado la categoría correctamente");
                limpiar(null);
            } else {
                MostrarAlerta.AlertaError("Error", "Error al intentar crear la categoría", "No se ha podido crear la categoría");
            }
        }
    }

    private boolean comprobarCampos() {
        String nombreCategoria = txtNombreCategoria.getText();
        String descripcion = txtDescripcion.getText();

        if (nombreCategoria.isEmpty() || descripcion.isEmpty()) {
            MostrarAlerta.showAlert("Faltan campos por rellenar.");
            return false;
        }

        if (descripcion.matches("\\d+")) {
            MostrarAlerta.showAlert("No se permite introducir solo números en la descripción.");
            return false;
        }
        return true;
    }

    @FXML
    private void limpiar(ActionEvent event) {
        txtNombreCategoria.clear();
        txtDescripcion.clear();
        toggleButtonActivo.setSelected(true);
        toggleButtonDesactivado.setSelected(false);
    }
}
