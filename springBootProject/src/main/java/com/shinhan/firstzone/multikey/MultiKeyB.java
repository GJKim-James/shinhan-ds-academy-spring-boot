package com.shinhan.firstzone.multikey;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Serializable(직렬화) : Network 전송 시 바이너리 형태로 데이터를 변환하여 보냄

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class MultiKeyB implements Serializable {
	
	Integer orderId; // 주문 번호
	Integer productId; // 상품 번호

}
