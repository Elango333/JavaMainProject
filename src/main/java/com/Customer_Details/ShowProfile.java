package com.Customer_Details;
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

@WebServlet(urlPatterns = "/ShowProfile")
public class ShowProfile extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger(ShowProfile.class.getName());

  public ShowProfile() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    Customer customerFeatures = new Customer();
    JSONObject resultjson = new JSONObject();

    try {
      String username = request.getParameter("username");
      logger.log(Level.INFO, "Received username in the request: {0}", username);
      resultjson = customerFeatures.viewProfile(username);
      logger.log(Level.INFO, "ShowProfile result send to js from showProfile");
      response.getWriter().write(resultjson.toString());
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Exception in doPost in showProfile servlet");
      JSONObject errorJson = new JSONObject();
      try {
        errorJson.put("error", "Error processing JSON data in showProfile servlet");
        response.getWriter().write(errorJson.toString());
      } catch (JSONException e1) {
        logger.log(Level.SEVERE, "JSONException while writing errorJson in showProfile servlet");
        response.getWriter().write("Error processing JSON data");
      }
    }
  }
}