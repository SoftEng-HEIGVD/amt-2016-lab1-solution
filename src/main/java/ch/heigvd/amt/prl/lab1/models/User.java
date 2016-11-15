package ch.heigvd.amt.prl.lab1.models;

import ch.heigvd.amt.prl.lab1.dao.filtering.FilterName;

/**
 * User model to connect to the website.
 * 
 * This model does not have a setter for the ID as it will be always 
 * defined by the database.
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public class User implements IModel {
  /**
   * For simplification purposes, we only have length for string fields of the user
   */
  public static final int LGTH_MIN_FIELDS = 3;
  public static final int LGTH_MAX_FIELDS = 15;
  
  /**
   * Unique numerical identifier
   */
  private long id;
  
  /**
   * User details
   */
  @FilterName
  private String username;
  
  @FilterName("first_name")
  private String firstname;
  
  @FilterName("last_name")
  private String lastname;

  /**
   * The password hash
   */
  private String hashedPassword;
  
  /**
   * Constructor to create a new user without an id
   * 
   * @param username Username
   * @param firstname The first name of the user
   * @param lastname The last name of the user
   */
  public User(String username, String firstname, String lastname) {
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
  }
  
  @Override
  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  @Override
  public String toString() {
    return "User{" +
      "id: " + id + ", " +
      "username: " + username + ", " +
      "firstname: " + firstname + ", " +
      "lastname: " + lastname + ", " +
      "hashedPassword: " + hashedPassword + 
    "}";
  }
}
