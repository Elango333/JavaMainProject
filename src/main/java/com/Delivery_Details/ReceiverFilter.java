package com.Delivery_Details;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.json.JSONObject;

/**
 * Servlet Filter implementation class ReceiverFilter
 */
@WebFilter("/ReceiverServlet")
public class ReceiverFilter implements Filter {

  private static final Logger LOGGER = Logger.getLogger(ReceiverFilter.class.getName());

  /**
   * Default constructor.
   */
  public ReceiverFilter() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {
    // TODO Auto-generated method stub
  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws IOException, ServletException {
    String name;
    String emailID;
    String mobileNumber;
    String address;
    BufferedReader reader = request.getReader();
    StringBuilder stringBuilder = new StringBuilder();
    String singleLine;
    JSONObject resultJson = new JSONObject();

    try {
      while ((singleLine = reader.readLine()) != null) {
        stringBuilder.append(singleLine);
      }
      reader.close();

      JSONObject jsonValue = new JSONObject(stringBuilder.toString());
      name = jsonValue.getString("receiverName");
      emailID = jsonValue.getString("receiverEmailID");
      mobileNumber = jsonValue.getString("receiverMobileNumber");
      address = jsonValue.getString("receiverAddress");

      // MobileNumber check
      boolean mobileNumberLength = mobileNumber.length() >= 10;

      // Email check
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

      Pattern pattern = Pattern.compile(emailRegex);
      Matcher matcher = pattern.matcher(emailID);

      boolean mailCheck = matcher.matches();

      if (mobileNumberLength && mailCheck && (name != null) && (address != null)) {
        request.setAttribute("jsonObj", jsonValue.toString());
        chain.doFilter(request, response);
      } else {
        resultJson.put("statusCode", 500);
        resultJson.put("error", "Can't store receiver details: Invalid user input");
        response.getWriter().write(resultJson.toString());
        LOGGER.log(Level.SEVERE, "Invalid user input for receiver details");
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Exception occurred in ReceiverFilter");

    }

  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    // TODO Auto-generated method stub
  }

}