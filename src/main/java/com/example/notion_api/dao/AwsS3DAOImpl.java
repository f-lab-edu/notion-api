package com.example.notion_api.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
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
    public List<String> downloadFileAsStringList(String bucketName, List<String> keyNames) throws IOException {
        List<String> fileContents = new ArrayList<>();

        for (String keyName : keyNames){
            String content = downloadFileAsString(bucketName, keyName);
            fileContents.add(content);
        }
        return fileContents;
    }

    @Override
    public void deleteFile(String bucketName, String keyName){
        s3Client.deleteObject(bucketName, keyName);
    }

    @Override
    public void updateFile(String bucketName, String keyName, String filePath) throws Exception{
        deleteFile(bucketName, keyName);
        uploadFile(bucketName, keyName, filePath);
    }

    @Override
    public PageSyncResultDTO compareAndSyncFiles(PageDTO pageDTO, String bucketName, String keyName) {
        return null;
    }
}
