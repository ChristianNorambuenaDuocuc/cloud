package com.duoc.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.cursos.model.CursoModel;


@Repository
public interface CursoRepository extends JpaRepository<CursoModel, Long> {
    
    Optional<CursoModel> findByNombre(String nombre);
}
