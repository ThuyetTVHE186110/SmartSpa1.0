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
import java.util.List;
import model.Category;
import model.Product;
import model.Supplier;

/**
 *
 * @author Dell Alienware
 */
@WebServlet(name = "ProductListServlet", urlPatterns = {"/productlist"})
public class ProductListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    ProductDAO productDAO = new ProductDAO();
    String searchQuery = request.getParameter("search");
    int page = 1;
    int recordsPerPage = 6;

    if (request.getParameter("page") != null) {
        page = Integer.parseInt(request.getParameter("page"));
    }

    List<Product> productList;
    int totalRecords;

    if (searchQuery != null && !searchQuery.isEmpty()) {
        productList = productDAO.search(searchQuery, page);
        totalRecords = productDAO.count(searchQuery);
    } else {
        productList = productDAO.getAllProducts();
        totalRecords = productDAO.count("");
    }

    int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

    // Thêm danh sách nhà cung cấp vào yêu cầu
    List<Supplier> suppliers = productDAO.getAllSuppliers();
    request.setAttribute("suppliers", suppliers);
    List<Category> categories = productDAO.getAllCategories();
    request.setAttribute("categories", categories);
    request.setAttribute("productList", productList);
    request.setAttribute("currentPage", page);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("searchQuery", searchQuery);

    request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
}


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
