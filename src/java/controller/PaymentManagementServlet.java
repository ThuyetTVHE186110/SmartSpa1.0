package controller;

import dal.PaymentDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Payment;
import java.util.Date;

@WebServlet(name = "PaymentManagementServlet", urlPatterns = {"/payment-management"})
public class PaymentManagementServlet extends HttpServlet {

    private PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if (account == null || (account.getRole() != 1 && account.getRole() != 2)) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        String search = request.getParameter("search");
        String status = request.getParameter("status");
        String period = request.getParameter("period");
        
        int page = 1;
        int pageSize = 10; // Number of items per page
        
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
        } catch (NumberFormatException e) {
            // Keep default page value
        }
        
        if ("export".equals(action)) {
            List<Payment> allPayments = paymentDAO.getAllPayments(); // Or use filtered results
            exportPayments(response, allPayments);
            return;
        }
        
        List<Payment> payments = paymentDAO.getPaymentsWithPagination(page, pageSize, status, period, search);
        int totalPayments = paymentDAO.getTotalPayments(status, period, search);
        int totalPages = (int) Math.ceil((double) totalPayments / pageSize);
        
        request.setAttribute("payments", payments);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalPayments", totalPayments);
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("period", period);
        
        request.getRequestDispatcher("paymentManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "create":
                createPayment(request, response);
                break;
            case "update":
                updatePayment(request, response);
                break;
            case "delete":
                deletePayment(request, response);
                break;
            default:
                response.sendRedirect("payment-management");
                break;
        }
    }

    private void createPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Payment payment = new Payment();
            payment.setTransactionId("TXN-" + System.currentTimeMillis());
            payment.setOrderCode(request.getParameter("orderCode"));
            payment.setAmount(Double.parseDouble(request.getParameter("amount")));
            payment.setCurrency(request.getParameter("currency"));
            payment.setStatus("PENDING");
            payment.setDescription(request.getParameter("description"));
            payment.setPersonId(Integer.parseInt(request.getParameter("personId")));
            payment.setCreatedAt(new Date());

            boolean success = paymentDAO.createPayment(payment);
            if (success) {
                request.getSession().setAttribute("successMessage", "Payment created successfully");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to create payment");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Error creating payment: " + e.getMessage());
        }
        response.sendRedirect("payment-management");
    }

    private void updatePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String transactionId = request.getParameter("transactionId");
            String status = request.getParameter("status");
            String description = request.getParameter("description");

            Payment payment = paymentDAO.getPaymentByTransactionId(transactionId);
            if (payment != null) {
                payment.setStatus(status);
                if (description != null && !description.trim().isEmpty()) {
                    payment.setDescription(description);
                }

                boolean success = paymentDAO.updatePayment(payment);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Payment updated successfully");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to update payment");
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Error updating payment: " + e.getMessage());
        }
        response.sendRedirect("payment-management");
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String transactionId = request.getParameter("transactionId");
            boolean success = paymentDAO.deletePayment(transactionId);
            if (success) {
                request.getSession().setAttribute("successMessage", "Payment deleted successfully");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to delete payment");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Error deleting payment: " + e.getMessage());
        }
        response.sendRedirect("payment-management");
    }

    private void exportPayments(HttpServletResponse response, List<Payment> payments) 
            throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"payments.csv\"");
        
        try (var writer = response.getWriter()) {
            // Write CSV header
            writer.write("Transaction ID,Order Code,Amount,Currency,Status,Description,Person ID,Created At\n");
            
            // Write payment data
            for (Payment payment : payments) {
                writer.write(String.format("%s,%s,%.2f,%s,%s,%s,%d,%s\n",
                    payment.getTransactionId(),
                    payment.getOrderCode(),
                    payment.getAmount(),
                    payment.getCurrency(),
                    payment.getStatus(),
                    payment.getDescription().replace(",", ";"),
                    payment.getPersonId(),
                    payment.getCreatedAt()
                ));
            }
        }
    }
} 