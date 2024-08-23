package com.example.notion_api.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AwsS3Config {

    @Value("{aws.s3.credentials.access-key}")
    private String accessKey;

    @Value("${aws.s3.credentials.secret-key}")
    private String secretKey;

    @Value("${aws.s3.credentials.region}")
    private String region;

    @Value("${aws.s3.credentials.bucket-name}")
    private String bucketName;

    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();

        return s3Client;
    }

    @PostConstruct
    public void init(){
        if (!s3Client().doesBucketExistV2(bucketName)){
            s3Client().createBucket(new CreateBucketRequest(bucketName));
            System.out.println("버킷 생성 [버킷 이름 : "+bucketName+"]");
        }else {
            System.out.println("버킷 생성 실패...(버킷이 이미 존재함)");
            System.out.println(s3Client().getRegionName());
        }
    }

    @PreDestroy
    public void cleanup(){
        if (s3Client().doesBucketExistV2(bucketName)){
            deleteAllObjectsInBucket(bucketName);

            s3Client().deleteBucket(bucketName);
            System.out.println("버킷 삭제 완료. [버킷 이름 : "+bucketName+"]");
        }
    }

    private void deleteAllObjectsInBucket(String bucketName){
        ListObjectsV2Request listObjects = new ListObjectsV2Request()
                .withBucketName(bucketName);
        ListObjectsV2Result result;
        do {
            result = s3Client().listObjectsV2(listObjects);
            result.getObjectSummaries().forEach(summary -> s3Client().deleteObject(bucketName, summary.getKey()));
        }while (result.isTruncated());
        System.out.println("버킷의 모든 오브젝트 삭제");


    }
}

