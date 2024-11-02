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
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Product;
import model.Person;
import dal.CartDAO;
import model.CartItem;

/**
 *
 * @author Dell Alienware
 */
@WebServlet(name = "Product", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n-------- ProductServlet doGet START --------");
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");
        
        System.out.println("Session ID: " + session.getId());
        System.out.println("Person in session: " + (person != null ? 
            "ID=" + person.getId() + ", Valid=" + (person.getId() > 0) : "null"));
        
        // Load products regardless of login status
        List<Product> products = productDAO.getAllProducts();
        request.setAttribute("product", products);
        
        // Only load cart for valid logged-in users
        if (person != null && person.getId() > 0) {
            List<CartItem> cartItems = cartDAO.getCartItems(person.getId());
            System.out.println("Loaded " + cartItems.size() + " cart items for person " + person.getId());
            
            // Calculate cart total
            int total = 0;
            for (CartItem item : cartItems) {
                total += item.getPrice() * item.getProductQuantity();
                System.out.println("Cart item: " + item.getProductName() + 
                                     ", Quantity: " + item.getProductQuantity() + 
                                     ", Price: " + item.getPrice());
            }
            
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("cartTotal", String.format("%,d", total));
            request.setAttribute("cartCount", cartItems.size());
            System.out.println("Cart total: " + total + ", Cart count: " + cartItems.size());
        }
        
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n-------- ProductServlet doPost START --------");
        System.out.println("Forwarding to CartServlet");
        request.getRequestDispatcher("/cart").forward(request, response);
        System.out.println("-------- ProductServlet doPost END --------\n");
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
