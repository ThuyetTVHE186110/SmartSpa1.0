/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import model.Person;

/**
 *
 * @author admin
 */
@WebServlet(name = "CustomerController", urlPatterns = {"/customer-management"})
public class CustomerManagementServlet extends HttpServlet {

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
        CustomerDAO customerDAO = new CustomerDAO();

        List<Person> customerList = customerDAO.getAllCustomer();
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

        // Lấy thông tin từ request
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        // Chuyển đổi dateOfBirth từ chuỗi sang java.sql.Date
        java.sql.Date dateOfBirth = java.sql.Date.valueOf(request.getParameter("dateOfBirth"));

        char gender = request.getParameter("gender").charAt(0);
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

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
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.updateCustomer(person);

        // Chuyển hướng đến trang danh sách nhân viên sau khi cập nhật
        response.sendRedirect("customer-management");
//        if (action.equals("delete")) {
//            // Delete customer
//            String action = request.getParameter("action");
//            String id = request.getParameter("id");
//            customerDAO.deleteCustomerByID(id);
//            response.sendRedirect("customer-management?action=viewAll");
//        }
        
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
