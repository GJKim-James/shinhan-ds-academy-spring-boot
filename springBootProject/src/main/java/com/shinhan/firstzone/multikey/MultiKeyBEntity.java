package com.shinhan.firstzone.multikey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_child2")
public class MultiKeyBEntity {
	
	@EmbeddedId
	MultiKeyB id;
	
	String title;
	int count;

}
/*
create table tbl_child2 (
    order_id integer not null,
    product_id integer not null,
    count integer not null,
    title varchar(255),
    primary key (order_id, product_id)
) engine=InnoDB
*/
