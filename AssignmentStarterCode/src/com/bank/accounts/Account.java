package com.bank.accounts;

import java.math.BigDecimal;

public abstract class Account {
  private int id;
  private String name;
  private BigDecimal balance;
  private int type;
  
  /**
   * Gets the account ID number.
   * @return id The account ID.
   */
  public int getId() {
    int id = this.id;
    return id;
  }
  
  /**
   * Sets the account ID number.
   * @param id The ID to be set.
   */
  public void setId(int id) {
    this.id = id;
  }
  
  /**
   * Gets the name of the account, eg. "Bob's Savings Account".
   * @return name The account name.
   */
  public String getName() {
    String name = this.name;
    return name;
  }
  
  /**
   * Sets the name of the account, eg. "Mary's Savings Account".
   * @param name The name to be set.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Get the amount of balance in the account.
   * @return balance The account balance.
   */
  public BigDecimal getBalance() {
    BigDecimal balance = this.balance;
    return balance;
  }
  
  /**
   * Set the balance in the account.
   * @param balance The amount of balance to be set.
   */
  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
  
  /**
   * Returns an integer that corresponds to an account type (from AccountTypes table).
   * @return type A value representing the account type.
   */
  public int getType() {
    int type = this.type;
    return type;
  }
}
