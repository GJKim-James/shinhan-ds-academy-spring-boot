package com.shinhan.firstzone.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "t_board")
public class BoardEntity {
	
	@Id // PK(Primary Key) 필수
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle : SEQUENCE, MySQL : IDENTITY
	private Long boardNo;
	
	@Nonnull // 자바에서 필수 field임
	@Column(nullable = false)// DB의 컬럼이 not null 
	private String title;
	
	@Column(length = 100)
	private String content;
	private String writer;
	
	@CreationTimestamp // insert 시 시각이 입력
	private Timestamp regDate; // reg_date로 DB에 생성
	
	@UpdateTimestamp // insert, update 시에 자동으로 시각이 입력
	private Timestamp updateDate; // update_date로 DB에 생성
	
}
