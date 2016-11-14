package ch.heigvd.amt.prl.lab1.dao.exceptions;

import java.sql.SQLException;
import javax.ejb.ApplicationException;

/**
 * Any exception that occurs due to a problem with a SQL query
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@ApplicationException
public class QueryException extends RuntimeException{
  /**
   * Constructor to wrap any other exception
   * 
   * @param cause The root cause
   */
  public QueryException(Throwable cause) {
    super(cause.getMessage(), cause);
  }
  
  /**
   * Constructor to wrap an SQLException
   * 
   * @param sqle The SQLException that occurs
   */
  public QueryException(SQLException sqle) {
    super(sqle.getMessage(), sqle);
  }
}
