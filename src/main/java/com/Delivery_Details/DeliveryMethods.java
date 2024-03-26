package com.Delivery_Details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loginPackage.ConstantFile;
import com.loginPackage.DatabaseConnection;

public class DeliveryMethods {
  private static final Logger logger = Logger.getLogger(DeliveryMethods.class.getName());
  DatabaseConnection connection = DatabaseConnection.getInstance();
  Connection conn = connection.getConnection();
  
  //Store Receiver Details to DB
  public int setReceiverDetails(Receiver receiver) {
    int receiverID = 0;
    try {
      PreparedStatement statement = conn.prepareStatement(ConstantFile.insert_query_for_Receiver_details);
      statement.setString(1, receiver.getName());
      statement.setString(2, receiver.getAddress());
      statement.setString(3, receiver.getEmail());
      statement.setString(4, receiver.getMobileNumber());
      int rowsAffected = statement.executeUpdate();
      if (rowsAffected > 0) {
        PreparedStatement stmnt = conn.prepareStatement(ConstantFile.select_query_for_ReceiverID);
        stmnt.setString(1, receiver.getEmail());
        ResultSet resultSet = stmnt.executeQuery();
        if (resultSet.next()) {
          receiverID = resultSet.getInt(1);
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Exception occurred in setReceiverDetails method", e);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Error occurred in setReceiverDetails method", e);
    }
    return receiverID;
  }

 // Store Package Details to DB
  public String setPackageDetails(PackageDetails pack) {
    int packageID = 0;
    int amount = 0;
    int customerID = 0;
    JSONObject json = new JSONObject();
    if (pack.getDeliveryType().equals("NORMALDELIVERY")) {
      NormalDelivery normaldelivery = new NormalDelivery();
      normaldelivery.setAmount(pack.getWeight());
      amount = normaldelivery.getAmount();
    } else {
      SpeedDelivery speeddelivery = new SpeedDelivery();
      speeddelivery.setAmount(pack.getWeight());
      amount = speeddelivery.getAmount();
    }

    try {
      PreparedStatement stmt = conn.prepareStatement(ConstantFile.select_query_for_customer_id);
      stmt.setString(1, pack.getCustomerName());
      logger.log(Level.INFO, "Successfully getCustomerName" +  pack.getCustomerName());
      ResultSet rs = stmt.executeQuery();
      if(rs.next()) {
    	 customerID =  rs.getInt(1);
    	  logger.log(Level.INFO, "Successfully get customerID" + customerID);
      }
      
      PreparedStatement stmnt2 = conn.prepareStatement(ConstantFile.select_query_for_deliveryPartnerDetails);
      int partnerId = 0;
      int orderCount = 0;
      ResultSet resultSet2 = stmnt2.executeQuery();
      while(resultSet2.next()) {
    	  partnerId = resultSet2.getInt(1);
    	  orderCount = resultSet2.getInt(2);
      }
      logger.log(Level.INFO, "Successfully get data from deliveryPartner");
      
      PreparedStatement statement = conn.prepareStatement(ConstantFile.insert_query_for_Package_details);
      statement.setString(1, pack.getItemname());
      statement.setString(2, pack.getDeliveryType());
      statement.setString(3, pack.getPaymentType());
      statement.setString(4, pack.getDescription());
      statement.setInt(5, amount);
      statement.setInt(6, pack.getWeight());
      java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
      statement.setDate(7,currentDate);
      statement.setInt(8, pack.getReceiverID());
      statement.setInt(9, partnerId);
      int rowsAffected = statement.executeUpdate();
      if (rowsAffected > 0) {
    	  logger.log(Level.INFO, "Successfully stored data in Package_details");
        PreparedStatement stmnt = conn.prepareStatement(ConstantFile.select_query_for_PackageID);
        stmnt.setInt(1, pack.getReceiverID());
        ResultSet resultSet = stmnt.executeQuery();
        if (resultSet.next()) {
          packageID = resultSet.getInt(1);
          logger.log(Level.INFO, "Successfully get PackageID" + packageID);
        }
      }
      PreparedStatement stmnt = conn.prepareStatement(ConstantFile.insert_query_for_Customer_orderlist);
      stmnt.setInt(1, customerID);
      stmnt.setInt(2,  packageID);
      int row = stmnt.executeUpdate();
      if(row > 0){
    	  logger.log(Level.INFO, "Successfully stored data in Customer_Orderlist");
          json.put("packageID", packageID);
          json.put("amount", amount);
      }
  
      
      PreparedStatement stmnt3 = conn.prepareStatement(ConstantFile.insert_query_for_deliveryPartner_deliveryList);
      stmnt3.setInt(1, partnerId);
      stmnt3.setInt(2,  packageID);
      stmnt3.setString(3,  "SHIPPED");
      int row2 = stmnt3.executeUpdate();
      if(row2 > 0) {
    	  logger.log(Level.INFO, "Successfully stored data in deliveryPartner_develiveryList");  
      }
      
      
      PreparedStatement stmnt4 = conn.prepareStatement(ConstantFile.insert_query_for_deliveryPartner);
      stmnt4.setInt(1, orderCount+1);
      stmnt4.setInt(2,  partnerId);
      int row3 = stmnt4.executeUpdate();
      if(row3 > 0) {
    	  logger.log(Level.INFO, "Successfully stored data in deliveryPartner");  
      }
      
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Exception occurred in setPackageDetails method", e);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Error occurred in setPackageDetails method", e);
    }
    return json.toString();
  }

