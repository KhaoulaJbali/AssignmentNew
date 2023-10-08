package com.example.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.assignment.entity.Statement;
import com.example.assignment.service.StatementService;


 class StatementControllerTest {

    @InjectMocks
    private StatementController statementController;

    @Mock
    private StatementService statementService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
     void testGetStatementsForAdmin() {
        // Arrange
        UserDetails userDetails = new User("admin", "", Arrays.asList(() -> "ROLE_ADMIN"));
        Long accountId = 1L;
        Date fromDate = new Date();
        Date toDate = new Date();
        Double fromAmount = 100.0;
        Double toAmount = 500.0;
        List<Statement> mockStatementList = Arrays.asList(new Statement(1L,"Saving", "1234","01-01-2019","109.1"), new Statement(2L,"Current", "1234000","01-07-2018","209.1"));
        when(statementService.getStatements(accountId, fromDate, toDate, fromAmount, toAmount))
            .thenReturn(mockStatementList);

        // Act
        ResponseEntity<?> responseEntity = statementController.getStatements(userDetails, accountId, fromDate, toDate, fromAmount, toAmount);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockStatementList, responseEntity.getBody());
        verify(statementService, times(1)).getStatements(accountId, fromDate, toDate, fromAmount, toAmount);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
     void testGetStatementsForUser() {
        // Arrange
        UserDetails userDetails = new User("user", "", Arrays.asList(() -> "ROLE_USER"));
        List<Statement> mockStatementList = Arrays.asList(new Statement(1L,"Saving", "1234","01-01-2019","109.1"));

        when(statementService.getStatements(null, null, null, null, null))
            .thenReturn(mockStatementList);

        // Act
        ResponseEntity<?> responseEntity = statementController.getStatements(userDetails,null);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockStatementList, responseEntity.getBody());
        verify(statementService, times(1)).getStatements(null, null, null, null, null);
    }
    
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
     void testGetStatementsForUserWithParams() {
        // Arrange
        UserDetails userDetails = new User("user", "", Arrays.asList(() -> "ROLE_USER"));
        Map<String, String> mockRequestParams =new HashMap<>();
        mockRequestParams.put("ar01", "Intro to Map");
        // Act
        ResponseEntity<?> responseEntity = statementController.getStatements(userDetails, mockRequestParams);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
   
    }
    
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
     void testGetStatementsForAdminWithInvalidAccountId() {
        // Arrange
        UserDetails userDetails = new User("admin", "", Arrays.asList(() -> "ROLE_ADMIN"));
        Long accountId = -1L;
        Date fromDate = new Date();
        Date toDate = new Date();
        Double fromAmount = 100.0;
        Double toAmount = 500.0;
        List<Statement> mockStatementList = Arrays.asList(new Statement(1L,"Saving", "1234","01-01-2019","109.1"), new Statement(2L,"Current", "1234000","01-07-2018","209.1"));
        when(statementService.getStatements(accountId, fromDate, toDate, fromAmount, toAmount))
            .thenReturn(mockStatementList);

        ResponseEntity<?> responseEntity = statementController.getStatements(userDetails, accountId, fromDate, toDate, fromAmount, toAmount);

        
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid account ID", responseEntity.getBody());
       
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
     void testGetStatementsForAdminWithInvalidDate() {
        // Arrange
        UserDetails userDetails = new User("admin", "", Arrays.asList(() -> "ROLE_ADMIN"));
        Long accountId = 1L;
        Date toDate = new Date();
        Date fromDate = new  Date(toDate.getTime() + (1000 * 60 * 60 * 24));
        
        Double fromAmount = 100.0;
        Double toAmount = 500.0;
        List<Statement> mockStatementList = Arrays.asList(new Statement(1L,"Saving", "1234","01-01-2019","109.1"), new Statement(2L,"Current", "1234000","01-07-2018","209.1"));
        when(statementService.getStatements(accountId, fromDate, toDate, fromAmount, toAmount))
            .thenReturn(mockStatementList);

        // Act
        ResponseEntity<?> responseEntity = statementController.getStatements(userDetails, accountId, fromDate, toDate, fromAmount, toAmount);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid date range: from date should be before to date", responseEntity.getBody());
     
    }
    
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
     void testGetStatementsForAdminWithInvalidAmount() {
        // Arrange
        UserDetails userDetails = new User("admin", "", Arrays.asList(() -> "ROLE_ADMIN"));
        Long accountId = 1L;
        Date toDate = new Date();
        Date fromDate = new  Date();
        
        Double fromAmount = 500.0;
        Double toAmount = 100.0;
        List<Statement> mockStatementList = Arrays.asList(new Statement(1L,"Saving", "1234","01-01-2019","109.1"), new Statement(2L,"Current", "1234000","01-07-2018","209.1"));
        when(statementService.getStatements(accountId, fromDate, toDate, fromAmount, toAmount))
            .thenReturn(mockStatementList);

        // Act
        ResponseEntity<?> responseEntity = statementController.getStatements(userDetails, accountId, fromDate, toDate, fromAmount, toAmount);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid amount range: To amount should be geater than  from amount", responseEntity.getBody());
      }
}
