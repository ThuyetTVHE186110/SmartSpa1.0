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

//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Lấy tên đăng nhập và mật khẩu từ form
//        String username = request.getParameter("txtUsername");
//        String password = request.getParameter("txtPassword");
//        String rememberMe = request.getParameter("rememberMe"); // Lấy giá trị "Remember Me"
//
//        // Khởi tạo DAO và cố gắng lấy tài khoản dựa trên thông tin xác thực
//        AccountDAO accountDAO = new AccountDAO();
//        Account account = null;
//
//        try {
//            account = accountDAO.getByUsernamePassword(username, password);
//        } catch (Exception e) {
//            // Xử lý ngoại lệ
//            e.printStackTrace();
//            request.setAttribute("error", "An unexpected error occurred. Please try again.");
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//            return;
//        }
//
//        // Kiểm tra xem tài khoản có tồn tại không
//        if (account != null) {
//            // Tạo session và thiết lập thuộc tính session
//            HttpSession session = request.getSession();
//            session.setAttribute("account", account);
//            session.setAttribute("loggedIn", true);
//            session.setMaxInactiveInterval(30 * 60); // Thời gian timeout của session
//
//            // Xử lý chức năng "Remember Me"
//            if ("on".equals(rememberMe)) {
//                // Tạo cookie để ghi nhớ tên đăng nhập và mật khẩu
//                Cookie usernameCookie = new Cookie("savedUsername", username);
//                Cookie passwordCookie = new Cookie("savedPassword", password);
//                usernameCookie.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
//                passwordCookie.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
//                response.addCookie(usernameCookie);
//                response.addCookie(passwordCookie);
//            } else {
//                // Xóa cookie nếu "Remember Me" không được chọn
//                Cookie usernameCookie = new Cookie("savedUsername", "");
//                Cookie passwordCookie = new Cookie("savedPassword", "");
//                usernameCookie.setMaxAge(0); // Hết hạn cookie
//                passwordCookie.setMaxAge(0); // Hết hạn cookie
//                response.addCookie(usernameCookie);
//                response.addCookie(passwordCookie);
//            }
//
//            // Redirect based on user role
//            String role = account.getRole();
//            switch (role) {
//                case "Admin":
//                    response.sendRedirect("index.html");
//                    break;
//                case "Manager":
//                    response.sendRedirect("index.html");
//                    break;
//                case "Staff":
//                    response.sendRedirect("index.html");
//                    break;
//                case "Customer":
//                    response.sendRedirect("index.jsp");
//                    break;
//                default:
//                    request.setAttribute("error", "Role not recognized. Please contact support.");
//                    request.getRequestDispatcher("login.jsp").forward(request, response);
//                    break;
//            }
//        } else {
//            request.setAttribute("error", "Login failed! Invalid username or password.");
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//        }
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get username and password from the form
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
//        String userType = request.getParameter("userType"); // Determine if it's customer or admin
        String rememberMe = request.getParameter("rememberMe"); // Lấy giá trị "Remember Me"

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
            session.setMaxInactiveInterval(30 * 60); // Session timeout

            // Xử lý chức năng "Remember Me"
            if ("on".equals(rememberMe)) {
                // Tạo cookie để ghi nhớ tên đăng nhập và mật khẩu
                Cookie usernameCookie = new Cookie("savedUsername", username);
                Cookie passwordCookie = new Cookie("savedPassword", password);
                usernameCookie.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
                passwordCookie.setMaxAge(60 * 60 * 24 * 30); // 30 ngày
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            } else {
                // Xóa cookie nếu "Remember Me" không được chọn
                Cookie usernameCookie = new Cookie("savedUsername", "");
                Cookie passwordCookie = new Cookie("savedPassword", "");
                usernameCookie.setMaxAge(0); // Hết hạn cookie
                passwordCookie.setMaxAge(0); // Hết hạn cookie
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }

// Chuyển hướng đến trang chính
            response.sendRedirect("HomeServlet");
        } else {
            // Thiết lập thông báo lỗi cho đăng nhập không hợp lệ
            request.setAttribute("error", "Login failed! Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
