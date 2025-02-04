package com.shinhan.firstzone.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.entity.BoardEntity;
import com.shinhan.firstzone.repository.BoardRepository;

import lombok.Delegate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardRepository boardRepository;
	
	// 특정 boardNo 조회
	@GetMapping("/{boardNo}")
	public BoardEntity selectById(@PathVariable("boardNo") Long boardNo) {
		return boardRepository.findById(boardNo).orElse(null);
	}
	
	// 모두 조회
	@GetMapping("/all")
	public List<BoardEntity> selectAll() {
		return (List<BoardEntity>) boardRepository.findAll();
	}
	
	// 등록
	@PostMapping("/insert")
	public BoardEntity insert(@RequestBody BoardEntity boardEntity) {
		BoardEntity result = boardRepository.save(boardEntity);
		
		return result;
	}
	
	// 수정
	@PutMapping("/update")
	public String update(@RequestBody BoardEntity boardEntity) {
		boardRepository.findById(boardEntity.getBoardNo()).ifPresent(foundBoard -> {
			foundBoard.setContent(boardEntity.getContent());
			foundBoard.setTitle(boardEntity.getTitle());
			foundBoard.setWriter(boardEntity.getWriter());
			
			boardRepository.save(foundBoard);
		});
		
		return "수정 성공!";
	}
	
	// 삭제
	@DeleteMapping("/delete/{boardNo}")
	public String delete(@PathVariable("boardNo") Long boardNo) {
		boardRepository.deleteById(boardNo);
		
		return boardNo + "번 삭제 성공!";
	}
	
	// 페이징
	@GetMapping("/page/{page_number}")
	public List<BoardEntity> f5(@PathVariable("page_number") int page) {
		// 페이지 번호는 0부터 시작
		// PageRequest.of(0, 5) -> (출력할 페이지, 페이지당 출력할 데이터 개수)
//		Pageable pageable = PageRequest.of(0, 5);
//		Pageable pageable = PageRequest.of(0, 5, Sort.by("boardNo").ascending());
		
		String[] columns = {"writer", "boardNo"};
		Pageable pageable = PageRequest.of(page, 7, Sort.Direction.DESC, columns);
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
		
		return boardList;
	}

}
