package ch.heigvd.amt.prl.lab1.web.filters;

import ch.heigvd.amt.prl.lab1.web.servlets.AbstractServlet;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.HttpMethod;

/**
 * This filter takes care to serve the JSP pages when they are requested. For the other requests, the filter
 * let the request going through the filter chain.
 *
 * This filter is applied in second and the order is configured in the web.xml
 *
 * @author Laurent Prevost, laurent.prevost@heig-vd.ch
 */
@WebFilter(filterName = "RouteFilter")
public class RouteFilter implements Filter {

  @Override
  public void init(FilterConfig config) throws ServletException {}

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
    throws ServletException, IOException {
    
    HttpServletRequest request = (HttpServletRequest) req;

    // Only consider GET requests
    if (HttpMethod.GET.equals(request.getMethod())) {
      // Extract the path from the request URI without the context root
      String path = request.getRequestURI().substring(request.getContextPath().length());

      // Fix the path to get the index page when the request path is empty, / or /index
      if (path.isEmpty() || "/".equals(path) || AbstractServlet.PATH_INDEX.equals(path)) {
        path = AbstractServlet.BASE_PATH_PAGES + AbstractServlet.PATH_INDEX;
      }

      // Check if a page is requested
      if (path.startsWith(AbstractServlet.BASE_PATH_PAGES)) {
        request.getRequestDispatcher(path).forward(req, resp);
        return;
      }
    }

    chain.doFilter(req, resp);
  }

  @Override
  public void destroy() {}
}
