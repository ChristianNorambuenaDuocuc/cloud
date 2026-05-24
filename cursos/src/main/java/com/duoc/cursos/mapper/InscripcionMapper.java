package com.duoc.cursos.mapper;

import com.duoc.cursos.dto.curso.CursoInscripcionResponse;
import com.duoc.cursos.dto.inscripcion.InscripcionResponse;
import com.duoc.cursos.model.CursoModel;
import com.duoc.cursos.model.InscripcionModel;

import java.util.List;

public class InscripcionMapper {

    public static InscripcionResponse toResponse(InscripcionModel inscripcion) {
        InscripcionResponse response = new InscripcionResponse();

        response.setId(inscripcion.getId());
        response.setEstudiante(inscripcion.getEstudiante());
        response.setTotalPagar(inscripcion.getTotal());

        List<CursoInscripcionResponse> cursos = inscripcion.getCursos()
                .stream()
                .map(InscripcionMapper::toCursoResponse)
                .toList();

        response.setCursos(cursos);

        return response;
    }

    private static CursoInscripcionResponse toCursoResponse(CursoModel curso) {
        CursoInscripcionResponse response = new CursoInscripcionResponse();

        response.setId(curso.getId());
        response.setNombre(curso.getNombre());
        response.setInstructor(curso.getInstructor());
        response.setDuracion(curso.getDuracion());
        response.setCosto(curso.getCosto());

        return response;
    }
}