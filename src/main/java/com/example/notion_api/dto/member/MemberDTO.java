package com.example.notion_api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDTO {
    private String userId;
    private List<String> memberIds;

}
