package com.example.notion_api.service;

import com.example.notion_api.dto.member.MemberDTO;
import com.example.notion_api.dto.member.MemberIdsDTO;

import java.util.List;

public interface MemberService {

    MemberDTO addMember(String userId, String memberId);

    MemberIdsDTO getMemberList(String userIdPart);
}
