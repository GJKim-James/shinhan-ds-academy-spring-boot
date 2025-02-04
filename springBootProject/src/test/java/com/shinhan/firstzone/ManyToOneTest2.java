package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.manytoone.CustomerEntity;
import com.shinhan.firstzone.manytoone.CustomerRepository;
import com.shinhan.firstzone.manytoone.EmailBookEntity;
import com.shinhan.firstzone.manytoone.EmailBookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ManyToOneTest2 {
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	EmailBookRepository emailBookRepo;
	
	// 고객 5명 insert 하기
//	@Test
	public void customerInsert() {
		IntStream.rangeClosed(1, 5).forEach(i -> {
			CustomerEntity customer = CustomerEntity.builder()
					.customerId("customer00" + i)
					.customerName("홍길동" + i)
					.customerPhone("010-1234-567" + i)
					.build();
			
			customerRepo.save(customer);
		});
	}
	
	// 고객 조회
//	@Test
	public void selectAll() {
		customerRepo.findAll().forEach(entity -> {
			log.info(entity.toString());
		});
	}
	
	// 특정 고객의 email insert
//	@Test
	public void emailInsert() {
		String customerId = "customer004";
		CustomerEntity customer = CustomerEntity.builder().customerId(customerId).build();
		
		EmailBookEntity[] arr = new EmailBookEntity[2];
		arr[0] = EmailBookEntity.builder()
				.email("customer004@naver.com")
				.password("1234")
				.mainYn(true)
				.cust(customer)
				.build();
		arr[1] = EmailBookEntity.builder()
				.email("customer004@daum.net")
				.password("1234")
				.mainYn(false)
				.cust(customer)
				.build();
		
		for (EmailBookEntity entity : arr) {
			emailBookRepo.save(entity);
		}
	}
	
	// tbl_emailbook data 조회
//	@Test
	void emailSelectAll() {
		emailBookRepo.findAll().forEach(entity -> {
			log.info(entity.toString());
			log.info(entity.getEmail() + " ==> " + entity.getCust().getCustomerName());
		});
	}
	
	// [Quiz1] book_id가 1인 이메일 정보 조회
//	@Test
	void quiz1() {
		Long book_id = 10L;
		emailBookRepo.findById(book_id).ifPresentOrElse(entity -> {
			log.info(entity.toString());
		}, () -> {
			log.info("book_id가 " + book_id + "인 이메일 정보 Not Found.");
		});
	}
	
	// [Quiz2] customerId = "customer004"인 이메일 정보 조회
//	@Test
	void quiz2() {
		String customerId = "customer004";
		CustomerEntity customerEntity = CustomerEntity.builder().customerId(customerId).build();
		
		emailBookRepo.findByCust(customerEntity).forEach(entity -> {
			log.info(entity.toString());
		});
	}
	
	// [Quiz3] 고객별 이메일의 개수 조회(customer001 - 0개, customer002 - 3개 => ["홍길동1", 0], ["홍길동2", 3] 이와 같이 결과 출력)
	@Test
	void quiz3() {
		log.info("---------- JPQL(nativeQuery가 아닌 경우) ----------");
		emailBookRepo.findByCustEmailCount().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
		log.info("---------- JPQL(nativeQuery인 경우) ----------");
		emailBookRepo.findByCustEmailCount2().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}

}
