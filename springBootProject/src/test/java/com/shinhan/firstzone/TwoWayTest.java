package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.hibernate.annotations.BatchSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.twoway.FreeBoardEntity;
import com.shinhan.firstzone.twoway.FreeBoardRepository;
import com.shinhan.firstzone.twoway.FreeReplyEntity;
import com.shinhan.firstzone.twoway.FreeReplyRepository;
import com.shinhan.firstzone.twoway.QFreeBoardEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TwoWayTest {
	
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeReplyRepository replyRepo;
	
	// [Quiz1] tbl_freeboard에 10건 insert
//	@Test
	public void boardInsert() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			FreeBoardEntity board = FreeBoardEntity.builder()
					.title("자유게시판 글 작성" + i)
					.writer("user" + (i % 3 + 1))
					.content("JPA 이용해서 양방향 연습하기" + (10 - i))
					.build();
			
			boardRepo.save(board);
		});
	}
	
	// [Quiz2] tbl_freeboard 모두 조회
//	@Test
	public void selectAll() {
		boardRepo.findAll().forEach(board -> {
			log.info(board.getBno() + " : " + board.getTitle());
			
			board.getReplies().forEach(reply -> {
				log.info("[댓글 정보] - " + reply.toString());
			});
		});
	}
	
	// [Quiz3] 특정 board 1건 조회 후 해당 board에 reply 추가
//	@Test
	public void quiz3() {
		Long bno = 109L;
		boardRepo.findById(bno).ifPresent(board -> {
			log.info("before : " + board.toString());
			
			// 댓글(reply) 5개 추가
			List<FreeReplyEntity> replies = board.getReplies();
			IntStream.rangeClosed(1, 3).forEach(i -> {
				FreeReplyEntity reply = FreeReplyEntity.builder()
						.reply(bno + "번 게시글에 대한 댓글 : 댓글 작성" + i)
						.replier("user7")
						.board(board) // 주의! 넣어줘야함!
						.build();
				
				replies.add(reply); // insert
			});
			board.setContent(board.getContent() + "(댓글 있음)"); // update
			boardRepo.save(board);
		});
	}
	
	// [Quiz4] reply의 참조 칼럼이 비어있다. (21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L, 30L)
//	@Test
	public void quiz4() {
		Long[] arr = { 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L, 30L };
		List<FreeReplyEntity> replies = replyRepo.findAllById(Arrays.asList(arr)); // board_bno가 null인 데이터 조회
		
		FreeBoardEntity entity = boardRepo.findById(7L).orElse(null); // board_bno의 null을 7로 변경하기 위한 조회
		if (entity != null) {
			// 7번 board의 reply들
			List<FreeReplyEntity> boardReplies = entity.getReplies();
			log.info("boardReplies : " + boardReplies); // boardReplies : [](비어있음)
			
			// reply의 board가 null인 값들의 board를 수정하기
			replies.forEach(reply -> {
				reply.setBoard(entity); // 주의!!!
				boardReplies.add(reply);
			});
			
			entity.setContent(entity.getContent() + "(댓글 추가됨)");
			entity.setTitle(entity.getTitle() + "(2월 3일!!!)");
			boardRepo.save(entity);
		}
	}
	
	// [Quiz5] Board의 댓글 갯수
//	@Transactional
//	@Test
	public void replyCount() {
		boardRepo.findAll().forEach(board -> {
			log.info("제목 : " + board.getTitle());
			log.info("댓글 갯수 : " + board.getReplies().size());
			// 100개의 게시글에 대한 로그가 다 찍힘 => 댓글이 존재하는 게시글만 출력하기 위해 replyCount2() 생성
			// FreeBoardEntity에 @BatchSize(size = 100)을 선언해주지 않으면 board 개수만큼 SELECT 문장 수행
		});
	}
	
//	@Test
	public void replyCount2() {
		// JPQL 사용
		boardRepo.getBoardInfo().forEach(arr -> {
			log.info(Arrays.toString(arr));
			// [자유게시판 글 작성하기1, 5]
			// [자유게시판 글 작성하기2, 5]
			// [자유게시판 글 작성하기3, 5]
			// [자유게시판 글 작성하기4(춥다는 댓글 존재), 5]
			// 이런 식으로 댓글이 존재하는 게시글 정보만 출력
		});
		System.out.println("============================================================");
		
		// SQL 문에서 ON 구문 사용하지 않은 함수
		boardRepo.getBoardInfo2().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
		System.out.println("============================================================");
		
		// natvieQuery 사용(권장하지 않음)
		boardRepo.getBoardInfo3().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}
	
	// [Quiz6] 1번째 page 3건 조회, bno desc sort (1page는 3건임)
