package com.example.notion_api.enums;

public enum PermissionLevel {
    FULL_ACCESS("전체허용"),
    EDIT_ACCESS("편집허용"),
    COMMENT_ACCESS("댓글허용"),
    READ_ACCESS("읽기허용");

    private final String description;

    PermissionLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
