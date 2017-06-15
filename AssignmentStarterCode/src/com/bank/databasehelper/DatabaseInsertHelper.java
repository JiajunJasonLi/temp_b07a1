package com.bank.databasehelper;

import com.bank.database.DatabaseDriver;
import com.bank.database.DatabaseInserter;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.generics.AccountTypes;
import com.bank.generics.Roles;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DatabaseInsertHelper extends DatabaseInserter {
  
  /**
   * Creates a new account in the database.
   * @param name The name of the account
   * @param balance How much money is in the account
   * @param typeId Value corresponding to the account type (type must exist)
   * @return newAccountId if the account is successfully added, -1 otherwise
   */
  public static int insertAccount(String name, BigDecimal balance, int typeId) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Get all possible account type IDs
    List<Integer> allAccountTypes = DatabaseSelectHelper.getAccountTypesIds();
    
    // Check if the given account type ID is valid
    boolean validTypeId = false;
    
    for (Integer accountType: allAccountTypes) {
      if (accountType.equals(typeId)) {
        validTypeId = true;
      }
    }
    
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
    
    // Check that the name and interest rate is valid; assume false initially
    boolean validName = false;
    boolean validInterestRate = false;
    
    // Validate name
    // Get all valid account type names
    AccountTypes[] allAccountTypes = AccountTypes.values();
    
    // Check if input name is one of pre-defined names
    for (AccountTypes accountType: allAccountTypes) {
      if (name.equalsIgnoreCase(accountType.toString())) {
        validName = true;
      }
    }
    
    // Validate interest rate
    // Check that 0 <= interest rate < 1
    if ((interestRate.compareTo(BigDecimal.ZERO) > -1) 
        && (interestRate.compareTo(BigDecimal.ONE) < 0)) {
      validInterestRate = true;
    }
    
    // If both are valid, create the account type
    try {
      if (validName && validInterestRate) {
        DatabaseInserter.insertAccountType(name, interestRate, connection);
        connection.close();
        return true;
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
   * @return newUserId if the user is successfully added, -1 otherwise
   */
  public static int insertNewUser(String name, 
      int age, String address, int roleId, String password) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate role ID; initially assume invalid
    boolean validRole = false;
    
    // Get all possible IDs
    List<Integer> allUserRoleIds = DatabaseSelectHelper.getRoles();
    
    // Check if input role ID is one of the possible ones
    for (Integer userRoleId: allUserRoleIds) {
      if (userRoleId.equals(roleId)) {
        validRole = true;
      }
    }
    
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
    
    // Validate role name; initially assume invalid
    boolean validRole = false;
    
    // Get all role names
    Roles[] allRoleNames = Roles.values();
    
    // Check if input role is one of them
    for (Roles roleName: allRoleNames) {
      if (role.equalsIgnoreCase(roleName.toString())) {
        validRole = true;
      }
    }
    
    // If it is, create the new role
    try {
      if (validRole) {
        DatabaseInserter.insertRole(role, connection);
        connection.close();
        return true;
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
    
    // Validate the user and account IDs; initially assume valid
    boolean validUserId = true;
    boolean validAccountId = true;
    
    // Try to access information based on user/account ID;
    // if it fails, then ID is invalid
    try {
      DatabaseSelectHelper.getAccountIds(userId);
    } catch (SQLException e) {
      validUserId = false;
    }
    
    try {
      DatabaseSelectHelper.getBalance(accountId);
    } catch (SQLException e) {
      validAccountId = false;
    }
    
    // If the user and account ID are valid, create the new user account
    try {
      if (validUserId && validAccountId) {
        DatabaseInserter.insertUserAccount(userId, accountId, connection);
        connection.close();
        return true;
      } else {
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      System.out.println("Database connection failed.");
      return false;
    }
  }
}
