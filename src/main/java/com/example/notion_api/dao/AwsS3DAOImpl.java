package com.example.notion_api.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.notion_api.dto.s3.S3FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AwsS3DAOImpl implements AwsS3DAO{

    @Value("${aws.s3.credentials.bucket-name}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    @Autowired
    public AwsS3DAOImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    @Override
    public void uploadLocalFile(String localPath, String remotePath, String fileName) throws IOException {
        String keyName = new StringBuilder().append(remotePath).append("/")
                                            .append(fileName).toString();
        File file = new ClassPathResource(localPath).getFile();
        amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file));
    }

    @Override
    public void uploadStringAsFile(String remotePath, String fileName, String content) throws IOException {
        String keyName = new StringBuilder().append(remotePath).append("/")
                                            .append(fileName).toString();
        File file = File.createTempFile(keyName, ".txt");
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
        amazonS3.putObject(new PutObjectRequest(bucketName,keyName,file));
    }

    @Override
    public String downloadFileAsString(String remotePath, String fileName) throws IOException {
        String keyName = new StringBuilder().append(remotePath).append(("/"))
                                            .append(fileName).toString();
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName,keyName));
        InputStream inputStream = s3Object.getObjectContent();

        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null){
            content.append(line);
        }
        inputStream.close();

        return content.toString();
    }

    @Override
    public void deleteFile(String remotePath, String fileName) {
        String keyName = new StringBuilder().append(remotePath).append("/")
                                                .append(fileName).toString();
        amazonS3.deleteObject(bucketName, keyName);
    }

    @Override
    public List<String> getFileNames(String remotePath, String prefix) {
        List<String> fileNames = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);
        ListObjectsV2Result result;

        do {
            result = amazonS3.listObjectsV2(request);
            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String fileName = objectSummary.getKey();
                if (!fileName.endsWith("/")) {
                    fileNames.add(fileName);
                }
            }
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());
        return fileNames;
    }

    @Override
    public void updateStringAsFile(String remotePath, String fileName, String content) throws IOException {
        byte[] contentAsBytes = content.getBytes();
        InputStream inputStream = new ByteArrayInputStream(contentAsBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentAsBytes.length);

        amazonS3.putObject(bucketName, fileName, inputStream, metadata);
    }

    @Override
    public S3FileDTO getFileNameAndContent(String remotePath, String prefix) throws IOException {
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix)
                .withMaxKeys(1);

        ListObjectsV2Result result = amazonS3.listObjectsV2(request);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        if (!objects.isEmpty()) {
            String fileName = objects.get(0).getKey();

            S3Object s3Object = amazonS3.getObject(bucketName, fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            StringBuilder contentBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            String content = contentBuilder.toString();

            return new S3FileDTO(fileName, content);
        }
        return null;
    }
}
