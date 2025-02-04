package com.shinhan.firstzone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.dto.CarVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SampleTest {

	@Test
	public void f1() {
		CarVO carVO = CarVO.builder().model("ABC모델").price(1000).color("Black").build();

		log.info(carVO.toString());
	}

	@Test
	public void f2() {
		List<CarVO> carVOList = new ArrayList<>();

		IntStream.rangeClosed(1, 5).forEach(i -> {
			CarVO carVO = CarVO.builder().model("ABC모델" + i).price(1000 * i).color("Black").build();

			carVOList.add(carVO);
		});

		log.info(carVOList.toString());
		
		assertEquals(carVOList.size(), 5);
	}

}
