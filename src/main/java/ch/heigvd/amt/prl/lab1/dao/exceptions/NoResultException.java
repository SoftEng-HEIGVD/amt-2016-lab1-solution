package ch.heigvd.amt.prl.lab1.dao.exceptions;

import javax.ejb.ApplicationException;

/**
 * Exception used when we expect at least one result from the database.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@ApplicationException
public class NoResultException extends RuntimeException {
  /**
   * Constructor
   * 
   * @param message Message
   */
  public NoResultException(String message) {
    super(message);
  }
}
