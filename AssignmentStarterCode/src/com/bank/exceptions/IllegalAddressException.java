package com.bank.exceptions;

public class IllegalAddressException extends Exception {

  private static final long serialVersionUID = 1L;
  
  public IllegalAddressException() {
    super();
  }
  
  public IllegalAddressException(String arg) {
    super(arg);        
  }

}
