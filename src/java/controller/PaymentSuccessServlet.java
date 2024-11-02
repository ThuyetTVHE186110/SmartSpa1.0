package controller;

import dal.CartDAO;
import dal.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;

public class PaymentSuccessServlet extends HttpServlet {
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String orderCode = request.getParameter("orderCode");
            String status = request.getParameter("status");
            
            if ("PAID".equals(status)) {
                // Update payment status
                paymentDAO.updatePaymentStatus(orderCode, status);
                
                // Clear the user's cart
                HttpSession session = request.getSession();
                Account account = (Account) session.getAttribute("account");
                if (account != null) {
                    cartDAO.clearCart(account.getId());
                }
                
                // Redirect to homepage with success message
                response.sendRedirect("home?status=success&message=" + 
                    java.net.URLEncoder.encode("Payment successful! Your order has been confirmed.", "UTF-8"));
            } else {
                // Redirect to products page with error message
                response.sendRedirect("product?status=error&message=" + 
                    java.net.URLEncoder.encode("Payment was not successful. Please try again.", "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("product?status=error&message=" + 
                java.net.URLEncoder.encode("An error occurred during payment processing.", "UTF-8"));
        }
    }
} 