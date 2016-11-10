package ch.heigvd.amt.prl.lab1.dto;

/**
 * Representation for the write operations for a user.
 * This representation contains the password and password confirmation which
 * are sensitive informations.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class UserWriteDto extends UserReadDto {
  private String password;
  private String passwordConfirmation;

  /**
   * Constructor for new user
   * 
   * @param password The password
   * @param passwordConfirmation The password confirmation
   * @param username The username
   * @param firstname The first name
   * @param lastname The last name
   */
  public UserWriteDto(String password, String passwordConfirmation, String username, String firstname, String lastname) {
    super(-1, username, firstname, lastname);
    this.password = password;
    this.passwordConfirmation = passwordConfirmation;
  }

  /**
   * Constructor for existing user
   * 
   * @param password The password
   * @param passwordConfirmation The password confirmation
   * @param id The user id
   * @param username The username
   * @param firstname The first name
   * @param lastname The last name
   */
  public UserWriteDto(String password, String passwordConfirmation, long id, String username, String firstname, String lastname) {
    super(id, username, firstname, lastname);
    this.password = password;
    this.passwordConfirmation = passwordConfirmation;
  }

  /**
   * Constructor to update the profile data without the password
   * 
   * @param id The user id
   * @param username The username
   * @param firstname The first name
   * @param lastname  The last name
   */
  public UserWriteDto(long id, String username, String firstname, String lastname) {
    super(id, username, firstname, lastname);
  }
  
  /**
   * Constructor to update the user password only
   * 
   * @param id The user id
   * @param password The password
   * @param passwordConfirmation The password confirmation
   */
  public UserWriteDto(long id, String password, String passwordConfirmation) {
    super(id, null, null, null);
    this.password = password;
    this.passwordConfirmation = passwordConfirmation;
  }
  
  public String getPassword() {
    return password;
  }

  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }
}
