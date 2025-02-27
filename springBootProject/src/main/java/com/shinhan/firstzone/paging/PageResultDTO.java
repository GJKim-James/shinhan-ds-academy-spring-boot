package com.shinhan.firstzone.paging;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

// PageResultDTO : 페이징한 정보를 Client에 보내기(Entity를 DTO로 변환하여 보냄)
@Data
public class PageResultDTO<DTO, EN> {
	
	// data(Entity를 DTO로 변환한 데이터)
	private List<DTO> dtoList;
	// 총 페이지 번호
	private int totalPage;
	// 현재 페이지 번호
	private int page;
	// 목록 사이즈
	private int size;
	// 시작 페이지 번호, 끝 페이지 번호
	private int start, end;
	// 이전, 다음
	private boolean prev, next;
	// 페이지 번호 목록
	private List<Integer> pageList;

	// Page<Entity> 객체들을 DTO 객체로 변환해서 자료 구조에 넣기
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) { // fn : WebBoardService의 'entity -> entityToDTO(entity)'
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		totalPage = result.getTotalPages();
		makePageList(result.getPageable());
	}

	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1;
		this.size = pageable.getPageSize();
		int tempEnd = (int) (Math.ceil(page / 10.0)) * 10; // 1~10 start:1 end:10 11~20
		
		// 한 화면에 10개의 페이지 번호를 출력하기로 가정함
		start = tempEnd - 9;
		end = totalPage > tempEnd ? tempEnd : totalPage;
		end = totalPage < 10 ? totalPage : end; // 10개 보다 작다면 page 번호까지만
		prev = start > 1; // 전 페이지기 있는가?
		next = totalPage > tempEnd;
		System.out.printf("tempEnd:%d, totalPage:%d start:%d end:%d \n", tempEnd, totalPage, start, end);
		
		// int -> Integer
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
	
}
