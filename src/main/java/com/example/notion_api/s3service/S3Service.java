package com.example.notion_api.s3service;

import com.example.notion_api.config.AwsS3Config;
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
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class S3Service {

    private final AwsS3Config awsS3Config;

    Logger logger = Logger.getLogger("S3Service");

    @Value("${aws.s3.credentials.bucket-name}")
    private String bucketName;

    public S3Service(AwsS3Config awsS3Config) {
        this.awsS3Config = awsS3Config;
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
        awsS3Config.s3Client().putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
    }

    public void uploadFileWithKeyname(MultipartFile multipartFile, String keyName) throws IOException {
        // 오브젝트 키는 인코딩하지 않고 원본 파일 이름을 그대로 사용

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)  // 원본 키 사용
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();

        // 파일을 바이트 배열로 읽어서 업로드
        awsS3Config.s3Client().putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
    }

    /** 경로를 설정하여 파일을 업로드*/
    public void uploadFileByPath(MultipartFile multipartFile, String filePath) throws IOException {
        // 오브젝트 키는 인코딩하지 않고 원본 파일 이름을 그대로 사용
        String originalKey = multipartFile.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath+"/"+originalKey)  // 원본 키 사용
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();

        // 파일을 바이트 배열로 읽어서 업로드
        awsS3Config.s3Client().putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
    }
    public void uploadFileByPathFileName(MultipartFile multipartFile,String fileName, String filePath) throws IOException {
        // 오브젝트 키는 인코딩하지 않고 원본 파일 이름을 그대로 사용

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath+"/"+fileName)  // 원본 키 사용
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build();

        // 파일을 바이트 배열로 읽어서 업로드
        awsS3Config.s3Client().putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
    }

    /*
    *   TODO : 파일명이 한글일 떄 읽어오는 경우, 발생하는 에러 해결하기.
    * */
    /** 파일(이미지, 동영상, 오디오)를 URL로 다운로드 */
    public String downloadFileAsURL(String keyName) throws IOException {
        // S3Presigner 생성
        try (S3Presigner presigner = S3Presigner.create()) {
            // S3에서 객체 가져오기 요청 생성
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)  // 원본 키 사용
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

        awsS3Config.s3Client().deleteObject(deleteObjectRequest);
    }

    /** 오브젝트 목록 가져오기 */
    public List<String> objectList(){
        List<String> objKey = new ArrayList<>();
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
//                .maxKeys(5)
                .build();

        ListObjectsV2Response listObjectsV2Response = awsS3Config.s3Client().listObjectsV2(listObjectsV2Request);

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

        ListObjectsV2Response listObjectsV2Response = awsS3Config.s3Client().listObjectsV2(listObjectsV2Request);

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
        awsS3Config.s3Client().putObject(putObjectRequest, RequestBody.fromBytes(utf8Bytes));
    }

    /** 오브젝트를 문자열 데이터로 읽어오기 */
    public String readObjectContent(String objectKey) {
        // S3에서 객체 가져오기 요청 생성
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        // S3에서 객체 가져오기 (객체 데이터와 메타데이터를 포함한 응답)
        ResponseInputStream<GetObjectResponse> s3ObjectResponse = awsS3Config.s3Client().getObject(getObjectRequest);

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

    public String readObjectContentFromPath(String prefix, String nameContains) throws IOException {
        List<String> objectKeys = new ArrayList<>();

        String continuationToken = null;

        do {
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .continuationToken(continuationToken)
                    .build();

            ListObjectsV2Response listObjectsResponse = awsS3Config.s3Client().listObjectsV2(listObjectsRequest);

            objectKeys.addAll(listObjectsResponse.contents().stream()
                    .map(S3Object::key)
                    .filter(key -> key.contains(nameContains))
                    .collect(Collectors.toList()));

            continuationToken = listObjectsResponse.nextContinuationToken();
        } while (continuationToken != null);

        // 첫 번째 객체 키를 가져와서 그 객체의 내용을 읽어옵니다.
        String objectKey = objectKeys.get(0);

        // S3에서 객체 가져오기 요청 생성
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        // S3에서 객체 가져오기 (객체 데이터와 메타데이터를 포함한 응답)
        try (ResponseInputStream<GetObjectResponse> s3ObjectResponse = awsS3Config.s3Client().getObject(getObjectRequest)) {
            // InputStream을 문자열로 변환
            return new BufferedReader(new InputStreamReader(s3ObjectResponse, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        }
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
            downloadUrl.add(downloadFileAsURL(fileName));
        }

        return downloadUrl;
    }

    /** 여러개의 파일(이미지, 동영상, 오디오) 파일 Url 가져오기 */
    public List<String> downloadFileList(List<String> fileNameList) throws IOException {
        List<String> urlList = new ArrayList<>();
        for (String fileName : fileNameList){
            urlList.add(downloadFileAsURL(fileName));
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

    public List<String> getObjectListByPath(String prefix) {
        List<S3Object> s3Objects = new ArrayList<>();
        String continuationToken = null;

        do {
            // S3에서 객체를 가져오는 요청 생성
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .continuationToken(continuationToken)
                    .build();

            // 요청을 실행하여 응답을 받음
            ListObjectsV2Response listObjectsResponse = awsS3Config.s3Client().listObjectsV2(listObjectsRequest);

            // 응답에서 S3 오브젝트의 이름을 리스트에 추가
            s3Objects.addAll(listObjectsResponse.contents());

            // 다음 페이지가 있는지 확인하고 ContinuationToken 설정
            continuationToken = listObjectsResponse.nextContinuationToken();
        } while (continuationToken != null); // 다음 페이지가 존재하면 계속 반복

        // 오브젝트를 생성 날짜(수정 날짜) 순으로 정렬
        s3Objects.sort(Comparator.comparing(S3Object::lastModified));

        // 정렬된 오브젝트의 이름(키)만 추출하여 리스트로 반환
        List<String> objectNames = new ArrayList<>();
        for (S3Object s3Object : s3Objects) {
            objectNames.add(s3Object.key());
        }

        return objectNames;
    }
    public LocalDateTime getSingleLatestModificationDateByName(String prefix, String keyword) {
        LocalDateTime latestModificationDate = null;
        List<String> matchedObjectKeys = new ArrayList<>();
        String continuationToken = null;

        do {
            // S3에서 객체를 가져오는 요청 생성
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix+"/")
                    .continuationToken(continuationToken)
                    .build();

            // 요청을 실행하여 응답을 받음
            ListObjectsV2Response listObjectsResponse = awsS3Config.s3Client().listObjectsV2(listObjectsRequest);

            // 응답에서 S3 오브젝트의 이름(키)을 리스트에 추가
            for (S3Object s3Object : listObjectsResponse.contents()) {
                String objectKey = s3Object.key();

                // 오브젝트 키가 키워드를 포함하는지 확인
                if (objectKey.contains(keyword)) {
                    matchedObjectKeys.add(objectKey);
                    // 만약 2개 이상의 오브젝트가 발견되면 예외를 발생시킴
                    if (matchedObjectKeys.size() > 1) {

                        throw new IllegalStateException("여러 개의 오브젝트가 발견되었습니다. 오브젝트는 하나만 있어야 합니다.");
                    }
                }
            }

            // 다음 페이지가 있는지 확인하고 ContinuationToken 설정
            continuationToken = listObjectsResponse.nextContinuationToken();
        } while (continuationToken != null && matchedObjectKeys.isEmpty()); // 다음 페이지가 존재하면 계속 반복

        // 발견된 오브젝트가 없으면 null 반환
        if (matchedObjectKeys.isEmpty()) {
            return null;
        }

        // 유일하게 발견된 오브젝트의 마지막 수정 시간을 가져오기
        String objectKey = matchedObjectKeys.get(0);
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        HeadObjectResponse headObjectResponse = awsS3Config.s3Client().headObject(headObjectRequest);
        return LocalDateTime.ofInstant(
                headObjectResponse.lastModified(),
                ZoneId.systemDefault()
        );
    }


    /** 특정 경로에서 이름을 포함하는 파일의 URL을 반환 */
    public Map<String, String> getFileUrlByNameContains(String prefix, String fileNameContains) throws IOException {
        List<String> objectKeys = new ArrayList<>();
        String continuationToken = null;

        // 모든 오브젝트 키를 가져옴
        do {
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .continuationToken(continuationToken)
                    .build();

            ListObjectsV2Response listObjectsResponse = awsS3Config.s3Client().listObjectsV2(listObjectsRequest);

            objectKeys.addAll(listObjectsResponse.contents().stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList()));

            continuationToken = listObjectsResponse.nextContinuationToken();
        } while (continuationToken != null);

        // 이름을 포함하는 오브젝트를 필터링
        List<String> matchingKeys = objectKeys.stream()
                .filter(key -> key.contains(fileNameContains))
                .collect(Collectors.toList());

        // 하나의 파일만 발견된 경우 URL을 반환
        if (matchingKeys.size() == 1) {
            String matchingKey = matchingKeys.get(0);
            return Collections.singletonMap(fileNameContains, generatePresignedUrl(matchingKey));
        }

        // 파일이 없거나 여러 개가 발견된 경우 빈 맵을 반환
        return Collections.emptyMap();
    }

    /** 주어진 오브젝트 키에 대한 presigned URL을 생성 */
    private String generatePresignedUrl(String keyName) throws IOException {
        // S3Presigner 생성
        try (S3Presigner presigner = S3Presigner.create()) {
            // S3에서 객체 가져오기 요청 생성
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(r -> r.bucket(bucketName).key(keyName))
                    .build();

            // presigned URL 생성
            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toExternalForm();
        }
    }

    public Map<String, String> getFileUrlByNameContainsList(String prefix, List<String> fileNameContainsList) throws IOException {
        List<String> objectKeys = new ArrayList<>();
        String continuationToken = null;

        // 모든 오브젝트 키를 가져옴
        do {
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .continuationToken(continuationToken)
                    .build();

            ListObjectsV2Response listObjectsResponse = awsS3Config.s3Client().listObjectsV2(listObjectsRequest);

            objectKeys.addAll(listObjectsResponse.contents().stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList()));

            continuationToken = listObjectsResponse.nextContinuationToken();
        } while (continuationToken != null);

        // 파일 이름 목록을 포함하는 오브젝트 키를 필터링
        Map<String, String> matchingFiles = new HashMap<>();
        for (String fileNameContains : fileNameContainsList) {
            List<String> matchingKeys = objectKeys.stream()
                    .filter(key -> key.contains(fileNameContains))
                    .collect(Collectors.toList());

            // 파일이 하나만 발견된 경우 URL을 반환
            if (matchingKeys.size() == 1) {
                String matchingKey = matchingKeys.get(0);
                matchingFiles.put(fileNameContains, generatePresignedUrl(matchingKey));
            }
        }

        return matchingFiles;
    }
}

