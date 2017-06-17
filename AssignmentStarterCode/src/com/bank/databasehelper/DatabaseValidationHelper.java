package com.bank.databasehelper;

import com.bank.generics.AccountTypes;
import com.bank.generics.Roles;

import java.math.BigDecimal;
import java.util.List;

// A class containing methods to help validate database input
public class DatabaseValidationHelper {
  
  /**
   * Checks if the given ID is associated with an existing account.
   * @param accountId The account ID to examine.
   * @return validAccounId True if an account with this ID exists.
   *
  protected static boolean validateAccountId(int accountId) {
    boolean validAccountId = true;
    
    // Try to access information with this account ID;
    // if it fails, then ID is invalid
    try {
      DatabaseSelectHelper.getBalance(accountId);
    } catch (SQLException e) {
      validAccountId = false;
    }
    
    return validAccountId;
  } **/
  
  /**
   * Checks if the given account type ID exists.
   * @param accountTypeId The ID for the account type
   * @return validAccountTypeId True if the ID exists.
   */
  protected static boolean validateAccountTypeId(int accountTypeId) {
    boolean validAccountTypeId = false;
    
    // Get all possible account type IDs
    List<Integer> allAccountTypes = DatabaseSelectHelper.getAccountTypesIds();
    
    for (Integer accountType: allAccountTypes) {
      if (accountType.equals(accountTypeId)) {
        validAccountTypeId = true;
      }
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
   */
  protected static boolean validateRoleId(int roleId) {
    boolean validRoleId = false;
    
    // Get all possible IDs
    List<Integer> allUserRoleIds = DatabaseSelectHelper.getRoles();
    
    // Check if input role ID is one of the possible ones
    for (Integer userRoleId: allUserRoleIds) {
      if (userRoleId.equals(roleId)) {
        validRoleId = true;
      }
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
   *
  protected static boolean validateUserId(int userId) {
    boolean validUserId = true;
    
    // Try to access information with this user ID;
    // if it fails, then ID is invalid
    try {
      DatabaseSelectHelper.getAccountIds(userId);
    } catch (SQLException e) {
      validUserId = false;
    }
    
    return validUserId;
  } **/
  
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
