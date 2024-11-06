package controller;

import dal.ServiceDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.Service;


public class ServiceServlet extends HttpServlet {
   
    private ServiceDAO serviceDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        serviceDAO = new ServiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("detail".equals(action)) {
                showServiceDetail(request, response);
            } else {
                listServices(request, response);
            }
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Error loading services: " + ex.getMessage());
            request.getRequestDispatcher("service.jsp").forward(request, response);
        }
    }

    // List all services
    private void listServices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Get services by category
        List<Service> extensionServices = serviceDAO.getServicesByCategory("Extensions");
        List<Service> liftsAndTints = serviceDAO.getServicesByCategory("Lifts & Tints");
        
        // Set attributes
        request.setAttribute("extensionServices", extensionServices);
        request.setAttribute("liftsAndTints", liftsAndTints);
        
        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("service.jsp");
        dispatcher.forward(request, response);
    }

    // Show service detail
    private void showServiceDetail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        try {
            String serviceId = request.getParameter("id");
            if (serviceId == null || serviceId.trim().isEmpty()) {
                throw new ServletException("Service ID is required");
            }

            Service service = serviceDAO.selectService(Integer.parseInt(serviceId));
            if (service == null) {
                throw new ServletException("Service not found");
            }

            if (!"ACTIVE".equals(service.getStatus())) {
                throw new ServletException("This service is currently not available");
            }

            request.setAttribute("service", service);
            request.getRequestDispatcher("servicedetails.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid service ID format");
            listServices(request, response);
        } catch (ServletException e) {
            request.setAttribute("errorMessage", e.getMessage());
            listServices(request, response);
        }
    }
}
