package com.loginPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(urlPatterns = "/deliveryBoyLogin")
public class DeliveryBoyLogin extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static Logger LOGGER = Logger.getLogger(DeliveryBoyLogin.class.getName());

  DatabaseConnection connection = DatabaseConnection.getInstance();
  Connection conn = connection.getConnection();

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
    response.getWriter().write("Checking!!");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    BufferedReader reader = request.getReader();
    StringBuilder stringbuilder = new StringBuilder();
    String singleLine;
    while ((singleLine = reader.readLine()) != null) {
      stringbuilder.append(singleLine);
    }
    reader.close();

    String username;
    String password;
    JSONObject resultjson = new JSONObject();
    try {
      JSONObject jsonValue = new JSONObject(stringbuilder.toString());
      LOGGER.log(Level.INFO, "Received login request for username: " + jsonValue.getString("username"));

      username = jsonValue.getString("username");
      password = jsonValue.getString("password");

      CommonMethodsForLogin commonmethods = new CommonMethodsForLogin();
      String partnerId = commonmethods.isIdExistsForDeliveryBoy(username, password);
      System.out.println("checkuser "+ partnerId);
      if (partnerId != null) {
        Cookie ckSessionID = new Cookie("sessionID", (commonmethods.sessionID));
        Cookie ckName = new Cookie("username", username);
        Cookie ckUserType = new Cookie("userType", "DeliveryBoy");
        Cookie ckPartnerId = new Cookie("parterId", partnerId);
        response.addCookie(ckSessionID);
        response.addCookie(ckName);
       response.addCookie(ckUserType);
       response.addCookie(ckPartnerId);
        resultjson.put("statusCode", 200);
        resultjson.put("message", "Login successful");
//        resultjson.put("partnerId", partnerId);
        response.getWriter().write(resultjson.toString());
        LOGGER.log(Level.INFO, "Login successful.");
      } else {
        resultjson.put("statusCode", 500);
        resultjson.put("error", "Invalid Login Details");
        response.getWriter().write(resultjson.toString());
        LOGGER.log(Level.WARNING, "Login Unsuccessful.");
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "An error occurred during login processing.");

    }
  }
}