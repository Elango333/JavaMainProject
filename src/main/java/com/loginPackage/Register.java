package com.loginPackage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.UserDetails.User;

@WebServlet(urlPatterns = "/Register")
public class Register extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static Logger LOGGER = Logger.getLogger(Register.class.getName());

  @Override
  public void init() throws ServletException {

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    User user = new User();
    CommonMethodsForLogin commonmethods = new CommonMethodsForLogin();
    String reader = (String) request.getAttribute("jsonObj");
    boolean checkUser = false;
    try {
      JSONObject jsonValue = new JSONObject(reader);
      String password = commonmethods.hashPassword(jsonValue.getString("password"));
      checkUser = commonmethods.idExistsForRegister(jsonValue.getString("username"), jsonValue.getString("password"));
      user.setName(jsonValue.getString("username"));
      user.setPassword(password);
      user.setEmail(jsonValue.getString("emailID"));
      user.setMobileNumber(jsonValue.getString("mobileNumber"));
      user.setAddress(jsonValue.getString("address"));
      LOGGER.log(Level.INFO, "Received registration request for user: " + jsonValue.getString("username"));
    } catch (JSONException e) {
      LOGGER.log(Level.SEVERE, "Error parsing JSON data in Register servlet");
    }

    if (checkUser) {
      response.getWriter().write("User already exists");
    } else {
      response.getWriter().write(commonmethods.storeRegisterDetails(user).toString());
    }
  }

  @Override
  public void destroy() {

  }
}