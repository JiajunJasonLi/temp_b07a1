package com.bank.databasehelper;

import com.bank.database.DatabaseDriver;
import com.bank.database.DatabaseInserter;

import java.math.BigDecimal;
import java.sql.Connection;


public class DatabaseInsertHelper extends DatabaseInserter {
 
  public static int insertAccount(String name, BigDecimal balance, int typeId) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    DatabaseInserter.insertAccount(name, balance, typeId, connection);
    connection.close();
    return -1;
  }
  
  public static boolean insertAccountType(String name, BigDecimal interestRate) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    DatabaseInserter.insertAccountType(name, interestRate, connection);
    connection.close();
    return false;
  }
  
  public static int insertNewUser(String name, int age, String address, int roleId, String password) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    DatabaseInserter.insertNewUser(name, age, address, roleId, password, connection);
    connection.close();
    return -1;
  }
  
  public static boolean insertRole(String role) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    DatabaseInserter.insertRole(role, connection);
    connection.close();
    return false;
  }
  
  public static boolean insertUserAccount(int userId, int accountId) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    DatabaseInserter.insertUserAccount(userId, accountId, connection);
    connection.close();
    return false;
  }
}
