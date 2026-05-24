package com.duoc.cursos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.cursos.dto.curso.CursoRequest;
import com.duoc.cursos.dto.curso.CursoResponse;
import com.duoc.cursos.service.CursoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

	private final CursoService service;

	public CursoController(CursoService service) {

		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<CursoResponse>> findAll() {

		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CursoResponse> findById(@PathVariable Long id) {

		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<CursoResponse> create(@Valid @RequestBody CursoRequest req) {

		CursoResponse created = service.create(req);

		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CursoResponse> update(@PathVariable Long id, @Valid @RequestBody CursoRequest req) {

		return ResponseEntity.ok(service.update(id, req));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CursoResponse> delete(@PathVariable Long id) {

		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}


