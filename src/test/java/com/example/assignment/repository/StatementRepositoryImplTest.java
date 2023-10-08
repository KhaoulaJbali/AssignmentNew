package com.example.assignment.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.assignment.controller.StatementController;
import com.example.assignment.entity.Statement;

public class StatementRepositoryImplTest {

	@InjectMocks
	private StatementRepositoryImpl assignmentRepositoryImpl;
  
	@Test
	void testGetStatementsForAdmin() {
		PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
		
		try {
			ResultSet resultSet = mock((ResultSet.class));
			String databaseURL = "jdbc:ucanaccess://src/main/resources/accountsdb.accdb";

			Connection connection=mock(Connection.class);

			connection = DriverManager.getConnection(databaseURL);

			when(connection.prepareStatement(toString())).thenReturn(mockPreparedStatement);
			when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