//	@Test
	public void quiz6() {
		int page = 1;
		String[] property = {"bno"};
		Pageable pageable = PageRequest.of(page - 1, 3, Sort.by(Direction.DESC, property));
		// JpaRepository를 상속받았기 때문에 findAll(Pageable pageable) 사용 가능
		Page<FreeBoardEntity> result = boardRepo.findAll(pageable);
		
		System.out.println("getNumber() : " + result.getNumber()); // 0
		System.out.println("getSize() : " + result.getSize()); // 3
		System.out.println("getTotalElements() : " + result.getTotalElements()); // 10
		System.out.println("getTotalPages() : " + result.getTotalPages()); // 4
		
		result.getContent().forEach(board -> {
			System.out.println("[board 정보] : " + board);
		});
	}
	
//	@Test
	public void getWriter() {
		int page = 1;
		String writer = "user1";
		String[] property = {"bno"};
		Pageable pageable = PageRequest.of(page - 1, 2, Sort.by(Direction.DESC, property));
		Page<FreeBoardEntity> result = boardRepo.findByWriter(writer, pageable);
		
		System.out.println("getNumber() : " + result.getNumber()); // 0
		System.out.println("getSize() : " + result.getSize()); // 2
		System.out.println("getTotalElements() : " + result.getTotalElements()); // 3
		System.out.println("getTotalPages() : " + result.getTotalPages()); // 2
		
		result.getContent().forEach(board -> {
			System.out.println("[board 정보] : " + board);
		});
	}
	
	// Board의 내용 지우기
//	@Test
	public void deleteBoard() {
		boardRepo.deleteAll(); // Entity에서 cascade = CascadeType.ALL 설정했기 때문에 댓글까지 함께 삭제됨
	}
	
	// board를 통한 댓글 추가가 아닌 그냥 reply에 댓글 추가
//	@Test
	public void insertReply() {
		IntStream.rangeClosed(1, 5).forEach(i -> {
			FreeReplyEntity reply = FreeReplyEntity.builder()
					.reply("신한DS" + i)
					.replier("user6")
//					.board(board) // 의도적으로 null
					.build();
			
			replyRepo.save(reply);
		});
	}
	
//	@Test
	public void insertReply2() {
		FreeBoardEntity board107 = FreeBoardEntity.builder().bno(107L).build();
		
		IntStream.rangeClosed(1, 5).forEach(i -> {
			FreeReplyEntity reply = FreeReplyEntity.builder()
					.reply("신한DS 금융 SW 아카데미" + i)
					.replier("user6")
					.board(board107) // 특정 board의 댓글임
					.build();
			
			replyRepo.save(reply);
		});
	}
	
	// 댓글 중 board_bno가 null인 데이터 조회 후 107로 변경
//	@Test
	public void selectReplyNull() {
		FreeBoardEntity board107 = FreeBoardEntity.builder().bno(107L).build();
		
		replyRepo.findByBoardIsNull().forEach(reply -> {
			log.info(reply.toString());
			
			reply.setBoard(board107); // null을 107로 update
			replyRepo.save(reply);
		});;
	}
	
	// user1이 작성한 board 정보 조회
//	@Transactional // fetch = FetchType.LAZY 설정으로 인해서 board.getReplies() 불가한 것을 가능하게끔 하기 위해 선언
//	@Test
	public void selectByWriter() {
		String writer = "user1";
		
		boardRepo.findByWriter(writer).forEach(board -> {
			log.info(board.getTitle() + "--" + board.getContent() + "--" + board.getReplies().size());
		});
	}
	
	// 댓글 조회
//	@Transactional // reply.getBoard() 가능하게 해줌
//	@Test
	public void selectReplyAll() {
		replyRepo.findAll().forEach(reply -> {
			System.out.println(reply.toString());
			System.out.println("board : " + reply.getBoard().toString());
		});
	}
	
	@Test
	public void dynamicSQL() {
		// type, keyword에 의해 동적으로 SQL문 만들기
		String type = "content";
		String keyword = "댓글 있음";
//		String type = "writer";
//		String keyword = "user1";
		
		QFreeBoardEntity board = QFreeBoardEntity.freeBoardEntity;
		BooleanBuilder builder = new BooleanBuilder();
		
		switch (type) {
		case "title":
			builder.and(board.title.like("%" + keyword + "%")); // WHERE title LIKE ?
			break;
		case "writer":
			builder.and(board.writer.like("%" + keyword + "%")); // WHERE writer LIKE ?
			break;
		case "content":
			builder.and(board.content.like("%" + keyword + "%")); // WHERE content LIKE ?
			break;
		}
		builder.and(board.bno.gt(0L)); // and bno > 0
		
		boardRepo.findAll(builder).forEach(resultBoard -> {
			log.info(resultBoard.toString());
		});;
	}

}
