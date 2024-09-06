package com.example.notion_api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserInfoDTO {
    private String userId;
    private String password;
    private String email;
    /**
     * 서버에서 최종적으로 페이지 동기화 작업이 된 시간.
     * 페이지의 업데이트 시간들은 각각 다른 값을 갖고 있으며,
     * 로컬과 서버의 페이지 존재유무 자체도 다를 수 있기 떄문에,
     * 한쪽의 페이지가 존재하지 않는 경우에는 페이지 버전의 비교가 불가능.
     * 로컬과 서버의 유저 데이터에 '최종 동기화된 시간'값을 갖도록 하며
     * 로컬과 서버의 업데이트 기준이 됨.
     * */
    @DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
    private LocalDateTime syncDate;
}
