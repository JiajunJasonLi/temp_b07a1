package com.bank.applications;

import com.bank.accounts.Account;
import com.bank.accounts.ChequingAccount;
import com.bank.accounts.SavingsAccount;
import com.bank.accounts.Tfsa;
import com.bank.databasehelper.DatabaseInsertHelper;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.IllegalAddressException;
import com.bank.exceptions.RecordNotFoundException;
import com.bank.users.Customer;
import com.bank.users.Teller;
import com.bank.users.User;

import java.math.BigDecimal;
import java.util.List;

public class TellerTerminal extends ATM {
  private Teller currentUser;
  private boolean currentUserAuthenticated;
  private Customer currentCustomer;
  private boolean currentCustomerAuthenticated;
  
  /**
   * Instantiates a TellerTerminal with the given Teller (if they are authenticated)
   * @param tellerId The ID of the teller.
   * @param password The password of the teller.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no teller with this user ID.
   */
  public TellerTerminal(int tellerId, String password) 
      throws ConnectionFailedException, RecordNotFoundException {
    // Temporary fix as ATM will not instantiate with a non-customer ID
    super(tellerId);

    // Authenticate the teller
    this.currentUserAuthenticated = this.authenticate(tellerId, password);
    
    // If authenticated, get the teller's user details
    if (this.currentUserAuthenticated) {
      
      // Get the user details
      User newUser = DatabaseSelectHelper.getUserDetails(tellerId);
      
      /// Check if user is a teller, if yes cast to new teller object
      int userRoleId = newUser.getRoleId();
      
      if (DatabaseSelectHelper.getRole(userRoleId).equalsIgnoreCase("TELLER")) {
        Teller newTeller = (Teller) newUser;
        
        // Set the ATM's teller to this teller object
        this.currentUser = newTeller;
      } else {
        throw new RecordNotFoundException("No teller with this ID found.");
      }
    }
  }
  
  /**
   * Creates a new account with the given information.
   * @param name The name of the account
   * @param balance The balance in the account
   * @param type The type ID of the account
   * @return True if the account was successfully created
   * @throws ConnectionFailedException If database connection fails.
   */
  public boolean makeNewAccount(String name, BigDecimal balance, int type) 
      throws ConnectionFailedException {
    boolean successful = false;
    
    // Check if both user and customer are authenticated
    if (this.currentUserAuthenticated && this.currentCustomerAuthenticated) {
      
      // Create the account
      int newAccountId = DatabaseInsertHelper.insertAccount(name, balance, type);
      
      // Link the account to the user
      int customerId = this.currentCustomer.getId();
      successful = DatabaseInsertHelper.insertUserAccount(customerId, newAccountId);
    }
    
    return successful;
  }
  
  public void setCurrentCustomer(Customer customer) {
    this.currentCustomer = customer;
  }
  
  /**
   * Checks the current customer's password is correct.
   * @param password The password input
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If no user exists with the customer's user ID.
   */
  public void authenticateCurrentCustomer(String password) 
      throws ConnectionFailedException, RecordNotFoundException {
    int customerId = this.currentCustomer.getId();
    
    boolean authenticated = this.authenticate(customerId, password);
    this.currentCustomerAuthenticated = authenticated;
  }
  
  /**
   * Creates a new customer in the database.
   * @param name The name of the customer
   * @param age The age of the customer
   * @param address The address of the customer
   * @param password The customer's password
   * @throws ConnectionFailedException If database connection fails.
   * @throws IllegalAddressException If address does not meet input requirements.
   */
  public void makeNewUser(String name, int age, String address, String password) 
      throws ConnectionFailedException, IllegalAddressException {
    // Find the role ID of a customer
    // First get all possible role IDs
    List<Integer> allRoleIds = DatabaseSelectHelper.getRoles();
    int customerRoleId;
    
    try {
      // Iterate over list and find ID corresponding to Customer role
      for (Integer currentRoleId: allRoleIds) {
        if (DatabaseSelectHelper.getRole(currentRoleId).equalsIgnoreCase("CUSTOMER")) {
          customerRoleId = currentRoleId;
          
          // Insert the new user
          DatabaseInsertHelper.insertNewUser(name, age, address, customerRoleId, password);
          
          break;
        }
      }
    } catch (RecordNotFoundException e) {
      // Should not be possible as role IDs being used with getRole were retrieved from database
      // So should all be valid.
      System.out.println("Invalid role ID.");
    }

  }
  
  /**
   * Adds the interest to the given account.
   * @param accountId The ID of the account to add interest to.
   * @throws ConnectionFailedException If database connection fails.
   * @throws RecordNotFoundException If there is no account with this ID.
   */
  public void giveInterest(int accountId) 
      throws ConnectionFailedException, RecordNotFoundException {
    if (this.currentCustomerAuthenticated && this.currentUserAuthenticated) {
      if (this.confirmAccountOwner(accountId)) {
        // Get the account details
        Account account = DatabaseSelectHelper.getAccountDetails(accountId);
        
        // Determine the account type
        String accountType = DatabaseSelectHelper.getAccountTypeName(account.getType());
        
        // Add the interest
        if (accountType.equalsIgnoreCase("CHEQUING")) {
          ChequingAccount chequingAccount = (ChequingAccount) account;
          chequingAccount.addInterest();
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
          SavingsAccount savingsAccount = (SavingsAccount) account;
          savingsAccount.addInterest();
        } else if (accountType.equalsIgnoreCase("Tfsa")) {
          Tfsa tfsaAccount = (Tfsa) account;
          tfsaAccount.addInterest();
        }
        
      }
    }
  }
  
  public void deAuthenticateCustomer() {
    this.currentCustomerAuthenticated = false;
    this.currentCustomer = null;
  }
}
