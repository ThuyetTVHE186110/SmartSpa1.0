package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import model.Payment;
import dal.PaymentDAO;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import model.Account;
import dal.CartDAO;
import model.Person;

public class PaymentCallbackServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final PaymentDAO paymentDAO;
    private final CartDAO cartDAO;

    public PaymentCallbackServlet() {
        this.objectMapper = new ObjectMapper();
        this.paymentDAO = new PaymentDAO();
        this.cartDAO = new CartDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Read the callback data
            String requestBody = request.getReader().lines().collect(Collectors.joining());
            Map<String, Object> callbackData = objectMapper.readValue(requestBody, Map.class);

            // Log the callback data
            System.out.println("Received callback data: " + requestBody);

            // Extract payment information
            String transactionId = (String) callbackData.get("transactionId");
            String status = (String) callbackData.get("status");

            if (transactionId != null && status != null) {
                // Update payment status in database
                boolean updated = paymentDAO.updatePaymentStatus(transactionId, status);
                if (updated) {
                    System.out.println("Payment status updated successfully: " + transactionId + " -> " + status);
                } else {
                    System.out.println("No payment found with transactionId: " + transactionId);
                }
            }

            // Send success response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("OK");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error processing callback: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String pendingOrderCode = (String) session.getAttribute("pendingOrderCode");
            Integer pendingAmount = (Integer) session.getAttribute("pendingAmount");
            Integer pendingPersonId = (Integer) session.getAttribute("pendingPersonId");
            
            String orderCode = request.getParameter("orderCode");
            String status = request.getParameter("status");
            String transactionId = request.getParameter("transactionId");
            
            System.out.println("Processing payment callback - Order: " + orderCode + ", Status: " + status);
            
            if (orderCode == null || !orderCode.equals(pendingOrderCode)) {
                throw new IllegalStateException("Invalid order code");
            }

            if ("PAID".equals(status)) {
                try {
                    // Create and save payment record
                    Payment payment = new Payment();
                    payment.setTransactionId(transactionId != null ? transactionId : "TXN-" + orderCode);
                    payment.setOrderCode(orderCode);
                    payment.setAmount(pendingAmount);
                    payment.setCurrency("VND");
                    payment.setStatus(status);
                    payment.setDescription("Order #" + orderCode);
                    payment.setPersonId(pendingPersonId);
                    
                    System.out.println("Saving payment for PersonID: " + pendingPersonId);
                    paymentDAO.savePayment(payment);
                    
                    // Clear cart
                    if (pendingPersonId != null) {
                        System.out.println("Clearing cart for PersonID: " + pendingPersonId);
                        cartDAO.clearCart(pendingPersonId);
                    }
                    
                    // Clear session attributes
                    session.removeAttribute("pendingOrderCode");
                    session.removeAttribute("pendingAmount");
                    session.removeAttribute("pendingPersonId");
                    
                    // Redirect with success message
                    response.sendRedirect(request.getContextPath() + 
                        "/index?status=success&message=" + 
                        URLEncoder.encode("Payment successful! Your order has been confirmed.", "UTF-8"));
                } catch (Exception e) {
                    System.err.println("Error processing successful payment: " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/product?status=error&message=" + 
                    URLEncoder.encode("Payment was not successful. Please try again.", "UTF-8"));
            }
        } catch (Exception e) {
            System.err.println("Payment callback error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + 
                "/product?status=error&message=" + 
                URLEncoder.encode("An error occurred: " + e.getMessage(), "UTF-8"));
        }
    }
}
