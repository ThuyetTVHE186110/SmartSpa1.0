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

        // Lấy session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            // Nếu session hoặc account không tồn tại, chuyển hướng về trang login
            response.sendRedirect("login");
            return;
        }

        // Lấy thông tin account và person từ session
        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();

        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        // Cập nhật đối tượng Person
        person.setName(fullName);
        person.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth));
        person.setGender(gender.charAt(0)); // Assuming 'M', 'F', 'O' for Male, Female, Other
        person.setPhone(phone);
        person.setEmail(email);
        person.setAddress(address);

        // Cập nhật mật khẩu cho account nếu có thay đổi
        if (password != null && !password.isEmpty()) {
            account.setPassword(password);
        }

        // Gọi DAO để cập nhật thông tin vào database
        PersonDAO personDAO = new PersonDAO();
        AccountDAO accountDAO = new AccountDAO();

        try {
            // Cập nhật thông tin Person và Account
            personDAO.updatePerson(person);  // Cập nhật Person trong DB
            accountDAO.updateAccount(account);  // Cập nhật Account trong DB

            // Cập nhật lại session với thông tin mới
            session.setAttribute("account", account);

            // Điều hướng người dùng về trang profile sau khi cập nhật thành công
            response.sendRedirect("customerProfile.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            // Gửi thông báo lỗi và chuyển hướng về trang profile
            request.setAttribute("errorMessage", "An error occurred while updating your profile.");
            request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang profile với thông tin hiện tại
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Chuyển tiếp đến trang customerProfile.jsp
        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
    }
}
