package ch.heigvd.amt.prl.lab1.validation;

import ch.heigvd.amt.prl.lab1.dto.FieldsErrorsDto;

/**
 * Share the validation logic
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class AbstractValidationService {
  /**
   * Validates if the string field is too long
   * 
   * @param field The field to validate
   * @param max The maximum length
   * @param errors The errors to enrich
   * @param errorMessage The error message in case of error
   */
  protected void isTooLong(String field, int max, FieldsErrorsDto errors, String errorMessage) {
    // Check if too long only if not null
    if (field != null && field.length() > max) {
      errors.addErrorMessage(field, String.format(errorMessage, max));
    }
  }
  
  /**
   * Validates if the string field is too short
   * 
   * @param field The field to validate
   * @param min The minimum length
   * @param errors The errors to enrich
   * @param errorMessage The error message in case of error
   */
  protected void isTooShort(String field, int min, FieldsErrorsDto errors, String errorMessage) {
    // Consider null value as too short string
    if (field == null || field.length() < min) {
      errors.addErrorMessage(field, String.format(errorMessage, min));
    }
  }
}
