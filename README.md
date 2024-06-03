# LiterAlura

## Descripción

LiterAlura es una aplicación de consola que permite interactuar con un catálogo de libros a través de una API específica. Los usuarios pueden buscar libros, autores y obtener estadísticas.

## Características

- Buscar libros por título.
- Buscar autores por nombre.
- Listar libros registrados.
- Listar autores registrados.
- Listar autores vivos.
- Listar libros por idioma.
- Listar autores por año.
- Mostrar los 10 libros más buscados.
- Generar estadísticas.

## Requisitos

- Java 17
- Spring Boot 3.3.0
- PostgreSQL

## Instalación

1. Clona el repositorio:
    ```bash
    git clone https://github.com/tu_usuario/LiterAlura.git
    ```

2. Navega al directorio del proyecto:
    ```bash
    cd LiterAlura
    ```

3. Configura la base de datos en `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
    ```

4. Compila y ejecuta la aplicación:
    ```bash
    ./mvnw spring-boot:run
    ```

## Uso

Al iniciar la aplicación, se mostrará un menú con varias opciones de interacción. Selecciona la opción deseada y sigue las instrucciones en pantalla.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, envía un pull request o abre un issue para discutir cualquier cambio.

## Licencia


