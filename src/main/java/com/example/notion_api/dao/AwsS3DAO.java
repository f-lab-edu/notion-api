package com.example.notion_api.dao;

import com.example.notion_api.dto.s3.S3FileDTO;

import java.io.IOException;
import java.util.List;

public interface AwsS3DAO {

    /** 아이콘, 이미지 파일 저장 */
    void uploadLocalFile(String localPath, String remotePath, String fileName) throws IOException;

    /** 페이지 생성, 기본 템플릿 페이지 생성 */
    void uploadStringAsFile(String remotePath, String fileName, String content) throws IOException;

    /**  */
    String downloadFileAsString(String remotePath, String fileName) throws IOException;

    /** 페이지 삭제 */
    void deleteFile(String remotePath, String fileName);

    /** 페이지 제목 목록 가져오기 */
    List<String> getFileNames(String remotePath, String prefix);
    String getFileName(String remotePath, String prefix);

    /** 로그인 시 페이지 업데이트, 특정 시간마다 페이지 업데이트 */
    void updateStringAsFile(String remotePath, String fileName, String content) throws IOException;

    /** 페이지 가져오기, 버전별 페이지 가져오기 */
    S3FileDTO getFileNameAndContent(String remotePaht, String prefix) throws IOException;
}
