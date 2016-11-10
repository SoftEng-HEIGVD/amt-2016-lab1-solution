package ch.heigvd.amt.prl.lab1.validation;

import ch.heigvd.amt.prl.lab1.dao.IUserDao;
import ch.heigvd.amt.prl.lab1.dto.FieldsErrorsDto;
import ch.heigvd.amt.prl.lab1.dto.UserWriteDto;
import ch.heigvd.amt.prl.lab1.models.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implementation of the user validation service
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Stateless
public class UserValidationService extends AbstractValidationService implements IUserValidationService {
  @EJB
  private IUserDao userDao;
  
  @Override
  public FieldsErrorsDto validateCreation(UserWriteDto dto) {
    FieldsErrorsDto errors = new FieldsErrorsDto();

    // Validates standard fields
    validateFields(dto, errors);

    // Check if the username is already taken only if the username is filled
    if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
      User user = userDao.find(dto.getUsername());
      if (user != null) {
        errors.addErrorMessage("username", 
          String.format("The username %s is already used.", dto.getUsername())
        );
      }
    }
    
    return errors;
  }

  @Override
  public FieldsErrorsDto validateModification(UserWriteDto dto) {
    FieldsErrorsDto errors = new FieldsErrorsDto();

    // Validates standard fields
    validateFields(dto, errors);
    
    // Check if the username is already taken by another user only if the username is filled
    if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
      User user = userDao.find(dto.getUsername());
      if (user != null && user.getId() != dto.getId()) {
        errors.addErrorMessage("username", 
          String.format("The username %s is already used.", dto.getUsername())
        );
      }
    }
    
    return errors;
  }
  
  /**
   * Validates the user fields
   * 
   * @param user The user to validate
   * @param errors The errors to enrich
   */
  private void validateFields(UserWriteDto user, FieldsErrorsDto errors) {
    validateUsername(user.getUsername(), errors);
    validatePassword(user.getPassword(), user.getPasswordConfirmation(), errors);
    validateFirstname(user.getFirstname(), errors);
    validateLastname(user.getLastname(), errors);
  }
  
  /**
   * Validate the username
   * 
   * @param username Username to validate
   * @param errors The errors to enrich
   */
  private void validateUsername(String username, FieldsErrorsDto errors) {
    isTooLong(username, User.LGTH_MAX_FIELDS, errors, "The username is too long. Max %s cars.");
    isTooShort(username, User.LGTH_MIN_FIELDS, errors, "The username is too short. Min %s cars.");
  }
  
  /**
   * Validate the firstname
   * 
   * @param firstname Firstname to validate
   * @param errors The errors to enrich
   */
  private void validateFirstname(String firstname, FieldsErrorsDto errors) {
    isTooLong(firstname, User.LGTH_MAX_FIELDS, errors, "The firstname is too long. Max %s cars.");
    isTooShort(firstname, User.LGTH_MIN_FIELDS, errors, "The firstname is too short. Min %s cars.");
  }
  
  /**
   * Validate the lastname
   * 
   * @param lastname Lastname to validate
   * @param errors The errors to enrich
   */
  private void validateLastname(String lastname, FieldsErrorsDto errors) {
    isTooLong(lastname, User.LGTH_MAX_FIELDS, errors, "The lastname is too long. Max %s cars.");
    isTooShort(lastname, User.LGTH_MIN_FIELDS, errors, "The lastname is too short. Min %s cars.");
  }

  /**
   * Validate the password
   * 
   * @param password Password to validate
   * @param errors The errors to enrich
   */
  private void validatePassword(String password, String passwordConfirmation, FieldsErrorsDto errors) {
    isTooLong(password, User.LGTH_MAX_FIELDS, errors, "The password is too long. Max %s cars.");
    isTooShort(password, User.LGTH_MIN_FIELDS, errors, "The password is too short. Min %s cars.");
    
    // Validate the password is confirmed
    if (password != null && !password.equals(passwordConfirmation)) {
      errors.addErrorMessage(
        "passwordConfirmation", 
        "The passsword and password confirmation does not match."
      );
    }
  }
}
