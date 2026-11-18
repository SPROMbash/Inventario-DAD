# Inventario DAD

Desktop application for managing a **school inventory by classroom**: products with
EAN-13 and RFID identifiers, product categories, classrooms, and **check-ins / check-outs**
that record when a product enters or leaves a classroom.

Built with **JavaFX** for the UI and **Hibernate (JPA)** on top of **MySQL**.

## Features

- **Dashboard** with access to every module.
- **CRUD for each entity**: classrooms, categories, products and check-ins
  (create, edit, delete and list).
- **Product ↔ category assignment** (many-to-many through `ProductoCategoria`).
- **Check-ins**: each one links a product and a classroom, records its type
  (entry or exit) and a timestamp.
- **Queries module**: predefined queries over the inventory, plus filtering by
  number, name or description using parameterized JPQL.
- Help, accessibility and settings options in the menu, with CSS-styled views.

## Tech stack

| Layer | Technology |
|---|---|
| UI | JavaFX 20 (FXML + CSS) |
| Persistence | Hibernate ORM 6.6 · JPA annotations |
| Database | MySQL 8 (Connector/J 8.0.33) |
| Build | Maven (wrapper included) · Java 22 |

## Architecture

```
src/main/java/
├── com/example/inventario_hib/   # App entry point + HibernateUtil (SessionFactory)
├── modelo/                       # JPA entities: Aula, Categoria, Producto, Marcaje, ProductoCategoria
├── DAO/                          # GenericDAO + one DAO per entity
└── controladores/                # JavaFX controllers (dashboard, modules, queries, menu)
src/main/resources/
├── com/example/inventario_hib/   # FXML views
├── estilos/                      # CSS
└── hibernate.cfg.xml             # DB connection + entity mappings
```

## Running it

1. Create an empty local MySQL database called `inventario_hib`. Hibernate creates
   the tables on first run (`hbm2ddl.auto=update`).
2. Check the connection settings in `src/main/resources/hibernate.cfg.xml`
   (by default `localhost:3306`, user `root`, empty password: development settings only).
3. Run:

```bash
./mvnw clean javafx:run
```
