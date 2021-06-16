package com.deepthi.fundstransfer.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepthi.fundstransfer.entity.Account;
import com.deepthi.fundstransfer.entity.Transactions;
import com.deepthi.fundstransfer.repository.TransactionsRepository;

@Service
public class TransactionsService 
{

	@Autowired
	TransactionsRepository transactionsRepository;
	
	
	public Transactions addTransaction(Account sender,Account receiver,Double amount) 
	{
		LocalDateTime timestamp = LocalDateTime.now();
		
		Transactions transaction=new Transactions();
		transaction.setSender(sender.getAcno());
		transaction.setReceiver(receiver.getAcno());
		transaction.setAmount(amount);
		transaction.setTime(timestamp);
		
		return transactionsRepository.save(transaction);
	}

}
