package com.example.notion_api.markdown;

public class MarkdownTemplate {
    public static final String MARKDOWN_TEMPLATE_BOARD =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"\"/>\n"+
            "<n-board title=\"보드 보기\", state1=\"시작전\", state2=\"진행 중\", state3=\"완료\"/>\n"+
            "<n-board-state1 title=\"카드1\", type1=\"상태\",text1=\"시작 전\",type2=\"people\",text2=\"\"/>\n"+
            "<n-board-state1 title=\"카드2\", type1=\"상태\",text1=\"시작 전\",type2=\"people\",text2=\"\"/>\n"+
            "<n-board-state1 title=\"카드3\", type1=\"상태\",text1=\"시작 전\",type2=\"people\",text2=\"\"/>\n";
    public static final String MARKDOWN_TEMPLATE_CALENDER =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"\"/>\n"+
            "<n-calender title=\"캘린더 보기\", date=\"{todaydate}\"/>";
    public static final String MARKDOWN_TEMPLATE_DEFAULT =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"\"/>\n"+
            "<n-icon path = \"...\">\n"+
            "<n-cover path = \"...\">\n";
    public static final String MARKDOWN_TEMPLATE_DIARY =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"{date}\"/>\n"+
            "<n-icon path = \"...\">\n"+
            "<n-cover path = \"...\">\n"+
            "<n-content type=\"title2\", text=\"오늘의 기분\"/>\n"+
            "<n-content type=\"text\", text=\"...\"/>\n"+
            "<n-content type=\"title2\", text=\"오늘의 생각\"/>\n"+
            "<n-content type=\"list\", text=\"...\"/>\n"+
            "<n-content type=\"list\", text=\"...\"/>\n"+
            "<n-content type=\"list\", text=\"...\"/>\n"+
            "<n-content type=\"title2\", text=\"긍정적인 확언\"/>\n"+
            "<n-content type=\"callout\", path=\"...\", text=\"\"/>\n";
    public static final String MARKDOWN_TEMPLATE_LIST =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"\"/>\n"+
            "<n-list title=\"리스트 보기\"/>\n"+
            "<n-page title=\"페이지1\", createddate=\"{createddate}\", tag=\"\",taghint=\"비어있음\"/>\n"+
            "<n-page title=\"페이지2\", createddate=\"{createddate}\", tag=\"\",taghint=\"비어있음\"/>\n"+
            "<n-page title=\"페이지3\", createddate=\"{createddate}\", tag=\"\",taghint=\"비어있음\"/>\n";

}
