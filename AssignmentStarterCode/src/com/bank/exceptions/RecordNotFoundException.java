package com.bank.exceptions;

public class RecordNotFoundException extends Exception {
  
  private static final long serialVersionUID = 1L;
  
  public RecordNotFoundException() {
    super();
  }
  
  public RecordNotFoundException(String arg) {
    super(arg);        
  }

}
