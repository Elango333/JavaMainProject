package com.loginPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.UserDetails.User;

class CommonMethodsForLogin {
  private static Logger logger = Logger.getLogger(CommonMethodsForLogin.class.getName());
  String sessionID;
  private DatabaseConnection connection = DatabaseConnection.getInstance();
  private Connection conn = connection.getConnection();

  
  //Check username & password ---> For login
  boolean idExists(String name, String password) {
    String checkSQL = "SELECT Password FROM Customer WHERE Name = ? LIMIT 1";
    boolean checkingForPresence = false;
    try (PreparedStatement stmnt = conn.prepareStatement(checkSQL)) {
      stmnt.setString(1, name);
      ResultSet resultSet = stmnt.executeQuery();
        if (resultSet.next()) {
          String hashedPasswordFromDB = resultSet.getString("Password");
          if (checkPassword(password, hashedPasswordFromDB)) {
            sessionID = String.valueOf(System.currentTimeMillis());
            User user = new User();
            user.setSessionID(sessionID);
            PreparedStatement stmt = conn.prepareStatement(ConstantFile.insert_query_for_Session);
            stmt.setString(1, sessionID);
            stmt.setString(2, name);
            stmt.setString(3, "Customer");
            stmt.execute();
            checkingForPresence = true;
          } else {
            checkingForPresence = false;
          }
        } else {
          checkingForPresence = false;
        }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error executing SQL query in idExists method");
      return false;
    }
    return checkingForPresence;
  }

  
  
  String isIdExistsForDeliveryBoy(String name, String password) {
	    String partnerId = null;
	    try (PreparedStatement stmnt = conn.prepareStatement(ConstantFile.select_query_for_DeliveryPartner)) {
	      stmnt.setString(1, name);
	      ResultSet resultSet = stmnt.executeQuery();
	        if (resultSet.next()) {
	          String DBPassword = resultSet.getString("Partner_password");
	          System.out.println("checkingggg" + DBPassword);
	          partnerId = resultSet.getInt("Partner_id") + "";
	          if (password.equals(DBPassword)) {
	            sessionID = String.valueOf(System.currentTimeMillis());
	            User user = new User();
	            user.setSessionID(sessionID);
	            PreparedStatement stmt = conn.prepareStatement(ConstantFile.insert_query_for_Session);
	            stmt.setString(1, sessionID);
	            stmt.setString(2, name);
	            stmt.setString(3, "DeliveryBoy");
	            stmt.execute();
	          } else {
	            partnerId = null;
	          }
	        } else {
	        	  partnerId = null;
	        }
	    } catch (SQLException e) {
	      logger.log(Level.SEVERE, "Error executing SQL query in idExists method");
	      return null;
	    }
	    return partnerId;
	  }

  
  
  
 // Check already user has account ---> For register
  boolean idExistsForRegister(String name, String password) {
    boolean checkingForPresence = false;
    try (PreparedStatement stmnt = conn.prepareStatement(ConstantFile.select_query_for_CheckAccount)) {
      stmnt.setString(1, name);
      try (ResultSet resultSet = stmnt.executeQuery()) {
        if (resultSet.next()) {
          checkingForPresence = true;
        } else {
          checkingForPresence = false;
        }
      }

    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error executing SQL query in idExistsForRegister method");
    }
    return checkingForPresence;
  }

  
  //Delete Session
  void deleteSessionID(String sessionID) {
    try (PreparedStatement stmnt = conn.prepareStatement(ConstantFile.select_query_for_Deletesession)) {
      stmnt.setString(1, sessionID);
      stmnt.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error executing SQL query in deleteSessionID");
    }
  }

  
 // Store User register details to DB
  JSONObject storeRegisterDetails(User user) {
    JSONObject resultjson = null;
    try {
      PreparedStatement statement = conn.prepareStatement(ConstantFile.insertSqlForRegister);
      statement.setString(1, user.getName());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getAddress());
      statement.setString(4, user.getMobileNumber());
      statement.setString(5, user.getPassword());
      statement.setInt(6, 1);
      int rowsAffected = statement.executeUpdate();
      resultjson = new JSONObject();

      if (rowsAffected > 0) {
        resultjson.put("statusCode", 200);
        resultjson.put("message", "Register successful");

      } else {
        resultjson.put("statusCode", 500);
        resultjson.put("error", "Failed to register");

      }

    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Exception occurred in storeRegisterDetails method");
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Error occurred in storeRegisterDetails method");
    }
    return resultjson;
  }

  
 // Check valid user 
  String checkValidUser(Cookie[] cookies) {
    String role = null;
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        Cookie ck = cookies[i];
        if (ck.getName().equals("sessionID")) {
          String sessionID = ck.getValue();
          DatabaseConnection connection = DatabaseConnection.getInstance();
          Connection conn = connection.getConnection();

          try {
            PreparedStatement pstmnt = conn.prepareStatement(ConstantFile.select_query_for_session);
            pstmnt.setString(1, sessionID);
            ResultSet rs = pstmnt.executeQuery();

            while (rs.next()) {
              String username = rs.getString(2);
              if (username != null) {
                PreparedStatement statement = conn.prepareStatement(ConstantFile.select_query_for_role);
                statement.setString(1, sessionID);
                ResultSet resultset = statement.executeQuery();
                if (resultset.next()) {
                  role = resultset.getString(1);
                }
              }
            }
          } catch (SQLException e) {
            logger.log(Level.SEVERE, "AuthenticationFilter: SQL Exception occurred in checkValidUser method");
          }
        }
      }
    }
    return role;
  }

  
 // Encrypt the password
  public String hashPassword(String plainTextPassword) {
    return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
  }

  
  // Decrypt the password
  public boolean checkPassword(String plainTextPassword, String hashedPassword) {
    return BCrypt.checkpw(plainTextPassword, hashedPassword);
  }
}