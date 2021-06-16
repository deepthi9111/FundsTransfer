package com.deepthi.fundstransfer.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cust_account")
@SequenceGenerator(name="acno_generator", initialValue=1673190500, allocationSize=1)
public class Account 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="acno_generator")
	private Long acno;
	
	@Column
	private String ifsc;
	
	@Column
	private String branch;
	
	@Column
	private Double balance;
	
	@Column
	private String bank;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Beneficiary> beneficiaryList;

	public Account() 
	{
		super();
	}

	public Account(Long acno, String ifsc, String branch, Double balance, String bank) {
		super();
		this.acno = acno;
		this.ifsc = ifsc;
		this.branch = branch;
		this.balance = balance;
		this.bank = bank;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
}
