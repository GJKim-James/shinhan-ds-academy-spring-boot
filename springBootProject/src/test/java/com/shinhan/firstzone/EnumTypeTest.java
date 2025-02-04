package com.shinhan.firstzone;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.entity.MemberRole;
import com.shinhan.firstzone.multikey.EnumTypeEntity;
import com.shinhan.firstzone.multikey.EnumTypeRepository;

@SpringBootTest
public class EnumTypeTest {
	
	@Autowired
	EnumTypeRepository enumTypeRepo;
	
//	@Test
	public void insert() {
		Set<MemberRole> roleSet = new HashSet<>();
		roleSet.add(MemberRole.ADMIN);
		roleSet.add(MemberRole.MANAGER);
		roleSet.add(MemberRole.USER);
		
		EnumTypeEntity entity = EnumTypeEntity.builder()
				.mid("shinhan")
				.mpassword("1234")
				.mname("신한DS")
				.mrole(roleSet)
				.build();
		
		enumTypeRepo.save(entity);
	}
	
	@Test
	public void select() {
		enumTypeRepo.findAll().forEach(member -> {
			System.out.println(member.toString());
			// EnumTypeEntity(mid=firstzone, mpassword=1234, mname=퍼스트존, mrole=[USER, ADMIN])
			// EnumTypeEntity(mid=shinhan, mpassword=1234, mname=신한DS, mrole=[MANAGER, ADMIN, USER])
		});
	}

}
