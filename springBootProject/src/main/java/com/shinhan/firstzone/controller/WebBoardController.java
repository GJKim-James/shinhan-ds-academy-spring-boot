package com.shinhan.firstzone.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;
import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.twoway2.WebBoardDTO;
import com.shinhan.firstzone.twoway2.WebBoardEntity;
import com.shinhan.firstzone.twoway2.WebBoardService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/webboard")
public class WebBoardController {
	
	@Autowired
	WebBoardService boardService;
	
	// tbl_webboards 목록 조회
	@GetMapping("/list")
	public void selectBoardAll(Model model) {
		model.addAttribute("boardlist", boardService.selectBoardAll());
	}
	
	// 페이징이 포함된 tbl_webboards 목록 조회
	@GetMapping("/list2")
	public String selectBoardAllPaging(Model model, PageRequestDTO pageRequestDTO) {
		System.out.println("[pageRequestDTO] : " + pageRequestDTO); // PageRequestDTO(page=0, size=0, type=null, keyword=null)
		
		// 처음 조회했을 경우
		if (pageRequestDTO.getPage() == 0) {
			pageRequestDTO = new PageRequestDTO(1, 10);
		}
//		PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10, "tcw", "gjkim1");
		
		PageResultDTO<WebBoardDTO, WebBoardEntity> boardResult = boardService.selectBoardAllPaging(pageRequestDTO);
		
		model.addAttribute("boardResult", boardResult);
		model.addAttribute("pageRequestDTO", pageRequestDTO);
		
		return "webboard/list2";
	}
	
	// tbl_webboards 특정 게시글 상세보기(@RequestParam 이용)
	@GetMapping("/detail")
	public void selectBoardById(@RequestParam Long bno, Model model) {
		model.addAttribute("board", detail(bno));
	}
	
	// tbl_webboards 특정 게시글 상세보기(@PathVariable 이용)
	@GetMapping("/detail2/{bno}")
	public String selectBoardById2(@PathVariable Long bno, Model model) {
		model.addAttribute("board", detail(bno));
		
		return "webboard/detail";
	}
	
	// Entity를 DTO로 변환하는 함수
	private WebBoardDTO detail(Long bno) {
		WebBoardEntity entity = boardService.selectBoardById(bno);
		
		// Entity를 DTO로 변환
		WebBoardDTO dto = boardService.entityToDTO(entity);
		
		return dto;
	}
	
	// tbl_webboards 수정
	@PostMapping("/update")
	public String updateBoard(WebBoardDTO board) {
		// DTO를 Entity로 변환
		WebBoardEntity entity = boardService.dtoToEntity(board);
		
		boardService.updateBoard(entity);
		
		return "redirect:list2";
	}
	
	// tbl_webboards에 등록하는 화면
	@GetMapping("/register")
	public void insertPage() {
		
	}
	
	// tbl_webboards에 등록
	@PostMapping("/register")
	public String insertBoard(WebBoardDTO board, Principal principal, Authentication authentication, HttpSession session) {
		// 1. Principal
		String a = principal.getName();
		System.out.println("1. Principal : " + a);
		
		// 2. Authentication
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String b = user.getUsername();
		System.out.println("2. Authentication : " + b);
		
		// 3. SecurityContextHolder
		UserDetails user2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String c = user2.getUsername();
		System.out.println("3. SecurityContextHolder : " + c);
		
		// 4. HttpSession
//		String mid = "gjkim1"; // Spring Security 추가하여 주석으로 변경
		MemberEntity member = (MemberEntity) session.getAttribute("loginMember");
		String mid = member.getMid();
		System.out.println("4. HttpSession : " + mid);
		
		board.setMid(mid);
		WebBoardEntity entity = boardService.dtoToEntity(board);
		boardService.insertBoard(entity);
		
		return "redirect:list2";
	}
	
	// 게시글 삭제
	@GetMapping("/delete")
	public String deleteBoard(Long bno) {
		boardService.deleteBoardByBno(bno);
		
		return "redirect:list2";
	}

}
