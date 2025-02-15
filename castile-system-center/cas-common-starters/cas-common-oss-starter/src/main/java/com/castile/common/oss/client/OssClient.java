package com.castile.common.oss.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * OSS客户端接口
 *
 * @author castile
 * @date 2024-12-18 21:17
 */
public interface OssClient {

    boolean bucketExists(String bucketName);
    void createBucket(String bucketName);
    void deleteBucket(String bucketName);

    String getObjectURL(String bucketName, String objectName);

    /**
     * 获取存储对象的信息
     *
     * @param bucketName
     * @param objectName
     * @return
     */
    S3Object getObjectInfo(String bucketName, String objectName);

    /**
     * 上传对象
     *
     * @param bucketName
     * @param objectName
     * @param inputStream
     * @return
     */
    PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream,long size, String contextType) throws IOException;


    /**
     * 上传文件
     *
     * @param file
     * @param bucketName
     * @return
     */
    String upload(MultipartFile file, String bucketName);


    /**
     *  拷贝文件
     *
     * @param sourceBucketName
     * @param sourceObjectName
     * @param targetBucketName
     * @param targetObjectName
     */
    void copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName);

    default PutObjectResult putObject(String bucketName, String objectName, InputStream stream) throws IOException {
        return putObject(bucketName,objectName,stream, stream.available(), "application/octet-stream");
    }

    AmazonS3 getS3Client();
}
