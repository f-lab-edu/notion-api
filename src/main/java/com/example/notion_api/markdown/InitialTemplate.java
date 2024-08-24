package com.example.notion_api.markdown;

public class InitialTemplate {

    public static final String INITIAL_TEMPLATE1 =
            "<n-title font=\"bold\",sort=\"left\",hint=\"\",text=\"모바일에서 시작하기\"/>\n"+
            "<n-content type=\"text\", text=\"Notion에 오신 것을 환영합니다!\"/>\n"+
            "<n-content type=\"text\", text=\"...\"/>\n"+
            "<n-content type=\"text\", text=\"기본 사항은 다음과 같습니다.\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"아무 곳이나 탭하여 입력을 시작하세요\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"키보드 위의 + 를 탭하여 제목과 하위 페이지 등의 콘텐츠를 추가합니다.\">\n"+
            "<n-content type=\"page\", text=\"하위 페이지 예시\">" +
                "<n-content type=\"text\", text=\"Notion에서는 페이지 내에 하위 페이지를 무한대로 중첩할 수 있습니다. 지저분한 폴더와 작별하세요!\"/>"+
                "<n-content type=\"text\", text=\"\"/>"+
                "<n-content type=\"text\", text=\"상위 페이지로 돌아가려면 왼쪽 상단의 링크를 탭하거나 화면을 왼쪽에서 오른쪽으로 미세요.\"/>"+
            "</n-content>"+
            "</n-content>"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"텍스트를 강조 표시하면 나타나는 메뉴바에서 서식을 지정할 수 있습니다.\"/>\n"+
                "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"이 메뉴바는 왼쪽에서 오른쪽으로 스크롤할 수 있습니다.\"/>\n" +
            "</n-content>"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"해당 줄을 탭한 상태로 끌어서 놓을 수 있습니다.\"/>\n"+
                    "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"☰ 버튼을 탭하여 사이드바를 엽니다.\"/>\n"+
            "<n-content type=\"text\", text=\"질문이 있으신가요? 사이드바의 도움말 및 피드백을 탭하세요.\"/>\n";

    public static final String INITIAL_TEMPLATE2 =
            "<n-title font=\"bold\",sort=\"left\",hint=\"\",text=\"빠른 메모\"/>\n"+
            "<n-content type=\"text\", text=\"풍부한 서식의 문서를 신속하게 작성해 보세요.\"/>\n"+
            "<n-content type=\"title1\", text=\"텍수투 입력하기\"/>"+
            "<n-content type=\"text\", text=\"\"/>\n"+
            "<n-content type=\"text\", text=\"메리는 여느 때처럼 통주저음과 인간 본성을 깊이 연구하고 있었다. 새로운 인용문들에 감탄하면서 몇 가지 진부한 교훈이 담긴 새로운 설교도 귀담아듣고 있었다. 캐서린과 리디아는 두 언니에게 다른 종류의 소식을 들려주었다. \"/>\n"+
            "<n-content type=\"text\", text=\"\"/>\n"+
            "<n-content type=\"title1\", text=\"체크리스트 만들기\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"기상\"/>\n"+
            "<n-content type=\"todo\", state=\"unchecked\", hint=\"할 일\",text=\"아침 식사\"/>\n"+
            "<n-content type=\"todo\", state=\"checked\", hint=\"할 일\",text=\"양치\"/>\n"+
            "<n-content type=\"text\", text=\"\"/>\n"+
            "<n-content type=\"title1\", text=\"하위 페이지 만들기\"/>\n"+
            "<n-content type=\"page\", text=\"하위 페이지\">" +
                "<n-content type=\"list\", text=\"\"/>"+
                "<n-content type=\"list\", text=\"\"/>"+
                "<n-content type=\"list\", text=\"\"/>"+
            "</n-content>"+
            "<n-content type=\"title1\", text=\"링크 임베드하기\"/>";
}
