package com.deepthi.fundstransfer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cust_benf")
@SequenceGenerator(name="bid_generator", initialValue=101, allocationSize=1)
public class Beneficiary 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="bid_generator")
	private Long bid;
	
	@ManyToOne
	private Account account;
	
	@Column
	private Long acno;
	
	@Column
	private String ifsc;
	
	@Column
	private String branch;
	
	@Column
	private String bank;
	
	@Column
	private String name;

	public Beneficiary() 
	{
		super();
	}
	

	public Beneficiary(Long bid, Account account, Long acno, String ifsc, String branch, String bank, String name) {
		super();
		this.bid = bid;
		this.account = account;
		this.acno = acno;
		this.ifsc = ifsc;
		this.branch = branch;
		this.bank = bank;
		this.name = name;
	}

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	

	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	public Long getAcno() {
		return acno;
	}

	public void setAcno(Long acno) {
		this.acno = acno;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}


	@Override
	public String toString() {
		return "Beneficiary [bid=" + bid + ", account=" + account + ", acno=" + acno + ", ifsc=" + ifsc + ", branch="
				+ branch + ", bank=" + bank + ", name=" + name + "]";
	}

	
}
