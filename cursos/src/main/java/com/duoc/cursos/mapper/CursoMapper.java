package com.duoc.cursos.mapper;

import com.duoc.cursos.dto.curso.CursoRequest;
import com.duoc.cursos.dto.curso.CursoResponse;
import com.duoc.cursos.model.CursoModel;


public class CursoMapper {

	public static CursoModel toEntity(CursoRequest req) {

		CursoModel c = new CursoModel();
		c.setNombre(req.getNombre());
		c.setInstructor(req.getInstructor());
        c.setCosto(req.getCosto());
        c.setDuracion(req.getDuracion());

		return c;
	}

	public static void updateEntity(CursoRequest req, CursoModel db) {

		db.setNombre(req.getNombre());
		db.setInstructor(req.getInstructor());
        db.setCosto(req.getCosto());
        db.setDuracion(req.getDuracion());

	}

	public static CursoResponse toResponse(CursoModel c) {

       return new CursoResponse(c.getId(), c.getNombre(), c.getInstructor(), c.getDuracion(), c.getCosto());
	}
}
