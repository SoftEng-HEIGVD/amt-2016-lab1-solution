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
 * Handle the registration
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@WebServlet(name = "RegisterServlet", urlPatterns = AbstractServlet.SERVLET_PATTERN_REGISTER)
public class RegisterServlet extends LoginServlet {
  /**
   * JSP Attributes for the register page
   */
  protected static final String JSP_ATTR_FIRSTNAME = "firstname";
  protected static final String JSP_ATTR_LASTNAME = "lastname";
  protected static final String JSP_ATTR_PASSWORD_CONFIRMATION = "passwordConfirmation";
  
  @EJB
  private IUserDao userDao;

  @EJB
  private IUserValidationService userValidationService;
  
  @EJB
  private ISecurityService securityService;
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /**
     * Retrieve the form parameters
     */
    UserWriteDto userDto = new UserWriteDto(
      request.getParameter(JSP_ATTR_PASSWORD),
      request.getParameter(JSP_ATTR_PASSWORD_CONFIRMATION),
      request.getParameter(JSP_ATTR_USERNAME),
      request.getParameter(JSP_ATTR_FIRSTNAME),
      request.getParameter(JSP_ATTR_LASTNAME)
    );

    // Validate the user data
    ErrorDto errors = userValidationService.validateCreation(userDto);
    
    // No validation errors
    if (errors.isEmpty()) {
      // We create the user and set the hashed password
      User user = new User(userDto.getUsername(), userDto.getFirstname(), userDto.getLastname());
      user.setHashedPassword(securityService.hashPassword(userDto.getPassword()));
        
      // We create the user in the database
      user = userDao.create(user);
      
      // If the user is correctly created we redirect the user to the login page
      if (user != null) {
        redirect(request, response, PATH_LOGIN);
        return;
      }
      else { // Otherwise we add an error message
        errors.addErrorMessage(
          "general", 
          "Unexpected error occurs. Please, contact the administrator for more info."
        );
      }
    }
    
    // Show error on register page
    error(request, response, PAGE_REGISTER, errors);
  }
}
