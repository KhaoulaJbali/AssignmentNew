package com.example.assignment.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.assignment.repository.StatementRepositoryImpl;

public class Statement {
    private static final Logger logger = LoggerFactory.getLogger(Statement.class);
	
	private Long accountId ;
	private String accountType ;
	private String accountNumber;
	private String datefield ;
	private String amount ;
	
	
	public Statement(Long accountId, String accountType, String accountNumber, String datefield, String amount)  {
		super();
		this.accountId = accountId;
		this.accountType = accountType;
		this.datefield = datefield;
		this.amount = amount;
		this.accountNumber = hashAccountNumber(accountNumber);
		
	}
	
	
	  public Long getAccountId() {
		return accountId;
	}


	public String getAccountType() {
		return accountType;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public String getDatefield() {
		return datefield;
	}


	public String getAmount() {
		return amount;
	}


	public static String hashAccountNumber(String accountNumber) {
	        MessageDigest digest = null;
				try {
					digest = MessageDigest.getInstance("SHA-256");
				
				byte[] hash = digest.digest(accountNumber.getBytes());
			
	        // Convert the byte array to a hexadecimal string
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }
            return hexString.toString();
				} catch (NoSuchAlgorithmException e) {
					logger.error(e.getMessage());
				}
			return accountNumber;
	        
	    }


}
