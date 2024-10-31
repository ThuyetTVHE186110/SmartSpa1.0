/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.PersonDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Person;

/**
 *
 * @author Asus
 */
public class LoginServlet extends HttpServlet {

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
        // Get username, password, and userType from the form
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String rememberMe = request.getParameter("rememberMe"); // Get the "Remember Me" value
        String userType = request.getParameter("userType"); // Identify if the user is customer or admin/staff

        // Initialize DAO and attempt to retrieve the account based on the credentials
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getByUsernamePassword(username, password);

        try {
            account = accountDAO.getByUsernamePassword(username, password);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);  // Default to customer login page for errors
            return;
        }

        // Check if the account exists
        if (account != null) {
            // Get the user's role
            int roleID = account.getRole();  // Use `account.getRoleID()`
            String roleName = account.getRoleName();
            if ("admin".equals(userType)) {
                // Admin or Staff login: only roleID 1, 2, or 3 are allowed
                if (roleID == 1 || roleID == 2 || roleID == 3) {
                    // Create session and set session attributes
                    HttpSession session = request.getSession();
                    session.setAttribute("account", account);
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("roleName", roleName);
                    session.setMaxInactiveInterval(30 * 60); // Session timeout (30 minutes)

                    // Handle "Remember Me" functionality for admin login
                    if ("on".equals(rememberMe)) {
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

                    // Set success message for admin/staff
                    session.setAttribute("successMessage", "Admin/Staff login successful! Welcome, " + account.getPersonInfo().getName() + ".");
                    // Lấy thông tin Person từ PersonDAO dựa trên PersonID trong Account
                    PersonDAO personDAO = new PersonDAO();
                    Person person = personDAO.getPersonById(account.getPersonInfo().getId());
                    // Kiểm tra person có null không trước khi lưu vào session
                    if (person != null) {
                        session.setAttribute("person", person);  // Lưu person vào session
                    }
                    // Redirect to the admin/staff dashboard
                    response.sendRedirect("dashboard");
                } else {
                    // Not authorized for admin login
                    request.setAttribute("error", "You do not have permission to access this area.");
                    request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
                }
            } else {
                // Customer login: only roleID 4 is allowed
                if (roleID == 4) {
                    // Create session and set session attributes for customer
                    HttpSession session = request.getSession();
                    session.setAttribute("account", account);
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("roleName", roleName);
                    session.setMaxInactiveInterval(30 * 60); // Session timeout (30 minutes)

                    // Handle "Remember Me" functionality for customer login
                    if ("on".equals(rememberMe)) {
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

                    // Set success message for customer
                    session.setAttribute("successMessage", "Login successful! Welcome, " + account.getPersonInfo().getName() + ".");
                    // Redirect to the customer dashboard or index
                    response.sendRedirect("index");
                } else {
                    // Not authorized for customer login
                    request.setAttribute("error", "Invalid username or password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } else {
            // Set error message for invalid login
            request.setAttribute("error", "Login failed! Invalid username or password.");
            request.setAttribute("username", username);  // Retain the entered username
            if ("admin".equals(userType)) {
                request.getRequestDispatcher("adminLogin.jsp").forward(request, response); // Admin login failed
            } else {
                request.getRequestDispatcher("login.jsp").forward(request, response); // Customer login failed
            }
        }
    }
}
