package com.duoc.cursos.controller;


import com.duoc.cursos.service.AwsS3Service;
import com.duoc.cursos.service.InscripcionService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/inscripciones")
public class ResumenInscripcionController {

    private final InscripcionService inscripcionService;
    private final AwsS3Service awsS3Service;

    public ResumenInscripcionController(InscripcionService inscripcionService,
                                        AwsS3Service awsS3Service) {
        this.inscripcionService = inscripcionService;
        this.awsS3Service = awsS3Service;
    }

    @GetMapping("/{id}/resumen/archivo")
    public ResponseEntity<ByteArrayResource> generarArchivoResumen(
            @PathVariable Long id) {

        String resumen = inscripcionService.generarResumenTexto(id);
        String fileName = "resumen-inscripcion-" + id + ".txt";

        ByteArrayResource resource = new ByteArrayResource(
                resumen.getBytes(StandardCharsets.UTF_8)
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                .contentLength(resumen.getBytes(StandardCharsets.UTF_8).length)
                .body(resource);
    }

   @PostMapping("/{id}/resumen/s3")
public ResponseEntity<String> subirResumenAS3(
        @PathVariable Long id,
        @RequestParam String bucketName) throws IOException {

    String resumen = inscripcionService.generarResumenTexto(id);
    String fileName = "resumen-inscripcion-" + id + ".txt";
    String key = id + "/" + fileName;

    File tempFile = File.createTempFile("resumen-inscripcion-" + id, ".txt");

    try (FileWriter writer = new FileWriter(tempFile)) {
        writer.write(resumen);
    }

    awsS3Service.uploadFileFromFile(bucketName, key, tempFile);

    tempFile.delete();

    return ResponseEntity.ok("Resumen subido a S3: " + key);
}

   @PutMapping("/{id}/resumen/s3")
public ResponseEntity<String> modificarResumenAS3(
        @PathVariable Long id,
        @RequestParam String bucketName) throws IOException {

    String resumen = inscripcionService.generarResumenTexto(id);
    String fileName = "resumen-inscripcion-" + id + ".txt";
    String key = id + "/" + fileName;

    File tempFile = File.createTempFile("resumen-inscripcion-" + id, ".txt");

    try (FileWriter writer = new FileWriter(tempFile)) {
        writer.write(resumen);
    }

    awsS3Service.uploadFileFromFile(bucketName, key, tempFile);

    tempFile.delete();

    return ResponseEntity.ok("Resumen actualizado en S3: " + key);
}

    @GetMapping("/{id}/resumen/s3")
    public ResponseEntity<ByteArrayResource> descargarResumenDesdeS3(
            @PathVariable Long id,
            @RequestParam String bucketName) throws IOException {

        String fileName = "resumen-inscripcion-" + id + ".txt";
        String key = id + "/" + fileName;

        byte[] data = awsS3Service.downloadFile(bucketName, key);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                .contentLength(data.length)
                .body(resource);
    }

    @DeleteMapping("/{id}/resumen/s3")
    public ResponseEntity<String> eliminarResumenDesdeS3(
            @PathVariable Long id,
            @RequestParam String bucketName) {

        String fileName = "resumen-inscripcion-" + id + ".txt";
        String key = id + "/" + fileName;

        awsS3Service.deleteObject(bucketName, key);

        return ResponseEntity.ok("Resumen eliminado de S3: " + key);
    }
}
