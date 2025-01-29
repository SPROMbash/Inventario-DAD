package controladores.gestionmodulos.productos;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Producto;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CrearProductoController {
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
        botonLimpiar.setOnAction(event ->
            limpiar(event)
        );
    }
    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad");
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
            App.setRoot("/com/example/inventario_hib/gestionmodulos/productos/productos", "Productos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearProducto(ActionEvent actionEvent) {
        if (!comprobarCampos()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Campos obligatorios",
                    "Todos los campos son obligatorios. Por favor, rellena todos los campos.");
            return;
        }

        if (!comprobarEAN13(txtEAN13.getText())) {
            if (!txtEAN13.getText().matches("[0-9]+")) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en EAN13", "Formato incorrecto",
                        "El código EAN13 solo puede contener números.");
            } else if (txtEAN13.getText().length() != 13) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en EAN13", "Longitud incorrecta",
                        "El código EAN13 debe contener exactamente 13 dígitos. Actualmente tiene " + txtEAN13.getText().length() + " dígitos.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en EAN13", "Formato no válido",
                        "El código EAN13 no es válido.");
            }
            return;
        }

        if (!comprobarRFID(txtRFID.getText())) {
            if (!txtRFID.getText().startsWith("RFID")) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en RFID", "Formato incorrecto",
                        "El código RFID debe comenzar con 'RFID'.");
            } else if (!txtRFID.getText().matches("RFID[0-9]+")) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en RFID", "Formato incorrecto",
                        "El código RFID debe contener solo números después de 'RFID'.");
            } else if (txtRFID.getText().length() != 9) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en RFID", "Longitud incorrecta",
                        "El código RFID debe tener exactamente 9 caracteres. Actualmente tiene " + txtRFID.getText().length() + " caracteres.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en RFID", "Formato no válido",
                        "El código RFID no es válido.");
            }
            return;
        }

        ProductoDAO productoDAO = new ProductoDAO();
        Producto nuevoProducto = new Producto(txtDescripcion.getText(), Long.parseLong(txtEAN13.getText()), txtRFID.getText());
        if (productoDAO.create(nuevoProducto)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto creado", "Éxito",
                    "Producto creado correctamente:\nDescripción: " + txtDescripcion.getText() +
                            "\nEAN13: " + txtEAN13.getText() +
                            "\nKeyRFID: " + txtRFID.getText());
            limpiar(null);
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al crear producto", "Error",
                    "No se ha podido crear el producto. Por favor, inténtelo de nuevo.");
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        txtDescripcion.clear();
        txtEAN13.clear();
        txtRFID.clear();
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

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}