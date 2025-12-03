package com.petproject.workflow.api.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.bucket.path}")
    private String path;

    public FileResponse getFile(String key) {
        FileResponse response = new FileResponse();
        response.setContentType(getContentType(key));
        response.setData(downloadFile(key));
        return response;
    }

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileKey = fileName + fileExtension;
            String fullKey = path + fileKey;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullKey)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return fileKey;

        } catch (S3Exception e) {
            throw new RuntimeException("Ошибка при загрузке файла в S3: " + e.getMessage(), e);
        }
    }

    public byte[] downloadFile(String key) {
        try {
            String fullKey = path + key;
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullKey)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return objectBytes.asByteArray();

        } catch (S3Exception e) {
            throw new RuntimeException("Ошибка при получении файла из S3: " + e.getMessage(), e);
        }
    }

    public MediaType getContentType(String key) {
        try {
            String fullKey = path + key;
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullKey)
                    .build();

            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);
            return MediaType.valueOf(headObjectResponse.contentType());

        } catch (S3Exception e) {
            throw new RuntimeException("Ошибка при получении метаданных файла: " + e.getMessage(), e);
        }
    }

    public boolean fileExists(String key) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.headObject(headObjectRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (S3Exception e) {
            throw new RuntimeException("Ошибка при проверке существования файла: " + e.getMessage(), e);
        }
    }

    @Data
    public static class FileResponse {
        public MediaType contentType;
        public byte[] data;
    }
}