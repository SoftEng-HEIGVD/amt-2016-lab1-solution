package ch.heigvd.amt.prl.lab1.validation;

import ch.heigvd.amt.prl.lab1.dto.ErrorDto;

/**
 * Share the validation logic
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class AbstractValidationService {
  /**
   * Validates if the string value is too long
   * 
   * @param value The value to validate
   * @param max The maximum length
   * @param errors The errors to enrich
   * @param fieldName The name of the field validated
   * @param errorMessage The error message in case of error
   */
  protected void isTooLong(String value, int max, ErrorDto errors, String fieldName, String errorMessage) {
    // Check if too long only if not null
    if (value != null && value.length() > max) {
      errors.addErrorMessage(fieldName, String.format(errorMessage, max));
    }
  }
  
  /**
   * Validates if the string value is too short
   * 
   * @param value The value to validate
   * @param min The minimum length
   * @param errors The errors to enrich
   * @param fieldName The name of the field validated
   * @param errorMessage The error message in case of error
   */
  protected void isTooShort(String value, int min, ErrorDto errors, String fieldName, String errorMessage) {
    // Consider null value as too short string
    if (value == null || value.length() < min) {
      errors.addErrorMessage(fieldName, String.format(errorMessage, min));
    }
  }
}
