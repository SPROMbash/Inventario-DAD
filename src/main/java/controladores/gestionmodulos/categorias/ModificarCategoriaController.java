package controladores.gestionmodulos.categorias;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import modelo.AbrirAccesibilidad;
import modelo.AbrirAyuda;
import modelo.Categoria;
import modelo.MostrarAlerta;


public class ModificarCategoriaController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

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

        categoriaSeleccionada = (Categoria) App.getUserData();

        ToggleGroup EstadoCategoriaGroup = new ToggleGroup();
        toggleButtonActivo.setToggleGroup(EstadoCategoriaGroup);
        toggleButtonDesactivado.setToggleGroup(EstadoCategoriaGroup);

        limpiar();

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

    public void modificarCategoria(ActionEvent actionEvent) {
        if (comprobarCampos() && comprobarCamposModificados()) {
            categoriaSeleccionada.setNombre(txtNombreCategoria.getText());
            categoriaSeleccionada.setDescripcion(txtDescripcion.getText());
            categoriaSeleccionada.setEstado(toggleButtonActivo.isSelected());
            if (categoriaDAO.update(categoriaSeleccionada)) {
                MostrarAlerta.AlertaExitosa("Modificación exitosa", "Categoría modificada", "Se ha modificado la categoría correctamente");
                limpiar();
            } else {
                MostrarAlerta.AlertaError("Error", "Error al intentar modificar la categoría", "No se ha podido modifcar la categoría");
            }
        }
    }

    @FXML
    private void limpiar() {
        txtNombreCategoria.setText(categoriaSeleccionada.getNombre());
        txtDescripcion.setText(categoriaSeleccionada.getDescripcion());
        toggleButtonActivo.setSelected(categoriaSeleccionada.isEstado());
        toggleButtonDesactivado.setSelected(!categoriaSeleccionada.isEstado());
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

    private boolean comprobarCamposModificados() {
        if (txtNombreCategoria.getText().equals(categoriaSeleccionada.getNombre()) &&
                txtDescripcion.getText().equals(categoriaSeleccionada.getDescripcion()) &&
                ((toggleButtonActivo.isSelected() && categoriaSeleccionada.isEstado()) ||
                        (toggleButtonDesactivado.isSelected() && !categoriaSeleccionada.isEstado()))) {

            MostrarAlerta.showAlert("No has modificado ningún campo.");
            return false;
        }
        return true;
    }
}
