package com.albaraka.albaraka.service.impl;

import com.albaraka.albaraka.exception.generic.FileStorageException;
import com.albaraka.albaraka.service.interfaces.FileStorageService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImp implements FileStorageService {
    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public String uploadDocument(MultipartFile file, UUID documentUuid, UUID ownerUuid) {
        try {
            String extension = getFileExtension(file.getOriginalFilename());
            String objectName = String.format("private/documents/%s/%s.%s",
                    ownerUuid.toString(),
                    documentUuid.toString(),
                    extension);

            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()))
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return objectName;
        } catch (Exception e) {
            throw new FileStorageException("Échec du stockage de fichiers dans MinIO: " + e.getMessage());
        }
    }

    @Override
    public String getDocumentUrl(String filePath, UUID ownerUuid, String role) {
        boolean isAdminOrAgent = role.equals("ROLE_ADMIN") || role.equals("ROLE_AGENT");

        if (!isAdminOrAgent) {
            String[] pathParts = filePath.split("/");

            if (pathParts.length < 3)
                throw new FileStorageException("Le chemin d'accès au fichier est invalide");

            UUID currentOwnerUuid = UUID.fromString(pathParts[2]);
            if (!UUID.randomUUID().equals(currentOwnerUuid))
                throw new FileStorageException("Accès refusé : ce fichier ne vous appartient pas");
        }

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(filePath)
                            .build()
            );
        } catch (Exception e) {
            throw new FileStorageException("Erreur lors de la génération du lien: " + e.getMessage());
        }
    }


    public String getFileExtension(String fileName) {
        return (fileName != null && fileName.contains("."))
                ? fileName.substring(fileName.lastIndexOf(".")) : "bin";
    }
}
