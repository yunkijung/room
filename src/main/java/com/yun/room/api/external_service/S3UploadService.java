package com.yun.room.api.external_service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.yun.room.domain.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<Image> saveFiles(List<MultipartFile> files) throws IOException {
        List<Image> result = new ArrayList<>();
        for (MultipartFile file : files) {
            result.add(saveFile((file)));
        }
        return result;
    }

    public Image saveFile(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();

        String uniqueFilename = originalFilename + UUID.randomUUID();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, uniqueFilename, multipartFile.getInputStream(), metadata);
        String fileUrl = amazonS3.getUrl(bucket, uniqueFilename).toString();

        return new Image(originalFilename, fileUrl);
    }

    public void deleteFile(String fileKey) {
        amazonS3.deleteObject(bucket, fileKey);
    }

    public void deleteFiles(List<String> fileKeys) {
        for (String fileKey : fileKeys) {
            deleteFile(fileKey);
        }
    }

//    public void deleteImage(Image image) {
//        deleteFile(getFileKeyFromUrl(image.getFileUrl()));
//    }

    public void deleteImages(List<String> fileUrls) {
        List<String> fileKeys = new ArrayList<>();
        for (String fileUrl : fileUrls) {
            fileKeys.add(getFileKeyFromUrl(fileUrl));
        }
        deleteFiles(fileKeys);
    }

    private String getFileKeyFromUrl(String fileUrl) {
        // Assuming that the file URL follows the S3 URL pattern
        // e.g., https://your-s3-bucket.s3.amazonaws.com/uniqueFilename123
        return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    }
}