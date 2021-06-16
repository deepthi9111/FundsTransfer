package com.deepthi.fundstransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.fundstransfer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByEmail(String email);

}
