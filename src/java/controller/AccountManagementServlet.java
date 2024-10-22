/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;

/**
 *
 * @author PC
 */
public class AccountManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra xem có session hay không
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            // Chưa đăng nhập, chuyển hướng về trang đăng nhập
            response.sendRedirect("adminLogin.jsp");
            return;
        }
        // Lấy danh sách tài khoản từ DAO
        AccountDAO accountDAO = new AccountDAO();
        List<Account> accounts = accountDAO.getAllStaffAccounts();  // Lấy danh sách từ database

        // Đặt danh sách accounts vào request
        request.setAttribute("accounts", accounts);

        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("accountManagement.jsp").forward(request, response);
    }

}
