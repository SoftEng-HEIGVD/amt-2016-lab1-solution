package ch.heigvd.amt.prl.lab1.web.servlets;

import ch.heigvd.amt.prl.lab1.dto.FieldsErrorsDto;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract servlet to share the paths and various other things
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
public abstract class AbstractServlet extends HttpServlet {
  /**
   * Root folder for the JSP pages
   */
  public static final String JSP_FOLDER = "/WEB-INF/pages/";
  
  /**
   * Various JSP pages
   */
  public static final String PAGE_INDEX = JSP_FOLDER + "index.jsp";
  public static final String PAGE_LOGIN = JSP_FOLDER + "login.jsp";
  public static final String PAGE_PROFILE = JSP_FOLDER + "profile.jsp";
  public static final String PAGE_REGISTER = JSP_FOLDER + "register.jsp";
  public static final String PAGE_USERS = JSP_FOLDER + "users.jsp";
  
  /**
   * Error JSP pages
   */
  public static final String ERRORS_FOLDER = JSP_FOLDER + "errors/";
  public static final String PAGE_ERROR_404 = ERRORS_FOLDER + "404.jsp";
  
  /**
   * Servlet patterns (used in the servlet annotations)
   */
  public static final String SERVLET_ACTION_PATTERN = "/do/";
  public static final String SERVLET_PATTERN_PAGES = "/pages/*";
  public static final String SERVLET_PATTERN_LOGIN = SERVLET_ACTION_PATTERN + "login";
  public static final String SERVLET_PATTERN_LOGOUT = SERVLET_ACTION_PATTERN + "logout";
  public static final String SERVLET_PATTERN_PROFILE = SERVLET_ACTION_PATTERN + "profile";
  public static final String SERVLET_PATTERN_REGISTER = SERVLET_ACTION_PATTERN + "register";
  
  /**
   * URL Paths (used for redirections)
   */
  public static final String BASE_PATH_ACTIONS = "/do";
  public static final String BASE_PATH_API = "/api";
  public static final String BASE_PATH_STATIC = "/static";
  public static final String BASE_PATH_PAGES = "/pages";
  public static final String PATH_INDEX = "/index";
  public static final String PATH_LOGIN = BASE_PATH_PAGES + "/login";
  public static final String PATH_REGISTER = BASE_PATH_PAGES + "/register";
  public static final String PATH_USERS = BASE_PATH_PAGES + "/users";

  /**
   * JSP Attributes
   */
  public static final String JSP_ATTR_ERROR = "error";
  public static final String JSP_ATTR_ERRORS = "errors";
  
  /**
   * HTTP Session attributes
   */
  public static final String SESSION_ATTR_USER = "user";
 
  /**
   * Forward the request to the JSP page
   * 
   * @param request The HTTP request
   * @param response The HTTP response
   * @param page The page to redirect
   * @throws ServletException When an error occurs related to the servlet execution
   * @throws IOException When an error occurs related to file operations
   */
  protected void forward(HttpServletRequest request, HttpServletResponse response, String page) 
    throws ServletException, IOException {
    
    request.getRequestDispatcher(page).forward(request, response);
  }
  
  /**
   * Send a redirection to the browser
   * 
   * @param request The HTTP request
   * @param response The HTTP response
   * @param path The path for the redirection
   * @throws IOException When an error occurs with the network
   */
  protected void redirect(HttpServletRequest request, HttpServletResponse response, String path) 
    throws IOException {
    
    response.sendRedirect(request.getContextPath() + path);
  }
  
  /**
   * Dispatch an error
   * 
   * @param request The HTTP request
   * @param response The HTTP response
   * @param page The page to redirect
   * @param errorMessage The error message to set for the page rendering
   * @throws ServletException When an error occurs related to the servlet execution
   * @throws IOException When an error occurs related to file operations
   */
  protected void error(HttpServletRequest request, HttpServletResponse response, String page, 
    String errorMessage) throws ServletException, IOException {
    
    request.setAttribute(JSP_ATTR_ERROR, errorMessage);
    forward(request, response, page);
  }
  
  /**
   * Dispatch an error
   * 
   * @param request The HTTP request
   * @param response The HTTP response
   * @param page The page to redirect
   * @param errors The errors to set for the page rendering
   * @throws ServletException When an error occurs related to the servlet execution
   * @throws IOException When an error occurs related to file operations
   */
  protected void error(HttpServletRequest request, HttpServletResponse response, String page, 
    FieldsErrorsDto errors) throws ServletException, IOException {
    
    request.setAttribute(JSP_ATTR_ERRORS, errors);
    forward(request, response, page);
  }
  
  /**
   * Dispatch an error
   * 
   * @param request The HTTP request
   * @param response The HTTP response
   * @param page The page to redirect
   * @param jspAttributeName The name of the error attribute
   * @param errors The errors to set for the page rendering
   * @throws ServletException When an error occurs related to the servlet execution
   * @throws IOException When an error occurs related to file operations
   */
  protected void error(HttpServletRequest request, HttpServletResponse response, String page, 
    String jspAttributeName, FieldsErrorsDto errors) throws ServletException, IOException {
    
    request.setAttribute(jspAttributeName, errors);
    forward(request, response, page);
  }  
}
