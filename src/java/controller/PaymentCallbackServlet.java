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

public class PaymentCallbackServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final PaymentDAO paymentDAO;

    public PaymentCallbackServlet() {
        this.objectMapper = new ObjectMapper();
        this.paymentDAO = new PaymentDAO();
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
}
