package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fecha AND a.fechaFallecimiento >= :fecha ")
    List<Autor> buscarAutoresPorAno (int fecha);

    Optional<Autor> findByNombre(String nombre);
}
