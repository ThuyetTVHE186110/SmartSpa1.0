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
        Part imagePart = request.getPart("file");
        String image = request.getParameter("currentImage"); // Giữ hình ảnh hiện tại

        if (imagePart != null && imagePart.getSize() > 0) {
            // Kiểm tra và lưu tệp hình ảnh mới
            String fileName = imagePart.getSubmittedFileName();
            if (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".png")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only JPG and PNG files are allowed");
                return;
            }
            if (imagePart.getSize() > 5 * 1024 * 1024) { // Giới hạn 5MB
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File size must be 5MB or less");
                return;
            }

            // Đường dẫn lưu tệp
            String uploadPath = getServletContext().getRealPath("img");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Tạo thư mục nếu không tồn tại
            }

            // Lưu tệp và thiết lập đường dẫn hình ảnh
            Path filePath = Paths.get(uploadPath, fileName);
            Files.copy(imagePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            image = "img/" + fileName; // Cập nhật đường dẫn hình ảnh
        }

        String branchName = request.getParameter("branchName");
        String status = request.getParameter("status");
        String ingredient = request.getParameter("ingredient");
        String howToUse = request.getParameter("howToUse");
        String benefit = request.getParameter("benefit");

        // Tạo đối tượng ProductDAO và gọi phương thức updateProduct
        ProductDAO productDAO = new ProductDAO();
        productDAO.updateProduct(id, name, description, price, quantity, image, categoryId, supplierId, branchName, status, ingredient, howToUse, benefit);
        // Chuyển hướng đến trang danh sách sản phẩm (hoặc trang hiển thị)
        response.sendRedirect("productlist");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
