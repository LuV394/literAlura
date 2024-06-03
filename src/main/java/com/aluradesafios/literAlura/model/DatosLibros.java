package com.aluradesafios.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<String> autores,
        @JsonAlias("lenguages")List<String> idioma,
        @JsonAlias("download_count") Integer numeroDeDescargas
        ) {
}
