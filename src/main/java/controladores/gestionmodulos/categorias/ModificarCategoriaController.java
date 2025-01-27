package controladores.gestionmodulos.categorias;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Categoria;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ModificarCategoriaController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private RadioButton rbActivada;
    @FXML
    private RadioButton rbDesactivada;

    @FXML
    private Button botonLimpiar;

    @FXML
    public void initialize() {
        categoriaSeleccionada = (Categoria) App.getUserData();

        limpiar();

        botonLimpiar.setOnAction(event ->
                limpiar()
        );
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad.");
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
            App.setRoot("/com/example/inventario_hib/gestionmodulos/categorias/categorias", "Categorías");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar(ActionEvent actionEvent) {
        if (comprobarCampos() && comprobarCamposModificados()) {
            categoriaSeleccionada.setNombre(txtNombre.getText());
            categoriaSeleccionada.setDescripcion(txtDescripcion.getText());
            categoriaSeleccionada.setEstado(rbActivada.isSelected());
            if (categoriaDAO.update(categoriaSeleccionada)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Categoría modificada");
                alert.setContentText("Nombre: " + categoriaSeleccionada.getNombre() +
                        "\nDescripción: " + categoriaSeleccionada.getDescripcion() +
                        "\nEstado: " + (categoriaSeleccionada.isEstado() ? "Activado" : "Desactivado"));
                alert.showAndWait();
                limpiar();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al modificar la categoría");
                alert.setContentText("Ha ocurrido un error al modificar la categoría. Por favor, inténtelo de nuevo.");
                alert.showAndWait();
            }
        }
    }

    private void limpiar() {
        txtNombre.setText(categoriaSeleccionada.getNombre());
        txtDescripcion.setText(categoriaSeleccionada.getDescripcion());
        rbActivada.setSelected(categoriaSeleccionada.isEstado());
        rbDesactivada.setSelected(!categoriaSeleccionada.isEstado());
    }

    private boolean comprobarCampos() {
        if (txtNombre.getText().isEmpty() && txtDescripcion.getText().isEmpty() && (!rbActivada.isSelected() && !rbDesactivada.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al modificar la categoría");
            alert.setContentText("Debes rellenar, al menos, un campo.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean comprobarCamposModificados() {
        if (txtNombre.getText().equals(categoriaSeleccionada.getNombre()) && txtDescripcion.getText().equals(categoriaSeleccionada.getDescripcion()) && ((rbActivada.isSelected() && categoriaSeleccionada.isEstado()) || (rbDesactivada.isSelected() && !categoriaSeleccionada.isEstado()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al modificar la categoría");
            alert.setContentText("No has modificado ningún campo.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
