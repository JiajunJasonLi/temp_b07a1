package com.bank.databasehelper;

import com.bank.database.DatabaseUpdater;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.IllegalAddressException;
import com.bank.exceptions.IllegalInterestRateException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseUpdateHelper extends DatabaseUpdater {
  
  /**
   * Updates the role of the user with the given ID.
   * @param name The name of the new role.
   * @param id The ID of the user to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateRoleName(String name, int id) throws ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate role name
    boolean validName = DatabaseValidationHelper.validateRoleName(name);
    
    try {
      if (validName) {
        boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
        connection.close();
        return complete;
      } else {
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
  }
  
  /**
   * Updates the name of the user with the given ID.
   * @param name The new name for the user.
   * @param id The ID of the user to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateUserName(String name, int id) throws ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      boolean complete = DatabaseUpdater.updateUserName(name, id, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
  }
  
  /**
   * Updates the age of the user with the given ID.
   * @param age The new age of the user.
   * @param id The ID of the user.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateUserAge(int age, int id) throws ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      boolean complete = DatabaseUpdater.updateUserAge(age, id, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
  }
  
  /**
   * Updates the role of the user with the given ID.
   * @param roleId The new role ID.
   * @param id The ID of the user to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateUserRole(int roleId, int id) throws ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate role ID
    boolean validRoleId = DatabaseValidationHelper.validateRoleId(roleId);
    
    try {
      if (validRoleId) {
        boolean complete = DatabaseUpdater.updateUserRole(roleId, id, connection);
        connection.close();
        return complete;
      } else {
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
  }
  
  /**
   * Updates the address of the user with the given ID.
   * @param address The new address of the user.
   * @param id The ID of the user to update.
   * @return true If the update was successful.
   * @throws IllegalAddressException If an address not meeting input requirements is entered.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateUserAddress(String address, int id) 
      throws IllegalAddressException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate address
    boolean validAddress = DatabaseValidationHelper.validateUserAddress(address);
    
    if (!validAddress) {
      throw new IllegalAddressException();
    } else {
      try {
        if (validAddress) {
          boolean complete = DatabaseUpdater.updateUserAddress(address, id, connection);
          connection.close();
          return complete;
        } else {
          connection.close();
          return false;
        }
      } catch (SQLException e) {
        throw new ConnectionFailedException();
      }
    }
  }

  /**
   * Updates the name of the account with the given ID.
   * @param name The new name of the account.
   * @param id The ID of the account to update.
   * @return true If the update was successful.
   */
  public static boolean updateAccountName(String name, int id) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    try {
      boolean complete = DatabaseUpdater.updateAccountName(name, id, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Database connection failed.");
      return false;
    }
  }
  
  /**
   * Updates the balance of the account with the given ID.
   * @param balance The new balance of the account.
   * @param id The ID of the account to update.
   * @return true If the update was successful.
   */
  public static boolean updateAccountBalance(BigDecimal balance, int id) {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate account balance
    boolean validAccountBalance = DatabaseValidationHelper.validateAccountBalance(balance);
    
    try {
      if (validAccountBalance) {
        boolean complete = DatabaseUpdater.updateAccountBalance(balance, id, connection);
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
   * Updates the account type of the account with the given ID.
   * @param typeId The ID of the account type.
   * @param id The ID of the account to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateAccountType(int typeId, int id) throws ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate account type ID
    boolean validAccountTypeId = DatabaseValidationHelper.validateAccountTypeId(typeId);
    
    try {
      if (validAccountTypeId) {
        boolean complete = DatabaseUpdater.updateAccountType(typeId, id, connection);
        connection.close();
        return complete;
      } else {
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
  }
  
  /**
   * Updates the name of the account type with the given ID.
   * @param name The new name of the account type.
   * @param id The ID of the account type to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateAccountTypeName(String name, int id) 
      throws ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate account type ID and account type ID
    boolean validAccountTypeName = DatabaseValidationHelper.validateAccountType(name);
    boolean validAccountTypeId = DatabaseValidationHelper.validateAccountTypeId(id);
    
    try {
      if (validAccountTypeName && validAccountTypeId) {
        boolean complete = DatabaseUpdater.updateAccountTypeName(name, id, connection);
        connection.close();
        return complete;
      } else {
        connection.close();
        return false;
      }
    } catch (SQLException e) {
      throw new ConnectionFailedException();
    }
  }
  
  /**
   * Updates the interest rate of the account type with the given ID.
   * @param interestRate The new interest rate of the account type.
   * @param id The ID of the account type to update.
   * @return true If the update was successful.
   * @throws IllegalInterestRateException If the interest rate is not within the limit.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) 
      throws IllegalInterestRateException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate interest rate and account type ID
    boolean validInterestRate = DatabaseValidationHelper.validateAccountInterestRate(interestRate);
    boolean validAccountTypeId = DatabaseValidationHelper.validateAccountTypeId(id);
    
    if (!validInterestRate) {
      throw new IllegalInterestRateException();
    } else {
      try {
        if (validInterestRate && validAccountTypeId) {
          boolean complete = 
              DatabaseUpdater.updateAccountTypeInterestRate(interestRate, id, connection);
          connection.close();
          return complete;
        } else {
          connection.close();
          return false;
        }
      } catch (SQLException e) {
        throw new ConnectionFailedException();
      }
    }
  }
}
