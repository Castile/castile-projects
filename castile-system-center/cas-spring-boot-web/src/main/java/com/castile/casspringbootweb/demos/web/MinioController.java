package com.castile.casspringbootweb.demos.web;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import com.castile.common.oss.client.OssClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author castile
 * @date 2024-12-18 21:55
 */
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private OssClient s3OssClient;

    @GetMapping("/bucketExists")
    public BaseResponse<String> bucketExists(@RequestParam("bucketName") String bucketName) {
        s3OssClient.bucketExists(bucketName);
        if (s3OssClient.bucketExists(bucketName)) {
            return new BaseResponse<>("bucket exists");
        } else
            return new BaseResponse<>("bucket not exists");
    }

    @GetMapping("/makeBucket")
    public void makeBucket(@RequestParam("bucketName") String bucketName) {
        s3OssClient.createBucket(bucketName);
    }

    @GetMapping("/deleteBucket")
    public void deleteBucket(@RequestParam("bucketName") String bucketName) {
        s3OssClient.deleteBucket(bucketName);
    }

    @ResponseBody
    @PostMapping(value = "upload", produces = "application/json")
    public BaseResponse<String> uploadFile(@RequestBody MultipartFile file) {
        String name = s3OssClient.upload(file, "castile");
        return new BaseResponse<>(name);
    }

    @GetMapping("/getAllBuckets")
    public List<Bucket >getAllBuckets(){
        List<Bucket> buckets = s3OssClient.getS3Client().listBuckets(new ListBucketsRequest());
        return buckets;
    }

    @GetMapping("/copyFile")
    public void copyFile(){
        s3OssClient.copyObject("castile", "2024-12/18/minio.yml", "castile", "2024-12/back/mini11.yml");
    }

}
