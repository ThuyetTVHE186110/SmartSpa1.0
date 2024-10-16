/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author Asus
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check for saved cookies
        Cookie[] cookies = request.getCookies();
        String savedUsername = null;
        String savedPassword = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("savedUsername".equals(cookie.getName())) {
                    savedUsername = cookie.getValue();
                } else if ("savedPassword".equals(cookie.getName())) {
                    savedPassword = cookie.getValue();
                }
            }
        }

        // Set saved values as request attributes to display in the login form
        request.setAttribute("savedUsername", savedUsername);
        request.setAttribute("savedPassword", savedPassword);

        // Forward to the login page (or your login JSP)
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get username and password from the form
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String rememberMe = request.getParameter("rememberMe"); // Get the "Remember Me" value

        // Initialize DAO and attempt to retrieve the account based on the credentials
        AccountDAO accountDAO = new AccountDAO();
        Account account = null;

        try {
            account = accountDAO.getByUsernamePassword(username, password);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Check if the account exists
        if (account != null) {
            // Create session and set session attributes
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            session.setAttribute("loggedIn", true);
            session.setMaxInactiveInterval(30 * 60); // Session timeout (30 minutes)

            // Handle "Remember Me" functionality
            if ("on".equals(rememberMe)) {
                // Create cookies to remember username (not password for security reasons)
                Cookie usernameCookie = new Cookie("savedUsername", username);
                usernameCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
                usernameCookie.setHttpOnly(true); // Make it HTTP-only
                usernameCookie.setSecure(request.isSecure()); // Set Secure flag if HTTPS
                response.addCookie(usernameCookie);
            } else {
                // Delete cookies if "Remember Me" is not checked
                Cookie usernameCookie = new Cookie("savedUsername", "");
                usernameCookie.setMaxAge(0); // Expire the cookie
                response.addCookie(usernameCookie);
            }

            // Redirect to the main page after successful login
            response.sendRedirect("index.jsp");
        } else {
            // Set error message for invalid login
            request.setAttribute("error", "Login failed! Invalid username or password.");
            request.setAttribute("username", username);  // Retain the entered username
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}
