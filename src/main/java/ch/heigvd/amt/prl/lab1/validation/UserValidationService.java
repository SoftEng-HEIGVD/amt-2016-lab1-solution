package ch.heigvd.amt.prl.lab1.validation;

import ch.heigvd.amt.prl.lab1.dao.IUserDao;
import ch.heigvd.amt.prl.lab1.dto.ErrorDto;
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
  public ErrorDto validateCreation(UserWriteDto dto) {
    ErrorDto errors = new ErrorDto();

    // Validates standard fields
    validateUsername(dto.getUsername(), errors);
    validatePassword(dto.getPassword(), dto.getPasswordConfirmation(), errors);
    validateFirstname(dto.getFirstname(), errors);
    validateLastname(dto.getLastname(), errors);

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
  public ErrorDto validateModification(UserWriteDto dto) {
    ErrorDto errors = new ErrorDto();

    // Validates standard fields (if present)
    if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
      validateUsername(dto.getUsername(), errors);
    }
    
    if (dto.getPassword()!= null && !dto.getPassword().isEmpty()) {
      validatePassword(dto.getPassword(), dto.getPasswordConfirmation(), errors);
    }
    
    if (dto.getFirstname()!= null && !dto.getFirstname().isEmpty()) {
      validateFirstname(dto.getFirstname(), errors);
    }
    
    if (dto.getLastname()!= null && !dto.getLastname().isEmpty()) {
      validateLastname(dto.getLastname(), errors);
    }
    
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
   * Validate the username
   * 
   * @param username Username to validate
   * @param errors The errors to enrich
   */
  private void validateUsername(String username, ErrorDto errors) {
    isTooLong(username, User.LGTH_MAX_FIELDS, errors, "username", "The username is too long. Max %s cars.");
    isTooShort(username, User.LGTH_MIN_FIELDS, errors, "username", "The username is too short. Min %s cars.");
  }
  
  /**
   * Validate the firstname
   * 
   * @param firstname Firstname to validate
   * @param errors The errors to enrich
   */
  private void validateFirstname(String firstname, ErrorDto errors) {
    isTooLong(firstname, User.LGTH_MAX_FIELDS, errors, "firstname", "The firstname is too long. Max %s cars.");
    isTooShort(firstname, User.LGTH_MIN_FIELDS, errors, "firstname", "The firstname is too short. Min %s cars.");
  }
  
  /**
   * Validate the lastname
   * 
   * @param lastname Lastname to validate
   * @param errors The errors to enrich
   */
  private void validateLastname(String lastname, ErrorDto errors) {
    isTooLong(lastname, User.LGTH_MAX_FIELDS, errors, "lastname", "The lastname is too long. Max %s cars.");
    isTooShort(lastname, User.LGTH_MIN_FIELDS, errors, "lastname", "The lastname is too short. Min %s cars.");
  }

  /**
   * Validate the password
   * 
   * @param password Password to validate
   * @param errors The errors to enrich
   */
  private void validatePassword(String password, String passwordConfirmation, ErrorDto errors) {
    isTooLong(password, User.LGTH_MAX_FIELDS, errors, "password", "The password is too long. Max %s cars.");
    isTooShort(password, User.LGTH_MIN_FIELDS, errors, "password", "The password is too short. Min %s cars.");
    
    // Validate the password is confirmed
    if (password != null && !password.equals(passwordConfirmation)) {
      errors.addErrorMessage(
        "passwordConfirmation", 
        "The passsword and password confirmation does not match."
      );
    }
  }
}
