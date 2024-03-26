package com.loginPackage;

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

@WebFilter("/Register")
public class NewRegistrationFilter implements Filter {

  private static Logger logger = Logger.getLogger(NewRegistrationFilter.class.getName());

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws IOException, ServletException {
    String password;
    String username;
    String emailID;
    String address;
    String mobileNumber;
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
      username = jsonValue.getString("username");
      password = jsonValue.getString("password");
      emailID = jsonValue.getString("emailID");
      address = jsonValue.getString("address");
      mobileNumber = jsonValue.getString("mobileNumber");

      if ((password == null) || (username == null) || (address == null) || (emailID == null) || (mobileNumber == null)) {
        resultJson.put("statusCode", 500);
        resultJson.put("error", "Can't register: User input is null");
        response.getWriter().write(resultJson.toString());

        logger.log(Level.SEVERE, "Can't register: User input is null");
        return;
      }

      // Minimum 8 characters
      boolean minLengthCondition = password.length() >= 8;

      // At least 1 Uppercase letter
      boolean uppercaseCondition = Pattern.compile("[A-Z]").matcher(password).find();

      // At least 1 number
      boolean numberCondition = Pattern.compile("\\d").matcher(password).find();

      // MobileNumber check
      boolean mobileNumberLength = mobileNumber.length() >= 10;

      // Email check
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

      Pattern pattern = Pattern.compile(emailRegex);
      Matcher matcher = pattern.matcher(emailID);
      boolean mailCheck = matcher.matches();

      if (minLengthCondition && uppercaseCondition && numberCondition && mobileNumberLength && mailCheck) {
        request.setAttribute("jsonObj", jsonValue.toString());
        chain.doFilter(request, response);
      } else {
        resultJson.put("statusCode", 500);
        resultJson.put("error", "Can't register: Invalid user input");
        response.getWriter().write(resultJson.toString());

        logger.log(Level.WARNING, "Can't register: Invalid user input");
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Error occurred! in NewRegisterFilter");

    }
  }

  @Override
  public void destroy() {}
}