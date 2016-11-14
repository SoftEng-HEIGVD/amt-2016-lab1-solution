package ch.heigvd.amt.prl.lab1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
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
