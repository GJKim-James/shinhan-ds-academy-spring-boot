package com.shinhan.firstzone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// VO(Value Object) : 여러 개의 값들의 저장 묶음

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CarVO {
	
	String model;
	int price;
	String color;

}
