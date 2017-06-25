package com.bank.databasehelper;

import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;
import com.bank.generics.AccountTypes;
import com.bank.generics.Roles;

import java.math.BigDecimal;

// A class containing methods to help validate database input
public class DatabaseValidationHelper {
  
  /**
   * Checks if the given account type ID exists.
   * @param accountTypeId The ID for the account type
   * @return validAccountTypeId True if an account type with this ID exists.
   * @throws ConnectionFailedException If database connection fails.
   */
  protected static boolean validateAccountTypeId(int accountTypeId) 
      throws ConnectionFailedException {
    boolean validAccountTypeId = true;
    
    // Try to access information for this account type
    // If record not found, ID is invalid.
    try {
      DatabaseSelectHelper.getInterestRate(accountTypeId);
    } catch (RecordNotFoundException e) {
      validAccountTypeId = false;
    } 
    
    return validAccountTypeId;
  }
  
  /**
   * Checks if the given name is an existing name for an account type.
   * @param typeName The name to examine.
   * @return validAccountType True if the name is the name of an existing account type.
   */
  protected static boolean validateAccountType(String typeName) {
    boolean validAccountType = false;
    
    // Get all valid account type names
    AccountTypes[] allAccountTypes = AccountTypes.values();
    
    // Check if input name is one of pre-defined names
    for (AccountTypes accountType: allAccountTypes) {
      if (typeName.equalsIgnoreCase(accountType.toString())) {
        validAccountType = true;
      }
    }
    
    return validAccountType;
  }
  
  /**
   * Checks if the given account ID exists.
   * @param accountTypeId The ID for the account
   * @return validAccountTypeId True if an account with this ID exists.
   * @throws ConnectionFailedException If database connection fails.
   */
  protected static boolean validateAccountId(int accountId) 
      throws ConnectionFailedException {
    boolean validAccountId = true;
    
    // Try to access information for this account type
    // If record not found, ID is invalid.
    try {
      DatabaseSelectHelper.getBalance(accountId);
    } catch (RecordNotFoundException e) {
      validAccountId = false;
    } 
    
    return validAccountId;
  }
  
  /**
   * Checks if the given interest rate is an acceptable interest rate.
   * (Ie. 0 <= interest rate < 1)
   * @param interestRate The interest rate to examine
   * @return validInterestRate True if the interest rate is acceptable.
   */
  protected static boolean validateAccountInterestRate(BigDecimal interestRate) {
    boolean validInterestRate = false;
    
    // Check that 0 <= interest rate < 1
    if ((interestRate.compareTo(BigDecimal.ZERO) > -1) 
        && (interestRate.compareTo(BigDecimal.ONE) < 0)) {
      validInterestRate = true;
    }
    
    return validInterestRate;
  }
  
  /**
   * Checks if the input account balance value is acceptable. (Ie. Defined with two decimal places.)
   * @param balance The balance to examine.
   * @return validAccountBalance True if the balance is acceptable.
   */
  protected static boolean validateAccountBalance(BigDecimal balance) {
    boolean validAccountBalance = false;
    
    // TODO: Check if this actually works
    if (balance.scale() == 2) {
      validAccountBalance = true;
    }
    
    return validAccountBalance;
  }
  
  /**
   * Checks if the given role ID is associated with an existing role type.
   * @param roleId The role ID to examine.
   * @return validRoleId True if the role ID exists.
   * @throws ConnectionFailedException If database connection fails.
   */
  protected static boolean validateRoleId(int roleId) throws ConnectionFailedException {
    boolean validRoleId = true;
    
    // Try to access information for this account type
    // If record not found, ID is invalid.
    try {
      DatabaseSelectHelper.getRole(roleId);
    } catch (RecordNotFoundException e) {
      validRoleId = false;
    } 
    
    return validRoleId;
  }
  
  /**
   * Checks if the given role name is an existing role.
   * @param roleName The role name to examine.
   * @return validRoleName True if the role exists.
   */
  protected static boolean validateRoleName(String roleName) {
    boolean validRoleName = false;
    
    // Get all role names
    Roles[] allRoleNames = Roles.values();
    
    // Check if input role is one of them
    for (Roles name: allRoleNames) {
      if (roleName.equalsIgnoreCase(name.toString())) {
        validRoleName = true;
      }
    }
    
    return validRoleName;
  }
  
  /**
   * Checks if the given user ID is associated with an existing user.
   * @param userId The user ID to examine.
   * @return validUserId True if a user with this ID exists.
   * @throws ConnectionFailedException If database connection fails.
   */
  protected static boolean validateUserId(int userId) throws ConnectionFailedException {
    boolean validUserId = true;
    
    // Try to access information with this user ID;
    // if it fails, then ID is invalid
    try {
      DatabaseSelectHelper.getPassword(userId);
    } catch (RecordNotFoundException e) {
      validUserId = false;
    }
    
    return validUserId;
  }
  
  /**
   * Checks if the given user address is acceptable. (Ie. 100 characters or less.)
   * @param address The user address to examine.
   * @return validUserAddress True if the address is acceptable.
   */
  protected static boolean validateUserAddress(String address) {
    boolean validUserAddress = false;
    
    if (address.length() <= 100) {
      validUserAddress = true;
    }
    
    return validUserAddress;
  }
}
