package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.shinhan.firstzone.onetomany.PDSBoardEntity;
import com.shinhan.firstzone.onetomany.PDSBoardRepository;
import com.shinhan.firstzone.onetomany.PDSFileEntity;
import com.shinhan.firstzone.onetomany.PDSFileRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OneToManyTest {
	
	@Autowired
	PDSBoardRepository boardRepo;
	
	@Autowired
	PDSFileRepository fileRepo;
	
	// tbl_pdsboard에 10건 insert, 각각의 board에 첨부파일이 3건
//	@Test
	void boardInsert() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			List<PDSFileEntity> fileList = new ArrayList<>();
			IntStream.rangeClosed(1, 3).forEach(j -> {
				PDSFileEntity file = PDSFileEntity.builder()
						.filename("첨부파일-" + i + "-" + j + ".ppt")
						.build();
				
				fileList.add(file);
			});
			
			PDSBoardEntity board = PDSBoardEntity.builder()
					.pname("board" + i)
					.pwriter("user" + (i % 5 + 1))
					.files(fileList)
					.build();
			
			boardRepo.save(board); // 부모 저장 시 자식도 영향을 받음(cascade)
		});
	}
	
	// tbl_pdsfile에 insert
//	@Test
	void fileInsert() {
		IntStream.rangeClosed(1, 5).forEach(i -> {
			PDSFileEntity entity = PDSFileEntity.builder().filename("신한DS 2차 프로젝트 백업-" + i + ".zip").build();
			fileRepo.save(entity);
		});
	}
	
	// board를 통해서 file 수정
	// 1. board 1번의 첨부파일들 지우기(참조 안하기)
//	@Test
	void fileReferenceNo() {
		PDSBoardEntity entity3 = boardRepo.findById(1L).get();
		log.info("entity3 : " + entity3); // entity3 : PDSBoardEntity(pid=1, pname=board1, pwriter=user2)
		
		List<PDSFileEntity> files3 = entity3.getFiles();
		files3.clear();
		boardRepo.save(entity3);
	}
	
	// 2. board 4번의 첨부파일을 5번으로 바꾸기
//	@Test
	void change() {
		PDSBoardEntity entity4 = boardRepo.findById(4L).get();
		PDSBoardEntity entity5 = boardRepo.findById(5L).get();
		
		List<PDSFileEntity> files4 = entity4.getFiles();
		List<PDSFileEntity> files5 = entity5.getFiles();
		
		files4.forEach(file -> {
			files5.add(file);
		});
		log.info("files4 : " + files4); // files4 : [PDSFileEntity(fno=10, filename=첨부파일-4-1.ppt), PDSFileEntity(fno=11, filename=첨부파일-4-2.ppt), PDSFileEntity(fno=12, filename=첨부파일-4-3.ppt)]
		log.info("==============================");
		log.info("files5 : " + files5); // files5 : [PDSFileEntity(fno=13, filename=첨부파일-5-1.ppt), PDSFileEntity(fno=14, filename=첨부파일-5-2.ppt), PDSFileEntity(fno=15, filename=첨부파일-5-3.ppt), PDSFileEntity(fno=10, filename=첨부파일-4-1.ppt), PDSFileEntity(fno=11, filename=첨부파일-4-2.ppt), PDSFileEntity(fno=12, filename=첨부파일-4-3.ppt)]
		
		boardRepo.save(entity5);
	}
	
	// 3. board 6번에 첨부파일 2개 추가하기
//	@Test
	void add() {
		PDSBoardEntity entity6 = boardRepo.findById(6L).get();
		
		List<PDSFileEntity> files6 = entity6.getFiles();
		files6.add(new PDSFileEntity(null, "2차 프로젝트.ppt")); // insert, update
		files6.add(new PDSFileEntity(null, "2차 프로젝트.pdf")); // insert, update
		
		boardRepo.save(entity6);
	}
	
	// tbl_pdsboard 조회
//	@Transactional // FetchType.LAZY임에도 getFiles() 사용 가능
//	@Test
	void selectBoard() {
		boardRepo.findAll().forEach(board -> {
			log.info("pid : " + board.getPid());
			log.info("pname : " + board.getPname());
			log.info("pwriter : " + board.getPwriter());
			log.info("board : " + board); // toString() 자동 호출됨 => PDSBoardEntity에서 @ToString(exclude = {"files"})으로 수정
			log.info("files : " + board.getFiles());
		});
	}
	
	// pdsno IS NULL인 data 삭제하기
//	@Commit // @Test인 경우 작업 후 Rollback 된다. Commit 하기를 원하면 @Commit 추가하기!
//	@Transactional
//	@Test
	void fileDelete() {
		fileRepo.fileDelete();
	}
	
	// fno 38번~42번까지 select하기
	@Test
	void fileSelect() {
		Long[] arr = { 38L, 39L, 40L, 41L, 42L };
		List<PDSFileEntity> fileList = fileRepo.findAllById(Arrays.asList(arr)); // pdsno = null인 데이터
		log.info("fileList : " + fileList);
		
		PDSBoardEntity entity6 = boardRepo.findById(6L).get();
		List<PDSFileEntity> files6 = entity6.getFiles(); // pdsno가 6인 첨부파일들 get
		
		fileList.forEach(file -> {
			files6.add(file); // pdsno를 null에서 6으로 변경하기 위해 files6에 fileList의 내용 하나하나 추가
		});
		
		boardRepo.save(entity6); // 하나하나 추가하여 변경된 entity6을 다시 저장
	}
	

}
