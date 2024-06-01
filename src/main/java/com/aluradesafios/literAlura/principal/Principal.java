package com.aluradesafios.literAlura.principal;

import com.aluradesafios.literAlura.model.Datos;
import com.aluradesafios.literAlura.repository.AutorRepository;
import com.aluradesafios.literAlura.service.ConsumoAPI;
import com.aluradesafios.literAlura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private String URL_BASE = "https://gutendex.com/books/";
    private AutorRepository repository;

    public Principal(AutorRepository repository){
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
                    1 -  Buscar Libro por TÍtulo
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
            opcion = Integer.valueOf(teclado.nextInt());

            switch (opcion) {
                case 1:
                    libroPorTitulo();
                    break;
                case 2:
//                    buscaElNombreDelAutor();
                    break;

                case 0:
                    System.out.println("Gracias, la aplicacion se esta cerrando");
                    break;
                default:
                    System.out.println("Seleciona una opcion valida");
            }

        }

    }

    public void libroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        teclado.nextLine(); // Agregar esta línea si es la primera vez que se llama nextLine() después de nextInt()
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "!?search=" + nombreLibro.replace(" ", "+").toLowerCase());

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {
            var datos = conversor.obtenerDatos(json, Datos.class);





    }
}
