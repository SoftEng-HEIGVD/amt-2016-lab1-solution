package ch.heigvd.amt.prl.lab1.web.filters;

import ch.heigvd.amt.prl.lab1.web.servlets.AbstractServlet;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter to authorize the user to access private part of the web application. When denied, the user is
 * redirected to the login screen.
 * 
 * There is also a check for public pages that should not be accessed by authenticated users. In this case,
 * the user is redirected to the index page.
 * 
 * This filter is applied first and the order is configured in the web.xml
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {
  /**
   * The patterns to authorize public access. By default, all the URI are blocked.
   */
  private static final List<String> PUBLIC_PATTERNS = new LinkedList<>();
  static {
    // All static content is public
    PUBLIC_PATTERNS.add("^" + AbstractServlet.BASE_PATH_STATIC + "/.*");
    
    // Register and login actions are public
    PUBLIC_PATTERNS.add("^" + AbstractServlet.SERVLET_PATTERN_LOGIN + "$");
    PUBLIC_PATTERNS.add("^" + AbstractServlet.SERVLET_PATTERN_REGISTER + "$");

    // Register and login pages are public
    PUBLIC_PATTERNS.add("^" + AbstractServlet.PATH_LOGIN + "$");
    PUBLIC_PATTERNS.add("^" + AbstractServlet.PATH_REGISTER + "$");
    
    // Index page is public (through empty url, / or /index
    PUBLIC_PATTERNS.add("^" + AbstractServlet.PATH_INDEX + "$");
    PUBLIC_PATTERNS.add("^/$");
    PUBLIC_PATTERNS.add("^$");

    // API /api/* is public
    PUBLIC_PATTERNS.add("^" + AbstractServlet.BASE_PATH_API + "/.*");
  }
  
  /**
   * Once authenticated, we want to forbid the access to few pages
   */
  private static final List<String> PRIVATE_FORBIDDEN_WHEN_AUTHENTICATED_PATTERNS = new LinkedList<>();
  static {
    // Register and login actions must not be accessed once authenticated
    PRIVATE_FORBIDDEN_WHEN_AUTHENTICATED_PATTERNS.add("^" + AbstractServlet.SERVLET_PATTERN_LOGIN + "$");
    PRIVATE_FORBIDDEN_WHEN_AUTHENTICATED_PATTERNS.add("^" + AbstractServlet.SERVLET_PATTERN_REGISTER + "$");

    // Register and login pages must not be accessed once authenticated
    PRIVATE_FORBIDDEN_WHEN_AUTHENTICATED_PATTERNS.add("^" + AbstractServlet.PATH_LOGIN + "$");
    PRIVATE_FORBIDDEN_WHEN_AUTHENTICATED_PATTERNS.add("^" + AbstractServlet.PATH_REGISTER + "$");
  }
  
  @Override
  public void init(FilterConfig config) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    
    // Retrieve the path part after the context root of the URL
    String path = request.getRequestURI().substring(request.getContextPath().length());

    // Retrieve the current HTTP session
    HttpSession session = request.getSession(false);

    // Do the authorization for authenticated users
    if (session != null && session.getAttribute(AbstractServlet.SESSION_ATTR_USER) != null) {
      // Evaluate all the forbidden paths that are not accessible by authenticated users
      for (String forbiddenPattern : PRIVATE_FORBIDDEN_WHEN_AUTHENTICATED_PATTERNS) {
        if (path.matches(forbiddenPattern)) {
          // Redirect the user to the login page
          response.sendRedirect(request.getContextPath() + AbstractServlet.PATH_INDEX);
          return;
        }
      }

      // User can access to the requested URI
      chain.doFilter(req, resp);
    }
    
    // Do checks for non-authenticated users
    else {
      // Evaluate all the public paths to let the request going through the filter chain
      for (String publicPattern : PUBLIC_PATTERNS) {
        if (path.matches(publicPattern)) {
          chain.doFilter(req, resp);
          return;
        }
      }

      // Redirect the user to the login page
      response.sendRedirect(request.getContextPath() + AbstractServlet.PATH_LOGIN);
    }
  }

  @Override
  public void destroy() {}
}
