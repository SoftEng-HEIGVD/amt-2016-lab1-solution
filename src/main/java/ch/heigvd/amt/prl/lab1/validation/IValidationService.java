package ch.heigvd.amt.prl.lab1.validation;

import ch.heigvd.amt.prl.lab1.dto.ErrorDto;
import ch.heigvd.amt.prl.lab1.dto.IDto;

/**
 * Validation service to make all the validation for the DTOs
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 * @param <T> The model type for which the validation service is for
 */
public interface IValidationService<T extends IDto> {
  /**
   * Validate a model for the creation
   * 
   * @param dto The DTO to validate
   * @return The errors, empty errors if no error
   */
  ErrorDto validateCreation(T dto);
  
  /**
   * Validate a model for the modification
   * 
   * @param dto The DTO to validate
   * @return The errors, empty errors if no error
   */
  ErrorDto validateModification(T dto);
}
