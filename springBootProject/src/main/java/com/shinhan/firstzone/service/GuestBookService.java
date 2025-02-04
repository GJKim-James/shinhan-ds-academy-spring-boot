package com.shinhan.firstzone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.dto.GuestBookDTO;
import com.shinhan.firstzone.entity.GuestBookEntity;
import com.shinhan.firstzone.repository.GuestBookRepository;

/*
 * DB 객체 -> Java Entity
 * 사용자 <- Controller DTO <- Service DTO <- Repository Entity
 */

@Service
public class GuestBookService {
	
	@Autowired
	GuestBookRepository guestBookRepository;
	
	public List<GuestBookDTO> selectAll() {
		List<GuestBookEntity> booklist = (List<GuestBookEntity>) guestBookRepository.findAll();
		
		List<GuestBookDTO> booklist2 = new ArrayList<>();
		booklist.forEach(book -> {
			booklist2.add(entityToDTO(book));
		});
		
		return booklist2;
	}
	
	public List<GuestBookDTO> selectAll2() {
		ModelMapper mapper = new ModelMapper();
		
		List<GuestBookEntity> booklist = (List<GuestBookEntity>) guestBookRepository.findAll();
		
		return booklist.stream().map(entity -> mapper.map(entity, GuestBookDTO.class)).collect(Collectors.toList());
	}
	
	// Entity를 DTO로 변환
	public GuestBookDTO entityToDTO(GuestBookEntity entity) {
		GuestBookDTO dto = GuestBookDTO.builder()
				.gno(entity.getGno())
				.title(entity.getTitle())
				.content(entity.getContent())
				.writer(entity.getWriter())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		
		return dto;
	}
	
	// Entity를 DTO로 변환
	public GuestBookDTO entityToDTO2(GuestBookEntity entity) {
		ModelMapper mapper = new ModelMapper();
		
		GuestBookDTO dto = mapper.map(entity, GuestBookDTO.class);
		
		return dto;
	}
	
	// DTO를 Entity로 변환
	public GuestBookEntity dtoToEntity(GuestBookDTO dto) {
		GuestBookEntity entity = GuestBookEntity.builder()
				.gno(dto.getGno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(dto.getWriter())
				.build();
		entity.setRegDate(dto.getRegDate());
		entity.setModDate(dto.getModDate());
		
		return entity;
	}
	
	// DTO를 Entity로 변환
	public GuestBookEntity dtoToEntity2(GuestBookDTO dto) {
		ModelMapper mapper = new ModelMapper();
		
		GuestBookEntity entity = mapper.map(dto, GuestBookEntity.class);
		entity.setRegDate(dto.getRegDate());
		entity.setModDate(dto.getModDate());
		
		return entity;
	}

}
