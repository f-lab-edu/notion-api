package com.example.notion_api.dao;

import com.example.notion_api.dto.s3.S3FileDTO;

import java.util.List;

public class AwsS3DAOImpl implements AwsS3DAO{
    @Override
    public void uploadLocalFile(String localPath, String remotePath, String fileName) {

    }

    @Override
    public void uploadStringAsFile(String remotePath, String fileName, String content) {

    }

    @Override
    public String downloadFileAsString(String remotePath, String fileName) {
        return "";
    }

    @Override
    public void deleteFile(String remotePath, String fileName) {

    }

    @Override
    public List<String> getFileNames(String remotePath, String prefix) {
        return List.of();
    }

    @Override
    public void updateStringAsFile(String remotePath, String fileName, String content) {

    }

    @Override
    public S3FileDTO getFileNameAndContent(String remotePaht, String prefix) {
        return null;
    }
}
