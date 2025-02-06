package com.shinhan.firstzone.twoway2;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

@Service
public class WebReplyService {
	
	@Autowired
	WebReplyRepository replyRepo;
	
	// tbl_webreplies 모두 조회
	public List<WebReplyDTO> selectReplyAll() {
		List<WebReplyEntity> replyEntityList = replyRepo.findAll();
		List<WebReplyDTO> replyDTOList = replyEntityList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
		// 재명님 코드
//		List<WebReplyDTO> replyDTOList = replyEntityList.stream().map(this::entityToDTO).collect(Collectors.toList());
		
		return replyDTOList;
	}
	
	// 특정 rno의 상세보기
	public WebReplyDTO selectReplyById(Long rno) {
		WebReplyEntity entity = replyRepo.findById(rno).orElse(null);
		WebReplyDTO dto = entityToDTO(entity);
		
		return dto;
	}
	
	// 특정 Member가 작성한 reply 정보 조회
	public List<WebReplyDTO> selectReplyByMember(MemberEntity member) {
		List<WebReplyEntity> entityList = replyRepo.findByReplier(member);
		List<WebReplyDTO> dtoList = entityList.stream().map(entity -> entityToDTO(entity)).toList();
		
		return dtoList;
	}
	
	// 특정 board의 reply 정보 조회
	public List<WebReplyDTO> selectReplyByBoard(WebBoardEntity board) {
		List<WebReplyEntity> entityList = replyRepo.findByBoard(board);
		List<WebReplyDTO> dtoList = entityList.stream().map(entity -> entityToDTO(entity)).toList();
		
		return dtoList;
	}
	
	// 동적 SQL 사용하기(tbl_webreplies에서 type이 keyword를 가지는 데이터 조회)
	public List<WebReplyDTO> dynamicSQL(String type, String keyword) {
		Predicate predicate = replyRepo.makePredicate(type, keyword);
		
		List<WebReplyEntity> entityList = (List<WebReplyEntity>) replyRepo.findAll(predicate);
		List<WebReplyDTO> dtoList = entityList.stream().map(entity -> entityToDTO(entity)).toList();
		
		return dtoList;
	}
	
	// tbl_webreplies에 입력
	public WebReplyDTO insertReply(WebReplyDTO reply) {
		WebReplyEntity entity = dtoToEntity(reply);
		MemberEntity member = MemberEntity.builder().mid(reply.getMid()).build();
		entity.setReplier(member); // 작성자 추가
		
		WebReplyDTO newDTO = entityToDTO(replyRepo.save(entity));
		
		return newDTO;
	}
	
	// tbl_webreplies 수정
	public WebReplyDTO updateReply(WebReplyDTO reply) {
		WebReplyEntity entity = dtoToEntity(reply);
		MemberEntity member = MemberEntity.builder().mid(reply.getMid()).build();
		entity.setReplier(member); // 작성자 추가
		entity.setRegdate(replyRepo.findById(reply.getRno()).get().getRegdate());
		
		WebReplyDTO newDTO = entityToDTO(replyRepo.save(entity));
		
		return newDTO;
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
	
	// Entity를 DTO로 변환(Data 전송을 위함, controller, service, view에서 작업)
	// 조회 시 사용
	public WebReplyDTO entityToDTO(WebReplyEntity entity) {
		// ModelMapper 이용
		ModelMapper mapper = new ModelMapper();
		WebReplyDTO dto = mapper.map(entity, WebReplyDTO.class); // 이름이 같은 필드들은 자동으로 매핑
		dto.setMid(entity.getReplier().getMid());
		dto.setMname(entity.getReplier().getMname());
		dto.setBoardBno(entity.getBoard().getBno());
		
		return dto;
	}
	
	// DTO를 Entity로 변환(DB에 반영하기 위함)
	// insert, update 시 사용
	public WebReplyEntity dtoToEntity(WebReplyDTO dto){
		// ModelMapper 이용
		ModelMapper mapper = new ModelMapper();
		WebReplyEntity entity = mapper.map(dto, WebReplyEntity.class);
		MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
		WebBoardEntity board = WebBoardEntity.builder().bno(dto.getBoardBno()).build();
		entity.setReplier(member);
		entity.setBoard(board);
		
		return entity;
	}

}
