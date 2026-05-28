package com.duoc.cursos.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.duoc.cursos.model.Asset;

public interface S3Repository {

    List<Asset> listObjectsInBucket(String bucket);

    byte[] downloadFile(String bucketName, String fileName) throws IOException;

    void moveObject(String bucketName, String fileKey, String destinationFileKey);

    void deleteObject(String bucketName, String fileKey);

    String uploadFile(String bucketName, String fileName, File fileObj);
}