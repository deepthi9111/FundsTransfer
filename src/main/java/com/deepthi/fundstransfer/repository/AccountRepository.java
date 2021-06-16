package com.deepthi.fundstransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.fundstransfer.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
