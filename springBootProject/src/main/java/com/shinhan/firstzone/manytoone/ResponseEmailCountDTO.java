package com.shinhan.firstzone.manytoone;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseEmailCountDTO {
	
	String customerName; // 고객 이름
	Long count; // 해당 고객의 이메일 개수

}
