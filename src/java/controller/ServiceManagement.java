/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ServiceDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import model.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * ServiceManagement Servlet
 * 
 * This servlet handles CRUD operations for services, including listing, adding,
 * updating, and deleting services. It also manages file uploads for service images.
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class ServiceManagement extends HttpServlet {
    private ServiceDAO serviceDAO;
    private String uploadPath;

    /**
     * Initializes the servlet, setting up the ServiceDAO and upload path.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        serviceDAO = new ServiceDAO(); // Initializing ServiceDAO instance
        
        // Get the upload path
        ServletContext context = getServletContext();
        uploadPath = context.getRealPath("/img");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    /**
     * Handles GET requests for service management operations.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String searchQuery = request.getParameter("search");
        String statusFilter = request.getParameter("statusFilter");
        int page = 1;
        int recordsPerPage = 10;
        
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        
        System.out.println("ServiceManagement doGet - Action: " + action + ", Search: " + searchQuery + ", Page: " + page);
        
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "updateStatus":
                    updateServiceStatus(request, response);
                    break;
                default:
                    listServices(request, response, searchQuery, statusFilter, page, recordsPerPage);
                    break;
            }
        } catch (SQLException ex) {
            System.out.println("Error in ServiceManagement doGet: " + ex.getMessage());
            ex.printStackTrace();
            request.setAttribute("errorMessage", ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("service-management.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Handles POST requests for service management operations.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    insertService(request, response);
                    break;
                case "update":
                    updateService(request, response);
                    break;
                case "updateStatus":
                    updateServiceStatus(request, response);
                    break;
                default:
                    response.sendRedirect("servicemanagement");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * Lists services with pagination and search functionality.
     */
    private void listServices(HttpServletRequest request, HttpServletResponse response, 
            String searchQuery, String statusFilter, int currentPage, int recordsPerPage)
            throws SQLException, ServletException, IOException {
        
        int offset = (currentPage - 1) * recordsPerPage;
        List<Service> services;
        int totalRecords;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            services = serviceDAO.searchServices(searchQuery, offset, recordsPerPage);
            totalRecords = serviceDAO.getTotalSearchResults(searchQuery);
        } else if (statusFilter != null && !statusFilter.isEmpty()) {
            services = serviceDAO.getServicesByStatusPaginated(statusFilter, offset, recordsPerPage);
            totalRecords = serviceDAO.getTotalServicesByStatus(statusFilter);
        } else {
            services = serviceDAO.getServicesWithPagination(offset, recordsPerPage);
            totalRecords = serviceDAO.getTotalServices();
        }

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        request.setAttribute("services", services);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("statusFilter", statusFilter);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("service-management.jsp");
        dispatcher.forward(request, response);
    }

    // Show form to add a new service
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("addService.jsp");
        dispatcher.forward(request, response);
    }

    // Show form to edit an existing service
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Service existingService = serviceDAO.selectService(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("service-form.jsp");
        request.setAttribute("service", existingService);
        dispatcher.forward(request, response);
    }

    /**
     * Inserts a new service into the database, including file upload handling.
     */
    private void insertService(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {
        try {
            String name = validateAndGetParameter(request, "name", "Service name is required");
            int price = validateAndGetIntParameter(request, "price", "Price must be a valid number");
            int duration = validateAndGetIntParameter(request, "duration", "Duration must be a valid number");
            String description = validateAndGetParameter(request, "description", "Description is required");
            String category = validateAndGetParameter(request, "category", "Category is required");

            // Additional validations
            if (name.length() > 100) {
                throw new ServletException("Service name must be 100 characters or less");
            }
            if (price < 0) {
                throw new ServletException("Price must be a non-negative number");
            }
            if (duration <= 0) {
                throw new ServletException("Duration must be a positive number");
            }
            if (description.length() > 500) {
                throw new ServletException("Description must be 500 characters or less");
            }

            // Handle file upload
            Part filePart = request.getPart("file");
            String fileName = validateAndGetFileName(filePart);
            Path filePath = Paths.get(uploadPath, fileName);
            Files.copy(filePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String image = "img/" + fileName;

            // Create and save new service with default ACTIVE status
            Service newService = new Service(name, price, duration, description, image, category, "ACTIVE");
            serviceDAO.addService(newService);
            
            response.sendRedirect(request.getContextPath() + "/servicemanagement");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while adding the service: " + e.getMessage());
            listServices(request, response, null, null, 1, 10);
        }
    }

    /**
     * Updates an existing service in the database, including file upload handling.
     */
    private void updateService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = validateAndGetParameter(request, "name", "Service name is required");
            int price = validateAndGetIntParameter(request, "price", "Price must be a valid number");
            int duration = validateAndGetIntParameter(request, "duration", "Duration must be a valid number");
            String description = validateAndGetParameter(request, "description", "Description is required");
            String category = validateAndGetParameter(request, "category", "Category is required");

            // Additional validations
            if (name.length() > 100) {
                throw new ServletException("Service name must be 100 characters or less");
            }
            if (price < 0) {
                throw new ServletException("Price must be a non-negative number");
            }
            if (duration <= 0) {
                throw new ServletException("Duration must be a positive number");
            }
            if (description.length() > 500) {
                throw new ServletException("Description must be 500 characters or less");
            }

            // Handle file upload
            Part filePart = request.getPart("file");
            String image = "";
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = validateAndGetFileName(filePart);
                if (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".png")) {
                    throw new ServletException("Only JPG and PNG files are allowed");
                }
                if (filePart.getSize() > 5 * 1024 * 1024) { // 5MB limit
                    throw new ServletException("File size must be 5MB or less");
                }
                Path filePath = Paths.get(uploadPath, fileName);
                Files.copy(filePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                image = "img/" + fileName;
            } else {
                // If no new file is uploaded, keep the existing image
                image = request.getParameter("currentImage");
            }

            // Get the existing service to maintain its status
            Service existingService = serviceDAO.selectService(id);
            String status = existingService != null ? existingService.getStatus() : "ACTIVE";

            Service updatedService = new Service(id, name, price, duration, description, image, category, status);
            serviceDAO.updateService(updatedService);
            
            response.sendRedirect(request.getContextPath() + "/servicemanagement");
        } catch (ServletException e) {
            System.out.println("ServletException: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            listServices(request, response, null, null, 1, 10);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the service: " + e.getMessage());
            listServices(request, response, null, null, 1, 10);
        }
    }

    private void updateServiceStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        System.out.println("Updating service status...");
        System.out.println("Parameters received:");
        System.out.println("ID: " + request.getParameter("id"));
        System.out.println("Status: " + request.getParameter("status"));
        
        try {
            String idStr = request.getParameter("id");
            String status = request.getParameter("status");
            
            // Validate parameters
            if (idStr == null || idStr.trim().isEmpty()) {
                throw new ServletException("Service ID is required");
            }
            
            if (status == null || status.trim().isEmpty()) {
                throw new ServletException("Status is required");
            }
            
            // Parse and validate ID
            int id;
            try {
                id = Integer.parseInt(idStr.trim());
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid service ID format");
            }
            
            // Validate status value
            if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
                throw new ServletException("Invalid status value");
            }
            
            // Update the status
            boolean success = serviceDAO.updateServiceStatus(id, status);
            if (success) {
                request.getSession().setAttribute("successMessage", 
                    "Service status updated to " + status + " successfully");
            } else {
                request.getSession().setAttribute("errorMessage", 
                    "Failed to update service status");
            }
        } catch (ServletException e) {
            System.err.println("Error in updateServiceStatus: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error in updateServiceStatus: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", 
                "Error updating service status: " + e.getMessage());
        }
        
        response.sendRedirect("servicemanagement");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    // Helper methods for input validation

    private String validateAndGetParameter(HttpServletRequest request, String paramName, String errorMessage) 
            throws ServletException {
        String value = request.getParameter(paramName);
        System.out.println("Validating parameter: " + paramName + ", value: " + value);
        if (value == null || value.trim().isEmpty()) {
            throw new ServletException(errorMessage + " (Parameter: " + paramName + ")");
        }
        return value.trim();
    }

    private int validateAndGetIntParameter(HttpServletRequest request, String paramName, String errorMessage) 
            throws ServletException {
        String value = validateAndGetParameter(request, paramName, errorMessage);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ServletException(errorMessage + " (Invalid number format for: " + paramName + ")");
        }
    }

    private String validateAndGetFileName(Part part) throws ServletException {
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new ServletException("File name is required");
        }
        return fileName;
    }
}