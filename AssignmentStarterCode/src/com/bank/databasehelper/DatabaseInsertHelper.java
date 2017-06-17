package com.bank.databasehelper;

import com.bank.database.DatabaseInserter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseInsertHelper extends DatabaseInserter {
  
  /**
   * Creates a new account in the database.
   * @param name The name of the account
   * @param balance How much money is in the account
   * @param typeId Value corresponding to the account type (type must exist)
   * @return newAccountId The new account's ID if the account is successfully added, -1 otherwise
   */
  public static int insertAccount(String name, BigDecimal balance, int typeId) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Check if the given account type ID is valid
    boolean validTypeId = DatabaseValidationHelper.validateAccountTypeId(typeId);
    
    // If ID is valid, create the account
    try {
      if (validTypeId) {
        int newAccountId = DatabaseInserter.insertAccount(name, balance, typeId, connection);
        connection.close();
        return newAccountId;
      } else {
        connection.close();
        return -1;
      }
    } catch (SQLException e) {
      System.out.println("Database connection failed.");
      return -1;
    }
  }
  
  /**
   * Creates a new account type in the database.
   * @param name The name of the new type
   * @param interestRate The interest rate to be associated with the account (>= 0 and < 1)
   * @return True if the type is successfully created
   */
  public static boolean insertAccountType(String name, BigDecimal interestRate) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Check that the name and interest rate is valid
    boolean validName = DatabaseValidationHelper.validateAccountType(name);
    boolean validInterestRate = DatabaseValidationHelper.validateAccountInterestRate(interestRate);

    // If both are valid, create the account type
    try {
      if (validName && validInterestRate) {
        boolean complete = DatabaseInserter.insertAccountType(name, interestRate, connection);
        connection.close();
        return complete;
      } else {
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      System.out.println("Database connection failed.");
      return false;
    }
  }
  
  /**
   * Creates a new user in the database.
   * @param name The name of the user
   * @param age The age of the user
   * @param address The address of the user
   * @param roleId Value corresponding to the user's role (eg. employee, customer)
   * @param password The user's password
   * @return newUserId The new user's ID if the user is successfully added, -1 otherwise
   */
  public static int insertNewUser(String name, 
      int age, String address, int roleId, String password) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate role ID
    boolean validRole = DatabaseValidationHelper.validateRoleId(roleId);
    
    // If role ID is valid, create the user
    try {
      if (validRole) {
        int newUserId = DatabaseInserter.insertNewUser(name, 
            age, address, roleId, password, connection);
        connection.close();
        return newUserId;
      } else {
        connection.close();
        return -1;
      }
    } catch (SQLException e) {
      System.out.println("Database connection failed.");
      return -1;
    }
  }
  
  /**
   * Creates a new role in the database.
   * @param role The name of the role; must be one of the pre-defined roles.
   * @return true If the role is successfully created.
   */
  public static boolean insertRole(String role) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate role name
    boolean validRole = DatabaseValidationHelper.validateRoleName(role);
    
    // If it is, create the new role
    try {
      if (validRole) {
        boolean complete = DatabaseInserter.insertRole(role, connection);
        connection.close();
        return complete;
      } else {
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      System.out.println("Database connection failed.");
      return false;
    }
  }
  
  /**
   * Registers an existing account to an existing user.
   * @param userId The user the account will belong to
   * @param accountId The account to be registered
   * @return true If the account is successfully registered
   */
  public static boolean insertUserAccount(int userId, int accountId) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      boolean complete = DatabaseInserter.insertUserAccount(userId, accountId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Database connection failed.");
      return false;
    }
  }
}
