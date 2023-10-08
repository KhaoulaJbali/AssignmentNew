package com.example.assignment.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.assignment.entity.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Repository
public class StatementRepositoryImpl implements StatementRepository {
	
    private static final Logger logger = LoggerFactory.getLogger(StatementRepositoryImpl.class);

    @Override
	public List<Statement> getStatements(Long accountId, Date fromDate, Date toDate, Double fromAmount,
			Double toAmount) {

     	
     	String databaseURL = null;
		try {
			databaseURL = "jdbc:ucanaccess://src/main/resources/accountsdb.accdb";

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		ArrayList<Statement> statementList = null;
		
	

		

		try (Connection connection = DriverManager.getConnection(databaseURL);
				PreparedStatement statement = connection.prepareStatement(this.generateQuery( accountId,  fromDate,  toDate,  fromAmount, toAmount))) {
			 statementList=new ArrayList<>();
			
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Long accountId1 = result.getLong("account_id");
				String accountType = result.getString("account_type");
				String accountNumber = result.getString("account_number");
				String datefield = result.getString("datefield");
				String amount = result.getString("amount");
				Statement st=new Statement(accountId1, accountType, accountNumber, datefield, amount);
				statementList.add(st);
				

			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
		
		return statementList;

	}

	private String addWhereClause(String whereClause, boolean whereClauseAdded) {
		String returnValue = "";

		if (!whereClauseAdded) {
			returnValue = " where " + whereClause;
		} else {
			returnValue = " and " + whereClause;
		}

		return returnValue;
	}
	
	private String generateDateRangeWhereString(Date fromDate, Date toDate)
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");		
		String fromDateString = sdf.format(fromDate.getTime());
		String toDateString = sdf.format(toDate.getTime());
		return " to_date(s.datefield,'dd.MM.yyyy') >= to_date('" + fromDateString
				+ "','dd-MM-yyyy')  and  to_date(s.datefield,'dd.MM.yyyy') <= " + "to_date('" + toDateString
				+ "','dd-MM-yyyy')";
	    
		
	}

	public String generateQuery(Long accountId, Date fromDate, Date toDate, Double fromAmount,
			Double toAmount) {
		StringBuilder b = new StringBuilder();
		b.append("SELECT * FROM statement s join account a on s.account_id = a.id  ");

		boolean whereClauseAdded = false;
		String whereClause = "";
		if (accountId == null && fromDate == null && toDate == null && fromAmount == null && toAmount == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			Date dateBeforeThreeMonths=calendar.getTime();
			Date date=new Date();
			
		    whereClause = this.generateDateRangeWhereString(dateBeforeThreeMonths, date);
			b.append(addWhereClause(whereClause, whereClauseAdded));

		} else {
			if (accountId != null) {
				whereClause = " s.account_id = " + accountId;
				b.append(addWhereClause(whereClause, whereClauseAdded));
				whereClauseAdded = true;
			}

			if (fromDate != null && toDate != null) { 
				whereClause = this.generateDateRangeWhereString(fromDate, toDate);
				b.append(addWhereClause(whereClause, whereClauseAdded));
				whereClauseAdded = true;
			}

			if (fromAmount != null && toAmount != null) {

				whereClause = " to_number(s.amount) >= " + fromAmount + " and to_number(s.amount) <= " + toAmount;
				b.append(addWhereClause(whereClause, whereClauseAdded));
			}
		}

		return b.toString();

	}



}
