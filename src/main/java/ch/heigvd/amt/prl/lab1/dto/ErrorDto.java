package ch.heigvd.amt.prl.lab1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of form / API validation errors.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class ErrorDto {
  /**
   * The error message collection
   */
  private final Map<String, MessageCollectionDto> messageCollections = new HashMap<>();

  @JsonValue
  public Map<String, MessageCollectionDto> getMessageCollections() {
    return messageCollections;
  }
  
  /**
   * Add an error message for a field
   * 
   * @param fieldName The field name
   * @param errorMessage The error message
   */
  public void addErrorMessage(String fieldName, String errorMessage) {
    // Make sure there is the error object for the field
    if (!messageCollections.containsKey(fieldName)) {
      messageCollections.put(fieldName, new MessageCollectionDto());
    }
    
    // Add the error message for the field
    messageCollections.get(fieldName).addErrorMessage(errorMessage);
  }
  
  /**
   * Shortcut getter to retrieve the list of error messages for a specific field
   * 
   * @param fieldName The field name to get the error messages
   * @return If no messages, an empty list is returned, otherwise the list of error messages
   */
  @JsonIgnore
  public List<String> getMessages(String fieldName) {
    if (hasErrors(fieldName)) {
      return messageCollections.get(fieldName).getMessages();
    }
    else {
      return new ArrayList<>();
    }
  }
  
  /**
   * Shortcut getter to know if a field has errors or not
   * 
   * @param fieldName The field name to check the errors
   * @return True if there is at least one message for the field
   */
  @JsonIgnore
  public boolean hasErrors(String fieldName) {
    return messageCollections.containsKey(fieldName) && messageCollections.get(fieldName).hasMessages();
  }
  
  /**
   * @return True if there is no error for any field
   */
  @JsonIgnore
  public boolean isEmpty() {
    return messageCollections.isEmpty();
  }

  @Override
  public String toString() {
    return
      this.getClass().getSimpleName() + 
      messageCollections
        .entrySet()
        .stream()
        .map((entry) -> "{ " + entry.getKey() + ": { " + entry.getValue().toString() + " } }, ")
        .reduce("{ ", String::concat)
        .replaceAll(", $", " }");
  }
}
