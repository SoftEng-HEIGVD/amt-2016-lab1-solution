package ch.heigvd.amt.prl.lab1.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent validation errors for multiple fields
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class FieldsErrorsDto {
  /**
   * Map of fields with the related errors
   */
  private Map<String, FieldErrorsDto> fieldsErrors = new HashMap<String, FieldErrorsDto>();
  
  /**
   * Add an error message for a field
   * 
   * @param fieldName The field name
   * @param errorMessage The error message
   */
  public void addErrorMessage(String fieldName, String errorMessage) {
    // Make sure there is the error object for the field
    if (!fieldsErrors.containsKey(fieldName)) {
      fieldsErrors.put(fieldName, new FieldErrorsDto());
    }
    
    // Add the error message for the field
    fieldsErrors.get(fieldName).addErrorMessage(errorMessage);
  }
  
  /**
   * @return True if there is no error for any field
   */
  public boolean isEmpty() {
    return fieldsErrors.isEmpty();
  }
}
