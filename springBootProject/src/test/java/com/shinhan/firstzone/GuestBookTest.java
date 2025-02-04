package com.shinhan.firstzone;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.firstzone.entity.GuestBookEntity;
import com.shinhan.firstzone.entity.QGuestBookEntity;
import com.shinhan.firstzone.repository.GuestBookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class GuestBookTest {
	
	@Autowired
	GuestBookRepository guestBookRepository;
	
	@Autowired
	JPAQueryFactory queryFactory;
	
//	@Test
	public void insert() {
		IntStream.rangeClosed(1, 20).forEach(i -> {
			GuestBookEntity entity = GuestBookEntity.builder()
					.title("금요일" + i)
					.content("내일은 토요일" + (21 - i))
					.writer("작성자" + (i % 3 + 1))
					.build();
			
			guestBookRepository.save(entity);
		});
	}
	
//	@Test
	public void selectAll() {
		guestBookRepository.findAll().forEach(book -> {
			log.info(book.toString());
		});
	}
	
//	@Test
	public void selectById() {
		Long gno = 21L;
		GuestBookEntity entity = guestBookRepository.findById(gno).orElse(null);
		log.info(entity == null ? "not found" : entity.toString());
		
		guestBookRepository.findById(gno).ifPresent(book -> {
			book.setTitle(book.getTitle() + " 수정");
			book.setContent("일요일도 좋아~~~");
			book.setWriter("manager");
			GuestBookEntity updatedEntity = guestBookRepository.save(book);
			log.info("수정된 book : " + updatedEntity.toString());
		});
	}
	
//	@Test
	public void delete() {
		Long gno = 22L;
		log.info("before count : " + guestBookRepository.count() + "건");
		guestBookRepository.deleteById(gno);
		log.info("after count : " + guestBookRepository.count() + "건");
	}
	
//	@Test
	public void select() {
		String writer = "작성자1";
		guestBookRepository.findByWriter(writer).forEach(book -> {
			log.info(book.toString());
		});
		log.info("--------------------------------------------------");
		String content = "토요일1";
		guestBookRepository.findByContentContaining(content).forEach(book -> {
			log.info(book.toString());
		});
		log.info("--------------------------------------------------");
		Long gno = 35L;
		guestBookRepository.findByGnoGreaterThan(gno).forEach(book -> {
			log.info(book.toString());
		});
	}
	
//	@Test
	public void select2() {
		String writer = "작성자1";
		String content = "토요일1";
		guestBookRepository.findByWriterContent2(writer, content).forEach(book -> {
			log.info("[findByWriterContent2] : " + book.toString());
		});
		
		guestBookRepository.findByWriterContent3(writer, content).forEach(book -> {
			log.info("[findByWriterContent3] : " + book.toString());
		});
		
		guestBookRepository.findByWriterContent4(writer, content).forEach(book -> {
			log.info("[findByWriterContent4] : " + book.toString());
		});
		
		guestBookRepository.findByWriterContent5(writer, content).forEach(arr -> {
			log.info("[findByWriterContent5] : " + Arrays.toString(arr));
		});
		
		guestBookRepository.findByWriterContent6(writer, content).forEach(book -> {
			log.info("[findByWriterContent6] : " + book.toString());
		});
	}
	
	// 동적 SQL문 생성
	@Test
	public void dynamicSQL1() {
		String writer = "작성자1";
		String content = "%토요일1%";
		String type = "writer"; // where writer = ?
//		String type = "content"; // where content like ?
		LocalDateTime datetime = LocalDateTime.of(2025, 1, 31, 0, 0, 0);
		
//		BooleanBuilder builder = new BooleanBuilder();
		QGuestBookEntity book = QGuestBookEntity.guestBookEntity;
		List<GuestBookEntity> booklist = null;
		
		switch(type) {
		case "writer":
//			builder.and(book.writer.eq(writer));
			booklist = queryFactory.selectFrom(book).where(book.writer.eq(writer)).fetch();
			break;
		case "content":
//			builder.and(book.content.like(content));
			booklist = queryFactory.selectFrom(book).where(book.content.like(content)).fetch();
			break;
		}
		booklist.forEach(b -> log.info(b.toString()));
		
		
//		builder.and(book.gno.gt(30L)); // and gno > 30
//		builder.and(book.regDate.goe(datetime)); // and regDate >= 2025-01-31T00:00
//		log.info("builder : " + builder);
		
//		guestBookRepository.findAll(builder).forEach(b -> log.info(b.toString()));
	}

}
