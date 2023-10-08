package com.example.assignment.controller;


import java.util.Date;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.assignment.service.StatementService;

import com.example.assignment.entity.Statement;

@ComponentScan
@RestController
public class StatementController {

	private static final Logger logger = LoggerFactory.getLogger(StatementController.class);

	@Autowired
	private StatementService statementService;

	@GetMapping("/admin/statements")
//	--<List<Statement>>
	public ResponseEntity<?> getStatements(@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam(required = false) Long accountId,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate,
			@RequestParam(required = false) Double fromAmount, @RequestParam(required = false) Double toAmount) {
		
		 if (accountId != null && accountId <= 0) {
		    	logger.error("Invalid account ID");
			    return ResponseEntity.badRequest().body("Invalid account ID");
		    }
		    
		    if (fromDate != null && toDate != null && fromDate.after(toDate)) {
		    	logger.error("Invalid date range: from date should be before to date");
		        return ResponseEntity.badRequest().body("Invalid date range: from date should be before to date");
		    }

		    if (fromAmount != null && toAmount != null && fromAmount > toAmount) {
		    	logger.error("Invalid amount range: To amount should be geater than  from amount");
		    	 return ResponseEntity.badRequest().body("Invalid amount range: To amount should be geater than  from amount");		    }


		List<Statement> statementList;
		statementList = statementService.getStatements(accountId, fromDate, toDate, fromAmount, toAmount);
		     return ResponseEntity.ok(statementList);


	}

	@GetMapping("/user/statements")
	public ResponseEntity<List<Statement>> getStatements(@AuthenticationPrincipal UserDetails userDetails,@RequestParam Map<String, String> requestParams) {
		List<Statement> statementList;
		if( requestParams!=null && !requestParams.isEmpty())  return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		statementList = statementService.getStatements(null, null, null, null, null);
		return new ResponseEntity<>(statementList, HttpStatus.OK);

	}

}
