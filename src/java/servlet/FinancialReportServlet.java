package servlet;

import com.google.gson.Gson;
import dao.FinancialReportDAO;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import util.ExportUtil;
import util.ReportGenerator;

@WebServlet(name = "FinancialReport", urlPatterns = {"/financial-report"})
public class FinancialReportServlet extends HttpServlet {
    
    private FinancialReportDAO reportDAO;
    
    @Override
    public void init() {
        reportDAO = new FinancialReportDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in and is manager
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null || account.getRole() != 1&&account.getRole()!=2) {
            response.sendRedirect("login");
            return;
        }
        
        // Get parameters with default values
        String startDate = request.getParameter("startDate");
        if (startDate == null || startDate.isEmpty()) {
            startDate = LocalDate.now().minusMonths(1).toString();
        }
        
        String endDate = request.getParameter("endDate");
        if (endDate == null || endDate.isEmpty()) {
            endDate = LocalDate.now().toString();
        }
        
        String yearParam = request.getParameter("year");
        int year;
        if (yearParam == null || yearParam.isEmpty()) {
            year = LocalDate.now().getYear();
        } else {
            try {
                year = Integer.parseInt(yearParam);
            } catch (NumberFormatException e) {
                year = LocalDate.now().getYear();
            }
        }
        
        try {
            // Get financial data
            Map<String, Double> revenueByService = reportDAO.getRevenueByService(startDate, endDate);
            Map<String, Double> monthlyRevenue = reportDAO.getMonthlyRevenue(year);
            Map<String, Object> summary = reportDAO.getFinancialSummary(startDate, endDate);
            List<Map<String, Object>> topCustomers = reportDAO.getTopCustomers(startDate, endDate, 10);
            
            // Convert data to JSON for charts
            Gson gson = new Gson();
            request.setAttribute("revenueByService", gson.toJson(revenueByService));
            request.setAttribute("monthlyRevenue", gson.toJson(monthlyRevenue));
            
            // Set attributes
            request.setAttribute("summary", summary);
            request.setAttribute("topCustomers", topCustomers);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("year", year);
            
            // Forward to JSP
            request.getRequestDispatcher("financial-report.jsp").forward(request, response);
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating report: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("export".equals(action)) {
            String format = request.getParameter("format");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            
            try {
                // Prepare data for export
                Map<String, Object> data = new HashMap<>();
                data.put("summary", reportDAO.getFinancialSummary(startDate, endDate));
                data.put("monthlyRevenue", reportDAO.getMonthlyRevenue(LocalDate.now().getYear()));
                data.put("topCustomers", reportDAO.getTopCustomers(startDate, endDate, 10));
                
                // Set response headers
                String filename = "financial_report_" + LocalDate.now().toString() + "." + format;
                
                if ("pdf".equals(format)) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                    ReportGenerator.generatePDFReport(response.getOutputStream(), data, startDate, endDate);
                } else if ("excel".equals(format)) {
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                    ReportGenerator.generateExcelReport(response.getOutputStream(), data, startDate, endDate);
                }
                
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error exporting report: " + e.getMessage());
            }
        }
    }
} 