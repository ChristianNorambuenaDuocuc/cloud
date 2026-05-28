package com.duoc.cursos.controller;


import com.duoc.cursos.model.Asset;
import com.duoc.cursos.service.AwsS3Service;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class AwsController {

    private final AwsS3Service awsService;

    public AwsController(AwsS3Service awsService) {
        this.awsService = awsService;
    }

    @GetMapping("/getS3FileContent")
    public ResponseEntity<String> getS3FileContent(
            @RequestParam String bucketName,
            @RequestParam String fileName) throws IOException {

        return ResponseEntity.ok(
                awsService.getS3FileContent(bucketName, fileName)
        );
    }

    @GetMapping("/listS3Files")
    public ResponseEntity<List<Asset>> getS3Files(
            @RequestParam String bucketName) {

        try {
            return ResponseEntity.ok(
                    awsService.getS3Files(bucketName)
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/downloadS3File")
    public ResponseEntity<ByteArrayResource> downloadS3File(
            @RequestParam String bucketName,
            @RequestParam String fileName) throws IOException {

        byte[] data =
                awsService.downloadFile(bucketName, fileName);

        ByteArrayResource resource =
                new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\""
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        "application/octet-stream"
                )
                .contentLength(data.length)
                .body(resource);
    }

    @DeleteMapping("/deleteObject")
    public ResponseEntity<String> deleteFile(
            @RequestParam String bucketName,
            @RequestParam String fileName) {

        awsService.deleteObject(bucketName, fileName);

        return ResponseEntity.ok("File deleted");
    }

    @GetMapping("/moveFile")
    public ResponseEntity<String> moveFile(
            @RequestParam String bucketName,
            @RequestParam String fileKey,
            @RequestParam String destinationFileKey) {

        awsService.moveObject(
                bucketName,
                fileKey,
                destinationFileKey
        );

        return ResponseEntity.ok("File moved");
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(
            @RequestParam String bucketName,
            @RequestParam String filePath,
            @RequestParam MultipartFile file) throws IOException {

        return ResponseEntity.ok(
                awsService.uploadFile(
                        bucketName,
                        filePath,
                        file
                )
        );
    }
}