package com.deepthi.fundstransfer.entity;

public class Transfer 
{
	private Long facno;
	private Long tacno;
	private Double amount;
	
	public Transfer() 
	{
		super();
	}

	public Transfer(Long facno, Double amount, Long tacno) {
		super();
		this.facno = facno;
		this.tacno=tacno;
		this.amount = amount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
	public Long getFacno() {
		return facno;
	}

	public void setFacno(Long facno) {
		this.facno = facno;
	}

	public Long getTacno() {
		return tacno;
	}

	public void setTacno(Long tacno) {
		this.tacno = tacno;
	}

	@Override
	public String toString() {
		return "Transfer [acno=" + facno + ", amount=" + amount + "]";
	}
	
}
