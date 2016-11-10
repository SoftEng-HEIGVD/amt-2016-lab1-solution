package ch.heigvd.amt.prl.lab1.services;

import ch.heigvd.amt.prl.lab1.models.User;
import javax.ejb.Local;

/**
 * Manage the security actions like a check of a password or a hash of a password.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Local
public interface ISecurityService {
  /**
   * Check if a user is known and his password is valid
   * 
   * @param username The username
   * @param password The password
   * @return The user if credentials are valid, null otherwise
   */
  User checkCredentials(String username, String password);
  
  /**
   * Hash a clear password
   * 
   * @param password The clear password
   * @return The hashed password
   */
  String hashPassword(String password);
}
