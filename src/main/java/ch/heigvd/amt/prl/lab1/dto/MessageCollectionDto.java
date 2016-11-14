package ch.heigvd.amt.prl.lab1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.LinkedList;
import java.util.List;

/**
 * Represent a list of errors for a field
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class MessageCollectionDto {
  /**
   * The list of error messages for a field
   */
  private final List<String> messages = new LinkedList<>();

  @JsonValue
  public List<String> getMessages() {
    return messages;
  }
  
  /**
   * Add an error message to the list of messages
   * 
   * @param errorMessage The error message to add
   */
  public void addErrorMessage(String errorMessage) {
    messages.add(errorMessage);
  }

  @JsonIgnore
  public boolean hasMessages() {
    return !messages.isEmpty();
  }
  
  @Override
  public String toString() {
    return 
      this.getClass().getSimpleName() + 
      messages
        .stream()
        .map((message) -> "{ " + message + " }, ")
        .reduce("{ ", String::concat)
        .replaceAll(", $", " }");
  }
}
