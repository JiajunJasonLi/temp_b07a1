package com.bank.exceptions;

public class IllegalInterestRateException extends Exception {
  
  private static final long serialVersionUID = 1L;
  
  public IllegalInterestRateException() {
    super();
  }
  
  public IllegalInterestRateException(String arg) {
    super(arg);        
  }
  
}
