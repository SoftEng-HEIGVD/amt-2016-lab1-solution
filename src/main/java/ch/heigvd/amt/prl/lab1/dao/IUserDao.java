package ch.heigvd.amt.prl.lab1.dao;

import ch.heigvd.amt.prl.lab1.models.User;

import javax.ejb.Local;
import java.util.List;

/**
 * Database operations for a User
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Local
public interface IUserDao {
  /**
   * Create a user in the database
   * 
   * @param user The user to create
   * @return The user with its id updated
   */
  User create(User user);
  
  /**
   * Update a user
   * 
   * @param user The user to update
   * @return True if update is done
   */
  boolean update(User user);
  
  /**
   * Delete a user
   * 
   * @param user The user to delete
   * @return True if the delete is done
   */
  boolean delete(User user);
  
  /**
   * @return Retrieve the list of all the users
   */
  List<User> findAll();
  
  /**
   * Find a user from its unique numerical ID
   * 
   * @param id The id of the user
   * @return The user found
   */
  User find(long id);
  
  /**
   * Find a user by its unique username
   * 
   * @param username The username of the user
   * @return The user found, null otherwise
   */
  User find(String username);
}