 // Store Tracking Details to DB
  public int setTrackingStatus(int packageID, String deliveryType) {
    int trackingCode = 0;
    try {
      PreparedStatement statement = conn.prepareStatement(ConstantFile.insert_query_for_Tracking_status);
      statement.setInt(1, packageID);
      statement.setString(2, "SHIPPED");
      if (deliveryType.equals("NORMALDELIVERY")) {
        NormalDelivery normaldelivery = new NormalDelivery();
        normaldelivery.setDeliveryDate();
        statement.setDate(3, new java.sql.Date(normaldelivery.getDeliveryDate()));
      } else {
        SpeedDelivery speeddelivery = new SpeedDelivery();
        speeddelivery.setDeliveryDate();
        statement.setDate(3, new java.sql.Date(speeddelivery.getDeliveryDate()));
      }
      int rowsAffected = statement.executeUpdate();
      if (rowsAffected > 0) {
        PreparedStatement stmnt = conn.prepareStatement(ConstantFile.select_query_for_TrackingCode);
        stmnt.setInt(1, packageID);
        ResultSet resultSet = stmnt.executeQuery();
        if (resultSet.next()) {
          trackingCode = resultSet.getInt(1);
        }
      }

    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Exception occurred in setTrackingStatus method", e);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Error occurred in setTrackingStatus method", e);
    }
    return trackingCode;
  }

  
  //Track Courier
  JSONObject trackCourier(int trackID) throws JSONException {
    JSONObject resultjson = new JSONObject();
    try {
      PreparedStatement statement = conn.prepareStatement(ConstantFile.select_query_for_Tracking_details);
      statement.setInt(1, trackID);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        resultjson.put("Tracking_code", resultSet.getInt(1));
        resultjson.put("Delivery_status", resultSet.getString(2));
        resultjson.put("Delivery_date", resultSet.getDate(3));
        resultjson.put("Item", resultSet.getString(4));
        resultjson.put("Type_of_delivery", resultSet.getString(5));
        resultjson.put("Type_of_payment", resultSet.getString(6));
        resultjson.put("Description", resultSet.getString(7));
        resultjson.put("Amount", resultSet.getInt(8));
        resultjson.put("Weight", resultSet.getInt(9));
        resultjson.put("Ordered_date", resultSet.getDate(10));
        resultjson.put("Name", resultSet.getString(11));
        resultjson.put("Address", resultSet.getString(12));
        resultjson.put("EmailId", resultSet.getString(13));
        resultjson.put("Phone_number", resultSet.getString(14));
        resultjson.put("statusCode", 200);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "SQL Exception occurred in trackCourier method", e);
    }
    return resultjson;
  }
  
  JSONArray getOrderlistMiniDetails(int partnerId, String statusType) throws JSONException {
	  JSONArray jsonArray = new JSONArray();
	    try {
	        PreparedStatement statement = conn.prepareStatement(ConstantFile.select_query_for_orderlistMiniDetails);
	        statement.setInt(1, partnerId);
	        statement.setString(2, statusType);
	        ResultSet resultSet = statement.executeQuery();
	       
	        while (resultSet.next()) {
	          JSONObject resultjson = new JSONObject();
	          resultjson.put("Pack_Id", resultSet.getInt(1));
	          resultjson.put("Delivery_Status", resultSet.getString(2));
	          resultjson.put("Delivery_Date", resultSet.getDate(3));
	          resultjson.put("Amount", resultSet.getInt(4));
	          resultjson.put("Weight", resultSet.getInt(5));
	          resultjson.put("Receiver_Name", resultSet.getString(6));
	          jsonArray.put(resultjson);
	        }
	    } catch (SQLException e) {
	      logger.log(Level.SEVERE, "SQL Exception occurred in getOrderlistMiniDetails method", e);
	    }
	    return jsonArray;
  }
  
  JSONObject getOrderlistLargeDetails(int packID) throws JSONException {
	  JSONObject resultjson = new JSONObject();
	    try {
	      PreparedStatement statement = conn.prepareStatement(ConstantFile.select_query_for_orderlistLargeDetails);
	      statement.setInt(1, packID);
	      ResultSet resultSet = statement.executeQuery();

	      while (resultSet.next()) {
	        resultjson.put("Delivery_status", resultSet.getString(1));
	        resultjson.put("Delivery_date", resultSet.getDate(2));
	        resultjson.put("Item", resultSet.getString(3));
	        resultjson.put("Type_of_delivery", resultSet.getString(4));
	        resultjson.put("Type_of_payment", resultSet.getString(5));
	        resultjson.put("Description", resultSet.getString(6));
	        resultjson.put("Amount", resultSet.getInt(7));
	        resultjson.put("Weight", resultSet.getInt(8));
	        resultjson.put("Ordered_date", resultSet.getDate(9));
	        resultjson.put("Name", resultSet.getString(10));
	        resultjson.put("Address", resultSet.getString(11));
	        resultjson.put("EmailId", resultSet.getString(12));
	        resultjson.put("Phone_number", resultSet.getString(13));
	        resultjson.put("Partner_Id", resultSet.getInt(14));
	        resultjson.put("statusCode", 200);
	      }
	    } catch (SQLException e) {
	      logger.log(Level.SEVERE, "SQL Exception occurred in getOrderlistLargeDetails method", e);
	    }
	    return resultjson;
  }
}