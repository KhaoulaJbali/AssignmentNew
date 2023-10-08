package com.example.assignment.service;

import java.util.Date;
import java.util.List;


import com.example.assignment.entity.Statement;

public interface StatementService {
	
	public List<Statement> getStatements(	Long accountId,
    		Date  fromDate,
    		Date toDate,
    		Double fromAmount,
    		Double toAmount);
	
	
	

}
