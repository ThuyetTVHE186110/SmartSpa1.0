package controller.Product;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
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

@MultipartConfig
@WebServlet(name = "UpdateProduct", urlPatterns = {"/updateproduct"})
public class UpdateProduct extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Product ID format");
            return;
        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Khởi tạo biến với giá trị mặc định
        int price = 0;
        int quantity = 0;
        int categoryId = 0;
        int supplierId = 0;

        // Kiểm tra và phân tích các tham số
        try {
            String priceParam = request.getParameter("price");
            if (priceParam != null && !priceParam.isEmpty()) {
                price = Integer.parseInt(priceParam);
            }

            String quantityParam = request.getParameter("quantity");
            if (quantityParam != null && !quantityParam.isEmpty()) {
                quantity = Integer.parseInt(quantityParam);
            }

            String categoryIdParam = request.getParameter("categoryId");
            if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
                categoryId = Integer.parseInt(categoryIdParam);
            }

            String supplierIdParam = request.getParameter("supplierId");
            if (supplierIdParam != null && !supplierIdParam.isEmpty()) {
                supplierId = Integer.parseInt(supplierIdParam); // Lấy supplierId từ request
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            return;
        }

        // Xử lý tải lên hình ảnh hoặc giữ hình ảnh hiện tại
        String image = request.getParameter("currentImage");

// Check if a new image file is uploaded
        Part imagePart = request.getPart("file");
        if (imagePart != null && imagePart.getSize() > 0) {
            // If a new image is uploaded, update the image path
            String fileName = imagePart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("img");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            Path filePath = Paths.get(uploadPath, fileName);
            Files.copy(imagePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            image = "img/" + fileName; // Update image path with new file
        }

        String branchName = request.getParameter("branchName");
        String status = request.getParameter("status");
        String ingredient = request.getParameter("ingredient");
        String howToUse = request.getParameter("howToUse");
        String benefit = request.getParameter("benefit");

        // Tạo đối tượng ProductDAO
        ProductDAO productDAO = new ProductDAO();

        // Kiểm tra tên sản phẩm mới có bị trùng với tên sản phẩm khác không
        if (productDAO.isProductNameExistsForEdit(id, name)) {
            response.sendRedirect("productlist?productId=" + id + "&message=Product name already exists. Please choose a different name.");
            return;
        }

        // Cập nhật thông tin sản phẩm
        try {
            productDAO.updateProduct(id, name, description, price, quantity, image, categoryId, supplierId, branchName, status, ingredient, howToUse, benefit);

            // Nếu thành công, chuyển hướng đến trang danh sách sản phẩm với thông báo
            response.sendRedirect("productlist?message=Product%20updated%20successfully");

        } catch (Exception e) {
            // Nếu có lỗi, chuyển hướng đến trang danh sách sản phẩm với thông báo lỗi
            response.sendRedirect("productlist?message=Error%20updating%20product%3A%20" + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
