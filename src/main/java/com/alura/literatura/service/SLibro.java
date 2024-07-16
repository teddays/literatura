package com.alura.literatura.service;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepositorio;
import com.alura.literatura.repository.IdiomaRepositorio;
import com.alura.literatura.repository.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SLibro {

    private LibroRepositorio libroRepositorio;
    private AutorRepositorio autorRepositorio;
    private IdiomaRepositorio idiomaRepositorio;

    @Autowired
    public SLibro(LibroRepositorio libroRepositorio, AutorRepositorio autorRepositorio, IdiomaRepositorio idiomaRepositorio){
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.idiomaRepositorio = idiomaRepositorio;
    }

    @Transactional
    public Libro guardaLibro(DatoLibro datoLibro){
        Libro libro = new Libro();
        libro.setTitulo(datoLibro.titulo());
        libro.setDescargas(datoLibro.descargas());
        List<Autor> autores = datoLibro.autor().stream()
                .map(datoAutor -> {
                    Optional<Autor> existeAutor = autorRepositorio.findByNombre(datoAutor.nombre());
                    if (existeAutor.isPresent()){
                        return existeAutor.get();
                    } else {
                        Autor nuevoAutor = new Autor();
                        nuevoAutor.setNombre(datoAutor.nombre());
                        nuevoAutor.setFechaNacimiento(datoAutor.fechaNacimiento());
                        nuevoAutor.setFechaFallecimiento(datoAutor.fechaFallecimiento());
                        return autorRepositorio.save(nuevoAutor);
                    }

                }).collect(Collectors.toList());
        libro.setAutores(autores);

        List<Idioma> idiomas = datoLibro.idioma().stream()
                .map(idiomaStr -> {
                    Optional<Idioma> existeIdioma = idiomaRepositorio.findByNombre(idiomaStr);
                    if (existeIdioma.isPresent()) {
                        return existeIdioma.get();
                    } else {
                        Idioma nuevoIdioma = new Idioma();
                        nuevoIdioma.setNombre(idiomaStr);
                        return idiomaRepositorio.save(nuevoIdioma);
                    }
                })
                .collect(Collectors.toList());
        libro.setIdiomas(idiomas);

        return libroRepositorio.save(libro);
    }

}
