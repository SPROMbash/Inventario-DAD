package controladores.gestionmodulos.productos;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Producto;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ModificarProductoController {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private Producto productoSeleccionado;

    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtEAN13;
    @FXML
    private TextField txtRFID;

    @FXML
    private Button botonLimpiar;


    @FXML
    public void initialize() {
        productoSeleccionado = (Producto) App.getUserData();

        limpiar();

        botonLimpiar.setOnAction(event ->
                limpiar()
        );
    }


    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("accesibilidad", "Accesibilidad");
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
            App.setRoot("gestionmodulos/productos/productos", "Productos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar() {
        txtDescripcion.setText(productoSeleccionado.getDescripcion());
        txtEAN13.setText(productoSeleccionado.getEAN13().toString());
        txtRFID.setText(productoSeleccionado.getKeyRFID());
    }

    public void modificar(ActionEvent actionEvent) {
        try {
            if (!comprobarCampos()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Campos vacíos", "Faltan campos obligatorios", "Por favor, rellena todos los campos antes de continuar.");
                return;
            }

            if (!comprobarEAN13(txtEAN13.getText())) {
                mostrarAlerta(Alert.AlertType.ERROR, "EAN13 inválido", "Formato incorrecto", "El código EAN13 debe contener exactamente 13 dígitos.");
                return;
            }

            if (!comprobarRFID(txtRFID.getText())) {
                mostrarAlerta(Alert.AlertType.ERROR, "RFID inválido", "Formato incorrecto", "El código RFID debe comenzar con 'RFID' seguido de 5 dígitos (ejemplo: RFID12345).");
                return;
            }

            if (!comprobarCamposModificados()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Sin cambios", "No se detectaron modificaciones", "No se realizaron cambios en los datos del producto.");
                return;
            }

            // Actualizamos los valores del producto seleccionado
            productoSeleccionado.setDescripcion(txtDescripcion.getText());
            productoSeleccionado.setEAN13(Long.parseLong(txtEAN13.getText()));
            productoSeleccionado.setKeyRFID(txtRFID.getText());

            // Intentamos realizar la actualización en la base de datos
            if (productoDAO.update(productoSeleccionado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto modificado", "El producto se modificó correctamente:\n\n" +
                        "Descripción: " + txtDescripcion.getText() +
                        "\nEAN13: " + txtEAN13.getText() +
                        "\nKeyRFID: " + txtRFID.getText());
                volver(actionEvent);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo modificar el producto", "Ocurrió un error al intentar modificar el producto. Por favor, inténtalo nuevamente.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Número inválido", "El código EAN13 debe ser un número válido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error inesperado", "Ocurrió un error inesperado", "Por favor, inténtalo nuevamente o contacta con el soporte técnico.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }


    private boolean comprobarCampos() {
        return !txtDescripcion.getText().isEmpty() && !txtEAN13.getText().isEmpty() && !txtRFID.getText().isEmpty();
    }

    private boolean comprobarEAN13(String EAN13) {
        return EAN13.matches("[0-9]{13}");
    }

    private boolean comprobarRFID(String RFID) {
        return RFID.matches("RFID[0-9]{5}");
    }

    private boolean comprobarCamposModificados() {
        if (txtRFID.getText().equals(productoSeleccionado.getKeyRFID()) && txtEAN13.getText().equals(productoSeleccionado.getEAN13().toString()) && txtDescripcion.getText().equals(productoSeleccionado.getDescripcion())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Producto no modificado");
            alert.setHeaderText("No se ha modificado ningún campo.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
