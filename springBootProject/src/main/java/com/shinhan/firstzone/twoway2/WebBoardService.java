package com.shinhan.firstzone.twoway2;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;

@Service
public class WebBoardService {
	
	@Autowired
	WebBoardRepository boardRepo;
	
	// tbl_webboards 모두 조회
	public List<WebBoardDTO> selectBoardAll() {
		List<WebBoardEntity> boardEntityList = boardRepo.findAll();
		
		// Entity를 DTO로 변환
		List<WebBoardDTO> boardDTOList = boardEntityList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
		
		return boardDTOList;
	}
	
	// 페이징, tbl_webboards 조건 조회
	public PageResultDTO<WebBoardDTO, WebBoardEntity> selectBoardAllPaging(PageRequestDTO pageRequestDTO) {
		Predicate predicate = boardRepo.makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword());
		PageRequestDTO pageDTO = new PageRequestDTO(pageRequestDTO.getPage(), pageRequestDTO.getSize());
		Pageable pageable = pageDTO.getPageable(Sort.by("bno").descending());
		
		Page<WebBoardEntity> boardResult = boardRepo.findAll(predicate, pageable);
		
		Function<WebBoardEntity, WebBoardDTO> function = entity -> entityToDTO(entity);
		
		PageResultDTO<WebBoardDTO, WebBoardEntity> result = new PageResultDTO<>(boardResult, function);
		
		return result;
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
	
	// Entity를 DTO로 변환(Data 전송을 위함, controller, service, view에서 작업)
	// 조회 시 사용
	public WebBoardDTO entityToDTO(WebBoardEntity entity) {
		// 방법1) ModelMapper 이용
		ModelMapper mapper = new ModelMapper();
		WebBoardDTO dto = mapper.map(entity, WebBoardDTO.class); // 이름이 같은 필드들은 자동으로 매핑
		dto.setMid(entity.getWriter().getMid());
		dto.setMname(entity.getWriter().getMname());
		dto.setReplyCount(entity.getReplies().size());
		
		// 방법2) DTO의 builder 이용
//		WebBoardDTO dto = WebBoardDTO.builder()
//				.bno(entity.getBno())
//				.title(entity.getTitle())
//				.mid(entity.getWriter().getMid())
//				.mname(entity.getWriter().getMname())
//				.content(entity.getContent())
//				.regdate(entity.getRegdate())
//				.updatedate(entity.getUpdatedate())
//				.replyCount(entity.getReplies().size())
//				.build();
		
		return dto;
	}
	
	// DTO를 Entity로 변환(DB에 반영하기 위함)
	// insert, update 시 사용
	public WebBoardEntity dtoToEntity(WebBoardDTO dto){
		// 방법1) ModelMapper 이용
		ModelMapper mapper = new ModelMapper();
		WebBoardEntity entity = mapper.map(dto, WebBoardEntity.class);
		MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
		entity.setWriter(member);
		
		// 방법2) Entity의 builder 이용
//		MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
//		WebBoardEntity entity = WebBoardEntity.builder()
//				.bno(dto.getBno())
//				.title(dto.getTitle())
//				.content(dto.getContent())
//				.regdate(dto.getRegdate())					
//				.writer(member)
//				.build();
		
		return entity;
	}

}
