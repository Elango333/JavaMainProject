package com.loginPackage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/filter/*")
public class AuthenticationFilter implements Filter {

  private static Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());

  public AuthenticationFilter() {
    // TODO Auto-generated constructor stub
  }

  public void destroy() {
    // TODO Auto-generated method stub
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    Cookie[] cookies = req.getCookies();
    CommonMethodsForLogin commonmethods = new CommonMethodsForLogin();
    LOGGER.log(Level.INFO, "AuthenticationFilter: Checking session cookies.");

    String IsUser = commonmethods.checkValidUser(cookies);

    if (IsUser.equals("Customer")) {
      LOGGER.log(Level.INFO, "AuthenticationFilter: User {0} authenticated. Allowing the request to proceed.");
      chain.doFilter(request, response);
    } else {
      LOGGER.log(Level.WARNING, "AuthenticationFilter: No valid session found. Blocking the request.");
      response.getWriter().write("Access denied: No valid session found.");
    }

  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    // TODO Auto-generated method stub
  }
}