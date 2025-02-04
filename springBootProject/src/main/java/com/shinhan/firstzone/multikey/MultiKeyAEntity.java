package com.shinhan.firstzone.multikey;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(MultiKeyA.class)
@Entity
@Table(name = "tbl_child1")
public class MultiKeyAEntity {
	
	@Id
	Integer orderId; // 주문 번호
	
	@Id
	Integer productId; // 상품 번호
	
	String title;
	int count;

}
/*
create table tbl_child1 (
    order_id integer not null,
    product_id integer not null,
    count integer not null,
    title varchar(255),
    primary key (order_id, product_id)
) engine=InnoDB
*/
