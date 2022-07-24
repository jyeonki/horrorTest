package com.project.horror.community.domain;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor // 6개는 보통 기본으로 설정
public class Board {

    // table column field
    private long boardNo; // table NUMBER(10) -> long
    private String writer;
    private String category;
    private String categoryNm;
    private String title;
    private String content;
    private long viewCnt; // table NUMBER(10) -> long
    private long likeCnt;
    private String id;
    private Date regDate; // 등록일자

    // custom data field
    private String shortTitle;   // 줄임 제목
    private String prettierDate; // 변경된 날짜포맷 문자열
    private boolean newArticle;  // 신규 게시물 여부
}
