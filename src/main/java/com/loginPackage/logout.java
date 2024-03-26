package com.loginPackage;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/logout")
public class logout extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(logout.class.getName());

    public logout() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
//        String sessionID = null;
//        CommonMethodsForLogin methods = new CommonMethodsForLogin();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionID")) {
//                    sessionID = cookie.getValue();
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    logger.info("Session ID cookie deleted");

                }
                if (cookie.getName().equals("username")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
//                    methods.deleteSessionID(sessionID);
                    logger.info("Username cookie deleted");
                }
                if (cookie.getName().equals("userType")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
//                    methods.deleteSessionID(sessionID);
                    logger.info("UserType cookie deleted");
                }
            }
        }
    }
}
