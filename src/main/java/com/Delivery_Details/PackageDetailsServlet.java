package com.Delivery_Details;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.spi.Configurator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class PackageDetailsServlet
 */
@WebServlet(urlPatterns = "/PackageDetailsServlet")
public class PackageDetailsServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger(PackageDetailsServlet.class.getName());
  //	PropertyConfigurator.configure("/home/elango-zstk340/log4.properties");

  /**
   * @see HttpServlet#HttpServlet()
   */
  public PackageDetailsServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    // TODO Auto-generated method stub
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    BufferedReader reader = request.getReader();
    StringBuilder stringbuilder = new StringBuilder();
    String singleLine;
    int amount;
    int packageID;
    JSONObject jsonValue = null;
    while ((singleLine = reader.readLine()) != null) {
      stringbuilder.append(singleLine);
    }

    try {
      jsonValue = new JSONObject(stringbuilder.toString());
      String itemName = jsonValue.getString("itemName");
      int weight = jsonValue.getInt("weight");
      String typeOfDelivery = jsonValue.getString("typeOfDelivery");
      String description = jsonValue.getString("description");
      String paymentType = jsonValue.getString("paymentType");
      int receiverID = jsonValue.getInt("receiverID");
      String customerName = jsonValue.getString("customerName");
      logger.log(Level.INFO, "Received package details from JS " + receiverID);
      DeliveryMethods deliverymethod = new DeliveryMethods();
      PackageDetails pack = new PackageDetails();
      pack.setItemname(itemName);
      pack.setWeight(weight);
      pack.setDeliveryType(typeOfDelivery);
      pack.setDescription(description);
      pack.setPaymentType(paymentType);
      pack.setReceiverID(receiverID);
      pack.setCustomerName(customerName);

      String setPackageDetails = deliverymethod.setPackageDetails(pack);
      JSONObject packageValue = new JSONObject(setPackageDetails);
      amount = packageValue.getInt("amount");
      packageID = packageValue.getInt("packageID");
      JSONObject resultjson = new JSONObject();
      int setTrackingstatus = deliverymethod.setTrackingStatus(packageID, typeOfDelivery);
      if ((packageID > 0) && (setTrackingstatus > 0)) {
        resultjson.put("statusCode", 200);
        resultjson.put("paymentType", paymentType);
        resultjson.put("trackingCode", setTrackingstatus);
        resultjson.put("amount", amount);
        resultjson.put("message", "Successfully stored Package details");
        response.getWriter().write(resultjson.toString());
        logger.log(Level.INFO, "Package details stored successfully. PackageID: {0}");
      } else {
        resultjson.put("statusCode", 500);
        resultjson.put("Error", "Can't store package details");
        response.getWriter().write(resultjson.toString());
        logger.log(Level.WARNING, "Failed to store package details");
      }

    } catch (JSONException e) {
      logger.log(Level.SEVERE, "Error parsing JSON in packageDetails servlet");
    }
  }
  
  
  
}