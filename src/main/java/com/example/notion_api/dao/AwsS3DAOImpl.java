package com.example.notion_api.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.dto.page.PageSyncResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AwsS3DAOImpl implements AwsS3DAO{

    private final AmazonS3 s3Client;

    @Autowired
    public AwsS3DAOImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(String bucketName, String keyName, String filePath) throws IOException {
        File file = new ClassPathResource(filePath).getFile();
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file));

        return "";
    }

    @Override
    public String uploadImage(String bucketName, String keyName, MultipartFile file) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        return s3Client.getUrl(bucketName, keyName).toString();
    }

    @Override
    public String uploadVideo(String bucketName, String keyName, MultipartFile file) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        return s3Client.getUrl(bucketName, keyName).toString();
    }

    @Override
    public String uploadAudio(String bucketName, String keyName, MultipartFile file) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), null));
        return s3Client.getUrl(bucketName, keyName).toString();
    }

    @Override
    public void uploadStringAsFile(String bucketName, String keyName, String content) throws IOException {
        File file = File.createTempFile(keyName,".txt");
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();

        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file));
    }

    @Override
    public void uploadStringAsFileList(String bucketName, List<String> keyNames, List<String> contents) throws IOException {
        for (int i=0; i<keyNames.size(); i++){
            String keyName = keyNames.get(i);
            String content = contents.get(i);

            File file = File.createTempFile(keyName, ".txt");
            FileWriter writer = new FileWriter(file);
            writer.write(content);

            s3Client.putObject(new PutObjectRequest(bucketName, keyName, file));
        }
    }

    @Override
    public String downloadFileAsString(String bucketName, String keyName) throws IOException {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
        InputStream inputStream = s3Object.getObjectContent();

        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null){
            content.append(line).append("\n");
        }
        inputStream.close();

        return content.toString();
    }

    @Override
    public String getS3StorageFileContent(String bucketName, String userId, String pageId) throws IOException {
        // userId 및 pageId에 해당하는 파일의 keyName을 찾기 위한 prefix 설정
        String prefix = new StringBuilder().append(userId).append("/")
                                            .append(userId).append("_")
                                            .append(pageId)
                                            .toString();
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        // S3에서 해당 prefix로 객체 목록 가져오기
        ListObjectsV2Result result = s3Client.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        if (objects.isEmpty()) {
            throw new IOException("No files found for the given userId and pageId.");
        }

        // 첫 번째 객체의 keyName 사용 (일반적으로 해당 객체가 유일하다고 가정)
        String keyName = objects.get(0).getKey();

        // S3에서 해당 keyName에 해당하는 파일을 다운로드하여 내용을 스트링으로 변환
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
        InputStream inputStream = s3Object.getObjectContent();

        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        inputStream.close();

        return content.toString();
    }


    @Override
    public List<String> downloadFileAsStringList(String bucketName, List<String> keyNames) throws IOException {
        List<String> fileContents = new ArrayList<>();

        for (String keyName : keyNames){
            String content = downloadFileAsString(bucketName, keyName);
            fileContents.add(content);
        }
        return fileContents;
    }

    @Override
    public void deleteFile(String bucketName, String userId, String pageId){
        String prefix = new StringBuilder().append(userId).append("/")
                                            .append(userId).append("_")
                                            .append(pageId)
                                            .toString();
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result result = s3Client.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        for (S3ObjectSummary object : objects) {
            String keyName = object.getKey();
            s3Client.deleteObject(bucketName, keyName);
        }
    }

    @Override
    public void updateFile(String bucketName, String keyName, String filePath) throws Exception{
//        deleteFile(bucketName, keyName);
        uploadFile(bucketName, keyName, filePath);
    }

    @Override
    public PageSyncResultDTO compareAndSyncFiles(PageDTO pageDTO, String bucketName, String keyName) {
        return null;
    }

    @Override
    public String getS3StorageFileDateTime(String bucketName, String userId, String pageId) {
        String prefix = new StringBuilder().append(userId).append("/")
                                            .append(userId).append("_")
                                            .append(pageId)
                                            .toString();

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result result = s3Client.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        // 첫 번째 객체의 keyName에서 formattedDate 추출
        String keyName = objects.get(0).getKey();
        String[] parts = keyName.split("_");

        return parts[parts.length-1];
    }

    @Override
    public String getKeyName(String bucketName, String userId, String pageId) throws IOException {
        String prefix = new StringBuilder().append(userId).append("/")
                                            .append(userId).append("_")
                                            .append(pageId)
                                            .toString();
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result result = s3Client.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        String keyName = objects.get(0).getKey();

        return keyName;
    }

    @Override
    public List<String> getListOfKeyName(String bucketName, String userId) {
        List<String> objectKeys = new ArrayList<>();
        String prefix = userId + "/";
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request()
                                                            .withBucketName(bucketName)
                                                            .withPrefix(prefix);
        ListObjectsV2Result result;
        String continuationToken = null;
        do {
            if (continuationToken != null){
                listObjectsV2Request.setContinuationToken(continuationToken);
            }
            result = s3Client.listObjectsV2(listObjectsV2Request);
            result.getObjectSummaries().forEach(summary -> objectKeys.add(summary.getKey()));

            listObjectsV2Request.setContinuationToken(result.getNextContinuationToken());
        }while (result.isTruncated());

        return objectKeys;
    }
}
