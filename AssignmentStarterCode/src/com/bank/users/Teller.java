package com.bank.users;

import java.util.List;

import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.ConnectionFailedException;
import com.bank.exceptions.RecordNotFoundException;

public class Teller extends User {
  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;
  
  /**
   * Creates a new Teller object with the given information.
   * @param id The user's ID
   * @param name The user's name
   * @param age The user's age
   * @param address The user's address
   * @throws ConnectionFailedException If database connection fails.
   */
  public Teller(int id, String name, int age, String address) throws ConnectionFailedException {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    
    // Find the role ID of an Teller
    List<Integer> allRoleIds = DatabaseSelectHelper.getRoles();
    
    try {
      // Iterate over list and find ID corresponding to Teller role
      for (Integer currentRoleId: allRoleIds) {
        if (DatabaseSelectHelper.getRole(currentRoleId).equalsIgnoreCase("TELLER")) {
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
   * Creates a new Teller object with the given information.
   * @param id The user's ID
   * @param name The user's name
   * @param age The user's age
   * @param address The user's address
   * @param authenticated Whether the user has been authenticated (has had their password validated)
   * @throws ConnectionFailedException If database connection fails.
   */
  public Teller(int id, String name, int age, String address, boolean authenticated) 
      throws ConnectionFailedException {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.authenticated = authenticated;
    
    // Find the role ID of an Teller
    List<Integer> allRoleIds = DatabaseSelectHelper.getRoles();
    
    try {
      // Iterate over list and find ID corresponding to Teller role
      for (Integer currentRoleId: allRoleIds) {
        if (DatabaseSelectHelper.getRole(currentRoleId).equalsIgnoreCase("TELLER")) {
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
