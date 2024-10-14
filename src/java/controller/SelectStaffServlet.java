/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
public class SelectStaffServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selectedServices = request.getParameter("selectedServices");
        if (selectedServices != null && !selectedServices.isEmpty()) {
            String[] serviceIds = selectedServices.split(",");

            // Lưu danh sách dịch vụ vào session
            HttpSession session = request.getSession();
            session.setAttribute("selectedServices", Arrays.asList(serviceIds));

            // Chuyển hướng sang trang chọn nhân viên
            response.sendRedirect("select-staff.jsp");
        } else {
            // Nếu không có dịch vụ nào được chọn, quay lại trang chọn dịch vụ
            response.sendRedirect("select-service");
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
