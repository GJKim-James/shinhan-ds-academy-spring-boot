package com.shinhan.firstzone.onetoone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_user3")
public class UserEntity3 {
	
	@Id
	@Column(name = "user_id")
	String userid;
	
	@Column(name = "user_name")
	String username;
	
	// 주 테이블에서 참조하기(양방향), 식별자로 사용하기 위해(주의 키가 부의 키로 사용)
	@OneToOne(mappedBy = "user3", cascade = CascadeType.ALL)
	UserCellPhoneEntity3 phone;

}