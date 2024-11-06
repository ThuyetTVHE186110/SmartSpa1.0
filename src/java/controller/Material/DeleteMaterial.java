/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Material;

import dal.MaterialDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell Alienware
 */
@WebServlet(name = "DeleteMaterial", urlPatterns = {"/deletematerial"})
public class DeleteMaterial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String materialIdParam = request.getParameter("materialID");

    // Kiểm tra xem materialID có null hay không
    if (materialIdParam == null || materialIdParam.isEmpty()) {
        // Xử lý lỗi: thông báo cho người dùng hoặc ghi log
        request.setAttribute("errorMessage", "Material ID is required.");
        request.getRequestDispatcher("materialmanagement.jsp").forward(request, response);
        return; // Dừng thực hiện nếu có lỗi
    }

    int materialID;
    try {
        materialID = Integer.parseInt(materialIdParam);
    } catch (NumberFormatException e) {
        // Xử lý lỗi: không thể chuyển đổi thành số
        request.setAttribute("errorMessage", "Invalid Material ID format.");
        request.getRequestDispatcher("materialmanagement.jsp").forward(request, response);
        return; // Dừng thực hiện nếu có lỗi
    }

    // Xóa sản phẩm từ cơ sở dữ liệu
    MaterialDAO materialDAO = new MaterialDAO();
    try {
        materialDAO.deleteMaterial(materialID);
    } catch (SQLException ex) {
        Logger.getLogger(DeleteMaterial.class.getName()).log(Level.SEVERE, null, ex);
        // Xử lý lỗi SQL
        request.setAttribute("errorMessage", "Error deleting material. Please try again.");
        request.getRequestDispatcher("materialmanagement.jsp").forward(request, response);
        return; // Dừng thực hiện nếu có lỗi
    }

    // Chuyển hướng trở lại trang quản lý sản phẩm sau khi xóa
    response.sendRedirect("materialmanagement");
    }

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
