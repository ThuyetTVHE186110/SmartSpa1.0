/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.PersonDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Person;

/**
 *
 * @author PC
 */
public class CustomerProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session hiện tại và thông tin account
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();

        // Lấy các giá trị từ form
        String fullName = request.getParameter("fullName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        // Cập nhật thông tin cá nhân của person
        person.setName(fullName);
        person.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth));  // Chuyển từ String sang Date
        person.setGender(gender.charAt(0));  // 'M', 'F', or 'O'
        person.setPhone(phone);
        person.setEmail(email);
        person.setAddress(address);

        PersonDAO personDAO = new PersonDAO();
        AccountDAO accountDAO = new AccountDAO();

        try {
            // Cập nhật thông tin cá nhân của Person trong DB
            personDAO.updatePerson(person);

            // Nếu password không trống, cập nhật password cho account
            if (password != null && !password.isEmpty()) {
                accountDAO.updatePassword(account.getUsername(), password);
                account.setPassword(password);  // Cập nhật password trong session
            }

            // Cập nhật lại session
            session.setAttribute("person", person);
            session.setAttribute("account", account);

            // Chuyển hướng về trang profile với thông báo thành công
            response.sendRedirect("customerProfile");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating your profile.");
            request.getRequestDispatcher("customerProfile").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang profile với thông tin hiện tại
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        // Chuyển tiếp đến trang customerProfile.jsp
        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
    }
}
