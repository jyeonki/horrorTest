package com.project.horror.community.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.horror.community.domain.CommunityCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.horror.common.paging.Page;
import com.project.horror.common.paging.PageMaker;
import com.project.horror.community.domain.Board;
import com.project.horror.community.domain.CommunitySearch;
import com.project.horror.community.service.BoardMapperService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/*
 * 게시물 목록 요청: /horror/community/list: GET
 * 게시물 상세조회 요청: /horror/community/content: GET
 * 게시글 쓰기화면 요청: /horror/community/write: GET
 * 게시글 등록 요청: /horror/community/write: POST
 * 게시글 삭제 요청: /horror/community/delete: GET
 * 게시글 수정화면 요청: /horror/community/modify: GET
 * 게시글 수정 요청: /horror/community/modify: POST
 */

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/horror/community")
public class CommunityController {

    private final BoardMapperService boardService;

    // 게시물 목록 요청
    @GetMapping("/list")
    public String list(@ModelAttribute("s") CommunitySearch search, Model model) {

        log.info("controller request /horror/community/list GET! - search: {}", search);

        Map<String, Object> boardMap = boardService.findAllWithSearchService(search);
        log.debug("return data - {}", boardMap);

        PageMaker pm = new PageMaker(new Page(search.getPageNum(), search.getAmount()), (Integer) boardMap.get("tc"));
        model.addAttribute("bList", boardMap.get("bList"));
        model.addAttribute("pm", pm);
        model.addAttribute("ctgrList", boardMap.get("ctgrList"));

        return "/community/board/board-list";
    }

    // 게시물 상세 조회 요청
    @GetMapping("/content/{boardNo}")
    public String content(@PathVariable Long boardNo, Model model, HttpServletResponse response, HttpServletRequest request, @ModelAttribute("p") Page page) {
        // response 쿠키를 실어서 보내주려고
        log.info("controller request /horror/community/content GET! - {}", boardNo);

        Board board = boardService.findOneService(boardNo, response, request);
        log.info("return data - {}", board);

        // 데이터를 실어서 보내줄 model
        model.addAttribute("b", board);
        return "/community/board/board-detail";
    }

    // 게시물 쓰기 화면 요청
    @GetMapping("/write")
    public String write(Model model) {

        log.info("controller request /horror/community/write GET!");

        List<CommunityCode> ctgrList = boardService.getCategoryListService();
        model.addAttribute("ctgrList", ctgrList);

        return "/community/board/board-write";
    }

    // 게시글 등록 요청
    @PostMapping("/write")
    public String write(Board board, RedirectAttributes ra) {

        log.info("controller request /horror/community/write POST! - {}", board);
        boolean flag = boardService.saveService(board);

        // 게시물 등록에 성공하면 클라이언트에 성공메시지 전송
        if (flag) ra.addFlashAttribute("msg", "reg-success");

        return flag ? "redirect:/horror/community/list" : "redirect:/";
    }

    // 게시물 삭제 요청
    @GetMapping("/delete")
    public String delete(long boardNo) {

        log.info("controller request /horror/community/delete GET! - bno: {}", boardNo);

        return boardService.removeService(boardNo) ? "redirect:/horror/community/list" : "redirect:/";
    }

    // 게시물 수정 화면 요청
    @GetMapping("/modify")
    public String modify(Long boardNo, Model model, HttpServletResponse response, HttpServletRequest request) {
        log.info("controller request /horror/community/modify GET! - bno: {}", boardNo);
        Board board = boardService.findOneService(boardNo, response, request);
        log.info("find article: {}", board);

        model.addAttribute("board", board);
        return "/community/board/board-modify";
    }

    // 게시물 수정 요청
    @PostMapping("/modify")
    public String modify(Board board) {

        log.info("controller request /horror/community/modify POST! - {}", board);

        boolean flag = boardService.modifyService(board);
        return flag ? "redirect:/horror/community/content/" + board.getBoardNo() : "redirect:/";
    }

    // 좋아요 처리
    @ResponseBody
    @PutMapping("/like")
    public ResponseEntity<Map<String, String>> like(@RequestBody Board board) {

        log.info("controller request /horror/community/like PUT! - {}", board);
        
        Map<String, String> rtnMap = new HashMap<>();

        // 좋아요 생성이력 확인
        int checkResult = boardService.checkLikeHistoryService(board);

        if( checkResult > 0 ) {
        	rtnMap.put("message", "이미 좋아요를 하셨습니다.");
        	
        } else {
            // 좋아요 상승 처리
            boardService.upLikeCountService(board);
            
            // 좋아요 이력 생성
            boardService.makeLikeHistoryService(board);
            
            rtnMap.put("message", "좋아요 하셨습니다.");
        }
        
    	return ResponseEntity.status(HttpStatus.OK).body(rtnMap);
    }
}
