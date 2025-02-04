package com.shinhan.firstzone;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shinhan.firstzone.entity.BoardEntity;
import com.shinhan.firstzone.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardCRUDTest {
	
	@Autowired
	BoardRepository boardRepository; // BoardRepository 인터페이스 구현 클래스가 Injection 된다.
	
//	@Test
	public void insert() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			BoardEntity boardEntity = BoardEntity.builder()
					.title("2025년 1월 16일 목요일")
					.content("배고프다, 밥 먹자!")
					.writer("user" + (i % 10 + 1)).build();
			
			boardRepository.save(boardEntity); // INSERT INTO ~ 실행
		});
	}
	
//	@Test
	public void selectAll() {
		boardRepository.findAll().forEach(boardEntity -> {
			log.info("boardEntity : " + boardEntity.toString());
		});;
	}
	
//	@Test
	public void insert2() {
		BoardEntity boardEntity = BoardEntity.builder()
				.title("2025년 1월 17일 금요일")
				.content("안과 라식하러 가기!")
				.writer("James").build();
		
		boardRepository.save(boardEntity); // INSERT INTO ~ 실행
	}
	
//	@Test
	public void selectById() {
		BoardEntity boardEntity = boardRepository.findById(101L).orElse(null);
		
		log.info(boardEntity == null ? "not found" : boardEntity.toString());
		
		BoardEntity boardEntity2 = boardRepository.findById(190L).orElse(null);
		
		log.info(boardEntity2 == null ? "not found" : boardEntity2.toString());
		
		boardRepository.findById(190L).ifPresentOrElse(board -> {
			log.info("찾음 : " + board.toString());
		}, () -> {
			log.info("못찾음");
		});;
	}
	
//	@Test
	public void update() {
		boardRepository.findById(100L).ifPresentOrElse(board -> {
			log.info("찾음 : " + board.toString());
			board.setTitle("제목 변경");
			board.setContent("금요일은 강점 교육");
			board.setWriter("admin");
			boardRepository.save(board); // 입력과 수정은 save 함수 사용
		}, () -> {
			log.info("못참음");
		});
	}
	
//	@Test
	public void delete() {
		log.info("삭제 전 : " + boardRepository.count());
		boardRepository.deleteById(1L);
		log.info("삭제 후 : " + boardRepository.count());
	}
	
//	@Test
	public void selectByWriter() {
		boardRepository.findByWriter("user7").forEach(board -> {
			log.info(board.toString());
			board.setContent("점심 먹고 수업은 졸리다.");
			
			boardRepository.save(board);
		});
	}
	
//	@Test
	public void selectByContent() {
		boardRepository.findByContentContaining("졸리다").forEach(board -> {
			log.info(board.toString());
		});
	}
	
//	@Test
	public void findByContentLike() {
		boardRepository.findByContentLike("%라식%").forEach(board -> {
			log.info(board.toString());
		});
	}
	
//	@Test
	public void f1() {
		boardRepository.findByBoardNoGreaterThanEqualAndBoardNoLessThanEqual(10L, 15L).forEach(board -> {
			log.info(board.toString());
		});
		log.info("---------- findByBoardNoBetween ----------");
		boardRepository.findByBoardNoBetween(10L, 15L).forEach(board -> {
			log.info(board.toString());
		});
	}
	
//	@Test
	public void findByTitleStartingWith() {
		boardRepository.findByTitleStartingWith("제목").forEach(board -> {
			log.info(board.toString());
		});
	}
	
//	@Test
	public void findByTitleEndingWith() {
		boardRepository.findByTitleEndingWith("금요일").forEach(board -> {
			log.info(board.toString());
		});
	}
	
//	@Test
	public void findByTitleEndingWithAndOderBy() {
		boardRepository.findByTitleEndingWithAndBoardNoBetweenOrderByBoardNoDesc("목요일", 97L, 101L).forEach(board -> {
			log.info(board.toString());
		});
	}
	
	@Test
	public void f2() {
		// 페이지 번호는 0부터 시작
		// PageRequest.of(0, 5) -> (출력할 페이지, 페이지당 출력할 데이터 개수)
//		Pageable pageable = PageRequest.of(0, 5);
//		Pageable pageable = PageRequest.of(0, 5, Sort.by("boardNo").ascending());
		
		String[] columns = {"writer", "boardNo"};
		Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, columns);
		Page<BoardEntity> result = boardRepository.findAll(pageable);
		
		log.info("페이지당 건수 : " + result.getSize());
		log.info("몇 페이지 : " + result.getNumber());
		log.info("페이지 총 수 : " + result.getTotalPages());
		log.info("전체 건수 : " + result.getTotalElements());
		log.info("다음 페이지 정보 : " + result.nextPageable());
		
		List<BoardEntity> boardList = result.getContent();
		boardList.forEach(board -> {
			log.info(board.toString());
		});
	}

}
