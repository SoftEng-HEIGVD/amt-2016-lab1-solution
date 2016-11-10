package ch.heigvd.amt.prl.lab1.rest.providers;

import ch.heigvd.amt.prl.lab1.dao.exceptions.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the NoResultException to a 404 response
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Provider
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException> {
  @Override
  public Response toResponse(NoResultException exception) {
    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
