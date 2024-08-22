package com.example.notion_api.dao;

import com.example.notion_api.dto.s3.S3FileDTO;

import java.util.List;

public interface AwsS3DAO {

    /** 아이콘, 이미지 파일 저장 */
    void uploadFile(String bucketName, String keyName, String filePath);

    /** 페이지 생성, 기본 템플릿 페이지 생성 */
    void uploadStringAsFile(String bucketName, String keyName, String content);

    /**  */
    String downloadFileAsString(String bucketName, String keyName);

    /** 페이지 삭제 */
    void deleteFile(String bucketName, String keyName);

    /** 페이지 제목 목록 가져오기 */
    List<String> getFileNames(String bucketName, String prefix);

    /** 로그인 시 페이지 업데이트, 특정 시간마다 페이지 업데이트 */
    void updateFile(String bucketName, String keyName, String content);

    /** 페이지 가져오기, 버전별 페이지 가져오기 */
    S3FileDTO getFileNameAndContent(String bucketName, String prefix);
}
