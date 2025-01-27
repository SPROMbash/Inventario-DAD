module com.example.inventario_hib {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    opens com.example.inventario_hib to javafx.fxml;
    exports com.example.inventario_hib;

    exports controladores;
    opens controladores to javafx.fxml;

    exports controladores.gestionmodulos;
    opens controladores.gestionmodulos to javafx.fxml;

    exports controladores.gestionmodulos.aulas;
    opens controladores.gestionmodulos.aulas to javafx.fxml;

    exports controladores.gestionmodulos.categorias;
    opens controladores.gestionmodulos.categorias to javafx.fxml;

    exports controladores.gestionmodulos.marcajes;
    opens controladores.gestionmodulos.marcajes to javafx.fxml;

    exports controladores.gestionmodulos.productos;
    opens controladores.gestionmodulos.productos to javafx.fxml;

    exports controladores.productoCategoria;
    opens controladores.productoCategoria to javafx.fxml;

    exports controladores.consultas;
    opens controladores.consultas to javafx.fxml;

    exports modelo;
    opens modelo to org.hibernate.orm.core, javafx.fxml;

    exports DAO;
    opens DAO to org.hibernate.orm.core, javafx.fxml;
    exports controladores.opcionesMenu;
    opens controladores.opcionesMenu to javafx.fxml;
}