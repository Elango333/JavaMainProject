package com.Customer_Details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import com.UserDetails.User;
import com.loginPackage.AuthenticationFilter;
import com.loginPackage.ConstantFile;
import com.loginPackage.DatabaseConnection;

public class Customer extends User {
  private DatabaseConnection connection = DatabaseConnection.getInstance();
  private Connection conn = connection.getConnection();
  private static Logger LOGGER = Logger.getLogger(Customer.class.getName());
  JSONObject viewProfile(String username) throws Exception {
    JSONObject resultjson = new JSONObject();
    User user = new User();
    int orderCount = 0;
    try {
      PreparedStatement statement = conn.prepareStatement(ConstantFile.select_query_for_Customer_details);
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        user.setUserID(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setEmail(resultSet.getString(3));
        user.setAddress(resultSet.getString(4));
        user.setMobileNumber(resultSet.getString(5));
      }
      PreparedStatement smnt = conn.prepareStatement(ConstantFile.select_query_for_Customer_Ordercount);
      smnt.setInt(1, user.getUserID());
      ResultSet reSet = smnt.executeQuery();
      if (reSet.next()) {
        orderCount = reSet.getInt(1);
      }
      resultjson.put("userID", user.getUserID());
      resultjson.put("name", user.getName());
      resultjson.put("email_ID", user.getEmail());
      resultjson.put("address", user.getAddress());
      resultjson.put("mobile_Number", user.getMobileNumber());
      resultjson.put("orderCount", orderCount);
      LOGGER.log(Level.INFO, "Successfully send profile details to js");
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error executing SQL query in showProfile method");
    }
    return resultjson;
  }

}