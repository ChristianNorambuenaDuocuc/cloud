package com.duoc.cursos.dto.curso;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CursoResponse {

	private Long id;
	private String nombre;
	private String instructor;
    private String duracion;
	private Integer costo;
	
}

