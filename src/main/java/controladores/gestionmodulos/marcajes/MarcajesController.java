package controladores.gestionmodulos.marcajes;

import DAO.MarcajeDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Marcaje;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MarcajesController {
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();
    private Marcaje marcajeSeleccionado;

    @FXML
    private DatePicker FechaInicio;
    @FXML
    private DatePicker FechaFinal;
    @FXML
    private TableView<Marcaje> tablaDeMarcajes;
    @FXML
    private TableColumn<Marcaje, Long> columnaIdMarcaje;
    @FXML
    private TableColumn<Marcaje, Long> columnaIdAula;
    @FXML
    private TableColumn<Marcaje, Long> columnaIdProducto;
    @FXML
    private TableColumn<Marcaje, Date> columnaFecha;
    @FXML
    private TableColumn<Marcaje, Boolean> columnaTipo;

    @FXML
    private Button botonLimpiar;


    @FXML
    public void initialize() {
        columnaIdMarcaje.setCellValueFactory(new PropertyValueFactory<>("idMarcaje"));
        columnaIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cargarMarcajes();

        FechaInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (FechaInicio.getValue() != null && FechaFinal.getValue() != null) {
                if (comprobarInicioYFin()) {
                    filtrarEntreFechas();
                }
            }
        });

        FechaFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
            filtrarEntreFechas();
        });

        botonLimpiar.setOnAction(event ->
                limpiar()
        );

        tablaDeMarcajes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            marcajeSeleccionado = newValue;
        });
    }

    private void filtrarEntreFechas() {
        if (comprobarFechas() && comprobarInicioYFin()) {
            LocalDate inicio = FechaInicio.getValue();
            LocalDate fin = FechaFinal.getValue();
            List<Marcaje> marcajes = marcajeDAO.obtenerEntreFechas(inicio, fin);
            tablaDeMarcajes.getItems().setAll(marcajes);
        }
    }

    private void cargarMarcajes() {
        List<Marcaje> marcajes = marcajeDAO.getTodos();
        tablaDeMarcajes.getItems().setAll(marcajes);
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/accesibilidad", "Accesibilidad");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirConfiguracion(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/opcionesMenu/configuracion", "Configuración");
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

    public void mostrarMensajeNoDisponible(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Función no disponible");
        alert.setHeaderText(null);
        alert.setContentText("Esta función estará disponible en versiones posteriores.");
        alert.showAndWait();
    }


    public void volver(ActionEvent actionEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/gestionModulos", "Gestión de los distintos módulos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearMarcaje(MouseEvent mouseEvent) {
        try {
            App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/crearMarcaje", "Crear Marcaje");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarMarcaje(MouseEvent mouseEvent) {
        try {
            if (marcajeSeleccionado != null) {
                App.setUserData(marcajeSeleccionado);
                App.setRoot("/com/example/inventario_hib/gestionmodulos/marcajes/modificarMarcaje", "Modificar Marcaje");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un marcaje para poder modificarlo.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarMarcaje(MouseEvent mouseEvent) {
        try {
            if (marcajeSeleccionado != null) {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setTitle("Eliminar marcaje");
                alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar el marcaje?");
                alertConfirmation.setContentText("Se eliminará el marcaje seleccionado.");
                alertConfirmation.showAndWait();
                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {
                    if (marcajeDAO.delete(marcajeSeleccionado)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Marcaje eliminado.");
                        alert.setContentText("El marcaje seleccionado ha sido eliminado correctamente.");
                        alert.showAndWait();
                        cargarMarcajes();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar el maracje.");
                        alert.setContentText("Ha ocurrido un error al intentar eliminar el marcaje seleccionado.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un marcaje para poder modificarlo.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar() {
        FechaInicio.setValue(null);
        FechaFinal.setValue(null);
        cargarMarcajes();
    }

    private boolean comprobarFechas() {
        if (FechaInicio.getValue() == null || FechaFinal.getValue() == null) {
            return false;
        }
        return true;
    }

    private boolean comprobarInicioYFin() {
        if (!comprobarFechas()) {
            return false;
        }
        if (FechaInicio.getValue().isAfter(FechaFinal.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el rango de fechas");
            alert.setContentText("La fecha de inicio no puede ser posterior a la fecha final. Por favor, corrige el rango de fechas.");
            alert.showAndWait();
            FechaInicio.setValue(null);
            FechaFinal.setValue(null);
            return false;
        }
        return true;
    }
}
