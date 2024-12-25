package com.castile.common.oss.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.castile.common.oss.client.OssClient;
import com.castile.common.oss.client.S3OssClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author castile
 * @date 2024-12-18 21:15
 */
@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(S3OssClient.class)
    public OssClient ossClient(AmazonS3 amazonS3){

        return new S3OssClient(amazonS3);
    }

    @Bean
    @ConditionalOnMissingBean(AmazonS3.class)
    @ConditionalOnProperty(prefix = "com.castile.oss", name = "enabled", havingValue = "true")
    public AmazonS3 amazonS3(S3Properties s3Properties){
        System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
        long nullSize = Stream.<String>builder()
                .add(s3Properties.getEndpoint())
                .add(s3Properties.getSecretKey())
                .add(s3Properties.getAccessKey())
                .build()
                .filter(Objects::isNull)
                .count();
        if (nullSize > 0) {
            throw new RuntimeException("oss 配置错误,请检查");
        }
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Properties.getAccessKey(),
                s3Properties.getSecretKey());
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
        return AmazonS3Client.builder()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getEndpoint(), s3Properties.getRegion()))
                .withCredentials(awsCredentialsProvider)
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(s3Properties.isPathStyleAccess())
                .build();
    }
}
