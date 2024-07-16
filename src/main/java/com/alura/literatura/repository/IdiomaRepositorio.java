package com.alura.literatura.repository;

import com.alura.literatura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdiomaRepositorio extends JpaRepository<Idioma, Long> {
     Optional<Idioma> findByNombre(String idioma);

}
