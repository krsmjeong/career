package com.example.domain;

import lombok.Data;

@Data
public class Criteria {
	
	private int pageNum;  // ����� ��û�� ��� ������ ��ȣ
	private int amount;   // ���������� ������ ��� ����
	private int startRow; // ������ ��� ���ڵ��� ���� ���ȣ
	
	public Criteria() {
		this(1, 10);  // �⺻������ 1�������� 10���� ��� ���������� ����
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		
		// MySQL ����: ���� ���ȣ�� 0���� ������
		this.startRow = (this.pageNum - 1) * this.amount;
		
		// Oracle ����: ���� ���ȣ�� 1���� ������
//		this.startRow = (this.pageNum - 1) * this.amount + 1;
	}
	

}
