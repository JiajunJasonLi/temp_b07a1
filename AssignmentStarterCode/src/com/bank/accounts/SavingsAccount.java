package com.bank.accounts;

import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
  private int id;
  private String name;
  private BigDecimal balance;
  private int type;
  private BigDecimal interestRate;
  
  /**
   * Initializes a SavingsAccount object with the given information.
   * @param id The account ID
   * @param name The account's name
   * @param balance The account balance
   */
  public SavingsAccount(int id, String name, BigDecimal balance) {
    this.setId(id);
    this.setName(name);
    this.setBalance(balance);
  }
  
  /**
   * Gets the interest rate for this account type, and defines it for this account object.
   * @throws RecordNotFoundException If the account has an invalid account type ID.
   * @throws ConnectionFailedException If database connection fails.
   */
  public void findAndSetInterestRate() throws RecordNotFoundException, ConnectionFailedException {
    // Get the account's type ID
    int typeId = this.getType();
    
    // Get the interest rate for this account type
    BigDecimal interestRate = DatabaseSelectHelper.getInterestRate(typeId);
    
    // Set the interest rate
    this.interestRate = interestRate;
  }
  
  /**
   * Calculates the interest for this account, and adds it onto the current balance, updating the
   * account balance accordingly.
   */
  public void addInterest() {
    BigDecimal interest = this.getBalance().multiply(this.interestRate);
    BigDecimal newBalance = this.getBalance().add(interest);
    
    this.setBalance(newBalance);
  }
}
