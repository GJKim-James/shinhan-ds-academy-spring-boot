package com.shinhan.firstzone.onetoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "tbl_user")
public class UserEntity {
	
	@Id
	@Column(name = "user_id")
	String userid;
	
	@Column(name = "user_name")
	String username;
	
	// 주 테이블에서 참조하기
	@OneToOne
	@JoinColumn(name = "phone_id") // tbl_user DB에 phone_id 칼럼 생성
	UserCellPhoneEntity phone;

}
/*
 alter table if exists tbl_user 
   add constraint FK5qub5qbviymspkaqw65kea6oe 
   foreign key (phone_id) 
   references tbl_usercellphone (phone_id)
 */