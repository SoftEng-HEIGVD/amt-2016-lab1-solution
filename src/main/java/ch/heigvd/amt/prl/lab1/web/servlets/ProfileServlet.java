package ch.heigvd.amt.prl.lab1.web.servlets;


import ch.heigvd.amt.prl.lab1.dao.IUserDao;
import ch.heigvd.amt.prl.lab1.dto.ErrorDto;
import ch.heigvd.amt.prl.lab1.dto.UserWriteDto;
import ch.heigvd.amt.prl.lab1.models.User;
import ch.heigvd.amt.prl.lab1.services.ISecurityService;
import ch.heigvd.amt.prl.lab1.validation.IUserValidationService;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handle the profile of a user
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@WebServlet(name = "ProfileServlet", urlPatterns = AbstractServlet.SERVLET_PATTERN_PROFILE)
public class ProfileServlet extends RegisterServlet {
  /**
   * JSP Profile attributes
   */
  private static final String JSP_ATTR_UPDATE_TYPE = "updateType";
  private static final String JSP_ATTR_VALID_UPDATE = "valid";
  
  /**
   * Update types
   */
  private static final String UPDATE_PROFILE = "profile";
  private static final String UPDATE_PASSWORD = "password";
  
  @EJB
  private IUserDao userDao;
  
  @EJB
  private IUserValidationService userValidationService;
  
  @EJB
  private ISecurityService securityService;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Retrieve the user id from the session
    long userId = ((User) request.getSession(false).getAttribute(SESSION_ATTR_USER)).getId();
    
    // Retrieve the operation type
    String updateType = request.getParameter(JSP_ATTR_UPDATE_TYPE);
    
    // Build the correct DTO for the corresponding operation
    UserWriteDto userDto;
    if (UPDATE_PROFILE.equalsIgnoreCase(updateType)) {
      userDto = new UserWriteDto(userId, 
        request.getParameter(JSP_ATTR_USERNAME),
        request.getParameter(JSP_ATTR_FIRSTNAME),
        request.getParameter(JSP_ATTR_LASTNAME)
      );
    }
    else if (UPDATE_PASSWORD.equalsIgnoreCase(updateType)) {
      userDto = new UserWriteDto(userId, 
        request.getParameter(JSP_ATTR_PASSWORD),
        request.getParameter(JSP_ATTR_PASSWORD_CONFIRMATION)
      );
    }
    else { // The operation is not valid
      error(request, response, PAGE_PROFILE, "general", "Unknown operation.");
      return;
    }
    
    // Validates the inputs
    ErrorDto errors = userValidationService.validateModification(userDto);
    
    // No errors, update the user
    if (errors.isEmpty()) {
      User user = userDao.find(userDto.getId());
      
      // Check what to update on the user
      if (UPDATE_PROFILE.equalsIgnoreCase(updateType)) {
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
      }
      else {
        user.setHashedPassword(securityService.hashPassword(userDto.getPassword()));
      }
      
      // Check if the update is done
      if (userDao.update(user)) {
        request.setAttribute(JSP_ATTR_VALID_UPDATE, updateType);
//          "Your details or password has been successfully updated.");
        
        // We make sure the user is also update in the session (probably not the same objects)
        request.getSession(false).setAttribute(SESSION_ATTR_USER, user);
        
        // Finally show again the profile page
        forward(request, response, PAGE_PROFILE);
      }
      else { // An error occured
        ErrorDto error = new ErrorDto();
        
        error.addErrorMessage(updateType + "General", 
          "Unable to update your details. Contact the administrator for more details.");
      }
    }
    else {
      error(request, response, PAGE_PROFILE, JSP_ATTR_UPDATE_TYPE + "Errors", errors);
    }
  }
}
