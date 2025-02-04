package com.shinhan.firstzone.multikey;

import java.util.Set;

import com.shinhan.firstzone.entity.MemberRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_member2")
public class EnumTypeEntity {
	
	@Id
	private String mid;
	private String mpassword;
	private String mname;
	
	@ElementCollection(targetClass = MemberRole.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "tbl_member_roles", joinColumns = @JoinColumn(name = "mid"))
	private Set<MemberRole> mrole;

}
/*
 	create table tbl_member2 (
        mid varchar(255) not null,
        mname varchar(255),
        mpassword varchar(255),
        primary key (mid)
    ) engine=InnoDB
Hibernate: 
    create table tbl_member_roles (
        mid varchar(255) not null,
        mrole tinyint check (mrole between 0 and 2)
    ) engine=InnoDB
Hibernate: 
    alter table if exists tbl_member_roles 
       add constraint FKrfa97bsotb1deggicc2g1dqri 
       foreign key (mid) 
       references tbl_member2 (mid)
 */