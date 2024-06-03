package com.aluradesafios.literAlura.repository;

import com.aluradesafios.literAlura.model.Autor;
import com.aluradesafios.literAlura.model.Idioma;
import com.aluradesafios.literAlura.model.Libro;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
//
//    @Query("SELECT l FROM Libro l ORDER BY l.descargas DESC")
//    List<Libro> findTop10ByOrderByDescargasDesc(Pageable pageable);

    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE %:nombre%")
    Optional<Autor> buscaElNombreDelAutor(@Param("nombre") String nombre);

    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:nombre%")
    Optional<Libro> buscarLibroPorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fallecimiento > :fecha")
    List<Autor> buscarAutoresVivos(@Param("fecha") Integer fecha);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> buscarLibrosPorIdioma(@Param("idioma") Idioma idioma);

    @Query("SELECT a FROM Autor a WHERE a.nacimiento = :fecha")
    List<Autor> listarAutoresPorNacimiento(@Param("fecha") Integer fecha);

    @Query("SELECT a FROM Autor a WHERE a.fallecimiento = :fecha")
    List<Autor> listarAutoresPorFallecimiento(@Param("fecha") Integer fecha);

    @Query("SELECT l FROM Libro l")
    List<Libro> buscarTodosLosLibros();

//    Iterable<Object> top10Libros(PageRequest of);
}
