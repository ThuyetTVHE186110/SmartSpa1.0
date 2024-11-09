/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import com.google.gson.Gson;
import dal.FinancialReportDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Asus
 */
public class FinancialReportServlet extends HttpServlet {
   
    private FinancialReportDAO reportDAO;
    
    @Override
    public void init() {
        reportDAO = new FinancialReportDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
            // Get parameters with default values
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusMonths(1);
            
            String startDateStr = request.getParameter("startDate");
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = LocalDate.parse(startDateStr);
            }
            
            String endDateStr = request.getParameter("endDate");
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = LocalDate.parse(endDateStr);
            }
            
            int year = LocalDate.now().getYear();
            String yearStr = request.getParameter("year");
            if (yearStr != null && !yearStr.isEmpty()) {
                year = Integer.parseInt(yearStr);
            }

            System.out.println("Date range: " + startDate + " to " + endDate);
            
            // Get financial data
            Map<String, Double> revenueByService = reportDAO.getRevenueByService(startDate.toString(), endDate.toString());
            System.out.println("Revenue by service data: " + revenueByService);
            
            Map<String, Double> monthlyRevenue = reportDAO.getMonthlyRevenue(year);
            System.out.println("Monthly revenue data: " + monthlyRevenue);
            
            Map<String, Object> summary = reportDAO.getFinancialSummary(startDate.toString(), endDate.toString());
            System.out.println("Summary data: " + summary);
            
            List<Map<String, Object>> topCustomers = reportDAO.getTopCustomers(startDate.toString(), endDate.toString(), 10);
            System.out.println("Top customers data: " + topCustomers);
            
            // Convert data to JSON for charts
            Gson gson = new Gson();
            String revenueByServiceJson = gson.toJson(revenueByService);
            String monthlyRevenueJson = gson.toJson(monthlyRevenue);
            
            System.out.println("Revenue by service JSON: " + revenueByServiceJson);
            System.out.println("Monthly revenue JSON: " + monthlyRevenueJson);
            
            // Set attributes
            request.setAttribute("revenueByService", revenueByServiceJson);
            request.setAttribute("monthlyRevenue", monthlyRevenueJson);
            request.setAttribute("summary", summary);
            request.setAttribute("topCustomers", topCustomers);
            request.setAttribute("startDate", startDate.toString());
            request.setAttribute("endDate", endDate.toString());
            request.setAttribute("year", year);
            
            // Forward to JSP
            request.getRequestDispatcher("financial-report.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in FinancialReportServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Error generating report: " + e.getMessage());
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
                // Get the data
                Map<String, Object> data = new HashMap<>();
                data.put("summary", reportDAO.getFinancialSummary(startDate, endDate));
                data.put("monthlyRevenue", reportDAO.getMonthlyRevenue(LocalDate.now().getYear()));
                data.put("topCustomers", reportDAO.getTopCustomers(startDate, endDate, 10));
                
                if ("excel".equals(format)) {
                    // Set response headers for Excel
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Disposition", "attachment; filename=financial_report.xlsx");
                    
                    // Add Summary Sheet
                    try ( // Create workbook
                            XSSFWorkbook workbook = new XSSFWorkbook()) {
                        // Add Summary Sheet
                        XSSFSheet summarySheet = workbook.createSheet("Summary");
                        int rowNum = 0;
                        
                        // Add title
                        XSSFRow titleRow = summarySheet.createRow(rowNum++);
                        titleRow.createCell(0).setCellValue("Financial Report");
                        
                        // Add date range
                        XSSFRow dateRow = summarySheet.createRow(rowNum++);
                        dateRow.createCell(0).setCellValue("Period: " + startDate + " to " + endDate);
                        
                        // Add empty row
                        rowNum++;
                        
                        // Add summary data
                        Map<String, Object> summary = (Map<String, Object>) data.get("summary");
                        XSSFRow headerRow = summarySheet.createRow(rowNum++);
                        headerRow.createCell(0).setCellValue("Metric");
                        headerRow.createCell(1).setCellValue("Value");
                        
                        XSSFRow revenueRow = summarySheet.createRow(rowNum++);
                        revenueRow.createCell(0).setCellValue("Total Revenue");
                        revenueRow.createCell(1).setCellValue(String.format("%,.2f VND", summary.get("totalRevenue")));
                        
                        XSSFRow transactionsRow = summarySheet.createRow(rowNum++);
                        transactionsRow.createCell(0).setCellValue("Total Transactions");
                        transactionsRow.createCell(1).setCellValue(String.valueOf(summary.get("totalTransactions")));
                        
                        XSSFRow avgRow = summarySheet.createRow(rowNum++);
                        avgRow.createCell(0).setCellValue("Average Transaction");
                        avgRow.createCell(1).setCellValue(String.format("%,.2f VND", summary.get("averageTransaction")));
                        
                        // Add Monthly Revenue Sheet
                        XSSFSheet monthlySheet = workbook.createSheet("Monthly Revenue");
                        rowNum = 0;
                        
                        // Add headers
                        headerRow = monthlySheet.createRow(rowNum++);
                        headerRow.createCell(0).setCellValue("Month");
                        headerRow.createCell(1).setCellValue("Revenue (VND)");
                        
                        // Add monthly data
                        Map<String, Double> monthlyRevenue = (Map<String, Double>) data.get("monthlyRevenue");
                        for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
                            XSSFRow row = monthlySheet.createRow(rowNum++);
                            row.createCell(0).setCellValue(entry.getKey());
                            row.createCell(1).setCellValue(String.format("%,.2f", entry.getValue()));
                        }
                        
                        // Add Top Customers Sheet
                        XSSFSheet customersSheet = workbook.createSheet("Top Customers");
                        rowNum = 0;
                        
                        // Add headers
                        headerRow = customersSheet.createRow(rowNum++);
                        headerRow.createCell(0).setCellValue("Customer Name");
                        headerRow.createCell(1).setCellValue("Total Visits");
                        headerRow.createCell(2).setCellValue("Total Spent (VND)");
                        
                        // Add customer data
                        List<Map<String, Object>> customers = (List<Map<String, Object>>) data.get("topCustomers");
                        for (Map<String, Object> customer : customers) {
                            XSSFRow row = customersSheet.createRow(rowNum++);
                            row.createCell(0).setCellValue(String.valueOf(customer.get("customerName")));
                            row.createCell(1).setCellValue(Integer.parseInt(String.valueOf(customer.get("totalVisits"))));
                            row.createCell(2).setCellValue(String.format("%,.2f", customer.get("totalSpent")));
                        }
                        
                        // Auto-size columns for all sheets
                        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                            XSSFSheet sheet = workbook.getSheetAt(i);
                            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                                sheet.autoSizeColumn(j);
                            }
                        }
                        
                        // Write to response output stream
                        workbook.write(response.getOutputStream());
                    }
                }
                
            } catch (IOException | NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Error generating report: " + e.getMessage());
            }
        }
    }
}
