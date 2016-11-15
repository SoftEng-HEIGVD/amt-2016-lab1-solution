package ch.heigvd.amt.prl.lab1.dao.exceptions;

import ch.heigvd.amt.prl.lab1.dao.filtering.Filter;
import javax.ejb.ApplicationException;

/**
 * Exception used when the filter is not correctly configured. The field name has an error or the field
 * value is missing.
 * 
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@ApplicationException
public class FilterValidationException extends RuntimeException {
  /**
   * When the field name is invalid
   */
  private final boolean invalidFieldName;

  /**
   * True if the value was present with the field name
   */
  private final boolean valueAbsent;

  /**  
   * Keep a reference on the filter for other processing of the error
   */
  private final Filter filter;
  
  /**
   * Constructor
   * 
   * @param invalidFieldName Define if the field name is invalid
   * @param valueAbsent Track if a value was not provided with the field name
   * @param filter The filter in error
   */
  public FilterValidationException(boolean invalidFieldName, boolean valueAbsent, Filter filter) {
    this.invalidFieldName = invalidFieldName;
    this.valueAbsent = valueAbsent;
    this.filter = filter;
  }

  public boolean isInvalidFieldName() {
    return invalidFieldName;
  }

  public boolean isValueAbsent() {
    return valueAbsent;
  }

  public Filter getFilter() {
    return filter;
  }
}
