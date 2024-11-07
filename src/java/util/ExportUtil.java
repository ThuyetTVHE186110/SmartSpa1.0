package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.OutputStream;
import java.util.Map;
import java.util.List;

public class ExportUtil {
    
    public static void exportToPDF(OutputStream out, Map<String, Object> data) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();
        
        // Add title
        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
        Paragraph title = new Paragraph("Financial Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
        
        // Add summary
        Map<String, Object> summary = (Map<String, Object>) data.get("summary");
        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(100);
        
        addRow(summaryTable, "Total Revenue", String.format("$%.2f", summary.get("totalRevenue")));
        addRow(summaryTable, "Total Transactions", String.valueOf(summary.get("totalTransactions")));
        addRow(summaryTable, "Average Transaction", String.format("$%.2f", summary.get("averageTransaction")));
        
        document.add(summaryTable);
        document.add(Chunk.NEWLINE);
        
        // Add top customers
        List<Map<String, Object>> topCustomers = (List<Map<String, Object>>) data.get("topCustomers");
        PdfPTable customersTable = new PdfPTable(3);
        customersTable.setWidthPercentage(100);
        
        // Add header with styled font
        com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD);
        customersTable.addCell(new Phrase("Customer Name", headerFont));
        customersTable.addCell(new Phrase("Total Visits", headerFont));
        customersTable.addCell(new Phrase("Total Spent", headerFont));
        
        // Add data
        com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);
        for (Map<String, Object> customer : topCustomers) {
            customersTable.addCell(new Phrase(String.valueOf(customer.get("customerName")), normalFont));
            customersTable.addCell(new Phrase(String.valueOf(customer.get("totalVisits")), normalFont));
            customersTable.addCell(new Phrase(String.format("$%.2f", customer.get("totalSpent")), normalFont));
        }
        
        document.add(customersTable);
        document.close();
    }
    
    public static void exportToExcel(OutputStream out, Map<String, Object> data) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        
        // Create Summary Sheet
        Sheet summarySheet = workbook.createSheet("Summary");
        Map<String, Object> summary = (Map<String, Object>) data.get("summary");
        
        Row headerRow = summarySheet.createRow(0);
        headerRow.createCell(0).setCellValue("Metric");
        headerRow.createCell(1).setCellValue("Value");
        
        Row row1 = summarySheet.createRow(1);
        row1.createCell(0).setCellValue("Total Revenue");
        row1.createCell(1).setCellValue((Double) summary.get("totalRevenue"));
        
        Row row2 = summarySheet.createRow(2);
        row2.createCell(0).setCellValue("Total Transactions");
        row2.createCell(1).setCellValue((Integer) summary.get("totalTransactions"));
        
        Row row3 = summarySheet.createRow(3);
        row3.createCell(0).setCellValue("Average Transaction");
        row3.createCell(1).setCellValue((Double) summary.get("averageTransaction"));
        
        // Create Monthly Revenue Sheet
        Sheet monthlySheet = workbook.createSheet("Monthly Revenue");
        Map<String, Double> monthlyRevenue = (Map<String, Double>) data.get("monthlyRevenue");
        
        Row monthlyHeader = monthlySheet.createRow(0);
        monthlyHeader.createCell(0).setCellValue("Month");
        monthlyHeader.createCell(1).setCellValue("Revenue");
        
        int rowNum = 1;
        for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
            Row row = monthlySheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }
        
        // Create Top Customers Sheet
        Sheet customersSheet = workbook.createSheet("Top Customers");
        List<Map<String, Object>> topCustomers = (List<Map<String, Object>>) data.get("topCustomers");
        
        Row custHeader = customersSheet.createRow(0);
        custHeader.createCell(0).setCellValue("Customer Name");
        custHeader.createCell(1).setCellValue("Total Visits");
        custHeader.createCell(2).setCellValue("Total Spent");
        
        rowNum = 1;
        for (Map<String, Object> customer : topCustomers) {
            Row row = customersSheet.createRow(rowNum++);
            row.createCell(0).setCellValue((String) customer.get("customerName"));
            row.createCell(1).setCellValue((Integer) customer.get("totalVisits"));
            row.createCell(2).setCellValue((Double) customer.get("totalSpent"));
        }
        
        // Auto-size columns
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                sheet.autoSizeColumn(j);
            }
        }
        
        workbook.write(out);
        workbook.close();
    }
    
    private static void addRow(PdfPTable table, String label, String value) {
        com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);
        table.addCell(new Phrase(label, font));
        table.addCell(new Phrase(value, font));
    }
} 