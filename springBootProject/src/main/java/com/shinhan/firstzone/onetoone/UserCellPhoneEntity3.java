package com.shinhan.firstzone.onetoone;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
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
@Table(name = "tbl_usercellphone3")
public class UserCellPhoneEntity3 {
	
	@Id
	String aa; // 실제로 사용하지는 않음(Entity에서 @Id를 선언해주지 않으면 에러이기 때문에 형식상 선언)
	
	// 식별자(주의 키가 부의 키로 사용)
	@MapsId // UserEntity3의 user_id를 mapping(PK이면서 FK)
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id")
	UserEntity3 user3;
	
	String phoneNumber;
	String model;

}
