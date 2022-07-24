package com.project.horror.community.domain;

import lombok.*;

@Setter @Getter @ToString @EqualsAndHashCode
@AllArgsConstructor // 6개는 보통 기본으로 설정
public class CommunitySearch {

    private int pageNum; // 페이지 번호
    private int amount;  // 한 페이지당 배치할 게시물 수
    
    private String category;
    private String type;
    private String keyword;

    public CommunitySearch() {
        this.pageNum = 1;
        this.amount = 10;
    }

    public void setPageNum(int pageNum) {
        if (pageNum <= 0 || pageNum > Integer.MAX_VALUE) {
            this.pageNum = 1;
            return;
        }
        this.pageNum = pageNum;
    }

    public void setAmount(int amount) {
        if (amount < 10 || amount > 100) {
            this.amount = 10;
            return;
        }
        this.amount = amount;
    }
}
