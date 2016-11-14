package ch.heigvd.amt.prl.lab1.dto;

/**
 * Standard representation of a user. This representation
 * does not contain any sensitive information.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class UserReadDto implements IDto {
  /**
   * Common user attributes
   */
  private long id;
  private String username;
  private String firstname;
  private String lastname;

  /**
   * Constructor required for Jackson serialization for incoming requests
   */
  protected UserReadDto() {
  }
  
  /**
   * Constructor
   * 
   * @param id The user id
   * @param username The username
   * @param firstname The first name
   * @param lastname The last name
   */
  public UserReadDto(long id, String username, String firstname, String lastname) {
    this.id = id;
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public long getId() {
    return id;
  }
  
  public String getUsername() {
    return username;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }
}
