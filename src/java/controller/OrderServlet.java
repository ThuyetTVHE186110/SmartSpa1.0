    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.CreatePaymentLinkRequestBody;
import model.Payment;
import dal.PaymentDAO;

import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Asus
 */
public class OrderServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
    private final PayOS payOS;
    private final ObjectMapper objectMapper;
    private final PaymentDAO paymentDAO;

    public OrderServlet() {
        super();
        this.payOS = new PayOS("db62b741-d594-4ad2-b9fb-cf9723699955", "cd7816bb-52b0-49a8-8c32-43a8982546c7", "3ae6fbbf5545772a0f6f9cc5faaad0d948e5d060f40bb21386e3b9233b33b36a");
        this.objectMapper = new ObjectMapper();
        this.paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("./")) {
            createPaymentLink(request, response);
        } else if (pathInfo.equals("./confirm-webhook")) {
            confirmWebhook(request, response);
        } else {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, "Not Found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("./")) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
        } else {
            long orderId = Long.parseLong(pathInfo.substring(1));
            getOrderById(orderId, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("./")) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
        } else {
            int orderId = Integer.parseInt(pathInfo.substring(1));
            cancelOrder(orderId, response);
        }
    }

    private void createPaymentLink(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CreatePaymentLinkRequestBody requestData;
            
            // Get the base URL for the application
            String baseUrl = request.getScheme() + "://" + request.getServerName();
            if (request.getServerPort() != 80 && request.getServerPort() != 443) {
                baseUrl += ":" + request.getServerPort();
            }
            baseUrl += request.getContextPath();

            // Check content type
            String contentType = request.getContentType();
            
            if (contentType != null && contentType.contains("application/json")) {
                // Handle JSON request
                String requestBody = request.getReader().lines().collect(Collectors.joining());
                requestData = objectMapper.readValue(requestBody, CreatePaymentLinkRequestBody.class);
            } else {
                // Handle form data
                requestData = new CreatePaymentLinkRequestBody();
                
                String productName = request.getParameter("productName");
                String priceStr = request.getParameter("price");
                String description = request.getParameter("description");
                
                if (productName == null || priceStr == null) {
                    throw new IllegalArgumentException("Product name and price are required");
                }
                
                requestData.setProductName(productName);
                requestData.setPrice(Integer.parseInt(priceStr));
                
                // Ensure description is not longer than 25 characters
                if (description != null) {
                    if (description.length() > 25) {
                        description = description.substring(0, 25);
                    }
                    requestData.setDescription(description);
                } else {
                    requestData.setDescription("Mi tom x1");
                }
                
                // Set absolute URLs for return and cancel
                requestData.setReturnUrl(baseUrl + "/success.html");
                requestData.setCancelUrl(baseUrl + "/cancel.html");
            }

            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            // Print debug information
            System.out.println("Creating payment with:");
            System.out.println("Return URL: " + requestData.getReturnUrl());
            System.out.println("Cancel URL: " + requestData.getCancelUrl());
            System.out.println("Description: " + requestData.getDescription());
            System.out.println("Amount: " + requestData.getPrice());

            ItemData item = ItemData.builder()
                    .name(requestData.getProductName())
                    .price(requestData.getPrice())
                    .quantity(1)
                    .build();

            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .description(requestData.getDescription())
                    .amount(requestData.getPrice())
                    .item(item)
                    .returnUrl(requestData.getReturnUrl())
                    .cancelUrl(requestData.getCancelUrl())
                    .build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            // Save payment information to database
            Payment payment = new Payment();
            payment.setTransactionId(data.getPaymentLinkId());
            payment.setOrderCode(String.valueOf(orderCode));
            payment.setAmount(requestData.getPrice());
            payment.setCurrency("VND");
            payment.setStatus("PENDING");
            payment.setDescription(requestData.getDescription());

            try {
                paymentDAO.savePayment(payment);
                System.out.println("Payment saved to database with ID: " + payment.getId());
            } catch (Exception e) {
                System.err.println("Error saving payment to database: " + e.getMessage());
                // Continue with redirect even if save fails
            }

            // Redirect to PayOS checkout URL
            String checkoutUrl = data.getCheckoutUrl();
            if (checkoutUrl != null && !checkoutUrl.isEmpty()) {
                response.sendRedirect(checkoutUrl);
            } else {
                throw new IllegalStateException("No checkout URL received from PayOS");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.html?message=" + 
                URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }

    private void getOrderById(long orderId, HttpServletResponse response) throws IOException {
        try {
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderId);

            ObjectNode responseJson = objectMapper.createObjectNode();
            responseJson.set("data", objectMapper.valueToTree(order));
            responseJson.put("error", 0);
            responseJson.put("message", "ok");

            sendJsonResponse(response, responseJson);
        } catch (Exception e) {
            sendErrorResponse(response, e);
        }
    }

    private void cancelOrder(int orderId, HttpServletResponse response) throws IOException {
        try {
            PaymentLinkData order = payOS.cancelPaymentLink(orderId, null);

            ObjectNode responseJson = objectMapper.createObjectNode();
            responseJson.set("data", objectMapper.valueToTree(order));
            responseJson.put("error", 0);
            responseJson.put("message", "ok");

            sendJsonResponse(response, responseJson);
        } catch (Exception e) {
            sendErrorResponse(response, e);
        }
    }

    private void confirmWebhook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestBody = request.getReader().lines().collect(Collectors.joining());
            Map<String, String> requestData = objectMapper.readValue(requestBody, Map.class);

            String str = payOS.confirmWebhook(requestData.get("webhookUrl"));

            ObjectNode responseJson = objectMapper.createObjectNode();
            responseJson.set("data", objectMapper.valueToTree(str));
            responseJson.put("error", 0);
            responseJson.put("message", "ok");

            sendJsonResponse(response, responseJson);
        } catch (Exception e) {
            sendErrorResponse(response, e);
        }
    }

    private void sendJsonResponse(HttpServletResponse response, ObjectNode jsonNode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonNode.toString());
        out.flush();
    }

    private void sendErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        ObjectNode errorJson = objectMapper.createObjectNode();
        errorJson.put("error", -1);
        errorJson.put("message", e.getMessage());
        errorJson.set("data", null);
        sendJsonResponse(response, errorJson);
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.sendError(status, message);
    }
}
