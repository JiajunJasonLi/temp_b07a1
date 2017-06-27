package com.bank.users;

import java.util.List;

import com.bank.databasehelper.DatabaseInsertHelper;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;

public class Admin extends User {
  
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;
  
  /**
   * Creates a new Admin object with the given information.
   * @param id The user's ID
   * @param name The user's name
   * @param age The user's age
   * @param address The user's address
   * @throws ConnectionFailedException If database connection fails.
   */
  public Admin(int id, String name, int age, String address) throws ConnectionFailedException {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    
    // Find the role ID of an Admin
    List<Integer> allRoleIds = DatabaseSelectHelper.getRoles();
    
    try {
      // Iterate over list and find ID corresponding to Admin role
      for (Integer currentRoleId: allRoleIds) {
        if (DatabaseSelectHelper.getRole(currentRoleId).equalsIgnoreCase("ADMIN")) {
          this.roleId = currentRoleId;
        }
      }
    } catch (RecordNotFoundException e) {
      // Should not be possible as role IDs being used with getRole were retrieved from database
      // So should all be valid.
      System.out.println("Invalid role ID.");
    }
    
  }
  
  /**
   * Creates a new Admin object with the given information.
   * @param id The user's ID
   * @param name The user's name
   * @param age The user's age
   * @param address The user's address
   * @param authenticated Whether the user has been authenticated (has had their password validated)
   * @throws ConnectionFailedException If database connection fails.
   */
  public Admin(int id, String name, int age, String address, boolean authenticated) 
      throws ConnectionFailedException {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.authenticated = authenticated;
    
    // Find the role ID of an Admin
    List<Integer> allRoleIds = DatabaseSelectHelper.getRoles();
    
    try {
      // Iterate over list and find ID corresponding to Admin role
      for (Integer currentRoleId: allRoleIds) {
        if (DatabaseSelectHelper.getRole(currentRoleId).equalsIgnoreCase("ADMIN")) {
          this.roleId = currentRoleId;
        }
      }
    } catch (RecordNotFoundException e) {
      // Should not be possible as role IDs being used with getRole were retrieved from database
      // So should all be valid.
      System.out.println("Invalid role ID.");
    }
  }
  
}
