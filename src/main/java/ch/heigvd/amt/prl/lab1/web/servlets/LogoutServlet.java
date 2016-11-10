package ch.heigvd.amt.prl.lab1.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handle the logout of a user
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@WebServlet(name = "LogoutServlet", urlPatterns = AbstractServlet.SERVLET_PATTERN_LOGOUT)
public class LogoutServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // Retrieve the current session and do not create a new session if there is no session
      HttpSession session = request.getSession(false);
      
      // Destroy the session
      if (session != null) {
        session.invalidate();
      }
      
      // Finally redirect the user to the index page
      redirect(request, response, PATH_INDEX);
    }
}
