/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PersonDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Person;

public class CustomerClassificationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PersonDAO personDAO;

    @Override
    public void init() throws ServletException {
        personDAO = new PersonDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra quyền truy cập: chỉ admin được phép
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        if (account.getRole() != 1) {
            request.setAttribute("errorMessage", "You do not have the required permissions to access this page.");
            request.getRequestDispatcher("roleError.jsp").forward(request, response);
            return;
        }

        // Xử lý phân trang
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            // Lấy danh sách khách hàng từ PersonDAO
            List<Person> allCustomers = personDAO.getAllCustomers();
            int totalRecords = allCustomers.size();
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            // Tính chỉ số bắt đầu và kết thúc
            int start = (page - 1) * recordsPerPage;
            int end = Math.min(start + recordsPerPage, totalRecords);

            // Kiểm tra chỉ số bắt đầu hợp lệ
            if (start >= totalRecords) {
                start = Math.max(0, totalRecords - recordsPerPage);
                page = totalPages;
            }

            // Tạo danh sách con từ danh sách tất cả khách hàng
            List<Person> customers = (totalRecords > 0) ? allCustomers.subList(start, end) : new ArrayList<>();

            // Đặt các thuộc tính cho JSP
            request.setAttribute("customers", customers);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // Chuyển tiếp đến trang JSP hiển thị
            request.getRequestDispatcher("customerClassification.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving customer data.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
