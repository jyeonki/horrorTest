package com.project.horror.community.domain;

import lombok.*;

@Setter @Getter @ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor // 6개는 보통 기본으로 설정
public class CommunityCode {

    // table column field
    private String codeGroup;
    private String code;
    private String codeNm;
    private int sort;

    // custom data field
}
