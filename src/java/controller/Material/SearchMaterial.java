package controller.Material;

import dal.MaterialDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Material;

/**
 * SearchMaterial Servlet
 */
@WebServlet(name = "SearchMaterial1", urlPatterns = {"/searchmaterial"})
public class SearchMaterial extends HttpServlet {

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
                response.sendRedirect("material");
                return;
            }

            int index = Integer.parseInt(request.getParameter("index"));
            MaterialDAO materialDAO = new MaterialDAO();
            int count = materialDAO.count(txtSearch);
            int pageSize = 6;
            int endPage = (int) Math.ceil((double) count / pageSize);

            List<Material> listSearch = materialDAO.search(txtSearch, index);
            request.setAttribute("endPage", endPage);
            request.setAttribute("listSearch", listSearch);
            request.getRequestDispatcher("searchMaterial.jsp").forward(request, response);
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
