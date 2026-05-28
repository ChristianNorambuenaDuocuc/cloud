package com.duoc.cursos.service;


import com.duoc.cursos.model.Asset;
import com.duoc.cursos.repository.S3Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class AwsServiceImpl implements AwsS3Service {

    private final S3Repository s3Repository;

    public AwsServiceImpl(S3Repository s3Repository) {
        this.s3Repository = s3Repository;
    }

    @Override
    public String getS3FileContent(String bucketName, String fileName) throws IOException {
        byte[] fileBytes = s3Repository.downloadFile(bucketName, fileName);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    @Override
    public List<Asset> getS3Files(String bucketName) throws IOException {
        return s3Repository.listObjectsInBucket(bucketName);
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileName) throws IOException {
        return s3Repository.downloadFile(bucketName, fileName);
    }

    @Override
    public void moveObject(String bucketName, String fileKey, String destinationFileKey) {
        s3Repository.moveObject(bucketName, fileKey, destinationFileKey);
    }

    @Override
    public void deleteObject(String bucketName, String fileKey) {
        s3Repository.deleteObject(bucketName, fileKey);
    }

    @Override
    public String uploadFile(String bucketName, String filePath, MultipartFile file) throws IOException {
        File fileObj = convertMultipartFileToFile(file);

        String fileName = file.getOriginalFilename();
        String fullPath = filePath + fileName;

        String result = s3Repository.uploadFile(bucketName, fullPath, fileObj);

        fileObj.delete();

        return result;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = File.createTempFile("upload-", "-" + file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
}