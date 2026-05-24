package com.duoc.cursos.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Min;

@Data
public class CursoRequest {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
	private String nombre;

	
    
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 6, max = 100, message = "La descripcion debe tener entre 6 y 100 caracteres")            
    private String instructor;

    @NotBlank(message = "La duracion es obligatoria")
    @Size(min = 2, max = 50, message = "La duracion debe tener entre 2 y 50 caracteres")
    private String duracion;

    @NotNull(message = "El costo es obligatorio")
    @Min(value = 1, message = "El costo debe ser mayor a 0")
    private Integer costo;

}