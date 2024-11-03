/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Material;

import dal.MaterialDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author hotdo
 */
@WebServlet(name="AddMaterial", urlPatterns={"/addmaterial"})
public class AddMaterial extends HttpServlet {
   
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Khởi tạo các biến với giá trị mặc định
        int price = 0;
        int supplierId = 0;

        // Kiểm tra và chuyển đổi các tham số, nếu có giá trị
        try {
            String priceParam = request.getParameter("price");
            if (priceParam != null) {
                price = Integer.parseInt(priceParam);
            }

            String supplierIdParam = request.getParameter("supplierId");
            if (supplierIdParam != null) {
                supplierId = Integer.parseInt(supplierIdParam);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            return;
        }

        String image = request.getParameter("image"); // Cần xử lý upload file
        String status = request.getParameter("status");
        // Tạo đối tượng MaterialDAO và gọi phương thức addMaterial
        MaterialDAO productDAO = new MaterialDAO();
        System.out.println(name);
        System.out.println(description);
        System.out.println(price);
        System.out.println(image);
        System.out.println(supplierId);
        productDAO.addMaterial(name, description, price, image, supplierId, status);
        System.out.println("ass");

        // Chuyển hướng đến danh sách sản phẩm
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

