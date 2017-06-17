package com.bank.databasehelper;

import com.bank.database.DatabaseDriver;
import com.bank.database.DatabaseInserter;
import com.bank.database.DatabaseSelector;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSelectHelper extends DatabaseSelector {
  
  public static String getRole(int id) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    String role;

    try {
      role = DatabaseSelector.getRole(id, connection);
    } catch (SQLException e) {
      role = null;
    } finally {
      connection.close();
    }
    
    return role;
  }
  
  public static List<Integer> getRoles() {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getRoles(connection);
    List<Integer> ids = new ArrayList<>();
    while (results.next()) {
      System.out.println(results.getInt("ID"));
    }
    return ids;
    
  }
   
  public static String getPassword(int userId) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();    
    String hashPassword = DatabaseSelector.getPassword(userId, connection);
    connection.close();
    return hashPassword;
    
  }
  
  public static User getUserDetails(int userId) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
    
    while (results.next()) {
      //It may help you to figure out the user's type, and make an obj based on its
      System.out.println(results.getString("NAME"));
      System.out.println(results.getInt("AGE"));
      System.out.println(results.getString("ADDRESS"));                    
      System.out.println(results.getInt("ROLEID"));
    }
    
  }
 
  public static List<Integer> getAccountIds(int userId) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
    while (results.next()) {                 
      System.out.println(results.getInt("ACCOUNTID"));
    }
    
  }
  public static Account getAccountDetails(int accountId) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);
    
    while (results.next()) {
      //It may help you to figure out the account type, and make an obj based on its
      System.out.println(results.getString("NAME"));
      System.out.println(results.getString("BALANCE"));                    
      System.out.println(results.getInt("TYPE"));
    }
  }
  
 
  public static BigDecimal getBalance(int accountId) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    BigDecimal balance = DatabaseSelector.getBalance(accountId, connection);
    connection.close();
    return balance;
  }
 
  public static BigInteger getInterestRate(int accountType) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    BigInteger interestRate = DatabaseSelector.getInterestRate(accountType, connection);
    connection.close();
    return interestRate;
  }
  
  public static List<Integer> getAccountTypesIds() {
    //TODO Implement this method to get all account Type Id's stored in the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAccountTypesId(connection);
    List<Integer> ids = new ArrayList<>();
    while (results.next()) {
      System.out.println(results.getInt("ID"));
    }
    return ids;
  }
  
  public static String getAccountTypeName(int accountTypeId) {
    //TODO Implement this method to get the account Type Names
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    DatabaseSelector.getAccountTypeName(accountTypeId, connection);
    connection.close();
  }

}
