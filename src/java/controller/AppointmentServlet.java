// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.Person;
import model.Service;
import com.google.gson.Gson;
/**
 * Appointment Servlet
 *
 * @author ADMIN
 */
public class AppointmentServlet extends HttpServlet {

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
            ServiceDAO serviceDAO = new ServiceDAO();
            PersonDAO personDAO = new PersonDAO();
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            List<Appointment> appointmentList = appointmentDAO.getAll();
            request.setAttribute("appointmentList", appointmentList);
            List<Service> serviceList = serviceDAO.selectAllServices();
            List<Person> staffList = personDAO.getPersonByRole("Staff");
            request.setAttribute("staffList", staffList);
            request.setAttribute("serviceList", serviceList);
            request.getRequestDispatcher("appointment1.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
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
        //Empty
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
