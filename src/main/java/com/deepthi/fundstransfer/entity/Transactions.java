package com.deepthi.fundstransfer.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cust_txn")
@SequenceGenerator(name="tid_generator", initialValue=101, allocationSize=1)
public class Transactions 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tid_generator")
	private Long tid;
	
	@Column
	private Long sender;
	
	@Column
	private Long receiver;
	
	@Column
	private Double amount;
	
	@Column
	private LocalDateTime time;
	

	public Transactions() 
	{
		super();
	}

	public Transactions(Long tid, Long sender, Long receiver, Double amount, LocalDateTime time) {
		super();
		this.tid = tid;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.time = time;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "Transactions [tid=" + tid + ", sender=" + sender + ", receiver=" + receiver + ", amount=" + amount
				+ ", time=" + time + "]";
	}
	
}
