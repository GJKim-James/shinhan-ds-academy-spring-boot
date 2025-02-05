package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

@Service
public class WebReplyService {
	
	@Autowired
	WebReplyRepository replyRepo;
	
	// tbl_webreplies 모두 조회
	public List<WebReplyEntity> selectReplyAll() {
		return replyRepo.findAll();
	}
	
	// 특정 rno의 상세보기
	public WebReplyEntity selectReplyById(Long rno) {
		return replyRepo.findById(rno).orElse(null);
	}
	
	// 특정 Member가 작성한 reply 정보 조회
	public List<WebReplyEntity> selectReplyByMember(MemberEntity member) {
		return replyRepo.findByReplier(member);
	}
	
	// 특정 board의 reply 정보 조회
	public List<WebReplyEntity> selectReplyByBoard(WebBoardEntity board) {
		return replyRepo.findByBoard(board);
	}
	
	// 동적 SQL 사용하기(tbl_webreplies에서 type이 keyword를 가지는 데이터 조회)
	public List<WebReplyEntity> dynamicSQL(String type, String keyword) {
		Predicate predicate = replyRepo.makePredicate(type, keyword);
		
		return (List<WebReplyEntity>) replyRepo.findAll(predicate);
	}
	
	// tbl_webreplies에 입력
	public WebReplyEntity insertReply(WebReplyEntity reply) {
		return replyRepo.save(reply);
	}
	
	// tbl_webreplies 수정
	public WebReplyEntity updateReply(WebReplyEntity reply) {
		return replyRepo.save(reply);
	}
	
	// tbl_webreplies 특정 게시글 삭제
	public void deleteReplyByRno(Long rno) {
		replyRepo.deleteById(rno);
	}
	
	// tbl_webreplies 여러 게시글 삭제
	public void deleteReplies(List<Long> rnoList) {
		replyRepo.deleteAllById(rnoList);
	}
	
	// tbl_webreplies 모든 게시글 삭제
	public void deleteReplyAll() {
		replyRepo.deleteAll();
	}

}
