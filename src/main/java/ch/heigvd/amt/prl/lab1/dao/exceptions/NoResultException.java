package ch.heigvd.amt.prl.lab1.dao.exceptions;

/**
 * Exception used when we expect at least one result from the database.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
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
