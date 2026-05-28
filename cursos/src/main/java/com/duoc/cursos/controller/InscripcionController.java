package com.duoc.cursos.controller;


import com.duoc.cursos.dto.inscripcion.InscripcionRequest;
import com.duoc.cursos.dto.inscripcion.InscripcionResponse;
import com.duoc.cursos.mapper.InscripcionMapper;
import com.duoc.cursos.model.InscripcionModel;
import com.duoc.cursos.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponse> inscribir(@Valid @RequestBody InscripcionRequest request) {
        InscripcionModel inscripcion = inscripcionService.inscribir(request);
        return ResponseEntity.ok(InscripcionMapper.toResponse(inscripcion));
    }

    @GetMapping
    public ResponseEntity<List<InscripcionResponse>> listar() {
        List<InscripcionModel> inscripciones = inscripcionService.listar();

        List<InscripcionResponse> response = inscripciones.stream()
                .map(InscripcionMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
