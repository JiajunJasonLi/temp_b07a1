package com.bank.users;

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
   */
  public Teller(int id, String name, int age, String address) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
  }
  
  /**
   * Creates a new Teller object with the given information.
   * @param id The user's ID
   * @param name The user's name
   * @param age The user's age
   * @param address The user's address
   * @param authenticated Whether the user has been authenticated (has had their password validated)
   */
  public Teller(int id, String name, int age, String address, boolean authenticated) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.authenticated = authenticated;
  }
}
