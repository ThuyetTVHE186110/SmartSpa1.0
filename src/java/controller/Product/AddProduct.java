/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Product;

import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import model.Category;
import model.Supplier;

/**
 *
 * @author Dell Alienware
 */
@MultipartConfig
@WebServlet(name = "AddProduct", urlPatterns = {"/addproduct"})
public class AddProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<Supplier> suppliers = productDAO.getAllSuppliers();
        List<Category> categories = productDAO.getAllCategories();
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/productlist").forward(request, response);

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
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Khởi tạo các biến với giá trị mặc định
        int price = 0;
        int quantity = 0;
        int categoryId = 0;
        int supplierId = 0;

        // Kiểm tra và chuyển đổi các tham số, nếu có giá trị
        try {
            price = Integer.parseInt(request.getParameter("price"));
            quantity = Integer.parseInt(request.getParameter("quantity"));
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
            supplierId = Integer.parseInt(request.getParameter("supplierId"));

            if (price < 0 || quantity < 0) {
                throw new NumberFormatException("Price and quantity must be positive");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input for price, quantity, category or supplier.");
            return;
        }

        // Handle file upload
        Part imagePart = request.getPart("file");
        if (imagePart == null || imagePart.getSize() == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Image part is missing or empty");
            return;
        }

        // Define upload path
        String uploadPath = getServletContext().getRealPath("img");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create the directory if it doesn't exist
        }

        // Save the image to the specified path
        String fileName = imagePart.getSubmittedFileName();
        Path filePath = Paths.get(uploadPath, fileName);
        Files.copy(imagePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Store the relative path for the image in the database
        String image = "img/" + fileName;

        String branchName = request.getParameter("branchName");
        String status = request.getParameter("status");
        String ingredient = request.getParameter("ingredient");
        String howToUse = request.getParameter("howToUse");
        String benefit = request.getParameter("benefit");
        // Validate required fields
        if (name == null || name.isEmpty() || description == null || description.isEmpty()
                || branchName == null || branchName.isEmpty() || ingredient == null || ingredient.length() < 3
                || howToUse == null || howToUse.length() < 3 || benefit == null || benefit.length() < 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please fill all required fields with valid data.");
            return;
        }
        ProductDAO productDAO = new ProductDAO();
        if (productDAO.isProductNameExists(name)) {
            response.sendRedirect("productlist?message=Product name already exists. Please choose a different name.");
            return;
        }

        // Add the product to the database and handle the exception
        try {
            productDAO.addProduct(name, description, price, quantity, image, categoryId, supplierId, branchName, status, ingredient, howToUse, benefit);
            response.sendRedirect("productlist?message=Product added successfully");
        } catch (Exception e) {
            response.sendRedirect("productlist?message=Failed to add product: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
