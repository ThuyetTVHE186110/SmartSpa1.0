/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package controller;

import dal.AppointmentDAO;
import dal.AppointmentServiceDAO;
import dal.ServiceDAO;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Appointment;
import model.AppointmentService;
import model.Service;

/**
 * Appointment Management Servlet
 *
 * @author ADMIN
 */
public class AppointmentManagementServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            List<Appointment> appointments = appointmentDAO.getAll();
            request.setAttribute("appointmentList", appointments);
            
            ServiceDAO serviceDAO = new ServiceDAO();
            HashMap<Integer, List<Service>> appointmentServicesMap = new HashMap<>();
            AppointmentServiceDAO appointmentServiceDAO = new AppointmentServiceDAO();
            List<AppointmentService> appointmentServiceList = appointmentServiceDAO.getAllAppointmentServices();
            List<Service> serviceList = serviceDAO.selectAllServices();
            
            for (AppointmentService appointmentService : appointmentServiceList) {
                    int appointmentId = appointmentService.getAppointmentID();
                    Service service = serviceDAO.selectService(appointmentService.getServiceID()); // This is a method to fetch service details
                    // If this appointment ID already exists, add to the list of services
                    if (appointmentServicesMap.containsKey(appointmentId)) {
                        appointmentServicesMap.get(appointmentId).add(service);
                    } else {
                        // Otherwise, create a new list for this appointment ID
                        List<Service> services = new ArrayList<>();
                        services.add(service);
                        appointmentServicesMap.put(appointmentId, services);
                    }
            }
            request.setAttribute("serviceList", serviceList);            
            request.setAttribute("appointmentServicesMap", appointmentServicesMap);            
            request.getRequestDispatcher("appointmentManagement.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

//    private List<Service> listServices()
//            throws SQLException, IOException, ServletException {
//        ServiceDAO serviceDAO = new ServiceDAO();
//        List<Service> listServices = serviceDAO.selectAllServices();
//        return listServices;
//    }
}
