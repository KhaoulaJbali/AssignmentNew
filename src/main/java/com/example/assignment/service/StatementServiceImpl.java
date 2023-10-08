package com.example.assignment.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.repository.StatementRepository;

import com.example.assignment.entity.Statement;
@Service
public class StatementServiceImpl  implements StatementService{
	
	@Autowired
	StatementRepository statementRepository;
	@Override
	public List<Statement> getStatements(	Long accountId,
	    		Date  fromDate,
	    		Date toDate,
	    		Double fromAmount,
	    		Double toAmount)
	{
		return statementRepository.getStatements(accountId, fromDate, toDate, fromAmount, toAmount);
		
	}
	




}
