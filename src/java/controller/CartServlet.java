package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dal.CartDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Person;
import model.CartItem;
import model.Product;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n-------- CartServlet doPost START --------");
        
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");
        
        System.out.println("Session ID: " + session.getId());
        System.out.println("Person in session: " + (person != null ? "ID=" + person.getId() : "null"));
        
        // Print all request parameters
        System.out.println("\nRequest Parameters:");
        request.getParameterMap().forEach((key, value) -> {
            System.out.println(key + ": " + String.join(", ", value));
        });
        
        // Check if person is null or has invalid ID
        if (person == null || person.getId() <= 0) {
            System.out.println("Invalid person ID or not logged in. Person: " + 
                              (person == null ? "null" : "ID=" + person.getId()));
            // Store current URL before redirecting
            String currentUrl = request.getRequestURL().toString();
            if (request.getQueryString() != null) {
                currentUrl += "?" + request.getQueryString();
            }
            session.setAttribute("redirectUrl", currentUrl);
            
            // Set error message
            session.setAttribute("loginMessage", "Please login to add items to cart");
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        System.out.println("\nProcessing action: " + action);
        
        try {
            switch (action) {
                case "addToCart":
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    System.out.println("Adding product ID: " + productId + " to cart for person ID: " + person.getId());
                    
                    Product product = productDAO.getProductById(productId);
                    if (product == null) {
                        throw new RuntimeException("Product not found: " + productId);
                    }
                    System.out.println("Found product: " + product.getName() + ", Price: " + product.getPrice());
                    
                    CartItem newItem = new CartItem();
                    newItem.setPersonId(person.getId());
                    newItem.setProductId(productId);
                    newItem.setProductName(product.getName());
                    newItem.setPrice(product.getPrice());
                    newItem.setProductQuantity(1);
                    newItem.setImage(product.getImage());
                    
                    System.out.println("Adding to cart: " + newItem.getProductName() + " for person ID: " + newItem.getPersonId());
                    cartDAO.addToCart(newItem);
                    System.out.println("Successfully added to cart");
                    break;

                case "updateQuantity":
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    int newQuantity = Integer.parseInt(request.getParameter("quantity"));
                    System.out.println("Updating quantity for cart item: " + cartItemId + " to: " + newQuantity);
                    
                    if (newQuantity <= 0) {
                        cartDAO.removeFromCart(cartItemId);
                    } else {
                        cartDAO.updateQuantity(cartItemId, newQuantity, true);
                    }
                    break;

                case "removeItem":
                    cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    System.out.println("Removing cart item: " + cartItemId);
                    cartDAO.removeFromCart(cartItemId);
                    break;

                default:
                    System.out.println("Unknown action: " + action);
                    throw new RuntimeException("Unknown action: " + action);
            }
            
            System.out.println("Cart operation successful, redirecting to product page");
            response.sendRedirect("product");
            
        } catch (Exception e) {
            System.err.println("\nError in CartServlet: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("error", "Error processing cart operation: " + e.getMessage());
            response.sendRedirect("product");
        }
        
        System.out.println("-------- CartServlet doPost END --------\n");
    }
} 