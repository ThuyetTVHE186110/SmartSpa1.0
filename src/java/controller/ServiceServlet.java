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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Discount;
import model.Service;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Asus
 */
public class ServiceServlet extends HttpServlet {
   
    
   private ServiceDAO serviceDAO;

   @Override
    public void init() {
        serviceDAO = new ServiceDAO(); // Initializing ServiceDAO instance
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
        } catch (SQLException ex) {
            throw new ServletException(ex);
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

    // Insert a new service into the database
    private void insertService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        String description = request.getParameter("description");
        String image= request.getParameter("image"); // Fetch discount
        Service newService = new Service(name, price, duration,description,image);
        serviceDAO.addService(newService);
        response.sendRedirect("services?action=list");
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

}
