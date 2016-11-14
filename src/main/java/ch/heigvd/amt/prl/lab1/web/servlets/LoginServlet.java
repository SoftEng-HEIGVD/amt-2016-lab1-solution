package ch.heigvd.amt.prl.lab1.web.servlets;

import ch.heigvd.amt.prl.lab1.models.User;
import ch.heigvd.amt.prl.lab1.services.IMessageService;
import ch.heigvd.amt.prl.lab1.services.ISecurityService;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handle the login
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@WebServlet(name = "LoginServlet", urlPatterns = AbstractServlet.SERVLET_PATTERN_LOGIN)
public class LoginServlet extends AbstractServlet {

  /**
   * JSP Attributes for login page
   */
  protected static final String JSP_ATTR_USERNAME = "username";
  protected static final String JSP_ATTR_PASSWORD = "password";
  
  @EJB
  private ISecurityService securityService;
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter(JSP_ATTR_USERNAME);
    String password = request.getParameter(JSP_ATTR_PASSWORD);

    User user = securityService.checkCredentials(username, password);

    if (user != null) {
      // Retrieve existing HTTP Session
      HttpSession session = request.getSession(false);

      /**
       * If a session already exists, we invalidate it to make sure there is a clean session for the current
       * connected user.
       */
      if (session != null) {
        session.invalidate();
      }

      // Finally we create a new session
      session = request.getSession(true);

      // We keep the user in the session
      session.setAttribute(SESSION_ATTR_USER, user);

      // We redirect to the users page
      redirect(request, response, PATH_USERS);
    } 
    else {
      // Configure the username for the form and send back the generic error
      request.setAttribute("username", username);
      error(request, response, PAGE_LOGIN, "general", "Wrong credentials!");
    }
  }
}
