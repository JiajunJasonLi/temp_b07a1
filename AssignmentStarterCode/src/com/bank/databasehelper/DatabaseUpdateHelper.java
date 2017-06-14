package com.bank.databasehelper;

import java.math.BigDecimal;
import java.sql.Connection;

import com.bank.database.DatabaseDriver;
import com.bank.database.DatabaseUpdater;

public class DatabaseUpdateHelper extends DatabaseUpdater{
  
  public static boolean updateRoleName(String name, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
    connection.close();
    return complete;
  }
  
  
  public static boolean updateUserName(String name, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserName(name, id, connection);
    connection.close();
    return complete;
  }
  
  
  public static boolean updateUserAge(int age, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAge(age, id, connection);
    connection.close();
    return complete;
  }
  
  public static boolean updateUserRole(int roleId, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserRole(roleId, id, connection);
    connection.close();
    return complete;
  }
  
  
  public static boolean updateUserAddress(String address, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateUserAddress(address, id, connection);
    connection.close();
    return complete;
  }


  public static boolean updateAccountName(String name, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateAccountName(name, id, connection);
    connection.close();
    return complete;
  }
  
 
  public static boolean updateAccountBalance(BigDecimal balance, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateAccountBalance(balance, id, connection);
    connection.close();
    return complete;
  }
  
 
  public static boolean updateAccountType(int typeId, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateAccountType(typeId, id, connection);
    connection.close();
    return complete;
  }
  
  
  public static boolean updateAccountTypeName(String name, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateAccountTypeName(name, id, connection);
    connection.close();
    return complete;
  }
  
 
  public static boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = DatabaseUpdater.updateAccountTypeInterestRate(interestRate, id, connection);
    connection.close();
    return complete;
  }
}
