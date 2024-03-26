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
import com.Customer_Details.ShowProfile;

@WebServlet(urlPatterns = "/TrackCourierServlet")
public class TrackCourierServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static Logger logger = Logger.getLogger(TrackCourierServlet.class.getName());
  /**
   * @see HttpServlet#HttpServlet()
   */
  public TrackCourierServlet() {
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
    DeliveryMethods deliverymethods = new DeliveryMethods();

    try {
      int trackID = Integer.parseInt(request.getParameter("trackID"));
      JSONObject resultjson = new JSONObject();
      resultjson = deliverymethods.trackCourier(trackID);
      response.getWriter().write(resultjson.toString());
      logger.log(Level.INFO, "Result send to JS from trackDetails");

    } catch (Exception e) {

      JSONObject errorJson = new JSONObject();

      try {
        errorJson.put("error", "Error processing JSON data");
        response.getWriter().write(errorJson.toString());
        logger.log(Level.INFO, "Result send to JS from TrackCourierServlet");
      } catch (JSONException e1) {
        logger.log(Level.SEVERE, "Error processing JSON data in TrackingCourierServlet");
        response.getWriter().write("Error processing JSON data");
      }
    }
  }
}