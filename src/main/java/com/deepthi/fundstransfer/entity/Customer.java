package com.deepthi.fundstransfer.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_customer")
@SequenceGenerator(name="cid_generator", initialValue=1, allocationSize=1)
public class Customer 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cid_generator")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	@Size(min=10, max = 10)
	private String phone;

	@Column
	private String city;
	
	@Column
	@Email
	private String email;
	
	@Column
	@Size(min = 6, max=15)
	private String password;
	
	@OneToOne
	private Account account;
	
	@OneToMany
	private List<Beneficiary> beneficiaries;

	public Customer() 
	{
		super();
	}

	public Customer(Long id, String name, @Size(min = 10, max = 10) String phone, String city, @Email String email,
			@Size(min = 6, max = 15) String password) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.city = city;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
