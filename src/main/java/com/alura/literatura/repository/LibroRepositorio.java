package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Idioma;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    @Query("SELECT a FROM Libro l JOIN l.autores a WHERE l.id = :libroId")
    List<Autor> encuentraAutoresDeLibro(Long libroId);

    @Query("SELECT i FROM Libro l JOIN l.idiomas i WHERE l.id = :libroId")
    List<Idioma> encuentraIdiomasDeLibro(Long libroId);

    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE a.id = :autorId")
    List<Libro> encuentraLibrosDeAutor(Long autorId);

    @Query("SELECT l FROM Libro l JOIN l.idiomas i WHERE i.id = :idiomaId")
    List<Libro> encuentaLibrosPorIdioma(@Param("idiomaId") Long idiomaId);
}
