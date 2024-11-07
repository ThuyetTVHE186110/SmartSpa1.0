package controller;

import dal.ServiceDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PopularService;
import model.Service;

@WebServlet(name = "StaffServiceServlet", urlPatterns = {"/staff-services"})
public class StaffServiceServlet extends HttpServlet {

    private ServiceDAO serviceDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        serviceDAO = new ServiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            
            if (action == null || action.isEmpty()) {
                listServices(request, response);
                return;
            }

            switch (action) {
                case "view":
                    viewServiceDetails(request, response);
                    break;
                case "filter":
                    filterServices(request, response);
                    break;
                case "performance":
                    getServicePerformance(request, response);
                    break;
                case "staffPerformance":
                    getStaffPerformance(request, response);
                    break;
                case "resources":
                    getResourceRequirements(request, response);
                    break;
                case "steps":
                    getServiceSteps(request, response);
                    break;
                default:
                    listServices(request, response);
                    break;
            }
        } catch (SQLException ex) {
            handleError(request, response, "Database error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            handleError(request, response, "An error occurred: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    addService(request, response);
                    break;
                case "edit":
                    updateService(request, response);
                    break;
                case "delete":
                    deleteService(request, response);
                    break;
                case "status":
                    updateStatus(request, response);
                    break;
                default:
                    listServices(request, response);
            }
        } catch (SQLException ex) {
            handleError(request, response, "Database error occurred: " + ex.getMessage());
        }
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        try {
            // 1. Basic Service Data
            List<Service> services = serviceDAO.selectAllServices();
            request.setAttribute("services", services);

            // 2. Categories for Filtering
            List<String> categories = serviceDAO.getAllCategories();
            request.setAttribute("categories", categories);

            // 3. Service Statistics
            Map<String, Integer> stats = new HashMap<>();
            stats.put("total", serviceDAO.getTotalServices());
            stats.put("active", serviceDAO.getTotalServicesByStatus("ACTIVE"));
            stats.put("inactive", serviceDAO.getTotalServicesByStatus("INACTIVE"));
            request.setAttribute("stats", stats);

            // 4. Popular Services
            List<PopularService> popularServices = serviceDAO.getPopularServices();
            request.setAttribute("popularServices", popularServices);

            // 5. Monthly Performance Data
            Map<String, Object> monthlyStats = serviceDAO.getMonthlyStats();
            System.out.println("Monthly Stats Data:");
            System.out.println("Total Revenue: " + monthlyStats.get("totalRevenue"));
            System.out.println("Total Bookings: " + monthlyStats.get("totalBookings"));
            System.out.println("Chart Data Size: " + 
                ((List<?>)monthlyStats.get("chartData")).size());
            request.setAttribute("monthlyStats", monthlyStats);

            // Debug information
            System.out.println("=== Data Loading Status ===");
            System.out.println("Services: " + services.size() + " items");
            System.out.println("Categories: " + categories.size() + " items");
            System.out.println("Popular Services: " + popularServices.size() + " items");
            System.out.println("Monthly Stats: " + (monthlyStats != null ? "Loaded" : "Not loaded"));

            // Forward to JSP
            request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading services: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private Map<String, Integer> getServiceStatistics() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", serviceDAO.getTotalServices());
        stats.put("active", serviceDAO.getTotalServicesByStatus("ACTIVE"));
        stats.put("inactive", serviceDAO.getTotalServicesByStatus("INACTIVE"));
        return stats;
    }

    private List<PopularService> getPopularServices() throws SQLException {
        // Add this method to your ServiceDAO
        return serviceDAO.getPopularServices();
    }

    private void filterServices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        try {
            String category = request.getParameter("category");
            String status = request.getParameter("status");
            
            // Get filtered services
            List<Service> filteredServices;
            if ("all".equals(category)) {
                filteredServices = serviceDAO.selectAllServices();
            } else if (category != null && !category.isEmpty()) {
                filteredServices = serviceDAO.getServicesByCategory(category);
                request.setAttribute("selectedCategory", category);
            } else if (status != null && !status.isEmpty()) {
                filteredServices = serviceDAO.getServicesByStatus(status);
                request.setAttribute("selectedStatus", status);
            } else {
                filteredServices = serviceDAO.selectAllServices();
            }

            // Load all required data for the page
            List<String> categories = serviceDAO.getAllCategories();
            Map<String, Integer> stats = getServiceStatistics();
            List<PopularService> popularServices = serviceDAO.getPopularServices();
            Map<String, Object> monthlyStats = serviceDAO.getMonthlyStats();

            // Set all attributes
            request.setAttribute("services", filteredServices);
            request.setAttribute("categories", categories);
            request.setAttribute("stats", stats);
            request.setAttribute("popularServices", popularServices);
            request.setAttribute("monthlyStats", monthlyStats);

            // Debug information
            System.out.println("=== Filter Results ===");
            System.out.println("Category: " + category);
            System.out.println("Status: " + status);
            System.out.println("Filtered Services: " + filteredServices.size());

            // Forward to JSP
            request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in filterServices: " + e.getMessage());
            e.printStackTrace();
            handleError(request, response, "Error filtering services: " + e.getMessage());
        }
    }

    private void viewServiceDetails(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        try {
            int serviceId = Integer.parseInt(request.getParameter("id"));
            
            // Load service details
            Service service = serviceDAO.selectService(serviceId);
            if (service == null) {
                throw new ServletException("Service not found");
            }
            
            // Load related data
            Map<String, Object> performance = serviceDAO.getServicePerformanceMetrics(serviceId);
            List<Map<String, Object>> staffPerformance = serviceDAO.getStaffServicePerformance(serviceId);
            Map<String, Object> resources = serviceDAO.getServiceResourceRequirements(serviceId);
            List<Map<String, Object>> steps = serviceDAO.getServiceSteps(serviceId);

            // Set attributes
            request.setAttribute("service", service);
            request.setAttribute("performance", performance);
            request.setAttribute("staffPerformance", staffPerformance);
            request.setAttribute("resources", resources);
            request.setAttribute("steps", steps);

            // Forward back to services page with details
            request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            handleError(request, response, "Invalid service ID");
        } catch (Exception e) {
            handleError(request, response, "Error loading service details: " + e.getMessage());
        }
    }

    private void getServicePerformance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> metrics = serviceDAO.getServicePerformanceMetrics(serviceId);
        request.setAttribute("performanceMetrics", metrics);
        request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
    }

    private void getStaffPerformance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("id"));
        List<Map<String, Object>> staffPerformance = serviceDAO.getStaffServicePerformance(serviceId);
        request.setAttribute("staffPerformance", staffPerformance);
        request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
    }

    private void getResourceRequirements(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> resources = serviceDAO.getServiceResourceRequirements(serviceId);
        request.setAttribute("resources", resources);
        request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
    }

    private void getServiceSteps(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("id"));
        List<Map<String, Object>> steps = serviceDAO.getServiceSteps(serviceId);
        request.setAttribute("serviceSteps", steps);
        request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String message) 
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        String paramValue = request.getParameter(paramName);
        if (paramValue == null || paramValue.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


    private void addService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String image = request.getParameter("image");

        Service newService = new Service();
        newService.setName(name);
        newService.setPrice(price);
        newService.setDuration(duration);
        newService.setDescription(description);
        newService.setCategory(category);
        newService.setImage(image);
        newService.setStatus("ACTIVE");

        serviceDAO.addService(newService);
        response.sendRedirect("staff-services");
    }

    private void updateService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String image = request.getParameter("image");
        String status = request.getParameter("status");

        Service service = new Service();
        service.setId(id);
        service.setName(name);
        service.setPrice(price);
        service.setDuration(duration);
        service.setDescription(description);
        service.setCategory(category);
        service.setImage(image);
        service.setStatus(status);

        serviceDAO.updateService(service);
        response.sendRedirect("staff-services");
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        serviceDAO.deleteService(id);
        response.sendRedirect("staff-services");
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        serviceDAO.updateServiceStatus(id, status);
        response.sendRedirect("staff-services");
    }

    // Add method to handle filter form submission
    private void handleFilterForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String category = request.getParameter("category");
        String status = request.getParameter("status");
        String searchQuery = request.getParameter("search");
        
        // Get page parameters for pagination
        int page = getIntParameter(request, "page", 1);
        int limit = getIntParameter(request, "limit", 10);
        
        try {
            List<Service> filteredServices;
            
            // Apply filters
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                // Search takes precedence
                filteredServices = serviceDAO.searchServices(searchQuery, (page - 1) * limit, limit);
            } else if (category != null && !category.isEmpty() && !"all".equals(category)) {
                // Category filter
                filteredServices = serviceDAO.getServicesByCategory(category);
            } else if (status != null && !status.isEmpty()) {
                // Status filter
                filteredServices = serviceDAO.getServicesByStatus(status);
            } else {
                // No filter - get all services with pagination
                filteredServices = serviceDAO.getServicesWithPagination((page - 1) * limit, limit);
            }

            // Set filter parameters as attributes
            request.setAttribute("selectedCategory", category);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("searchQuery", searchQuery);
            request.setAttribute("currentPage", page);
            request.setAttribute("services", filteredServices);
            
            // Load other necessary data
            loadCommonData(request);
            
            // Forward to JSP
            request.getRequestDispatcher("/Frontend_Staff/services.jsp").forward(request, response);
            
        } catch (Exception e) {
            handleError(request, response, "Error applying filters: " + e.getMessage());
        }
    }

    // Helper method to load common data needed for the page
    private void loadCommonData(HttpServletRequest request) throws SQLException {
        List<String> categories = serviceDAO.getAllCategories();
        Map<String, Integer> stats = getServiceStatistics();
        List<PopularService> popularServices = serviceDAO.getPopularServices();
        Map<String, Object> monthlyStats = serviceDAO.getMonthlyStats();

        request.setAttribute("categories", categories);
        request.setAttribute("stats", stats);
        request.setAttribute("popularServices", popularServices);
        request.setAttribute("monthlyStats", monthlyStats);
    }
}
