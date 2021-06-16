package com.deepthi.fundstransfer.entity;

import javax.validation.constraints.NotNull;

public class CustomerLogin 
{
	@NotNull
	private String email=new StringBuilder().toString();
	
	@NotNull
	private String password=new StringBuilder().toString();

	public CustomerLogin() 
	{
		super();
	}
	

	public CustomerLogin(@NotNull String email, @NotNull String password) {
		super();
		this.email = email;
		this.password = password;
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

	@Override
	public String toString() {
		return "Login [email=" + email + ", password=" + password + "]";
	}

}
