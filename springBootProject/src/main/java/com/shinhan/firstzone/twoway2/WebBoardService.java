package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

@Service
public class WebBoardService {
	
	@Autowired
	WebBoardRepository boardRepo;
	
	// tbl_webboards 모두 조회
	public List<WebBoardEntity> selectBoardAll() {
		return boardRepo.findAll();
	}
	
	// 특정 bno의 상세보기
	public WebBoardEntity selectBoardById(Long bno) {
		return boardRepo.findById(bno).orElse(null);
	}
	
	// 특정 Member가 작성한 board 정보 조회
	public List<WebBoardEntity> selectBoardByMember(MemberEntity member) {
		return boardRepo.findByWriter(member);
	}
	
	// 동적 SQL 사용하기(tbl_webboards에서 type이 keyword를 가지는 데이터 조회)
	public List<WebBoardEntity> dynamicSQL(String type, String keyword) {
		Predicate predicate = boardRepo.makePredicate(type, keyword);
		
		return (List<WebBoardEntity>) boardRepo.findAll(predicate);
	}
	
	// tbl_webboards에 입력
	public WebBoardEntity insertBoard(WebBoardEntity board) {
		return boardRepo.save(board);
	}
	
	// tbl_webboards 수정
	public WebBoardEntity updateBoard(WebBoardEntity board) {
		return boardRepo.save(board);
	}
	
	// tbl_webboards 특정 게시글 삭제
	public void deleteBoardByBno(Long bno) {
		boardRepo.deleteById(bno);
	}
	
	// tbl_webboards 여러 게시글 삭제
	public void deleteBoards(List<Long> bnoList) {
		boardRepo.deleteAllById(bnoList);
	}
	
	// tbl_webboards 모든 게시글 삭제
	public void deleteBoardAll() {
		boardRepo.deleteAll();
	}

}
