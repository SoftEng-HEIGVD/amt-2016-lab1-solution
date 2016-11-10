package ch.heigvd.amt.prl.lab1.web.servlets;

import static ch.heigvd.amt.prl.lab1.web.servlets.AbstractServlet.PAGE_ERROR_404;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to handle all the GET requests for /pages/*
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@WebServlet(name = "IndexServlet", urlPatterns = { AbstractServlet.SERVLET_PATTERN_PAGES })
public class PagesServlet extends AbstractServlet {
  private static final Logger LOG = Logger.getLogger(PagesServlet.class.getSimpleName());

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Retrieve the page to show
    String page = getPage(request.getRequestURI().substring(request.getContextPath().length()));
    
    LOG.info(String.format("The following page was requested %s for URI %s", page, request.getRequestURI()));

    // Do the routing for all GET requests on the servlets
    forward(request, response, page);
  }
  
  /**
   * Retrieve a page from the request path
   * 
   * @param requestPath The request path from the request
   * @return The page path found, or page error 404
   */
  private String getPage(String requestPath) {
    String[] pathTokens = requestPath.split("/");

    // Prepare the field name for the page to find
    String fieldName = "PAGE_" + pathTokens[pathTokens.length - 1].toUpperCase();
    
    try {
      // We use the java reflexion to retrieve the page path
      return (String) AbstractServlet.class.getDeclaredField(fieldName).get(this);
    }
    catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
      LOG.info(String.format("Page not found %s", fieldName));
      return PAGE_ERROR_404;
    }
  }    
}
