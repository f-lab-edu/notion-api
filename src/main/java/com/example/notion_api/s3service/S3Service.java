package com.example.notion_api.s3service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class S3Service {

    private final S3Client s3Client;

    Logger logger = Logger.getLogger("S3Service");

    @Value("${aws.s3.credentials.bucket-name}")
    private String bucketName;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /** 파일(이미지, 동영상, 오디오) 파일 업로드 */
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        // 오브젝트 키는 인코딩하지 않고 원본 파일 이름을 그대로 사용
        String originalKey = multipartFile.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(originalKey)  // 원본 키 사용
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();

        // 파일을 바이트 배열로 읽어서 업로드
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
    }

    /*
    *   TODO : 파일명이 한글일 떄 읽어오는 경우, 발생하는 에러 해결하기.
    * */
    /** 파일(이미지, 동영상, 오디오)를 URL로 다운로드 */
    public String downloadFile(String fileName) throws IOException {
        // S3Presigner 생성
        try (S3Presigner presigner = S3Presigner.create()) {
            // S3에서 객체 가져오기 요청 생성
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)  // 원본 키 사용
                    .build();

            // presigned URL 요청 설정
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(objectRequest)
                    .build();

            // presigned URL 생성
            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toExternalForm();
        }
    }

    /** 오브젝트 삭제 */
    public void deleteObject(String objectPath) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectPath)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    /** 오브젝트 목록 가져오기 */
    public List<String> objectList(){
        List<String> objKey = new ArrayList<>();
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
//                .maxKeys(5)
                .build();

        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        for (S3Object s3Object : listObjectsV2Response.contents()){
            objKey.add(s3Object.key().toString());
        }
        return objKey;
    }

    /** 오브젝트 최종 수정시간 리스트 */
    public List<LocalDateTime> objectLastModifiedList(){
        List<LocalDateTime> objModifiedDate = new ArrayList<>();
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        ZoneId zoneId = ZoneId.systemDefault();

        for (S3Object s3Object : listObjectsV2Response.contents()) {
            Instant lastModifiedInstant = s3Object.lastModified();
            LocalDateTime lastModifiedDateTime = LocalDateTime.ofInstant(lastModifiedInstant, zoneId);
            objModifiedDate.add(lastModifiedDateTime);
        }

        return objModifiedDate;
    }

    /** 문자열 데이터를 오브젝트로 저장 */
    public void writeObjectContent(String objectKey, String content) {
        // 문자열을 UTF-8로 변환
        byte[] utf8Bytes = content.getBytes(StandardCharsets.UTF_8);

        // S3에 객체 업로드 요청 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType("text/plain; charset=UTF-8")  // Content-Type에 UTF-8 명시
                .contentLength((long) utf8Bytes.length)    // 바이트 배열의 길이로 설정
                .build();

        // UTF-8로 인코딩된 바이트 데이터를 RequestBody로 변환하여 객체 업로드
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(utf8Bytes));
    }

    /** 오브젝트를 문자열 데이터로 읽어오기 */
    public String readObjectContent(String objectKey) {
        // S3에서 객체 가져오기 요청 생성
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        // S3에서 객체 가져오기 (객체 데이터와 메타데이터를 포함한 응답)
        ResponseInputStream<GetObjectResponse> s3ObjectResponse = s3Client.getObject(getObjectRequest);

        // 오브젝트의 메타데이터 가져오기
        GetObjectResponse response = s3ObjectResponse.response();
        logger.info("Content-Type: " + response.contentType());
        logger.info("Content-Length: " + response.contentLength());
        logger.info("Last Modified: " + response.lastModified());
        logger.info("ETag: " + response.eTag());
        logger.info("Metadata: " + response.metadata()); // 사용자 정의 메타데이터

        // InputStream을 문자열로 변환
        String content = new BufferedReader(new InputStreamReader(s3ObjectResponse, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        return content;
    }

    /** 여러개의 파일(이미지, 동영상, 오디오) 파일 업로드 및 Url 가져오기 */
    public List<String> uploadFileList(Map<String,MultipartFile> multipartFileMap) throws IOException {

        List<String> downloadUrl = new ArrayList<>();
        for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()){
            MultipartFile multipartFile = entry.getValue();

            uploadFile(multipartFile);
        }

        for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()){
            String fileName = entry.getKey();
            downloadUrl.add(downloadFile(fileName));
        }

        return downloadUrl;
    }

    /** 여러개의 파일(이미지, 동영상, 오디오) 파일 Url 가져오기 */
    public List<String> downloadFileList(List<String> fileNameList) throws IOException {
        List<String> urlList = new ArrayList<>();
        for (String fileName : fileNameList){
            urlList.add(downloadFile(fileName));
        }
        return urlList;
    }

    /**
     *  TODO : 버전 비교의 방식 선택하기 및 구현
     *
     *         1. 날짜를 json 타입의 문자열 오브젝트로 커스터마이징.
     *         2. AWS S3의 최종 업데이트 시간을 읽어오는 방식.
     *         3. AWS S3의 파일별 버전을 비교하는 방식.
     * */

}

