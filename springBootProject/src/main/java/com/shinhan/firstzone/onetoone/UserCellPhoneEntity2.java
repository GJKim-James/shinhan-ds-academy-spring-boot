package com.shinhan.firstzone.onetoone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "tbl_usercellphone2")
public class UserCellPhoneEntity2 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	@Column(name = "phone_id")
	Long phoneId;
	
	String phoneNumber;
	String model;
	
	// 부 테이블에서 비식별자로 참조하기
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	UserEntity2 user;

}
