package com.deepthi.fundstransfer.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest req)
	{
		List<String> details=new ArrayList<>();
		
		details.add(ex.getLocalizedMessage());
		ErrorResponse error=new ErrorResponse(500L,details);
		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DuplicateEntryException.class)
	public final ResponseEntity<Object> handleDuplicateEntryException(DuplicateEntryException ex, WebRequest req)
	{
		List<String> details=new ArrayList<>();
		
		details.add(ex.getLocalizedMessage());
		ErrorResponse error=new ErrorResponse(400L,details);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BeneficiaryNotFoundException.class)
	public final ResponseEntity<Object> handleBeneficiaryNotFoundException(BeneficiaryNotFoundException ex, WebRequest req)
	{
		List<String> details=new ArrayList<>();
		
		details.add(ex.getLocalizedMessage());
		ErrorResponse error=new ErrorResponse(404L,details);
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BeneficiaryExistsException.class)
	public final ResponseEntity<Object> handleBeneficiaryExistsException(BeneficiaryExistsException ex, WebRequest req)
	{
		List<String> details=new ArrayList<>();
		
		details.add(ex.getLocalizedMessage());
		ErrorResponse error=new ErrorResponse(400L,details);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public final ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest req)
	{
		List<String> details=new ArrayList<>();
		
		details.add(ex.getLocalizedMessage());
		
		ErrorResponse error=new ErrorResponse(404L,details);
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InSufficientBalanceException.class)
	public final ResponseEntity<Object> handleNoSufficientBalanceException(InSufficientBalanceException ex, WebRequest req)
	{
		List<String> details=new ArrayList<>();
		
		details.add(ex.getLocalizedMessage());
		ErrorResponse error=new ErrorResponse(400L,details);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest req)
	{
		List<String> details=new ArrayList<>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors())
		{
			details.add(error.getDefaultMessage());
		}
		
		ErrorResponse error=new ErrorResponse(410L,details);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
