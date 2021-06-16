package com.deepthi.fundstransfer.exception;

public class InSufficientBalanceException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InSufficientBalanceException(String message) 
	{
		super(message);
	}
	
}
