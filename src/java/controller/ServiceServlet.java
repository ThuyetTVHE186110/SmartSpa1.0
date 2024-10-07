/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.DiscountDAO;
import dal.ServiceDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import model.Discount;
import model.Service;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletContext;

/**
 * ServiceServlet
 * 
 * This servlet handles operations related to services, including listing,
 * adding, updating, and deleting services. It also manages file uploads
 * for service images.
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB before writing to disk
    maxFileSize = 1024 * 1024 * 10,      // Maximum file size 10MB
    maxRequestSize = 1024 * 1024 * 50    // Maximum request size 50MB
)
public class ServiceServlet extends HttpServlet {
   
    private ServiceDAO serviceDAO;
    private String uploadPath;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertService(request, response);
                    break;
                case "delete":
                    deleteService(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateService(request, response);
                    break;
                default:
                    listServices(request, response);
                    break;
            }
        } catch (SQLException | ServletException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("addService.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Delegate POST to doGet for form submissions
    }

    // List all services
    private void listServices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Service> listServices = serviceDAO.selectAllServices();
        request.setAttribute("listServices", listServices); // Send service list to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("service.jsp");
        dispatcher.forward(request, response);
    }

    // Show form to add a new service
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("service-form.jsp");
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
     * Insert a new service into the database
     */
    private void insertService(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, SQLException {
        // Validate input parameters
        String name = validateAndGetParameter(request, "name", "Service name is required");
        int price = validateAndGetIntParameter(request, "price", "Price must be a valid number");
        int duration = validateAndGetIntParameter(request, "duration", "Duration must be a valid number");
        String description = validateAndGetParameter(request, "description", "Description is required");

        // Handle file upload
        Part filePart = request.getPart("file");
        String fileName = validateAndGetFileName(filePart);

        Path filePath = Paths.get(uploadPath, fileName);

        // Overwrite the existing file if it exists
        Files.copy(filePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String image = "img/" + fileName;

        // Create and save new service
        Service newService = new Service(name, price, duration, description, image);
        serviceDAO.addService(newService);
        response.sendRedirect(request.getContextPath() + "/services?action=list");
    }

    // Update an existing service in the database
    private void updateService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        Service updatedService = new Service(id, name, price, duration, description,image);
        serviceDAO.updateService(updatedService);
        response.sendRedirect("services?action=list");
    }

    // Delete a service from the database
    private void deleteService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        serviceDAO.deleteService(id);
        response.sendRedirect("services?action=list");
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    // Helper methods for input validation

    private String validateAndGetParameter(HttpServletRequest request, String paramName, String errorMessage) 
            throws ServletException {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new ServletException(errorMessage);
        }
        return value.trim();
    }

    private int validateAndGetIntParameter(HttpServletRequest request, String paramName, String errorMessage) 
            throws ServletException {
        String value = validateAndGetParameter(request, paramName, errorMessage);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ServletException(errorMessage);
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
