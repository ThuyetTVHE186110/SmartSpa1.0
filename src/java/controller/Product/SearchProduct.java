package controller.Product;

import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Product;

/**
 * SearchProduct Servlet
 */
@WebServlet(name = "SearchProduct1", urlPatterns = {"/searchproduct"})
public class SearchProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String txtSearch = request.getParameter("txtSearch");
            if (txtSearch != null) {
                txtSearch = txtSearch.trim();
            }

            // Kiểm tra trường hợp tìm kiếm chỉ có khoảng trắng
            if (txtSearch == null || txtSearch.isEmpty()) {
                // Redirect đến trang mặc định hoặc hiển thị tất cả sản phẩm
                response.sendRedirect("product");
                return;
            }

            int index = Integer.parseInt(request.getParameter("index"));
            ProductDAO productDAO = new ProductDAO();
            int count = productDAO.count(txtSearch);
            int pageSize = 6;
            int endPage = (int) Math.ceil((double) count / pageSize);

            List<Product> listSearch = productDAO.search(txtSearch, index);
            request.setAttribute("endPage", endPage);
            request.setAttribute("listSearch", listSearch);
            request.getRequestDispatcher("searchProduct.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại ngoại lệ để gỡ lỗi
            // Xử lý lỗi tùy chọn (ví dụ: hiển thị trang lỗi)
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
