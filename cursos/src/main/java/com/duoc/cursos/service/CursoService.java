package com.duoc.cursos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.cursos.repository.CursoRepository;
import com.duoc.cursos.mapper.CursoMapper;
import com.duoc.cursos.model.CursoModel;
import org.springframework.lang.NonNull;
import com.duoc.cursos.dto.curso.CursoRequest;
import com.duoc.cursos.dto.curso.CursoResponse;
import com.duoc.cursos.exception.NoEncontradoException;
import com.duoc.cursos.repository.InscripcionRepository;


@Service
public class CursoService {

	private final CursoRepository repo;
    private final InscripcionRepository usuarioRepo;


	public CursoService(CursoRepository repo, InscripcionRepository usuarioRepo) {

		this.repo = repo;
        this.usuarioRepo = usuarioRepo;
	}

	public List<CursoResponse> findAll() {

		return repo.findAll().stream().map(CursoMapper::toResponse).toList();
	}

	public CursoResponse findById(Long id) {

    if (id == null) {
        throw new IllegalArgumentException("El id no puede ser null");
    }

    CursoModel entity = repo.findById(id)
        .orElseThrow(() -> new NoEncontradoException("curso no encontrado"));

    return CursoMapper.toResponse(entity);
}
	
public CursoResponse create(CursoRequest request) {

  

    // Crear entidad desde mapper
    CursoModel entity = CursoMapper.toEntity(request);


    // Guardar
    CursoModel saved = repo.save(entity);

    return CursoMapper.toResponse(saved);
}
	

	public CursoResponse update(Long id, CursoRequest request) {

    CursoModel entity = repo.findById(id)
        .orElseThrow(() -> new NoEncontradoException("Curso no encontrado"));

   
    CursoMapper.updateEntity(request, entity);

    

    CursoModel updated = repo.save(entity);

    return CursoMapper.toResponse(updated);
}

	public void delete(Long id) {

		CursoModel entity = repo.findById(id).orElseThrow(() -> new NoEncontradoException("curso no encontrado"));
		repo.delete(entity);
	}
}
