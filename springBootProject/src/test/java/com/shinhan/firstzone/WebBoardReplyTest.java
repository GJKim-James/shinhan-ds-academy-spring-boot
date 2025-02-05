package com.shinhan.firstzone;

import static org.mockito.Mockito.CALLS_REAL_METHODS;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.twoway2.WebBoardEntity;
import com.shinhan.firstzone.twoway2.WebBoardRepository;
import com.shinhan.firstzone.twoway2.WebReplyEntity;
import com.shinhan.firstzone.twoway2.WebReplyRepository;

import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WebBoardReplyTest {

	@Autowired
	WebBoardRepository boardRepo;

	@Autowired
	WebReplyRepository replyRepo;

	// tbl_webboards에 100건 insert
//	@Test
	void insertBoard() {
		MemberEntity member1 = MemberEntity.builder().mid("gjkim1").build();
		MemberEntity member2 = MemberEntity.builder().mid("gjkim2").build();

		IntStream.rangeClosed(1, 100).forEach(i -> {
			WebBoardEntity board = WebBoardEntity.builder()
					.title("Web MVC 구현하기" + i)
					.content("JPA 사용" + (101 - i))
					.writer(i > 5 ? member1 : member2)
					.build();
			
			boardRepo.save(board);
		});
	}

	// tbl_webboards의 댓글 10건 insert(bno 1 -> 10개, bno 5 -> 5개)
//	@Test
	void insertReply() {
		WebBoardEntity board1 = WebBoardEntity.builder().bno(1L).build();
		WebBoardEntity board5 = WebBoardEntity.builder().bno(5L).build();
		
		MemberEntity member3 = MemberEntity.builder().mid("gjkim3").build();
		MemberEntity member4 = MemberEntity.builder().mid("gjkim4").build();
		
		IntStream.rangeClosed(1, 10).forEach(i -> {
			WebReplyEntity reply = WebReplyEntity.builder()
					.replyText("다대일은 ManyToOne" + i)
					.replier(member3)
					.board(board1)
					.build();
			
			replyRepo.save(reply);
		});
		
		IntStream.rangeClosed(1, 5).forEach(i -> {
			WebReplyEntity reply = WebReplyEntity.builder()
					.replyText("일대다는 OneToMany" + i)
					.replier(member4)
					.board(board5)
					.build();
			
			replyRepo.save(reply);
		});
	}
	
	// tbl_webboards 모두 조회
//	@Transactional // WebBoardEntity에서 fetch = FetchType.LAZY로 설정해도 replies를 select 하고자 한다.
	// N + 1 문제 발생(board 건수 만큼 reply 테이블 select 문이 생성됨)
	// 해결은 @BatchSize 추가(FROM tbl_webreplies WHERE rno IN (?, ?, ...))
//	@Test
	void selectBoardAll() {
		boardRepo.findAll().forEach(board -> {
			log.info("[번호: " + board.getBno() + ", 작성자: " + board.getWriter().getMname() +
					", 제목: " + board.getTitle() + ", 댓글 수: " + board.getReplies().size() + "]");
		});
	}
	
	// 특정 Member가 작성한 board 정보 조회
//	@Test
	void selectBoardByMember() {
		MemberEntity member = MemberEntity.builder().mid("gjkim1").build();
		
		boardRepo.findByWriter(member).forEach(board -> {
			log.info("[번호: " + board.getBno() + ", 작성자: " + board.getWriter().getMname()
					+ ", 제목: " + board.getTitle() + "]");
		});
	}
	
	// 특정 Member가 작성한 reply 정보 조회
//	@Test
	void selectReplyByMember() {
		MemberEntity member = MemberEntity.builder().mid("gjkim4").build();
		
		replyRepo.findByReplier(member).forEach(reply -> {
			log.info(reply.toString()); // @ToString(exclude = "board")이기 때문에 board 정보는 출력되지 않음
		});
	}
	
	// 특정 board의 reply 정보 조회
//	@Transactional // fetch = FetchType.LAZY이기 때문에 reply.getBoard() 호출 시 에러, 가능하게 하기 위해 @Transactional 선언
//	@Test
	void selectReplyByBoard() {
		WebBoardEntity board = WebBoardEntity.builder().bno(5L).build();
		
		replyRepo.findByBoard(board).forEach(reply -> {
			log.info("[게시글 제목: " + reply.getBoard().getTitle() + ", 게시글 작성자: " + reply.getBoard().getWriter().getMname()
					+ " / 댓글 번호: " + reply.getRno() + ", 작성자: " + reply.getReplier().getMname()
					+ ", 내용: " + reply.getReplyText() + "]");
		});
	}
	
	// 동적 SQL 사용하기(tbl_webboards에서 type이 keyword를 가지는 데이터 조회)
	@Test
	void dynamicSQLBoard() {
		String type = "title";
//		String type = "content";
		String keyword = "구현하기2";
//		String keyword = "사용7";
		
		Predicate predicate = boardRepo.makePredicate(type, keyword);
		
		boardRepo.findAll(predicate).forEach(board -> {
			log.info(board.toString());
		});
	}

}
