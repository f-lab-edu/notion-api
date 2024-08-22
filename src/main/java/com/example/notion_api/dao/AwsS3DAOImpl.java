package com.example.notion_api.dao;

import com.example.notion_api.dto.s3.S3FileDTO;

import java.util.List;

public class AwsS3DAOImpl implements AwsS3DAO{
    @Override
    public void uploadFile(String bucketName, String keyName, String filePath) {

    }

    @Override
    public void uploadStringAsFile(String bucketName, String keyName, String content) {

    }

    @Override
    public String downloadFileAsString(String bucketName, String keyName) {
        return "";
    }

    @Override
    public void deleteFile(String bucketName, String keyName) {

    }

    @Override
    public List<String> getFileNames(String bucketName, String prefix) {
        return List.of();
    }

    @Override
    public void updateFile(String bucketName, String keyName, String content) {

    }

    @Override
    public S3FileDTO getFileNameAndContent(String bucketName, String prefix) {
        return null;
    }
}
