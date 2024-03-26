package com.Delivery_Details;


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
@WebServlet(urlPatterns = "/ReceiverServlet")
public class ReceiverServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger(ReceiverServlet.class.getName());

  public ReceiverServlet() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    String reader = (String) request.getAttribute("jsonObj");
    JSONObject jsonValue;
    try {
      jsonValue = new JSONObject(reader);
      String receiverName = jsonValue.getString("receiverName");
      String receiverEmailID = jsonValue.getString("receiverEmailID");
      String receiverMobileNumber = jsonValue.getString("receiverMobileNumber");
      String receiverAddress = jsonValue.getString("receiverAddress");
     

      DeliveryMethods deliverymethod = new DeliveryMethods();
      Receiver receiver = new Receiver();
      receiver.setName(receiverName);
      receiver.setEmail(receiverEmailID);
      receiver.setMobileNumber(receiverMobileNumber);
      receiver.setAddress(receiverAddress);

      int setDetails = deliverymethod.setReceiverDetails(receiver);
      JSONObject resultjson = new JSONObject();

      if (setDetails > 0) {
        resultjson.put("statusCode", 200);
        resultjson.put("receiverID", setDetails);
        resultjson.put("message", "Successfully stored receiver details");
        response.getWriter().write(resultjson.toString());
        logger.log(Level.INFO, "Receiver details stored successfully for: " + receiverName);
      } else {
        resultjson.put("statusCode", 500);
        resultjson.put("Error", "Can't store receiver details");
        response.getWriter().write(resultjson.toString());
        logger.log(Level.INFO, "Receiver details can't stored for: " + receiverName);
      }

    } catch (JSONException e) {
      logger.log(Level.SEVERE, "Error parsing JSON in ReceiverServlet");
    }
  }
}