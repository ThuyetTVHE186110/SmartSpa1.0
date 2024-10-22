/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package productcontroller;

import dal.ProductDAO;
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
@WebServlet(name = "DeleteProduct", urlPatterns = {"/deleteproduct"})
public class DeleteProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String productIdParam = request.getParameter("productID");

    // Kiểm tra xem productID có null hay không
    if (productIdParam == null || productIdParam.isEmpty()) {
        // Xử lý lỗi: thông báo cho người dùng hoặc ghi log
        request.setAttribute("errorMessage", "Product ID is required.");
        request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
        return; // Dừng thực hiện nếu có lỗi
    }

    int productID;
    try {
        productID = Integer.parseInt(productIdParam);
    } catch (NumberFormatException e) {
        // Xử lý lỗi: không thể chuyển đổi thành số
        request.setAttribute("errorMessage", "Invalid Product ID format.");
        request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
        return; // Dừng thực hiện nếu có lỗi
    }

    // Xóa sản phẩm từ cơ sở dữ liệu
    ProductDAO productDAO = new ProductDAO();
    try {
        productDAO.deleteProduct(productID);
    } catch (SQLException ex) {
        Logger.getLogger(DeleteProduct.class.getName()).log(Level.SEVERE, null, ex);
        // Xử lý lỗi SQL
        request.setAttribute("errorMessage", "Error deleting product. Please try again.");
        request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
        return; // Dừng thực hiện nếu có lỗi
    }

    // Chuyển hướng trở lại trang quản lý sản phẩm sau khi xóa
    response.sendRedirect("productlist");
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
