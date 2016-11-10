package ch.heigvd.amt.prl.lab1.rest.resources;

import ch.heigvd.amt.prl.lab1.dao.IUserDao;
import ch.heigvd.amt.prl.lab1.dto.FieldsErrorsDto;
import ch.heigvd.amt.prl.lab1.dto.UserReadDto;
import ch.heigvd.amt.prl.lab1.dto.UserWriteDto;
import ch.heigvd.amt.prl.lab1.models.User;
import ch.heigvd.amt.prl.lab1.rest.PATCH;
import ch.heigvd.amt.prl.lab1.services.ISecurityService;
import ch.heigvd.amt.prl.lab1.validation.IUserValidationService;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Defines the REST API for the /users uri.
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@Stateless
@Path("/users")
public class UsersResource extends AbstractResource {

  @EJB
  private IUserDao userDao;

  @EJB
  private IUserValidationService userValidationService;

  @EJB
  private ISecurityService securityService;

  /**
   * Retrieve the list of users
   *
   * @param byUsername
   * @return The list of user with read DTO form
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserReadDto> findAll(@QueryParam("byUsername") String byUsername) {
    return userDao.findAll().stream().map(UsersResource::fromUser).collect(Collectors.toList());
  }

  /**
   * Retrieve user with its numerical identifier.
   *
   * In case the user is not found, an exception is thrown from the user dao which is mapped through the
   *
   * @param id The id of the user to retrieve
   * @return The user found
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public UserReadDto find(@PathParam(value = "id") long id) {
    return fromUser(userDao.find(id));
  }

  /**
   * Create a new user
   * 
   * @param userToCreate The user data
   * @return The response in case of success or error. Can be 201, 422 or 500
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(UserWriteDto userToCreate) {
    // Validate the user inputs
    FieldsErrorsDto errors = userValidationService.validateCreation(userToCreate);

    // Check if there is any error
    if (errors.isEmpty()) {
      // Create the user and set the hashed password
      User user = toUser(userToCreate);
      user.setHashedPassword(securityService.hashPassword(userToCreate.getPassword()));

      user = userDao.create(user);

      // The user was created correctly
      if (user != null) {
        return sendCreated(this.getClass(), "find", user.getId());
      }
      else {
        return serverError("Unknown error");
      }
    }
    else {
      return validationError(errors);
    }
  }

  @PATCH
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("id") long id, UserWriteDto userToUpdate) {
    // Validate the user inputs
    FieldsErrorsDto errors = userValidationService.validateModification(userToUpdate);
    
    // Check if there is any error
    if (errors.isEmpty()) {
      // Retrieve the user to update
      User user = userDao.find(id);
      
      // Update the user attributes
      user = toUser(user, userToUpdate);
      
      // Special case for the password as we need the security service
      if (userToUpdate.getPassword() != null) {
        user.setHashedPassword(securityService.hashPassword(userToUpdate.getPassword()));
      }
      
      if (userDao.update(user)) {
        return noContent(this.getClass(), "find", user.getId());
      }
      else {
        return serverError("Unable to update the user due to unknown error");
      }
    }
    else {
      return validationError(errors);
    }
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam(value = "id") long id) {
    // Find and delete the user
    if (userDao.delete(userDao.find(id))) {
      return noContent();
    }
    else {
      return serverError("Unable to delete the user due to an unknown error");
    }
  }

  /**
   * Convert a user model to a user read DTO
   * 
   * @param user The user model
   * @return The user read DTO
   */
  private static UserReadDto fromUser(User user) {
    return new UserReadDto(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname());
  }

  /**
   * Convert a user write DTO to a user model
   * 
   * @param userWriteDto The user write DTO
   * @return The user
   */
  private static User toUser(UserWriteDto userWriteDto) {
    return new User(
      userWriteDto.getUsername(),
      userWriteDto.getFirstname(),
      userWriteDto.getLastname()
    );
  }
  
  /**
   * We update a user model with the value provided through the DTO
   * 
   * @param user The user model to update
   * @param userWriteDto The user DTO with provided values
   * @return The user updated
   */
  private static User toUser(User user, UserWriteDto userWriteDto) {
    /**
     * For each field, we check if a value is provided, if yes, we
     * update it to the user model otherwise, we keep the original
     * value.
     */
    
    if (userWriteDto.getUsername() != null) {
      user.setUsername(userWriteDto.getUsername());
    }
    
    if (userWriteDto.getFirstname() != null) {
      user.setFirstname(userWriteDto.getFirstname());
    }
    
    if (userWriteDto.getLastname() != null) {
      user.setLastname(userWriteDto.getLastname());
    }
    
    return user;
  }
}
