package controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import model.Account;
import model.Bill;
import model.BillDetail;
import model.Service;
import dal.BillDAO;
import dal.ServiceDAO;
import java.util.Date;
import java.util.UUID;

@WebServlet(name = "BillServlet", urlPatterns = {"/staff/createBill", "/staff/bills", "/staff/bill/*", "/staff/billHistory"})
public class BillServlet extends HttpServlet {
    private final BillDAO billDAO;
    private final ServiceDAO serviceDAO;
    private final Gson gson;
    
    public BillServlet() {
        this.billDAO = new BillDAO();
        this.serviceDAO = new ServiceDAO();
        this.gson = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if (account == null || account.getRole() != 3) {
            response.sendRedirect(request.getContextPath() + "/adminLogin");
            return;
        }

        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        try {
            switch (servletPath) {
                case "/staff/createBill":
                    handleCreateBillPage(request, response);
                    break;
                case "/staff/bills":
                    handleGetBills(request, response);
                    break;
                case "/staff/billHistory":
                    handleBillHistoryPage(request, response);
                    break;
                case "/staff/bill":
                    if (pathInfo != null && pathInfo.length() > 1) {
                        String billCode = pathInfo.substring(1);
                        handleGetBill(billCode, response);
                    }
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void handleCreateBillPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        List<Service> services = serviceDAO.getServicesByStatus("ACTIVE");
        List<String> categories = serviceDAO.getAllCategories();
        
        request.setAttribute("services", services);
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("/Frontend_Staff/createBill.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if (account == null || account.getRole() != 3) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String requestBody = request.getReader().lines().collect(Collectors.joining());
            BillRequest billRequest = gson.fromJson(requestBody, BillRequest.class);
            
            Bill bill = new Bill();
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);
            bill.setBillCode("BILL-" + System.currentTimeMillis() + "-" + uniqueId);
            
            bill.setStaffId(account.getPersonInfo().getId());
            bill.setCustomerName(billRequest.getCustomerName());
            bill.setCustomerPhone(billRequest.getCustomerPhone());
            bill.setSubtotal(billRequest.getSubtotal());
            bill.setTax(billRequest.getTax());
            bill.setTotal(billRequest.getTotal());
            bill.setCreatedAt(new Date());
            
            List<BillDetail> details = billRequest.getServices().stream()
                .map(service -> {
                    BillDetail detail = new BillDetail();
                    detail.setServiceId(service.getId());
                    detail.setServiceName(service.getName());
                    detail.setPrice(service.getPrice());
                    return detail;
                })
                .collect(Collectors.toList());
            
            bill.setBillDetails(details);
            
            Bill savedBill = billDAO.createBill(bill);
            
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(savedBill));
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(new ErrorResponse("Error creating bill: " + e.getMessage())));
        }
    }
    
    private void handleGetBills(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            Account account = (Account) request.getSession().getAttribute("account");
            List<Bill> bills = billDAO.getBillsByStaffId(account.getId());
            
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(bills));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(new ErrorResponse("Error fetching bills: " + e.getMessage())));
        }
    }
    
    private void handleGetBill(String billCode, HttpServletResponse response) 
            throws IOException {
        try {
            Bill bill = billDAO.getBillByCode(billCode);
            if (bill != null) {
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(bill));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(gson.toJson(new ErrorResponse("Bill not found")));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(new ErrorResponse("Error fetching bill: " + e.getMessage())));
        }
    }
    
    private void handleBillHistoryPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Account account = (Account) request.getSession().getAttribute("account");
            if (account == null || account.getRole() != 3) {
                response.sendRedirect(request.getContextPath() + "/adminLogin");
                return;
            }

            // Get staff ID from account's person info
            int staffId = account.getPersonInfo().getId();
            List<Bill> bills = billDAO.getBillsByStaffId(staffId);
            
            // Add bills to request
            request.setAttribute("bills", bills);
            
            // Forward to JSP
            request.getRequestDispatcher("/Frontend_Staff/billHistory.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error loading bill history: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error loading bill history", e);
        }
    }
    
    // Inner classes for request/response handling
    private static class BillRequest {
        private String customerName;
        private String customerPhone;
        private double subtotal;
        private double tax;
        private double total;
        private List<ServiceItem> services;
        
        // Getters
        public String getCustomerName() { return customerName; }
        public String getCustomerPhone() { return customerPhone; }
        public double getSubtotal() { return subtotal; }
        public double getTax() { return tax; }
        public double getTotal() { return total; }
        public List<ServiceItem> getServices() { return services; }
    }
    
    private static class ServiceItem {
        private int id;
        private String name;
        private double price;
        
        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
    }
    
    private static class ErrorResponse {
        private final String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
    }
} 