package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.multikey.MultiKeyAEntity;
import com.shinhan.firstzone.multikey.MultiKeyARepository;
import com.shinhan.firstzone.multikey.MultiKeyB;
import com.shinhan.firstzone.multikey.MultiKeyBEntity;
import com.shinhan.firstzone.multikey.MultiKeyBRepository;

@SpringBootTest
public class MultikeyTest {
	
	@Autowired
	MultiKeyARepository multiKeyARepo;
	
	@Autowired
	MultiKeyBRepository multiKeyBRepo;
	
//	@Test
	public void f1() {
		MultiKeyAEntity entity = MultiKeyAEntity.builder()
				.orderId(2)
				.productId(100)
				.title("AA")
				.count(3)
				.build();
		MultiKeyAEntity entity2 = MultiKeyAEntity.builder()
				.orderId(2)
				.productId(200)
				.title("BB")
				.count(7)
				.build();
		
		multiKeyARepo.save(entity);
		multiKeyARepo.save(entity2);
	}
	
	@Test
	public void f2() {
		MultiKeyB id = MultiKeyB.builder()
				.orderId(1)
				.productId(200)
				.build();
		MultiKeyBEntity entity = MultiKeyBEntity.builder()
				.id(id)
				.title("EmbeddedId 사용함2")
				.count(20)
				.build();
		
		// save() 함수는 이미 존재하면 update, 없으면 insert 수행
		multiKeyBRepo.save(entity);
	}

}
