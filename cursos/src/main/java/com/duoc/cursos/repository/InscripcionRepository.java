package com.duoc.cursos.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.duoc.cursos.model.InscripcionModel;

public interface InscripcionRepository extends JpaRepository<InscripcionModel, Long> {
}