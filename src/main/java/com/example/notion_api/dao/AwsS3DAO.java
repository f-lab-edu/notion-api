package com.example.notion_api.dao;

import java.io.IOException;

public interface AwsS3DAO {

    /** 파일 업로드(markdown 템플릿을 업로드할 때 사용) */
    void uploadFile(String bucketName, String keyName, String filePath) throws IOException;

    /** 문자열 객체 -> 파일 변환 후 업로드 */
    void uploadStringAsFile(String bucketName, String keyName, String content) throws IOException;

    /** 파일 다운로드 -> 파일을 문자열 객체로 변환 */
    String downloadFileAsString(String bucketName, String keyName) throws IOException;

    /** 파일 삭제 */
    void deleteFile(String bucketName, String keyName) throws Exception;

    /** 파일 업데이트 */
    void updateFile(String bucketName, String keyName, String filePath);
}
