package com.aluradesafios.literAlura.principal;

import com.aluradesafios.literAlura.model.Autor;
import com.aluradesafios.literAlura.model.Datos;
import com.aluradesafios.literAlura.model.DatosLibros;
import com.aluradesafios.literAlura.model.Libro;
import com.aluradesafios.literAlura.repository.AutorRepository;
import com.aluradesafios.literAlura.service.ConsumoAPI;
import com.aluradesafios.literAlura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private String URL_BASE = "https://gutendex.com/books/";
    private AutorRepository repository;

    public Principal(AutorRepository repository) {
        this.repository = repository;
    }

    public Principal() {
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                        
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
                    8 -  Top 10 Libros más Buscados
                    9 -  Generar Estadísticas
                     
                    0 - SALIR DEL PROGRAMA  
                              ***
                    selecciona una opción:
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.valueOf(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
                teclado.nextLine(); // Limpiar el búfer del Scanner
                continue;
            }

            switch (opcion) {
                case 1:
                    libroPorTitulo();
                    break;
                case 2:
                    autorPorNombre();
                    break;
                case 3:
                    listarLibrosRegistrados();
                    break;
                case 4:
                    listarAutoresRegistrados();
                    break;
                case 5:
                    listarAutoresVivos();
                    break;
                case 6:
//                    listarLibrosPorIdioma();
                    break;
                case 7:
//                    listarAutoresPorAnno();
                    break;
                case 8:
//                    top10Libros();
                    break;
                case 9:
//                   generarEstadisticas();
                    break;
                case 0:
                    System.out.println("Gracias, la aplicación se está cerrando");
                    break;
                default:
                    System.out.println("Seleccione una opción válida");
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
        var nombre = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombre.replace(" ", "+").toLowerCase());

        // Check if JSON is empty
        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {
            var datos = conversor.obtenerDatos(json, Datos.class);

            // Process valid data
            Optional<DatosLibros> libroBuscado = datos.libros().stream().findFirst();
            if (libroBuscado.isPresent()) {
                System.out.println(
                        "\n------------- LIBRO \uD83D\uDCD9  --------------" +
                                "\nTítulo: " + libroBuscado.get().titulo() +
                                "\nAutor: " + libroBuscado.get().autores().stream()
                                .map(a -> a.nombre()).limit(1).collect(joining()) +
                                "\nIdioma: " + libroBuscado.get().idiomas().stream().collect(joining()) +
                                "\nNúmero de descargas: " + libroBuscado.get().numeroDeDescargas() +
                                "\n--------------------------------------\n"
                );

                try {
                    List<Libro> libroEncontrado = libroBuscado.stream().map(a -> new Libro(a)).collect(toList());
                    Autor autorAPI = libroBuscado.stream()
                            .flatMap(l -> l.autores().stream().map(a -> new Autor()))
                            .collect(toList()).stream().findFirst().get();
                    Optional<Autor> autorBD = repository.buscaElNombreDelAutor(libroBuscado.get().autores().stream()
                            .map(a -> a.nombre())
                            .collect(joining()));
                    Optional<Libro> libroOptional = repository.buscarLibroPorNombre(nombre);
                    if (libroOptional.isPresent()) {
                        System.out.println("El libro ya está guardado en la BD.");
                    } else {
                        Autor autor;
                        if (autorBD.isPresent()) {
                            autor = autorBD.get();
                            System.out.println("El autor ya está guardado en la BD");
                        } else {
                            autor = autorAPI;
                            repository.save(autor);
                        }
                        autor.setLibros(libroEncontrado);
                        repository.save(autor);
                    }
                } catch (Exception e) {
                    System.out.println("Warning! " + e.getMessage());
                }
            } else {
                System.out.println("Libro no encontrado!");
            }
        }
    }

    public void autorPorNombre() {
        System.out.println("""
                -------------------------------
                 📙 BUSCAR AUTOR POR NOMBRE 📙
                -------------------------------
                """);
        System.out.println("Ingrese el nombre del autor que deseas buscar:");
        var nombre = teclado.nextLine();
        Optional<Autor> autor = repository.buscaElNombreDelAutor(nombre);
        if (autor.isPresent()) {
            System.out.println(
                    "\nAutor: " + autor.get().getNombre() +
                            "\nFecha de Nacimiento: " + autor.get().getNacimiento() +
                            "\nFecha de Fallecimiento: " + autor.get().getFallecimiento() +
                            "\nLibros: " + autor.get().getLibros().stream()
                            .map(l -> l.getTitulo()).collect(toList()) + "\n"
            );
        } else {
            System.out.println("El autor no existe en la BD");
        }
    }

    public void listarLibrosRegistrados() {
        System.out.println("""
                ----------------------------------
                  LISTAR LIBROS REGISTRADOS 
                ----------------------------------
                 """);
        List<Libro> libros = repository.buscarTodosLosLibros();
        libros.forEach(l -> System.out.println(
                "-------------- LIBRO \uD83D\uDCD9  -----------------" +
                        "\nTítulo: " + l.getTitulo() +
                        "\nAutor: " + l.getAutor().getNombre() +
                        "\nIdioma: " + l.getIdioma().getIdioma() +
                        "\nNúmero de descargas: " + l.getDescargas() +
                        "\n----------------------------------------\n"
        ));
    }

    public void listarAutoresRegistrados() {
        System.out.println("""
                ----------------------------------
                   LISTAR AUTORES REGISTRADOS
                ----------------------------------
                 """);
        List<Autor> autores = repository.findAll();
        System.out.println();
        autores.forEach(l -> System.out.println(
                "Autor: " + l.getNombre() +
                        "\nFecha de Nacimiento: " + l.getNacimiento() +
                        "\nFecha de Fallecimiento: " + l.getFallecimiento() +
                        "\nLibros: " + l.getLibros().stream()
                        .map(t -> t.getTitulo()).collect(toList()) + "\n"
        ));
    }

    public void listarAutoresVivos() {
        System.out.println("""
                -----------------------------
                  📒 LISTAR AUTORES VIVOS 📒
                -----------------------------
                 """);

    }
}
