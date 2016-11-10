package ch.heigvd.amt.prl.lab1.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Represent a list of errors for a field
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class FieldErrorsDto {
  /**
   * The list of error messages for a field
   */
  private List<String> messages = new LinkedList<>();
  
  /**
   * Add an error message to the list of messages
   * 
   * @param errorMessage The error message to add
   */
  public void addErrorMessage(String errorMessage) {
    messages.add(errorMessage);
  }
}
