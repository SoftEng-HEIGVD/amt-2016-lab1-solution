package ch.heigvd.amt.prl.lab1.dao.exceptions;

import javax.ejb.ApplicationException;

/**
 * Exception used when we expect only one result from a query and there was more than one.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@ApplicationException
public class NonUniqueResultException extends RuntimeException {
  /**
   * Constructor
   * 
   * @param message The message
   */
  public NonUniqueResultException(String message) {
    super(message);
  }
}
