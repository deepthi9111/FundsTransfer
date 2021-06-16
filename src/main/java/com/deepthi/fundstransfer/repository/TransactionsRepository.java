package com.deepthi.fundstransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.fundstransfer.entity.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

}
