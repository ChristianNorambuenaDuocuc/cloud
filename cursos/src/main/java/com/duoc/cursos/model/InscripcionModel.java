package com.duoc.cursos.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "INSCRIPCION")
@Getter
@Setter
public class InscripcionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estudiante;

    private Integer total;

    @ManyToMany
    @JoinTable(
        name = "INSCRIPCION_CURSO",
        joinColumns = @JoinColumn(name = "inscripcion_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<CursoModel> cursos;
}