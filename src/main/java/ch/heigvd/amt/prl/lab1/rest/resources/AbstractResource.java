package ch.heigvd.amt.prl.lab1.rest.resources;

import ch.heigvd.amt.prl.lab1.dto.ErrorDto;
import java.net.URI;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Shared code to represent a resource with utility methods
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public abstract class AbstractResource {
  /**
   * HTTP Status for validation errors
   */
  private static final int UNPROCESSABLE_ENTITY = 422;
  
  @Context
  private UriInfo uriInfo;

  /**
   * Produces a created response with location header
   * 
   * @param cl The class to get the path
   * @param methodName The method to enrich the path
   * @param id The id to replace in the path
   * @return The response for HTTP 201 created
   */
  protected Response sendCreated(Class<? extends AbstractResource> cl, String methodName, long id) {
    // Return the created response
    return Response.created(buildUrl(cl, methodName, id)).build();
  }
  
  /**
   * Produces a response for validation errors
   * 
   * @param errors The validation errors
   * @return The response with HTTP status 422
   */
  protected Response validationError(ErrorDto errors) {
    return Response.status(UNPROCESSABLE_ENTITY).entity(errors).build();
  }
  
  /**
   * Produces a server error with a specific error message
   * 
   * @param message The error message
   * @return The response with HTTP status 500
   */
  protected Response serverError(String message) {
    return Response.serverError().entity(message).build();
  }
  
  /**
   * Produces a no content response
   * 
   * @return The response with HTTP status 204
   */
  protected Response noContent() {
    return Response.noContent().build();
  }
  
  /**
   * Produces a no content response with a location header
   * 
   * @param cl The class to get the path
   * @param methodName The method to enrich the path
   * @param id The id to replace in the path
   * @return The response for HTTP 204 no content
   */
  protected Response noContent(Class<? extends AbstractResource> cl, String methodName, long id) {
    return Response.noContent().location(buildUrl(cl, methodName, id)).build();
  }
  
  /**
   * Build an URI based on class, method and id of the resource
   * 
   * @param cl The class to get the path
   * @param method The method to enrich the path
   * @param id The id to replace in the path
   * @return The URI built
   */
  private URI buildUrl(Class<? extends AbstractResource> cl, String methodName, long id) {
    return uriInfo.getBaseUriBuilder().path(cl).path(cl, methodName).build(id);
  }
}
