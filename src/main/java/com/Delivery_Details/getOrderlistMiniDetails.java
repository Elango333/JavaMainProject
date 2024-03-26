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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class getOrderlistMiniDetails
 */
@WebServlet(urlPatterns = "/orderlistMiniDetails")
public class getOrderlistMiniDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static Logger logger = Logger.getLogger(getOrderlistMiniDetails.class.getName());  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getOrderlistMiniDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 BufferedReader reader = request.getReader();
		    StringBuilder stringbuilder = new StringBuilder();
		    String singleLine;
		    JSONObject jsonValue = null;
		    while ((singleLine = reader.readLine()) != null) {
		      stringbuilder.append(singleLine);
		    }

		    try {
		      jsonValue = new JSONObject(stringbuilder.toString());
		      int partnerId = jsonValue.getInt("partnerId");
		      String statusType = jsonValue.getString("statusType");
	      DeliveryMethods deliverymethod = new DeliveryMethods();
	      JSONArray jsonData = deliverymethod.getOrderlistMiniDetails(partnerId,statusType);
	      logger.log(Level.INFO, "Successfully get details from DB");
	      response.getWriter().write(jsonData.toString());
	}
    catch (JSONException e) {
        logger.log(Level.SEVERE, "Error parsing JSON in getOrderlistMiniDetails Servlet");
      }
	}
}
