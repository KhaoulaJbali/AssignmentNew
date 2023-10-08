package com.example.assignment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public interface StatementRepository {
	
	public List<com.example.assignment.entity.Statement> getStatements(Long accountId, Date fromDate, Date toDate, Double fromAmount,
			Double toAmount) ;

}
