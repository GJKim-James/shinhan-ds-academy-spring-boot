package com.shinhan.firstzone.manytoone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailBookService {
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	EmailBookRepository emailBookRepo;
	
	// [Quiz1] 특정 book_id의 이메일 정보 조회
	public EmailBookEntity quiz1(Long book_id) {
		return emailBookRepo.findById(book_id).orElse(null);
	}
	
	// [Quiz2] customerId = "customer004"인 이메일 정보 조회
	public List<EmailBookEntity> quiz2(String customerId) {
		CustomerEntity customerEntity = CustomerEntity.builder().customerId(customerId).build();
		
		return emailBookRepo.findByCust(customerEntity);
	}
	
	// [Quiz3] 고객별 이메일의 개수 조회; ["홍길동1", 0], ["홍길동2", 3] 등 이와 같이 결과 출력
	public List<ResponseEmailCountDTO> quiz3() {
		List<ResponseEmailCountDTO> datas = new ArrayList<>();
		
		emailBookRepo.findByCustEmailCount().forEach(arr -> {
			ResponseEmailCountDTO dto = ResponseEmailCountDTO.builder()
					.customerName((String) arr[0])
					.count((Long) arr[1])
					.build();
			
			datas.add(dto);
		});
		
		return datas;
	}

}
