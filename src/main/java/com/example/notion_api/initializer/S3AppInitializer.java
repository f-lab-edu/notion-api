package com.example.notion_api.initializer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class S3AppInitializer {

    @Value("${aws.s3.credentials.bucket}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Autowired
    public S3AppInitializer(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * 프로그램 실행 후 버킷 생성.
     */
    @PostConstruct
    public void init() throws IOException {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(new CreateBucketRequest(bucketName));
            System.out.println("버킷 생성 [버킷 이름 : " + bucketName + "]");
            uploadDefaultPageFiles();
            System.out.println("템플릿 페이지 S3 저장소에 추가");
        } else {
            System.out.println("버킷 생성 실패...(버킷이 이미 존재함)");
        }
    }

    private void uploadDefaultPageFiles() throws IOException {
        String filesPath = "markdown/DefaultPageConfig";
        File directory = new ClassPathResource(filesPath).getFile();

        File[] files = directory.listFiles();
        for (File file : files){
            String keyName = "DefaultPageConfig/"+file.getName();
            File tempFile = new File(file.getPath());
            s3Client.putObject(new PutObjectRequest(bucketName,keyName,tempFile));
        }
    }

    /**
     * 프로그램 종료 시 객체 및 버킷 삭제
     */
    @PreDestroy
    public void cleanup() {
        if (s3Client.doesBucketExistV2(bucketName)) {
            deleteAllObjectsInBucket(bucketName);

            s3Client.deleteBucket(bucketName);
            System.out.println("버킷 삭제 완료. [버킷 이름 : " + bucketName + "]");
        }
    }

    private void deleteAllObjectsInBucket(String bucketName) {
        ListObjectsV2Request listObjects = new ListObjectsV2Request()
                .withBucketName(bucketName);
        ListObjectsV2Result result;
        do {
            result = s3Client.listObjectsV2(listObjects);
            result.getObjectSummaries().forEach(summary -> s3Client.deleteObject(bucketName, summary.getKey()));
        } while (result.isTruncated());
        System.out.println("버킷의 모든 오브젝트 삭제");
    }
}
