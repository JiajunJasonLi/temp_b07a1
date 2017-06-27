package com.bank.applications;

import com.bank.accounts.Account;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.databasehelper.DatabaseUpdateHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.InsufficientFundsException;
import com.bank.exceptions.RecordNotFoundException;
import com.bank.users.Customer;
import com.bank.users.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ATM {
  private Customer currentCustomer;
  private boolean authenticated;
  
  /**
   * Takes a customer and authenticates them; 
   *     if authenticated the ATM is initialized with this customer.
   * @param customerId The ID of the customer.
   * @param password The password input for the customer.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no customer with this user ID.
   */
  public ATM(int customerId, String password) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Authenticate the customer
    this.authenticated = this.authenticate(customerId, password);
    
    // If authenticated, get the customer's user details
    if (this.authenticated) {
      
      // Get the user details
      User newUser = DatabaseSelectHelper.getUserDetails(customerId);
      
      // Check if user is a customer, if yes cast to new customer object
      int customerRoleId = newUser.getRoleId();
      
      if (DatabaseSelectHelper.getRole(customerRoleId).equalsIgnoreCase("CUSTOMER")) {
        Customer newCustomer = (Customer) newUser;
        
        // Set the ATM's customer to this customer object
        this.currentCustomer = newCustomer;
      } else {
        throw new RecordNotFoundException("No customer with this ID found.");
      }
    }
    
  }
  
  /**
   * Initializes this ATM with the given customer.
   * @param customerId The customer's ID.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no customer with this user ID.
   */
  public ATM(int customerId) throws ConnectionFailedException, RecordNotFoundException {
    // Get the user details
    User newUser = DatabaseSelectHelper.getUserDetails(customerId);
    
    // Check if user is a customer, if yes cast to new customer object
    int customerRoleId = newUser.getRoleId();
    
    if (DatabaseSelectHelper.getRole(customerRoleId).equalsIgnoreCase("CUSTOMER")) {
      Customer newCustomer = (Customer) newUser;
      
      // Set the ATM's customer to this customer object
      this.currentCustomer = newCustomer;
    } else {
      throw new RecordNotFoundException("No customer with this ID found.");
    }

  }
  
  /**
   * Checks if the password is correct for the given user.
   * @param userId The ID of the user.
   * @param password The password input.
   * @return authenticated True if the password is correct.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no user with this ID.
   */
  public boolean authenticate(int userId, String password) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Get user details
    User newUser = DatabaseSelectHelper.getUserDetails(userId);
    
    // Authenticate the password
    boolean authenticated = newUser.authenticate(password);
    
    return authenticated;
  }
  
  /**
   * Gets a list of all the accounts the customer owns.
   * @return allAccounts A list of all the customer's accounts as Account objects.
   * @throws RecordNotFoundException If 
   * @throws ConnectionFailedException If database connection fails. 
   */
  public List<Account> listAccounts() 
      throws ConnectionFailedException {
    int customerId = this.currentCustomer.getId();
    List<Account> allAccounts = new ArrayList<Account>();
    
    try {
      // Get all account IDs
      List<Integer> allAccountIds = DatabaseSelectHelper.getAccountIds(customerId);
      
      // Get account details for each account
      for (Integer accountId: allAccountIds) {
        Account currentAccount = DatabaseSelectHelper.getAccountDetails(accountId);
        allAccounts.add(currentAccount);
      }
    } catch (RecordNotFoundException e) {
      // Should not be possible as only valid customers can use the ATM
      // (This is checked upon ATM initialization)
      System.out.println("Invalid user ID.");
    }
    
    return allAccounts;
  }
  
  /**
   * Adds money into the given account.
   * @param amount The amount of money to add.
   * @param accountId The account to add money to.
   * @return successful True if the money was added to the account.
   * @throws RecordNotFoundException If there is no account with this ID, or if 
   *     user does not own this account.
   * @throws ConnectionFailedException If database connection fails.
   */
  public boolean makeDeposit(BigDecimal amount, int accountId) 
      throws RecordNotFoundException, ConnectionFailedException {
    boolean successful = false;
    
    // Check if account belongs to this customer
    boolean accountOwner = this.confirmAccountOwner(accountId);
    
    // If yes, make the deposit
    if (accountOwner) {
      DatabaseUpdateHelper.updateAccountBalance(amount, accountId);
      successful = true;
    } else {
      throw new RecordNotFoundException("No account with this ID was found under this user.");
    }
    
    return successful;
  }
  
  /**
   * Gets the balance of the account.
   * @param accountId The ID of the account to examine.
   * @return balance The balance in the account.
   * @throws RecordNotFoundException If there is no account with this ID, or if
   *     user does not own this account.
   * @throws ConnectionFailedException If database connection fails.
   */
  public BigDecimal checkBalance(int accountId) 
      throws RecordNotFoundException, ConnectionFailedException {
    // Check if account belongs to this customer
    boolean accountOwner = this.confirmAccountOwner(accountId);
    
    // If yes, check the balance
    if (accountOwner) {
      BigDecimal balance = DatabaseSelectHelper.getBalance(accountId);
      return balance;
    } else {
      throw new RecordNotFoundException("No account with this ID was found under this user.");
    }
    
  }
  
  /**
   * Removes money from this given account.
   * @param amount The amount of money to remove.
   * @param accountId The ID of the account.
   * @return successful True if the money was removed.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no account with this ID, or if
   *     user does not own the account.
   * @throws InsufficientFundsException If there is not enough money in the account to withdraw.
   */
  public boolean makeWithdrawal(BigDecimal amount, int accountId) 
      throws ConnectionFailedException, RecordNotFoundException, InsufficientFundsException {
    boolean successful = false;
    
    // Check if account belongs to this customer
    boolean accountOwner = this.confirmAccountOwner(accountId);
    
    // If yes, try to make the withdrawal
    if (accountOwner) {
      
      // First check if there is enough money to withdraw
      BigDecimal currentBalance = DatabaseSelectHelper.getBalance(accountId);
      
      if (currentBalance.compareTo(amount) == 1) {
        // Make the withdrawal (ie. negative deposit)
        DatabaseUpdateHelper.updateAccountBalance(amount.negate(), accountId);
        successful = true;
      } else {
        // Raise exception if there is not enough money
        throw new InsufficientFundsException();
      }

    } else {
      // Raise exception if user is not owner of the account
      throw new RecordNotFoundException("No account with this ID was found under this user.");
    }
    
    return successful;
  }
  
  /**
   * Checks that the current customer owns the account of a given ID.
   * @param accountId The ID of the account to examine.
   * @return accountOwner True if the customer owns this account.
   * @throws ConnectionFailedException If database connection fails.
   */
  protected boolean confirmAccountOwner(int accountId) 
      throws ConnectionFailedException {
    boolean accountOwner = false;
    
    // Get user ID
    int customerId = this.currentCustomer.getId();
    
    // Get all their account IDs
    try {
      List<Integer> allAccountIds = DatabaseSelectHelper.getAccountIds(customerId);
      
      // See if input account ID is one of the customer's accounts
      for (Integer currentAccountId: allAccountIds) {
        if (currentAccountId.equals(accountId)) {
          accountOwner = true;
          break;
        }
      } 
    } catch (RecordNotFoundException e) {
      // Should not be possible as only valid customers can use the ATM
      // (This is checked upon ATM initialization)
      System.out.println("Invalid user ID.");
    }
    
    return accountOwner;
  }
  
  
}
