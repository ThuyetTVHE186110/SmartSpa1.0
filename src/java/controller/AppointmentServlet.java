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

/**
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
            List<Service> serviceList = serviceDAO.selectAllServices();
            request.setAttribute("serviceList", serviceList);
            request.getRequestDispatcher("appointment.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Appointment appointment = new Appointment();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("h:mm a");
        String appointmentDateStr = request.getParameter("appointmentDate");
        String appointmentTimeStr = request.getParameter("appointmentTime");
        try {
            // Parse the date using the formatter
            LocalDate appointmentDate = LocalDate.parse(appointmentDateStr, formatter);
            appointment.setAppointmentDate(appointmentDate);
            LocalTime appointmentTime = LocalTime.parse(appointmentTimeStr, formatter1);
            appointment.setAppointmentTime(appointmentTime);
        } catch (DateTimeParseException e) {
        }
        //Get serviceID
        String serviceIDStr = request.getParameter("serviceID");
        int serviceID = Integer.parseInt(serviceIDStr);
        Service service = new Service();
        service.setId(serviceID);

        Person customer = new Person();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);

        PersonDAO personDAO = new PersonDAO();
        Person existCustomer = personDAO.existCheck(customer);
        if (existCustomer == null) {
            personDAO.addPerson(customer);
            appointment.setPerson(customer);
        } else {
            appointment.setPerson(existCustomer);
        }
        appointment.setCreatedDate(LocalDateTime.now());
        appointment.setStatus("Schedule");

        appointmentDAO.addAppointment(appointment);

        //Add bảng trung gian
        AppointmentServiceDAO aSDAO = new AppointmentServiceDAO();
        int maxID = appointmentDAO.getMaxAppointmentID();
        aSDAO.addAppointmentService(maxID + 1, service);
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
}
