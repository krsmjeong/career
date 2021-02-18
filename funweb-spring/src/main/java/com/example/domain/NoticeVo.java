package com.example.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class NoticeVo {
	
	private int num;
	private String id;
	private String subject;
	private String content;
	private int readcount;
	private Timestamp regDate;
	private String ip;
	private int reRef;
	private int reLev;
	private int reSeq;
	
	//private AttachVo attachVo;        // JOIN 쿼리 1:1 관계
	private List<AttachVo> attachList;  // JOIN 쿼리 1:N 관계
}
