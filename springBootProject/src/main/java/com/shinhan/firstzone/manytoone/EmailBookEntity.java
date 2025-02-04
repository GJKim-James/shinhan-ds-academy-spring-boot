package com.shinhan.firstzone.manytoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_emailbook")
public class EmailBookEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 추가
	private Long bookId;
	
	@Column(length = 100, nullable = false) // 길이 100, null 허용 안함
	private String email;
	
	private String password;
	private boolean mainYn; // main_yn
	
	@ManyToOne // Email 여러 개, Customer 1개
	CustomerEntity cust; // cust_customer_id(cust_ + CustomerEntity의 PK인 customer_id)

}
