package com.example.notion_api.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class AwsS3DAOImpl implements AwsS3DAO{

    private final AmazonS3 s3Client;

    @Autowired
    public AwsS3DAOImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void uploadFile(String bucketName, String keyName, String filePath) throws IOException {
        File file = new ClassPathResource(filePath).getFile();
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file));
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
    public void deleteFile(String bucketName, String keyName) throws Exception {
        s3Client.deleteObject(bucketName, keyName);
    }

    @Override
    public void updateFile(String bucketName, String keyName, String filePath) throws Exception{
        deleteFile(bucketName, keyName);
        uploadFile(bucketName, keyName, filePath);
    }
}
