package controller;

import dal.PersonDAO;
import dal.AccountDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Person;

public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session và kiểm tra xem người dùng đã đăng nhập hay chưa
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        // Lấy account và person từ session
        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();

        // Lấy thông tin từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        // Kiểm tra xem các giá trị từ form có lấy được hay không
        System.out.println("Received data from form:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Password: " + password);

        // Cập nhật thông tin cho person
        person.setName(fullName);
        person.setEmail(email);
        person.setPhone(phone);

        // Nếu có password, cập nhật mật khẩu cho account
        if (password != null && !password.isEmpty()) {
            account.setPassword(password);
        }

        // Gọi DAO để cập nhật thông tin vào database
        PersonDAO personDAO = new PersonDAO();
        AccountDAO accountDAO = new AccountDAO();

        try {
            // Cập nhật Person và Account trong cơ sở dữ liệu
            personDAO.updatePerson(person);
            accountDAO.updateAccount(account);

            // Cập nhật lại session
            session.setAttribute("account", account);

            // Chuyển hướng lại trang profile với thông báo thành công
            response.sendRedirect("customerProfile.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            // Gửi thông báo lỗi và chuyển hướng về trang profile
            request.setAttribute("errorMessage", "An error occurred while updating your profile.");
            request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
        }
    }
}
