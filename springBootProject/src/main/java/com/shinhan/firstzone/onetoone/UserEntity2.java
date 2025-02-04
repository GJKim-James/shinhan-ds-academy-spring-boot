package com.shinhan.firstzone.onetoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "tbl_user2")
public class UserEntity2 {
	
	@Id
	@Column(name = "user_id")
	String userid;
	
	@Column(name = "user_name")
	String username;

}
