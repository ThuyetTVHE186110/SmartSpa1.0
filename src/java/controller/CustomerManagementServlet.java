/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PersonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Person;

/**
 *
 * @author admin
 */
@WebServlet(name = "CustomerController", urlPatterns = {"/customer-management"})
public class CustomerManagementServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PersonDAO personDAO = new PersonDAO();

        List<Person> customerList = personDAO.getPersonByRole("customer");
        System.out.println(customerList.get(0).getId());
        request.setAttribute("customerList", customerList);
        request.getRequestDispatcher("customer-management.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
//         Xử lý cập nhật thông tin khách hàng
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        String genderStr = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        // Kiểm tra các trường nhập trống
        if (idStr == null || idStr.trim().isEmpty()
                || name == null || name.trim().isEmpty()
                || dateOfBirthStr == null || dateOfBirthStr.trim().isEmpty()
                || genderStr == null || genderStr.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || address == null || address.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ tất cả các trường.");
            request.getRequestDispatcher("customer-management.jsp").forward(request, response);
            return;
        }

        // Chuyển đổi dateOfBirth từ chuỗi sang java.sql.Date
        java.sql.Date dateOfBirth;
        try {
            dateOfBirth = java.sql.Date.valueOf(dateOfBirthStr);
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Ngày sinh không hợp lệ. Vui lòng nhập lại.");
            request.getRequestDispatcher("customer-management.jsp").forward(request, response);
            return;
        }

        // Kiểm tra ID là số nguyên hợp lệ
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ.");
            request.getRequestDispatcher("customer-management.jsp").forward(request, response);
            return;
        }

        // Lấy giới tính
        char gender = genderStr.charAt(0);

        // Tạo đối tượng Person và thiết lập thông tin
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setDateOfBirth(dateOfBirth); // Gán dateOfBirth đã chuyển đổi
        person.setGender(gender);
        person.setPhone(phone);
        person.setEmail(email);
        person.setAddress(address);

        // Cập nhật nhân viên
        PersonDAO personDAO = new PersonDAO();
        try {
            personDAO.updatePerson(person);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình cập nhật. Vui lòng thử lại.");
            request.getRequestDispatcher("customer-management.jsp").forward(request, response);
            return;
        }

        // Chuyển hướng đến trang danh sách nhân viên sau khi cập nhật
        response.sendRedirect("customer-management");
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
