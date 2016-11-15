package ch.heigvd.amt.prl.lab1.rest.providers;

import ch.heigvd.amt.prl.lab1.dao.exceptions.FilterValidationException;
import ch.heigvd.amt.prl.lab1.dao.filtering.Filter;
import ch.heigvd.amt.prl.lab1.dao.filtering.FilterUtils;
import ch.heigvd.amt.prl.lab1.dto.ErrorDto;
import ch.heigvd.amt.prl.lab1.rest.resources.AbstractResource;
import ch.heigvd.amt.prl.lab1.utils.StringUtils;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * When unknown filter name is used in filtering process
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Provider
public class FilterValidationExceptionMapper implements ExceptionMapper<FilterValidationException> {
  @Override
  public Response toResponse(FilterValidationException exception) {
    ErrorDto errors = new ErrorDto();
    
    // Add invalid field name error
    if (exception.isInvalidFieldName()) {
      // Build the error
      errors.addErrorMessage(
        AbstractResource.QUERY_PARAM_FIELD_NAME, 
        String.format(
          "The field name %s is invalid. Available filter names are: %s", 
          exception.getFilter().getFieldName(),
          buildAvailableFieldNames(exception.getFilter())
        )
      );
    }
    
    // Add missing value error
    if (exception.isValueAbsent()) {
      errors.addErrorMessage(
        AbstractResource.QUERY_PARAM_FIELD_VALUE,
        "The field value is mandatory when the field name is specified."
      );
    }
    
    return Response
      .status(AbstractResource.UNPROCESSABLE_ENTITY)
      .entity(errors)
      .build();
  }
    
  /**
   * Build the list of available field names
   * 
   * @param filter The filter to retrieve the list of field names
   * @return The human friendly form of the list of field names as "a, b and c"
   */
  private static String buildAvailableFieldNames(Filter filter) {
    return StringUtils.replaceLast(
      FilterUtils
        .getAvailableFilterNames(filter.getModelClass())
        .stream()
        .map((filterName) -> filterName + ", ")
        .reduce("", String::concat)
        .replaceAll(", $", ""),
      ", ",
      " and "
    );
  }
}
