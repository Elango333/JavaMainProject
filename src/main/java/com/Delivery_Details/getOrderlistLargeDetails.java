package com.Delivery_Details;

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
 * Servlet implementation class getOrderlistLargeDetails
 */
@WebServlet(urlPatterns = "/orderlistLargeDetails")
public class getOrderlistLargeDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static Logger logger = Logger.getLogger(getOrderlistLargeDetails.class.getName());  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getOrderlistLargeDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int packId = Integer.parseInt(request.getParameter("packId"));
		 try {
			  DeliveryMethods deliverymethod = new DeliveryMethods();
		      JSONObject jsonData = deliverymethod.getOrderlistLargeDetails(packId);
		      logger.log(Level.INFO, "Successfully get details from DB for getOrderlistLargeDetails serlvet");
		      response.getWriter().write(jsonData.toString());
		}
	    catch (JSONException e) {
	        logger.log(Level.SEVERE, "Error parsing JSON in getOrderlistLargeDetails Servlet");
	      }
		}
	
}
