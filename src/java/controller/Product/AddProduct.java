/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Product;

import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell Alienware
 */
@WebServlet(name = "AddProduct", urlPatterns = {"/addproduct"})
public class AddProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Khởi tạo các biến với giá trị mặc định
        int price = 0;
        int quantity = 0;
        int categoryId = 0;
        int supplierId = 0;
        int discountId = 0;

        // Kiểm tra và chuyển đổi các tham số, nếu có giá trị
        try {
            String priceParam = request.getParameter("price");
            if (priceParam != null) {
                price = Integer.parseInt(priceParam);
            }

            String quantityParam = request.getParameter("quantity");
            if (quantityParam != null) {
                quantity = Integer.parseInt(quantityParam);
            }

            String categoryIdParam = request.getParameter("categoryId");
            if (categoryIdParam != null) {
                categoryId = Integer.parseInt(categoryIdParam);
            }

            String supplierIdParam = request.getParameter("supplierId");
            if (supplierIdParam != null) {
                supplierId = Integer.parseInt(supplierIdParam);
            }

            String discountIdParam = request.getParameter("discountId");
            if (discountIdParam != null) {
                discountId = Integer.parseInt(discountIdParam);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            return;
        }

        String image = request.getParameter("image"); // Cần xử lý upload file
        String branchName = request.getParameter("branchName");
        String status = request.getParameter("status");
        String ingredient = request.getParameter("ingredient");
        String howToUse = request.getParameter("howToUse");
        String benefit = request.getParameter("benefit");
        // Tạo đối tượng ProductDAO và gọi phương thức addProduct
        ProductDAO productDAO = new ProductDAO();
        System.out.println(name);
        System.out.println(description);
        System.out.println(price);
        System.out.println(quantity);
        System.out.println(image);
        System.out.println(categoryId);
        System.out.println(supplierId);
        System.out.println(discountId);
        System.out.println(branchName);
        productDAO.addProduct(name, description, price, quantity, image, categoryId, supplierId, discountId, branchName, status, ingredient, howToUse, benefit);
        System.out.println("ass");

        // Chuyển hướng đến danh sách sản phẩm
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
