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
    public static final String MARKDOWN_TEMPLATE_TABLE =
           "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"\"/>\n"+
            "<n-table-head title=\"표\",column=\"2\", type1=\"title\", text1=\"이름\", type2=\"multichoice\",text2=\"태그\"/>\n"+
            "<n-table-content column=\"2\", text1=\"\", text2=\"\"/>\n"+
            "<n-table-content column=\"2\", text1=\"\", text2=\"\"/>\n"+
            "<n-table-content column=\"2\", text1=\"\", text2=\"\"/>\n";
    public static final String MARKDOWN_TEMPLATE_TIMELINE =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"\"/>\n"+
            "<n-timeline type=\"title\", title=\"타임라인 보기\"/>\n"+
            "<n-board-content title=\"카드1\", type1=\"date\",text=\"{todaydate}{date}\",type2=\"people\",text=\"\",type3=\"state\",text=\"시작전\"/>\n"+
            "<n-board-content title=\"카드2\", type1=\"date\",text=\"{todaydate}{date}\",type2=\"people\",text=\"\",type3=\"state\",text=\"시작전\"/>\n"+
            "<n-board-content title=\"카드3\", type1=\"date\",text=\"{todaydate}{date}\",type2=\"people\",text=\"\",type3=\"state\",text=\"시작전\"/>\n";
    public static final String MARKDOWN_TEMPLATE_TODO =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"할 일 목록\"/>\n"+
            "<n-icon path = \"...\">\n"+
            "<n-cover path = \"...\">\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"\"/>\n";
    public static final String MARKDOWN_TEMPLATE_WEEKPLAN =
            "<n-title font=\"bold\",sort=\"left\",hint=\"제목없음\",text=\"{date}\"/>\n"+
            "<n-icon path = \"...\">\n"+
            "<n-cover path = \"...\">\n"+
            "<n-content type=\"titletoggle1\" text=\"월요일\">\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "</n-content>\n"+
            "<n-content type=\"titletoggle1\" text=\"화요일\">\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "</n-content>\n"+
            "<n-content type=\"titletoggle1\" text=\"수요일\">\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "</n-content>\n"+
            "<n-content type=\"titletoggle1\" text=\"목요일\">\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "</n-content>\n"+
            "<n-content type=\"titletoggle1\" text=\"금요일\">\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"...\"/>\n"+
            "</n-content>\n";
}
