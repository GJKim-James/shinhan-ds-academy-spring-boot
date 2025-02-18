package com.shinhan.firstzone.hr;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpService {
	
	final DeptRepository deptRepo;
	final JobRepository jobRepo;
	final EmpRepository empRepo;
	
	// 조회
	@Transactional
	public List<EmpDTO> selectAllEmp() {
		List<EmpEntity> entityList = empRepo.findAll();
		List<EmpDTO> dtoList = entityList.stream().map(entity -> entityToDTO(entity)).toList();
		
		return dtoList;
	}
	
	// 상세보기
	@Transactional // fetch = FetchType.LAZY이기 때문에 선언해줌
	public EmpDTO selectById(Long empid) {
		EmpEntity entity = empRepo.findById(empid).orElse(null);
		EmpDTO dto = entityToDTO(entity);
		
		return dto;
	}
	
	// 입력
	public EmpDTO insertEmp(EmpDTO emp) {
		EmpEntity entity = dtoToEntity(emp);
		EmpEntity newEntity = empRepo.save(entity);
		
		return entityToDTO(newEntity);
	}
	
	// 수정
	public EmpDTO updateEmp(EmpDTO emp) {
		EmpEntity entity = dtoToEntity(emp);
		EmpEntity newEntity = empRepo.save(entity);
		
		return entityToDTO(newEntity);
	}
	
	// 삭제
	public void deleteEmp(Long empid) {
		empRepo.deleteById(empid);
	}
	
	// Entity -> DTO(DB에서 가져온 데이터를 client에 보내기; select)
	public EmpDTO entityToDTO(EmpEntity entity) {
		EmpDTO dto = null;
		
		ModelMapper mapper = new ModelMapper();
		dto = mapper.map(entity, EmpDTO.class); // Entity와 DTO의 필드명이 같은 필드는 자동 매핑
		
		dto.setJob_id(entity.getJob() == null ? null : entity.getJob().getJob_id());
		dto.setManager_id(entity.getManager() == null ? null : entity.getManager().getEmployee_id());
		if (entity.getDept() != null) {
			dto.setDepartment_id(entity.getDept().getDepartment_id());
			dto.setDepartment_name(entity.getDept().getDepartment_name());
		}
		
		return dto;
	}
	
	// DTO -> Entity(client에서 들어온 데이터를 DB에 insert, update)
	public EmpEntity dtoToEntity(EmpDTO dto) {
		ModelMapper mapper = new ModelMapper();
		EmpEntity entity = mapper.map(dto, EmpEntity.class); // Entity와 DTO의 필드명이 같은 필드는 자동 매핑
		JobEntity job = JobEntity.builder().job_id(dto.getJob_id()).build();
		EmpEntity manager = EmpEntity.builder().employee_id(dto.getManager_id()).build(); // 매니저의 직원 번호
		DeptEntity dept = DeptEntity.builder().department_id(dto.getDepartment_id()).build();
		entity.setJob(job);
		entity.setManager(manager);
		entity.setDept(dept);
		
		return entity;
	}

}
