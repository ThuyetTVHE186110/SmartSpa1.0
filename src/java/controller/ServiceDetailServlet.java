package controller;

import dal.ServiceDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.Service;

@WebServlet(name = "ServiceDetailServlet", urlPatterns = {"/service-detail"})
public class ServiceDetailServlet extends HttpServlet {
   
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
            showServiceDetail(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errorMessage", "Error loading service details: " + ex.getMessage());
            response.sendRedirect("services");
        }
    }

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
            RequestDispatcher dispatcher = request.getRequestDispatcher("servicedetails.jsp");
            dispatcher.forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid service ID format");
            response.sendRedirect("services");
        } catch (ServletException e) {
            request.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect("services");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("services");
    }
} 