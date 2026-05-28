package com.duoc.cursos.repository;


import com.duoc.cursos.model.Asset;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class S3RepositoryImpl implements S3Repository {

    private final S3Client s3Client;

    public S3RepositoryImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public List<Asset> listObjectsInBucket(String bucket) {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucket)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents()
                .stream()
                .map(s3Object -> mapS3Object(bucket, s3Object.key()))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileName) throws IOException {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(request);

        return objectBytes.asByteArray();
    }

    @Override
    public void moveObject(String bucketName, String fileKey, String destinationFileKey) {
        CopyObjectRequest copyRequest = CopyObjectRequest.builder()
                .sourceBucket(bucketName)
                .sourceKey(fileKey)
                .destinationBucket(bucketName)
                .destinationKey(destinationFileKey)
                .build();

        s3Client.copyObject(copyRequest);
        deleteObject(bucketName, fileKey);
    }

    @Override
    public void deleteObject(String bucketName, String fileKey) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

        s3Client.deleteObject(request);
    }

    @Override
    public String uploadFile(String bucketName, String fileName, File fileObj) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3Client.putObject(request, RequestBody.fromFile(fileObj));

        return "File uploaded: " + fileName;
    }

    private Asset mapS3Object(String bucket, String key) {
        URL url = s3Client.utilities()
                .getUrl(builder -> builder
                        .bucket(bucket)
                        .key(key));

        return Asset.builder()
                .name(key)
                .key(key)
                .url(url)
                .build();
    }
}