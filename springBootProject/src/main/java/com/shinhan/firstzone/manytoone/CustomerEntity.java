package com.shinhan.firstzone.manytoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity // table DDL 만들어짐(CREATE TABLE CustomerEntity ~~~)
@Table(name = "tbl_customer")
public class CustomerEntity {
	
	@Id // Primary Key 의미(없으면 오류)
	@Column(name = "customer_id")
	private String customerId; // @Column 생략 시 DB에 customer_id로 칼럼 생성
	private String customerName; // customer_name
	private String customerPhone; // customer_phone

}
