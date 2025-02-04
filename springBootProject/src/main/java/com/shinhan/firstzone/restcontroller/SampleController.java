package com.shinhan.firstzone.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.SpringComponent1;
import com.shinhan.firstzone.dto.CarVO;
import com.shinhan.firstzone.entity.BoardEntity;
import com.shinhan.firstzone.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Controller 수행 후 page가 forward
@RestController // @Controller + @ResponseBody
public class SampleController {
	
	@GetMapping("/hello1")
	public String f1() {
		return "Hello, World!";
	}
	
	@GetMapping("/hello2")
	public CarVO f2() {
		CarVO carVO = CarVO.builder()
				.model("ABC모델")
				.price(1000)
				.color("Black").build();
		
		return carVO;
	}
	
	@GetMapping("/hello3")
	public List<CarVO> f3() {
		List<CarVO> carVOList = new ArrayList<>();
		
		IntStream.rangeClosed(1, 5).forEach(i -> {
			CarVO carVO = CarVO.builder()
					.model("ABC모델" + i)
					.price(1000 * i)
					.color("Black").build();
			
			carVOList.add(carVO);
		});
		
		return carVOList;
	}
	
	@Autowired
	SpringComponent1 springComponent1;
	
	@GetMapping("/hello4")
	public String f4() {
		return springComponent1.getData();
	}
	
}
