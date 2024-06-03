package com.aluradesafios.literAlura.principal;

import com.aluradesafios.literAlura.model.*;
import com.aluradesafios.literAlura.repository.AutorRepository;
import com.aluradesafios.literAlura.service.ConsumoAPI;
import com.aluradesafios.literAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;


@Component
public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final AutorRepository repository;

    @Autowired
    public Principal(AutorRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    Bienvenido(a) a LiterAlura
                                ***
                           MENU PRINCIPAL
                                ***
                    1 -  Buscar Libro por Título
                    2 -  Buscar Autor por Nombre
                    3 -  Listar Libros Registrados
                    4 -  Listar Autores Registrados
                    5 -  Listar Autores Vivos
                    6 -  Listar Libros por Idioma
                    7 -  Listar Autores por Año
                    8 -  Generar Estadísticas
                    0 - SALIR DEL PROGRAMA
                              ***
                    selecciona una opción:
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> libroPorTitulo();
                case 2 -> autorPorNombre();
                case 3 -> listarLibrosRegistrados();
                case 4 -> listarAutoresRegistrados();
                case 5 -> listarAutoresVivos();
                case 6 -> listarLibrosPorIdioma();
                case 7 -> listarAutoresPorAnio();
                case 8 -> generarEstadisticas();
                case 0 -> System.out.println("Gracias, la aplicación se está cerrando");
                default -> System.out.println("Seleccione una opción válida");
            }
        }
    }
    public void libroPorTitulo() {
        System.out.println("""
                --------------------------------
                   BUSCAR LIBROS POR TÍTULO
                --------------------------------
                 """);
        System.out.println("Introduzca el nombre del libro que desea buscar:");
        String nombre = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombre.replace(" ", "+").toLowerCase());

        if (json.isEmpty() || json.contains("\"count\":0")) {
            System.out.println("Libro no encontrado!");
            return;
        }

        Datos datos = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datos.libros().stream().findFirst();
        if (libroBuscado.isPresent()) {
            DatosLibros libro = libroBuscado.get();
            System.out.println(
                    "\n------------- LIBRO 📚  --------------" +
                            "\nTítulo: " + libro.titulo() +
                            "\nAutor: " + libro.autores().stream().findFirst().orElse("Desconocido") +
                            "\nIdioma: " + libro.idioma().stream().findFirst().orElse("Desconocido") +
                            "\nNúmero de descargas: " + libro.numeroDeDescargas() +
                            "\n--------------------------------------\n"
            );

            // Resto del código para guardar en BD
        } else {
            System.out.println("Libro no encontrado!");
        }
    }

    public void autorPorNombre() {
        System.out.println("""
                --------------------------------
                   BUSCAR AUTOR POR NOMBRE
                --------------------------------
                 """);
        System.out.println("Introduzca el nombre del autor que desea buscar:");
        String nombre = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombre.replace(" ", "+").toLowerCase());

        if (json.isEmpty() || json.contains("\"count\":0")) {
            System.out.println("Autor no encontrado!");
            return;
        }

        Datos datos = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datos.libros().stream().findFirst();
        if (libroBuscado.isPresent()) {
            DatosLibros libro = libroBuscado.get();
            System.out.println(
                    "\n------------- AUTOR 📚  --------------" +
                            "\nNombre: " + libro.autores().stream().findFirst().orElse("Desconocido") +
                            "\n--------------------------------------\n"
            );

            // Resto del código para guardar en BD
        } else {
            System.out.println("Autor no encontrado!");
        }
    }

    public void listarLibrosRegistrados() {
        System.out.println("""
                --------------------------------
                   LISTAR LIBROS REGISTRADOS
                --------------------------------
                 """);
        repository.buscarTodosLosLibros().forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo() + ", Autor: " + libro.getAutor().getNombre());
        });
    }

    public void listarAutoresRegistrados() {
        System.out.println("""
                --------------------------------
                   LISTAR AUTORES REGISTRADOS
                --------------------------------
                 """);
        repository.findAll().forEach(autor -> {
            System.out.println("Nombre: " + autor.getNombre());
        });
    }

    public void listarAutoresVivos() {
        System.out.println("""
                --------------------------------
                   LISTAR AUTORES VIVOS
                --------------------------------
                 """);
        repository.buscarAutoresVivos(2024).forEach(autor -> {
            System.out.println("Nombre: " + autor.getNombre());
        });
    }

    public void listarLibrosPorIdioma() {
        System.out.println("""
                --------------------------------
                   LISTAR LIBROS POR IDIOMA
                --------------------------------
                 """);
        System.out.println("Introduzca el idioma:");
        String idioma = teclado.nextLine();
        repository.buscarLibrosPorIdioma(Idioma.valueOf(idioma.toUpperCase())).forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo() + ", Autor: " + libro.getAutor().getNombre());
        });
    }

    public void listarAutoresPorAnio() {
        System.out.println("""
                --------------------------------
                   LISTAR AUTORES POR AÑO
                --------------------------------
                 """);
        System.out.println("Introduzca el año:");
        int anio = Integer.parseInt(teclado.nextLine());
        repository.listarAutoresPorNacimiento(anio).forEach(autor -> {
            System.out.println("Nombre: " + autor.getNombre());
        });
    }
//    public void top10LibrosMasBuscados() {
//        System.out.println("""
//                --------------------------------
//                   TOP 10 LIBROS MÁS BUSCADOS
//                --------------------------------
//                 """);
//        repository.top10Libros(PageRequest.of(0, 10)).forEach(libro -> {
//            System.out.println("Título: " + libro.getTitulo() + ", Autor: " + libro.getAutor().getNombre() + ", Descargas: " + libro.getDescargas());
//        });
//    }

    public void generarEstadisticas() {
        System.out.println("""
                --------------------------------
                   GENERAR ESTADÍSTICAS
                --------------------------------
                 """);
        // Código para generar estadísticas
    }
}

