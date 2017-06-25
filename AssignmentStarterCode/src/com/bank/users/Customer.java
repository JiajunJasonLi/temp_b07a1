package com.bank.users;

import com.bank.accounts.Account;
import com.bank.databasehelper.DatabaseInsertHelper;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;
  
  /**
   * Creates a new Customer object with the given information.
   * @param id The user's ID
   * @param name The user's name
   * @param age The user's age
   * @param address The user's address
   */
  public Customer(int id, String name, int age, String address) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
  }
  
  /**
   * Creates a new Customer object with the given information.
   * @param id The user's ID
   * @param name The user's name
   * @param age The user's age
   * @param address The user's address
   * @param authenticated Whether the user has been authenticated (has had their password validated)
   */
  public Customer(int id, String name, int age, String address, boolean authenticated) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.authenticated = authenticated;
  }
  
  /**
   * Gets all the accounts the customer owns.
   * @return A list of all the accounts the customer owns, as Account objects.
   * @throws RecordNotFoundException If there is no user with this user ID, 
   *     or an invalid account ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public List<Account> getAccounts() throws RecordNotFoundException, ConnectionFailedException {
    // Get the IDs of all the accounts belonging to this customer
    List<Integer> allAccountIds = DatabaseSelectHelper.getAccountIds(this.getId());
    
    // Create list to store all the accounts
    List<Account> allAccounts = new ArrayList<Account>();
    
    for (Integer accountId: allAccountIds) {
      // Create an account object based on this account
      Account accountObject = DatabaseSelectHelper.getAccountDetails(accountId);
      
      // Add the account object to the list
      allAccounts.add(accountObject);
    }
    
    return allAccounts;
  }
  
  /**
   * Adds a new account under this customer's name.
   * @param account The account to add, as an initialized Account object.
   * @throws ConnectionFailedException If database connection fails.
   */
  public void addAccount(Account account) throws ConnectionFailedException {
    // Get raw data from the Account object needed to create a record in database
    String name = account.getName();
    BigDecimal balance = account.getBalance();
    int typeId = account.getType();
    
    // Insert a new record for this account
    int accountId = DatabaseInsertHelper.insertAccount(name, balance, typeId);
    
    // Link the account to the user
    if (accountId != -1) {
      int userId = this.getId();
      DatabaseInsertHelper.insertUserAccount(userId, accountId);
    }
  }
}
