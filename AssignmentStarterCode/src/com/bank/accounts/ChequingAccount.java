package com.bank.accounts;

import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.databasehelper.DatabaseUpdateHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class ChequingAccount extends Account {
  private int id;
  private String name;
  private BigDecimal balance;
  private int type;
  private BigDecimal interestRate;
  
  /**
   * Initializes a ChequingAccount object with the given information.
   * @param id The account ID
   * @param name The account's name
   * @param balance The account balance
   * @throws ConnectionFailedException If database connection fails.
   */
  public ChequingAccount(int id, String name, BigDecimal balance) throws ConnectionFailedException {
    this.setId(id);
    this.setName(name);
    this.setBalance(balance);
    
    // Find the type ID of an ChequingAccount
    List<Integer> allTypeIds = DatabaseSelectHelper.getAccountTypesIds();
    
    try {
      // Iterate over list and find ID corresponding to ChequingAccount type
      for (Integer currentTypeId: allTypeIds) {
        if (DatabaseSelectHelper.getAccountTypeName(currentTypeId).equalsIgnoreCase("CHEQUING")) {
          this.type = currentTypeId;
        }
      }
    } catch (RecordNotFoundException e) {
      // Should not be possible as role IDs being used with getRole were retrieved from database
      // So should all be valid.
      System.out.println("Invalid type ID.");
    }
    
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
   * @throws RecordNotFoundException If account ID is invalid.
   * @throws ConnectionFailedException If database connection fails.
   */
  public void addInterest() throws ConnectionFailedException, RecordNotFoundException {
    // If interest rate not already defined, set it
    if (this.interestRate == null) {
      this.findAndSetInterestRate();
    }
    
    // Calculate the interest and new balance of the account
    BigDecimal interest = this.getBalance().multiply(this.interestRate);
    BigDecimal newBalance = this.getBalance().add(interest);
    
    // Update the balance in the Account object and in the database
    this.setBalance(newBalance);
    DatabaseUpdateHelper.updateAccountBalance(newBalance, this.getId());
  }
}
