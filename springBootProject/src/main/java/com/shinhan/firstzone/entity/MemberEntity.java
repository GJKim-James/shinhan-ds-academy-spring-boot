package com.shinhan.firstzone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
@Entity
@Table(name = "tbl_members")
@EqualsAndHashCode(of = {"mid"}) // mid가 같으면 같은 것
public class MemberEntity {
	
	@Id // PK 의미
	String mid;
	String mpassword;
	String mname;
	
	@Enumerated(EnumType.STRING) // ADMIN, MANAGER, USER 문자 그대로 DB에 저장됨
	MemberRole mrole;

}
