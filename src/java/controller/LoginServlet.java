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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String rememberMe = request.getParameter("rememberMe");
        String userType = request.getParameter("userType");

        AccountDAO accountDAO = new AccountDAO();
        Account account = null;

        try {
            account = accountDAO.getByUsernamePassword(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (account != null) {
            // Check if the account is suspended
            if ("Suspended".equalsIgnoreCase(account.getStatus())) {
                request.setAttribute("error", "Your account is suspended.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            int roleID = account.getRole();
            String roleName = account.getRoleName();

            if ("admin".equals(userType) && (roleID == 1 || roleID == 2 || roleID == 3)) {
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                session.setAttribute("loggedIn", true);
                session.setAttribute("roleName", roleName);
                session.setMaxInactiveInterval(30 * 60);

                   
                    // Lấy thông tin Person từ PersonDAO dựa trên PersonID trong Account
                    PersonDAO personDAO = new PersonDAO();
                    Person person = personDAO.getPersonByAccount(username, password);
                    // Kiểm tra person có null không trước khi lưu vào session
                    if (person != null) {
                        session.setAttribute("person", person);  // Lưu person vào session
                    }
                if ("on".equals(rememberMe)) {
                    Cookie usernameCookie = new Cookie("savedUsername", username);
                    usernameCookie.setMaxAge(60 * 60 * 24 * 30);
                    usernameCookie.setHttpOnly(true);
                    usernameCookie.setSecure(request.isSecure());
                    response.addCookie(usernameCookie);
                } else {
                    Cookie usernameCookie = new Cookie("savedUsername", "");
                    usernameCookie.setMaxAge(0);
                    response.addCookie(usernameCookie);
                }

                session.setAttribute("successMessage", "Admin/Staff login successful! Welcome, " + account.getPersonInfo().getName() + ".");
                response.sendRedirect("dashboard");
            } else if ("customer".equals(userType) && roleID == 4) {
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                session.setAttribute("loggedIn", true);
                session.setAttribute("roleName", roleName);
                session.setMaxInactiveInterval(30 * 60);

                if ("on".equals(rememberMe)) {
                    Cookie usernameCookie = new Cookie("savedUsername", username);
                    usernameCookie.setMaxAge(60 * 60 * 24 * 30);
                    usernameCookie.setHttpOnly(true);
                    usernameCookie.setSecure(request.isSecure());
                    response.addCookie(usernameCookie);
                } else {
                    Cookie usernameCookie = new Cookie("savedUsername", "");
                    usernameCookie.setMaxAge(0);
                    response.addCookie(usernameCookie);
                }
                PersonDAO personDAO = new PersonDAO();
                    Person person = personDAO.getPersonByAccount(username, password);
                    // Kiểm tra person có null không trước khi lưu vào session
                    if (person != null) {
                        session.setAttribute("person", person);  // Lưu person vào session
                    }
                session.setAttribute("successMessage", "Login successful! Welcome, " + account.getPersonInfo().getName() + ".");
                response.sendRedirect("index");
            } else {
                
                request.setAttribute("error", "Invalid role or user type.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Login failed! Invalid username or password.");
            request.setAttribute("username", username);
            if ("admin".equals(userType)) {
                request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }

}
