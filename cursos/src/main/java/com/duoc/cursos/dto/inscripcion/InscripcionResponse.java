package com.duoc.cursos.dto.inscripcion;


import java.util.List;

import com.duoc.cursos.dto.curso.CursoInscripcionResponse;

public class InscripcionResponse {

    private Long id;
    private String estudiante;
    private List<CursoInscripcionResponse> cursos;
    private Integer totalPagar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public List<CursoInscripcionResponse> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoInscripcionResponse> cursos) {
        this.cursos = cursos;
    }

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }
}