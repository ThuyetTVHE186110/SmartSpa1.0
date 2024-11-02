/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package controller;

import dal.AppointmentDAO;
import dal.AppointmentServiceDAO;
import dal.PersonDAO;
import dal.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.AppointmentService;
import model.Person;
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
                Service service = serviceDAO.selectService(appointmentService.getService().getId()); // This is a method to fetch service details
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
            request.getRequestDispatcher("appointment-management.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PersonDAO personDAO = new PersonDAO();
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            AppointmentServiceDAO asdao = new AppointmentServiceDAO();
            String action = request.getParameter("action");
            List<Appointment> appointments = null;
            ServiceDAO serviceDAO = new ServiceDAO();
            AppointmentServiceDAO appointmentServiceDAO = new AppointmentServiceDAO();
            HashMap<Integer, List<Service>> appointmentServicesMap = new HashMap<>();
            List<Service> serviceList = serviceDAO.selectAllServices();
            List<AppointmentService> appointmentServiceList = appointmentServiceDAO.getAllAppointmentServices();
            switch (action) {
                case "customerSearch":
                    String searchTerm = request.getParameter("searchTerm").trim();
                    request.setAttribute("searchTerm", searchTerm);
                    appointments = appointmentDAO.getByCustomer(searchTerm);
                    break;
                case "remove":
                    String delAppointment = request.getParameter("appointmentID");
                    int appointmentID = Integer.parseInt(delAppointment);
                    asdao.deleteAppointmentServiceByAppointmentID(appointmentID);
                    appointmentDAO.deleteAppointment(appointmentID);
                    doGet(request, response);
                    break;
                case "edit":
                    String editAppointment = request.getParameter("appointmentID");
                    int editID = Integer.parseInt(editAppointment);
                    String editDatetemp = request.getParameter("editDate");
                    LocalDateTime editStart = LocalDateTime.parse(editDatetemp);
                    String editTimetemp = request.getParameter("editTime");
                    LocalDateTime editEnd = LocalDateTime.parse(editTimetemp);
                    String editStatus = request.getParameter("editStatus");
                    String editNote = request.getParameter("editNote");
                    String personIDtp = request.getParameter("personID");
                    int personID = Integer.parseInt(personIDtp);
                    Person person = personDAO.getPersonByID(personID);
                    Appointment appointment = new Appointment(editID, editStart, editEnd, LocalDateTime.MAX, editStatus, editNote, person, appointmentServiceList);
//                    appointmentDAO.updateAppointment(appointment);
                    doGet(request, response);
                    break;
                case "today":
                    appointments = appointmentDAO.getByDate(LocalDate.now());
                    for (AppointmentService appointmentService : appointmentServiceList) {
                        int appointmentId = appointmentService.getAppointmentID();
                        Service service = serviceDAO.selectService(appointmentService.getService().getId()); // This is a method to fetch service details
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
                    request.setAttribute("searchDate", LocalDate.now());
                    break;
                case "searchDate":
                    String searchDateTemp = request.getParameter("searchDate");
                    if (!searchDateTemp.isBlank()) {
                        LocalDate searchDate = LocalDate.parse(searchDateTemp);
                        appointments = appointmentDAO.getByDate(searchDate);
                        request.setAttribute("searchDate", searchDate);
                    } else {
                        doGet(request, response);
                    }
                    break;
                default:
                    throw new AssertionError();
            }
            request.setAttribute("appointmentList", appointments);
            for (AppointmentService appointmentService : appointmentServiceList) {
                int appointmentId = appointmentService.getAppointmentID();
                Service service = serviceDAO.selectService(appointmentService.getService().getId()); // This is a method to fetch service details
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
            request.getRequestDispatcher("appointment-management.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
