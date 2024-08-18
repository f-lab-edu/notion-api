package com.example.notion_api.dao;

import com.example.notion_api.dto.page.PageDTO;
import com.example.notion_api.dto.page.PageSyncResultDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AwsS3DAO {

    /** keyName(s3 객체) 구조
     *
     *   userId _ title _ date
     *
     * */

    /** 파일 업로드(markdown 템플릿을 업로드할 때 사용) */
    void uploadFile(String bucketName, String keyName, String filePath) throws IOException;

    /** 이미지, 동영상, 미디어 파일 업로드 */
    String uploadImage(String bucketName, String keyName, MultipartFile file) throws IOException;
    String uploadVideo(String bucketName, String keyName, MultipartFile file) throws IOException;
    String uploadAudio(String bucketName, String keyName, MultipartFile file) throws IOException;

    /** 문자열 객체 -> 파일 변환 후 업로드 */
    void uploadStringAsFile(String bucketName, String keyName, String content) throws IOException;

    /** 여러개의 문자열 객체 -> 여러 개의 파일 변환 후 업로드 */
    void uploadStringAsFileList(String bucketName, List<String> keyNames, List<String> contents) throws IOException;

    /** 파일 다운로드 -> 파일을 문자열 객체로 변환 */
    String downloadFileAsString(String bucketName, String keyName) throws IOException;

    /** 여러 개의 파일 다운로드 -> 여러개의 문자열 객체로 변환 */
    List<String> downloadFileAsStringList(String bucketName, List<String> keyName) throws IOException;

    /** 파일 삭제 */
    void deleteFile(String bucketName, String keyName);

    /** 파일 업데이트 */
    void updateFile(String bucketName, String keyName, String filePath) throws Exception;

    /** 로컬과 s3 저장소의 파일 수정날짜 비교 */
    PageSyncResultDTO compareAndSyncFiles(PageDTO pageDTO, String bucketName, String keyName);
}
