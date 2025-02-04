package com.shinhan.firstzone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"fno"})
@Entity
@Table(name = "tbl_profile")
public class ProfileEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	Long fno;
	String fname;
	boolean currentYn; // DB의 칼럼 이름은 current_yn
	
	@ManyToOne
	MemberEntity member; // DB의 칼럼 이름은 member_mid(profile 여러 건을 갖는 하나의 멤버가 있다.)

}
