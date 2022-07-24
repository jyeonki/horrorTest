package com.project.horror.community.repository;

import org.apache.ibatis.annotations.Mapper;

import com.project.horror.common.paging.Page;
import com.project.horror.community.domain.Board;
import com.project.horror.community.domain.CommunityCode;
import com.project.horror.community.domain.CommunitySearch;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시글 쓰기 기능
    boolean save(Board board);

    // 게시글 전체 조회
//    List<Board> findAll(); // mybatis는 같은 이름은 지원하지 않는다 (findAll 2개 불가능)

    // 게시글 전체 조회
    List<Board> findAll();

    // 게시글 전체 조회 with paging
    List<Board> findAllWithPaging(Page page);

    // 게시글 전체 조회 with searching
    List<Board> findAllWithSearch(CommunitySearch search);

    // 게시글 상세 조회
    Board findOne(long boardNo);

    // 게시글 수정
    boolean modify(Board board);

    // 게시글 삭제
    boolean remove(long boardNo);

    // 전체 게시물 수 조회
    int getTotalCount();
    int getTotalCountWithSearch(CommunitySearch search); //검색할 때 검색 건수에 따라 밑에 페이징 수 조절을 위해 WHERE 절 추가


    // 조회수 상승 처리
    void upViewCount(Long boardNo);

    // 카테고리 목록 조회 
    List<CommunityCode> getCategoryList();

    // 좋아요 상승 처리
    boolean upLikeCount(Board board);

    // 좋아요 이력 생성
    boolean makeLikeHistory(Board board);

    // 좋아요 생성이력 확인
    int checkLikeHistory(Board board);
}
