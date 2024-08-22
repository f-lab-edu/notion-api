package com.example.notion_api.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.notion_api.dto.s3.S3FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
        File directory = new File(remotePath+"/");
        if (directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();

            if (files != null){
                for (File file : files){
                    if(file.isFile() && file.getName().startsWith(prefix)){
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    @Override
    public void updateStringAsFile(String remotePath, String fileName, String content) throws IOException {
        File file = new File(remotePath, fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
    }

    @Override
    public S3FileDTO getFileNameAndContent(String remotePath, String prefix) throws IOException {
        File directory = new File(remotePath);
        if (directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            if (files != null){
                for (File file : files){
                    if (file.isFile() && file.getName().startsWith(prefix)){
                        String content = new String(Files.readAllBytes(file.toPath()));
                        return new S3FileDTO(file.getName(),content);
                    }
                }
            }
        }
        return null;
    }
}
