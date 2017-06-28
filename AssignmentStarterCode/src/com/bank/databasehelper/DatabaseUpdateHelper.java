package com.bank.databasehelper;

import com.bank.database.DatabaseUpdater;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.IllegalAddressException;
import com.bank.exceptions.IllegalInterestRateException;
import com.bank.exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseUpdateHelper extends DatabaseUpdater {
  
  /**
   * Updates the role of the user with the given ID.
   * @param name The name of the new role.
   * @param id The ID of the user to update.
   * @return true If the update was successful.
   * @throws RecordNotFoundException If there is no role with this name, or user with this ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public static boolean updateRoleName(String name, int id) 
      throws RecordNotFoundException, ConnectionFailedException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate role name and user ID
    boolean validName = DatabaseDriverHelper.validateRoleName(name);
    boolean validUserId = DatabaseDriverHelper.validateUserId(id);
    
    if (!validName) {
      throw new RecordNotFoundException("Role not found.");
    }
    
    if (!validUserId) {
      throw new RecordNotFoundException("User not found.");
    }
    
    try {
      if (validUserId && validName) {
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
   * @throws RecordNotFoundException If there is no user with this ID.
   */
  public static boolean updateUserName(String name, int id) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate user ID
    boolean validUserId = DatabaseDriverHelper.validateUserId(id);
    
    if (!validUserId) {
      throw new RecordNotFoundException("User not found.");
    } else {
    
      try {
        boolean complete = DatabaseUpdater.updateUserName(name, id, connection);
        connection.close();
        return complete;
      } catch (SQLException e) {
        throw new ConnectionFailedException();
      }
    
    }
  }
  
  /**
   * Updates the age of the user with the given ID.
   * @param age The new age of the user.
   * @param id The ID of the user.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no user with this ID.
   */
  public static boolean updateUserAge(int age, int id) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate user ID
    boolean validUserId = DatabaseDriverHelper.validateUserId(id);
    
    if (!validUserId) {
      throw new RecordNotFoundException("User not found.");
    } else {

      try {
        boolean complete = DatabaseUpdater.updateUserAge(age, id, connection);
        connection.close();
        return complete;
      } catch (SQLException e) {
        throw new ConnectionFailedException();
      }
      
    }
  }
  
  /**
   * Updates the role of the user with the given ID.
   * @param roleId The new role ID.
   * @param id The ID of the user to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no role or user with this ID.
   */
  public static boolean updateUserRole(int roleId, int id) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate role and user ID
    boolean validRoleId = DatabaseDriverHelper.validateRoleId(roleId);
    boolean validUserId = DatabaseDriverHelper.validateUserId(id);

    if (!validRoleId) {
      throw new RecordNotFoundException("Role not found.");
    }
    
    if (!validUserId) {
      throw new RecordNotFoundException("User not found.");
    }
    
    try {
      if (validRoleId && validUserId) {
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
   * @throws RecordNotFoundException If there is no user with this ID.
   */
  public static boolean updateUserAddress(String address, int id) 
      throws IllegalAddressException, ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate address and user ID
    boolean validAddress = DatabaseDriverHelper.validateUserAddress(address);
    boolean validUserId = DatabaseDriverHelper.validateUserId(id);
    
    if (!validAddress) {
      throw new IllegalAddressException();
    }
    
    if (!validUserId) {
      throw new RecordNotFoundException("User not found.");
    }
    
    try {
      if (validAddress && validUserId) {
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

  /**
   * Updates the name of the account with the given ID.
   * @param name The new name of the account.
   * @param id The ID of the account to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no user with this ID.
   */
  public static boolean updateAccountName(String name, int id) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate account ID
    boolean validAccountId = DatabaseDriverHelper.validateAccountId(id);
    
    if (!validAccountId) {
      throw new RecordNotFoundException("Account not found.");
    } else {
      
      try {
        boolean complete = DatabaseUpdater.updateAccountName(name, id, connection);
        connection.close();
        return complete;
      } catch (SQLException e) {
        throw new ConnectionFailedException();
      }
      
    }
  }
  
  /**
   * Updates the balance of the account with the given ID.
   * @param balance The new balance of the account.
   * @param id The ID of the account to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException  If there is no account with this ID.
   */
  public static boolean updateAccountBalance(BigDecimal balance, int id) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate account balance and account ID
    boolean validAccountBalance = DatabaseDriverHelper.validateAccountBalance(balance);
    boolean validAccountId = DatabaseDriverHelper.validateAccountId(id);
    
    if (!validAccountId) {
      throw new RecordNotFoundException("Account not found.");
    }
    
    try {
      if (validAccountBalance && validAccountId) {
        boolean complete = DatabaseUpdater.updateAccountBalance(balance, id, connection);
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
   * Updates the account type of the account with the given ID.
   * @param typeId The ID of the account type.
   * @param id The ID of the account to update.
   * @return true If the update was successful.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no account type or account with this ID.
   */
  public static boolean updateAccountType(int typeId, int id) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate account type and account ID
    boolean validAccountTypeId = DatabaseDriverHelper.validateAccountTypeId(typeId);
    boolean validAccountId = DatabaseDriverHelper.validateAccountId(id);
    
    if (!validAccountTypeId) {
      throw new RecordNotFoundException("Account type not found.");
    }
    if (!validAccountId) {
      throw new RecordNotFoundException("Account not found.");
    }
    
    try {
      if (validAccountTypeId && validAccountId) {
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
   * @throws RecordNotFoundException If there is no account type with this name or ID.
   */
  public static boolean updateAccountTypeName(String name, int id) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate account type ID and account type ID
    boolean validAccountTypeName = DatabaseDriverHelper.validateAccountType(name);
    boolean validAccountTypeId = DatabaseDriverHelper.validateAccountTypeId(id);
    
    if (!validAccountTypeName || !validAccountTypeId) {
      throw new RecordNotFoundException("Account type not found.");
    }
    
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
   * @throws RecordNotFoundException If there is no account type with this ID.
   */
  public static boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) 
      throws IllegalInterestRateException, ConnectionFailedException, RecordNotFoundException {
    // Create the database connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    
    // Validate interest rate and account type ID
    boolean validInterestRate = DatabaseDriverHelper.validateAccountInterestRate(interestRate);
    boolean validAccountTypeId = DatabaseDriverHelper.validateAccountTypeId(id);
    
    if (!validInterestRate) {
      throw new IllegalInterestRateException();
    } 
    
    if (!validAccountTypeId) {
      throw new RecordNotFoundException("Account type not found.");
    }
    
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
