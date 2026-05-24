package com.duoc.cursos.service;

import com.duoc.cursos.dto.inscripcion.InscripcionRequest;
import com.duoc.cursos.model.CursoModel;
import com.duoc.cursos.model.InscripcionModel;
import com.duoc.cursos.repository.CursoRepository;
import com.duoc.cursos.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {

    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(CursoRepository cursoRepository,
                              InscripcionRepository inscripcionRepository) {
        this.cursoRepository = cursoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

public List<InscripcionModel> listar() {
    return inscripcionRepository.findAll();
}
    
    public InscripcionModel inscribir(InscripcionRequest request) {

        List<CursoModel> cursos = cursoRepository.findAllById(request.getCursosIds());

        if (cursos.size() != request.getCursosIds().size()) {
            throw new RuntimeException("Uno o más cursos no existen");
        }

        Integer total = cursos.stream()
                .mapToInt(CursoModel::getCosto)
                .sum();

        InscripcionModel inscripcion = new InscripcionModel();
        inscripcion.setEstudiante(request.getEstudiante());
        inscripcion.setCursos(cursos);
        inscripcion.setTotal(total);

        return inscripcionRepository.save(inscripcion);
    }
}
