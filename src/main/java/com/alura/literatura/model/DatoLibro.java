package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoLibro (@JsonProperty("title") String titulo,
        @JsonProperty("authors") List<DatoAutor> autor,
        @JsonProperty("languages") List<String> idioma,
        @JsonProperty("download_count") Integer descargas,
        @JsonProperty("formats") Map<String, String> portada) {
}
