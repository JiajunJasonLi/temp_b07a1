package com.bank.users;

import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;
import com.bank.security.PasswordHelpers;

public abstract class User {
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;
  
  /**
   * Gets the ID of the user.
   * @return id The user ID.
   */
  public int getId() {
    int id = this.id;
    return id;
  }
  
  /**
   * Sets the ID of the user.
   * @param id The ID to set.
   */
  public void setId(int id) {
    this.id = id;
  }
  
  /**
   * Gets the name of the user for this account.
   * @return name The user's name.
   */
  public String getName() {
    String name = this.name;
    return name;
  }
  
  /**
   * Sets the name of the user for this account.
   * @param name The name to be set.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Gets the age of the user.
   * @return age The user's age.
   */
  public int getAge() {
    int age = this.age;
    return age;
  }
  
  /**
   * Sets the age of the user.
   * @param age The age to be set.
   */
  public void setAge(int age) {
    this.age = age;
  }
  
  /**
   * Gets the user's role as an integer corresponding to a role from the Roles table.
   * @return roleId A value corresponding to the user's role.
   */
  public int getRoleId() {
    int roleId = this.roleId;
    return roleId;
  }
  
  /**
   * Checks if the password entered matches the one stored in the database.
   * @param password The password entered for this user.
   * @return authenticated True if the user is authenticated (ie. the passwords match).
   * @throws RecordNotFoundException If the user does not exist.
   * @throws ConnectionFailedException If database connection fails.
   */
  public final boolean authenticate(String password) 
      throws RecordNotFoundException, ConnectionFailedException {
    
    // Get user ID
    int userId = this.getId();

    // Get the stored password for this user
    String storedPassword = DatabaseSelectHelper.getPassword(userId);
  
    // Check if the passwords match
    boolean authenticated = PasswordHelpers.comparePassword(storedPassword, password);    
    
    return authenticated;
  }
  
}
