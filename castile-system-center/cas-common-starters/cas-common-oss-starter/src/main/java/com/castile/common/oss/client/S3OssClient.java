package com.castile.common.oss.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author castile
 * @date 2024-12-18 21:20
 */
public class S3OssClient implements OssClient{
    private final AmazonS3 amazonS3;

    public S3OssClient(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public boolean bucketExists(String bucketName) {
        return amazonS3.doesBucketExistV2(bucketName);
    }

    @Override
    public void createBucket(String bucketName){
        if(!amazonS3.doesBucketExist(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }

    @Override
    public void deleteBucket(String bucketName) {
        amazonS3.deleteBucket(bucketName);
    }

    @Override
    public String getObjectURL(String bucketName, String objectName) {
        return amazonS3.getUrl(bucketName, objectName).toString();
    }

    @Override
    public S3Object getObjectInfo(String bucketName, String objectName) {
        return amazonS3.getObject(bucketName, objectName);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream, long size, String contextType) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contextType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream, objectMetadata);
        putObjectRequest.getRequestClientOptions().setReadLimit(Long.valueOf(size).intValue() + 1);
        amazonS3.putObject(putObjectRequest);
        return null;
    }

    @Override
    public String upload(MultipartFile file, String bucketName) {
        String originalFilename = file.getOriginalFilename();
        System.out.println("fileName: " + originalFilename);
        if (StringUtils.isBlank(originalFilename)) {
            throw new RuntimeException();
        }
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String dateFormat = "yyyy-MM/dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate nowDate = LocalDate.now();
        String format = nowDate.format(formatter);
        String objectName = format + "/" + originalFilename;
        try {
           putObject(bucketName, objectName, file.getInputStream());
            return objectName;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) {
        amazonS3.copyObject(sourceBucketName, sourceObjectName, targetBucketName, targetObjectName);
    }

    @Override
    public AmazonS3 getS3Client() {
        return amazonS3;
    }
}
