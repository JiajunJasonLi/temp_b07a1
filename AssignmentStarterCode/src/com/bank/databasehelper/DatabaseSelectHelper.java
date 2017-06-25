package com.bank.databasehelper;

import com.bank.accounts.Account;
import com.bank.accounts.ChequingAccount;
import com.bank.accounts.SavingsAccount;
import com.bank.accounts.Tfsa;

import com.bank.database.DatabaseSelector;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;
import com.bank.users.Admin;
import com.bank.users.Customer;
import com.bank.users.Teller;
import com.bank.users.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSelectHelper extends DatabaseSelector {
  
  /**
   * Gets the name of the role with the given ID.
   * @param id The ID of the role to select
   * @return role The name of the role of the given ID, if it exists. Otherwise returns nothing
   * @throws RecordNotFoundException If there is no role with this ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static String getRole(int id) throws RecordNotFoundException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
  
    try {
      String role = DatabaseSelector.getRole(id, connection);
      
      // If nothing was returned, role ID is invalid.
      if (role == null) {
        throw new RecordNotFoundException("Role not found.");
      }
      
      connection.close();
      return role;
      
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }

  }
  
  /**
   * Gets the IDs for all possible roles.
   * @return ids A list of all the role IDs as Integers.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static List<Integer> getRoles() throws ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      ResultSet results = DatabaseSelector.getRoles(connection);
      List<Integer> ids = new ArrayList<>();
      while (results.next()) {
        ids.add(results.getInt("ID"));
      }
      
      return ids;
      
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
    
  }
  
  /**
   * Gets the password of the user with the given ID.
   * @param userId The ID of the desired user.
   * @return hashPassword The user's password.
   * @throws RecordNotFoundException If there is no user with this ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static String getPassword(int userId) 
      throws RecordNotFoundException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Try to find user's password and return if found. If not, raise exception.
    try {
      String hashPassword = DatabaseSelector.getPassword(userId, connection);
      
      // If nothing was returned, user ID is invalid.
      if (hashPassword == null) {
        throw new RecordNotFoundException("User not found.");
      }
      
      connection.close();
      return hashPassword;

    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }    
  }
  
  /**
   * Gets the details of the user with the given ID, as a User object.
   * @param userId The ID of the user to examine.
   * @return A User object with the name, age, address, role, and ID of the corresponding user.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no user with this ID, 
   *     or user has an invalid role ID.
   */
  public static User getUserDetails(int userId) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      ResultSet results = DatabaseSelector.getUserDetails(userId, connection);

      // If getUserDetails was successful, there should only be one result in results
      // ie. loop should only run once
      while (results.next()) {
        
        String userName = results.getString("NAME");
        int userAge = results.getInt("AGE");
        String userAddress = results.getString("ADDRESS");
        int userRoleId = results.getInt("ROLEID");

        // Throws RecordNotFoundException if there is no role with this ID.
        String userRole = getRole(userRoleId);
        User userObject = null;
      
        if (userRole.equalsIgnoreCase("ADMIN")) {
          userObject = createAdmin(userId, userName, userAge, userAddress);
        } else if (userRole.equalsIgnoreCase("TELLER")) {
          userObject = createTeller(userId, userName, userAge, userAddress);
        } else if (userRole.equalsIgnoreCase("CUSTOMER")) {
          userObject = createCustomer(userId, userName, userAge, userAddress);
        } 

        return userObject;
        
      }
      
      // If code reaches this line, while loop did not run, meaning nothing was returned
      // from getUserDetails. This means the user does not exist
      throw new RecordNotFoundException("User not found.");
      
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    } 
  }
  
  /**
   * Creates a new Admin object with the given information.
   * @param id The Admin's user ID
   * @param name The name of the user
   * @param age The age of the user
   * @param address The address of the user
   * @return newAdmin The Admin object
   */
  private static User createAdmin(int id, String name, int age, String address) {
    User newAdmin = new Admin(id, name, age, address);
    return newAdmin;
  }
  
  /**
   * Creates a new Teller object with the given information.
   * @param id The Teller's user ID
   * @param name The name of the user
   * @param age The age of the user
   * @param address The address of the user
   * @return newTeller The Teller object
   */
  private static User createTeller(int id, String name, int age, String address) {
    User newTeller = new Teller(id, name, age, address);
    return newTeller;
  }
  
  /**
   * Creates a new Customer object with the given information.
   * @param id The Customer's user ID
   * @param name The name of the user
   * @param age The age of the user
   * @param address The address of the user
   * @return newCustomer The Customer object
   */
  private static User createCustomer(int id, String name, int age, String address) {
    User newCustomer = new Customer(id, name, age, address);
    return newCustomer;
  }
  
  /**
   * Gets the IDs for all accounts belonging to this user.
   * @param userId The ID of the user to examine.
   * @return ids A list of all the account IDs as Integers.
   * @throws RecordNotFoundException If there is no user with this ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static List<Integer> getAccountIds(int userId) 
      throws RecordNotFoundException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
      List<Integer> ids = new ArrayList<Integer>();
      
      // Retrieve account IDs, if user exists
      while (results.next()) {  
        ids.add(results.getInt("ACCOUNTID"));
      }
      
      // TODO differentiate between user with no accounts, and nonexistent user (both null return)
      // eg. validate ID using getPassword (all users will have a password)
      if (ids.isEmpty()) {
        throw new RecordNotFoundException("User has no accounts.");
      }
      
      return ids;
      
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
    
  }
  
  /**
   * Gets the details of the account with the given ID, as an Account object.
   * @param accountId The ID of the account to examine.
   * @return An Account object containing the name, balance, account type, 
   *     and ID of the corresponding account.
   * @throws RecordNotFoundException If there is no account with this ID, 
   *     or it has an invalid type ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static Account getAccountDetails(int accountId) 
      throws RecordNotFoundException, ConnectionFailedException {
    // Create database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);

      // If getAccountDetails was successful, there should only be one result in results
      // ie. loop should only run once
      while (results.next()) {
        
        String accountName = results.getString("NAME");
        BigDecimal accountBalance = new BigDecimal(results.getString("BALANCE"));
        int accountTypeId = results.getInt("TYPE");

        // Throws RecordNotFoundException if there is no account type with this ID.
        String accountType = getAccountTypeName(accountTypeId);
        Account accountObject = null;
      
        if (accountType.equalsIgnoreCase("CHEQUING")) {
          accountObject = createChequingAccount(accountId, accountName, accountBalance);
        } else if (accountType.equalsIgnoreCase("SAVING")) {
          accountObject = createSavingsAccount(accountId, accountName, accountBalance);
        } else if (accountType.equalsIgnoreCase("TFSA")) {
          accountObject = createTfsa(accountId, accountName, accountBalance);
        } 

        return accountObject;
        
      }
      
      // If code reaches this line, while loop did not run, meaning nothing was returned
      // from getAccountDetails. This means the account does not exist
      throw new RecordNotFoundException("Account not found.");
      
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    } 
    
  }
  
  /**
   * Creates a new ChequingAccount object with the given information.
   * @param id The account's ID
   * @param name The name of the account
   * @param balance The balance in the account
   * @return newChequingAccount The ChequingAccount object
   */
  private static Account createChequingAccount(int id, String name, BigDecimal balance) {
    Account newChequingAccount = new ChequingAccount(id, name, balance);
    return newChequingAccount;
  }
  
  /**
   * Creates a new SavingsAccount object with the given information.
   * @param id The account's ID
   * @param name The name of the account
   * @param balance The balance in the account
   * @return newSavingsAccount The SavingsAccount object
   */
  private static Account createSavingsAccount(int id, String name, BigDecimal balance) {
    Account newSavingsAccount = new SavingsAccount(id, name, balance);
    return newSavingsAccount;
  }
  
  /**
   * Creates a new Tfsa object with the given information.
   * @param id The account's ID
   * @param name The name of the account
   * @param balance The balance in the account
   * @return newTfsa The Tfsa object
   */
  private static Account createTfsa(int id, String name, BigDecimal balance) {
    Account newTfsa = new Tfsa(id, name, balance);
    return newTfsa;
  }
  
  /**
   * Gets the balance in the account with the given ID.
   * @param accountId The account to examine.
   * @return balance The balance in the account.
   * @throws RecordNotFoundException If there is no account with this ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static BigDecimal getBalance(int accountId) 
      throws RecordNotFoundException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      BigDecimal balance = DatabaseSelector.getBalance(accountId, connection);
      
      // If nothing was returned, account ID is invalid.
      if (balance == null) {
        throw new RecordNotFoundException("Account not found.");
      }
      
      connection.close();
      return balance;
      
    } catch (SQLException e) {
      throw new RecordNotFoundException("Account not found.");
    }
    
  }
  
  /**
   * Gets the interest rate for this account type.
   * @param accountType The ID of the account type.
   * @return interestRate The interest rate.
   * @throws RecordNotFoundException If there is no account type with this ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static BigDecimal getInterestRate(int accountType) 
      throws RecordNotFoundException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      BigDecimal interestRate = DatabaseSelector.getInterestRate(accountType, connection);
      
      // If nothing was returned, account ID is invalid.
      if (interestRate == null) {
        throw new RecordNotFoundException("Account type not found.");
      }
      
      connection.close();
      return interestRate;
      
    } catch (SQLException e) {
      throw new RecordNotFoundException("Account type not found.");
    }
    
  }
  
  /**
   * Gets the IDs for all possible account types.
   * @return ids A list of all the account type IDs as Integers.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static List<Integer> getAccountTypesIds() throws ConnectionFailedException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      ResultSet results = DatabaseSelector.getAccountTypesId(connection);
      List<Integer> ids = new ArrayList<Integer>();
      
      while (results.next()) {    
        ids.add(results.getInt("ID"));
      }
      
      return ids;
      
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
    
  }
  
  /**
   * Gets the name of the account type with the given ID.
   * @param accountTypeId The ID of the account type to examine.
   * @return accountTypeName The name of the account type.
   * @throws RecordNotFoundException If there is no account type with this ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static String getAccountTypeName(int accountTypeId) 
      throws RecordNotFoundException, ConnectionFailedException {  
    // Create database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      String accountTypeName = DatabaseSelector.getAccountTypeName(accountTypeId, connection);
      
      // If nothing was returned, account ID is invalid.
      if (accountTypeName == null) {
        throw new RecordNotFoundException("Account type not found.");
      }
      
      connection.close();
      return accountTypeName;
      
    } catch (SQLException e) {
      throw new RecordNotFoundException("Account type not found.");
    }

  }

}
