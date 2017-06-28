package com.bank.bank;

import com.bank.database.DatabaseDriver;
import com.bank.databasehelper.DatabaseInsertHelper;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.RecordNotFoundException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;


public class Bank {
  
  /**
   * This is the main method to run your entire program! Follow the Candy Cane instructions to
   * finish this off.
   * @param argv unused.
   */
  public static void main(String[] argv) {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    
    //boolean exit = false;
    //while (!exit) {
    try {
      //ONLY UNCOMMENT THIS ON FIRST RUN!
      //DatabaseDriverExtender.initialize(connection);
      
      //TODO Check what is in argv 
      //If it is -1
      if (argv[0].equals("-1")) {
        DatabaseDriverExtender.initialize(connection);
        
        // Find the role ID of an Admin
        int roleId;
        List<Integer> allRoleIds = DatabaseSelectHelper.getRoles();
        
        try {
          // Iterate over list and find ID corresponding to Admin role
          for (Integer currentRoleId: allRoleIds) {
            if (DatabaseSelectHelper.getRole(currentRoleId).equalsIgnoreCase("ADMIN")) {
              // Once found, create the Admin user
              roleId = currentRoleId;
              DatabaseInsertHelper.insertNewUser("ROOT", 0, "", roleId, "ADMINISTRATOR");
            }
          }
        } catch (RecordNotFoundException e) {
          // Should not be possible as role IDs being used with getRole were 
          // retrieved from database. So should all be valid.
          System.out.println("Invalid role ID.");
        }
        
        // read user input using BufferedReader and InputStreamReader objects
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        
        // take in user input
        String inputStr = br.readLine();
        
      } else if (argv[0].equals("1")) {
        // read user input using BufferedReader and InputStreamReader objects
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        
        System.out.println("You have chosen to log in as an administrator.");
        
        // Take their user ID
        System.out.println("Please enter your user ID: ");
        String name = br.readLine();
        
        // Take their password
        System.out.println("Please enter your password: ");
        String password = br.readLine();
        
        // Attempt to log in with this combination
        
      } else {
        // read user input using BufferedReader and InputStreamReader objects
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        
        String inputStr = br.readLine();
      }
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
      
    //}
  }
}
