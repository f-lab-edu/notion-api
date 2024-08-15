package com.example.notion_api.enumpage;

public enum PageType {
    DEFAULT("default"),
    TODO("todo"),
    WEEKPLAN("weekplan"),
    DIARY("diary"),
    TABLE("table"),
    BOARD("board"),
    LIST("list"),
    TIMELINE("timeline"),
    CALENDAR("calendar"),
    GALLERY("gallery");

    private final String value;

    PageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PageType fromValue(String value) {
        for (PageType type : PageType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
