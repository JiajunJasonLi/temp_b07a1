package com.bank.bank;

import com.bank.database.DatabaseDriver;

import java.sql.Connection;


public class Bank {
  
  /**
   * This is the main method to run your entire program! Follow the Candy Cane instructions to
   * finish this off.
   * @param argv unused.
   */
  public static void main(String[] argv) {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    try {
      //ONLY UNCOMMENT THIS ON FIRST RUN!
      //DatabaseDriverExtender.initialize(connection);
      
      //TODO Check what is in argv 
      //If it is -1
      /*
       * TODO This is for the first run only!
       * Add this code:
       * DatabaseDriverExtender.initialize(connection);
       * Then add code to create your first account, an administrator with a password
       * Once this is done, proceed to ask the user for their input
       */
      //If it is 1
      /*
       * TODO In admin mode, the user must first login with a valid admin account
       * This will allow the user to create new Teller's.  At this point, this is
       * all the admin can do.
       */
      //If anything else - including nothing
      /*
       * TODO Create a context menu, where the user is prompted with:
       * 1 - TELLER Interface
       * 2 - ATM Interface
       * 0 - Exit
       * Enter Selection: 
       */
      //If the user entered 1
      /*
       * TODO Create a context menu for the Teller interface
       * Prompt the user for their id and password
       * Attempt to authenticate them.
       * If the Id is not that of a Teller or password is incorrect, end the session
       * If the Id is a teller, and the password is correct, prompt as follows:
       * 1. authenticate new user
       * 2. Make new User
       * 3. Make new account
       * 4. Give interest
       * 5. Make a deposit
       * 6. Make a withdrawal
       * 7. check balance
       * 8. close customer session
       * 9. Exit
       * 
       * Continue to loop through as appropriate, ending once you get an exit code (9)
       */
      //If the user entered 2
      /*
       * TODO create a context menu for the ATM Interface
       * Prompt the user for their id and password
       * Attempt to authenticate them
       * If the authentication fails, repeat
       * If they get authenticated, give them this menu:
       * 1. List Accounts and balances (list all accounts and their balances)
       * 2. Make Deposit
       * 3. Check balance
       * 4. Make withdrawal
       * 5. Exit
       * 
       * For each of these, loop through and continue prompting for the information needed
       * Continue showing the context menu, until the user gives a 5 as input.
       */
      //If the user entered 0
      /*
       * TODO Exit condition
       */
      //If the user entered anything else:
      /*
       * TODO Re-prompt the user
       */
      
      
      
      
    } catch (Exception e) {
      //TODO Improve this!
      System.out.println("Something went wrong :(");
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }
    }
    
  }
}
