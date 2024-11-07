package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.OutputStream;
import java.util.Map;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Asus
 */
public class ReportGenerator {
    
    public static void generatePDFReport(OutputStream out, Map<String, Object> data, String startDate, String endDate) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        
        // Add header
        addHeader(document, startDate, endDate);
        
        // Add financial summary
        addFinancialSummary(document, (Map<String, Object>) data.get("summary"));
        
        // Add revenue chart
        if (data.containsKey("monthlyRevenue")) {
            addRevenueChart(document, (Map<String, Double>) data.get("monthlyRevenue"));
        }
        
        // Add top customers table
        if (data.containsKey("topCustomers")) {
            addTopCustomersTable(document, (List<Map<String, Object>>) data.get("topCustomers"));
        }
        
        document.close();
    }
    
    private static void addHeader(Document document, String startDate, String endDate) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Financial Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph dateRange = new Paragraph(
            String.format("Period: %s to %s", startDate, endDate), 
            dateFont
        );
        dateRange.setAlignment(Element.ALIGN_CENTER);
        dateRange.setSpacingBefore(10);
        dateRange.setSpacingAfter(20);
        document.add(dateRange);
    }
    
    private static void addFinancialSummary(Document document, Map<String, Object> summary) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph section = new Paragraph("Financial Summary", sectionFont);
        section.setSpacingBefore(20);
        section.setSpacingAfter(10);
        document.add(section);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        // Add summary rows
        addSummaryRow(table, "Total Revenue", String.format("$%.2f", summary.get("totalRevenue")));
        addSummaryRow(table, "Total Transactions", String.valueOf(summary.get("totalTransactions")));
        addSummaryRow(table, "Average Transaction", String.format("$%.2f", summary.get("averageTransaction")));
        
        document.add(table);
    }
    
    private static void addSummaryRow(PdfPTable table, String label, String value) {
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        table.addCell(new Phrase(label, cellFont));
        table.addCell(new Phrase(value, cellFont));
    }
    
    private static void addTopCustomersTable(Document document, List<Map<String, Object>> customers) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph section = new Paragraph("Top Customers", sectionFont);
        section.setSpacingBefore(20);
        section.setSpacingAfter(10);
        document.add(section);
        
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        
        // Add header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        table.addCell(new Phrase("Customer Name", headerFont));
        table.addCell(new Phrase("Total Visits", headerFont));
        table.addCell(new Phrase("Total Spent", headerFont));
        
        // Add data
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        for (Map<String, Object> customer : customers) {
            table.addCell(new Phrase(String.valueOf(customer.get("customerName")), cellFont));
            table.addCell(new Phrase(String.valueOf(customer.get("totalVisits")), cellFont));
            table.addCell(new Phrase(String.format("$%.2f", customer.get("totalSpent")), cellFont));
        }
        
        document.add(table);
    }
    
    public static void generateExcelReport(OutputStream out, Map<String, Object> data, String startDate, String endDate) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        // Add Summary Sheet
        addSummarySheet(workbook, (Map<String, Object>) data.get("summary"), startDate, endDate);
        
        // Add Monthly Revenue Sheet
        if (data.containsKey("monthlyRevenue")) {
            addMonthlyRevenueSheet(workbook, (Map<String, Double>) data.get("monthlyRevenue"));
        }
        
        // Add Top Customers Sheet
        if (data.containsKey("topCustomers")) {
            addTopCustomersSheet(workbook, (List<Map<String, Object>>) data.get("topCustomers"));
        }
        
        workbook.write(out);
        workbook.close();
    }
    
    private static void addSummarySheet(XSSFWorkbook workbook, Map<String, Object> summary, String startDate, String endDate) {
        XSSFSheet sheet = workbook.createSheet("Summary");
        
        // Add title and date range
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Financial Report");
        
        Row dateRow = sheet.createRow(1);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue(String.format("Period: %s to %s", startDate, endDate));
        
        // Add summary data
        Row headerRow = sheet.createRow(3);
        headerRow.createCell(0).setCellValue("Metric");
        headerRow.createCell(1).setCellValue("Value");
        
        int rowNum = 4;
        addSummaryDataRow(sheet.createRow(rowNum++), "Total Revenue", String.format("$%.2f", summary.get("totalRevenue")));
        addSummaryDataRow(sheet.createRow(rowNum++), "Total Transactions", String.valueOf(summary.get("totalTransactions")));
        addSummaryDataRow(sheet.createRow(rowNum++), "Average Transaction", String.format("$%.2f", summary.get("averageTransaction")));
        
        // Auto-size columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
    
    private static void addSummaryDataRow(Row row, String label, String value) {
        row.createCell(0).setCellValue(label);
        row.createCell(1).setCellValue(value);
    }
    
    private static void addMonthlyRevenueSheet(XSSFWorkbook workbook, Map<String, Double> monthlyRevenue) {
        XSSFSheet sheet = workbook.createSheet("Monthly Revenue");
        
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Month");
        headerRow.createCell(1).setCellValue("Revenue");
        
        int rowNum = 1;
        for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }
        
        // Auto-size columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
    
    private static void addTopCustomersSheet(XSSFWorkbook workbook, List<Map<String, Object>> customers) {
        XSSFSheet sheet = workbook.createSheet("Top Customers");
        
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Customer Name");
        headerRow.createCell(1).setCellValue("Total Visits");
        headerRow.createCell(2).setCellValue("Total Spent");
        
        int rowNum = 1;
        for (Map<String, Object> customer : customers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(String.valueOf(customer.get("customerName")));
            row.createCell(1).setCellValue(Integer.parseInt(String.valueOf(customer.get("totalVisits"))));
            row.createCell(2).setCellValue(Double.parseDouble(String.valueOf(customer.get("totalSpent"))));
        }
        
        // Auto-size columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
    }
    
    private static void addRevenueChart(Document document, Map<String, Double> monthlyRevenue) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph section = new Paragraph("Monthly Revenue", sectionFont);
        section.setSpacingBefore(20);
        section.setSpacingAfter(10);
        document.add(section);
        
        // Create table for chart data
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        // Add header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        table.addCell(new Phrase("Month", headerFont));
        table.addCell(new Phrase("Revenue", headerFont));
        
        // Add data
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        double maxRevenue = monthlyRevenue.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);
        
        for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
            table.addCell(new Phrase(entry.getKey(), cellFont));
            
            // Create a cell with a visual bar representing the revenue
            PdfPCell cell = new PdfPCell();
            double percentage = (entry.getValue() / maxRevenue) * 100;
            StringBuilder bar = new StringBuilder();
            bar.append(String.format("$%.2f ", entry.getValue()));
            
            // Add visual representation using characters
            int barLength = (int) (percentage / 5); // Each character represents 5%
            bar.append("[");
            for (int i = 0; i < barLength; i++) {
                bar.append("█");
            }
            for (int i = barLength; i < 20; i++) { // Max 20 characters for 100%
                bar.append("░");
            }
            bar.append("]");
            
            cell.addElement(new Phrase(bar.toString(), cellFont));
            table.addCell(cell);
        }
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
} 