package ch.heigvd.amt.prl.lab1.rest.providers;

import ch.heigvd.amt.prl.lab1.dto.FieldsErrorsDto;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * When unknown fields are present in JSON payload, an exception is thrown and this mapper handle it.
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {
  @Override
  public Response toResponse(UnrecognizedPropertyException exception) {
    FieldsErrorsDto errors = new FieldsErrorsDto();
    
    errors.addErrorMessage(exception.getPropertyName(), "is not allowed.");
    
    return Response
      .status(Response.Status.BAD_REQUEST)
      .entity(errors)
      .build();
  }
}
