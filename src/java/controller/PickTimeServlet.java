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
import java.io.PrintWriter;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.Person;
import model.Service;

/**
 *
 * @author ADMIN
 */
public class PickTimeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PickTimeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PickTimeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        ServiceDAO serviceDAO = new ServiceDAO();
        String serviceIDStr = request.getParameter("service");
        if (serviceIDStr == null) {
            response.sendRedirect("error.jsp");
        }
        if (serviceIDStr != null) {
            int serviceID = Integer.parseInt(serviceIDStr);
            try {
                Service service = serviceDAO.selectService(serviceID);
                request.setAttribute("service", service);
                request.getRequestDispatcher("pick-time.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(PickTimeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            String name = request.getParameter("name");
//            String email = request.getParameter("email");
//            String phone = request.getParameter("phone");
//            Appointment appointment = new Appointment();
//            AppointmentDAO appointmentDAO = new AppointmentDAO();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("h:mm a");
//            String appointmentDateStr = request.getParameter("appointmentDate");
//            String appointmentTimeStr = request.getParameter("appointmentTime");
//
//            // Parse the date using the formatter
//            LocalDate appointmentDate = LocalDate.parse(appointmentDateStr, formatter);
//            appointment.setAppointmentDate(appointmentDate);
//            LocalTime appointmentTime = LocalTime.parse(appointmentTimeStr, formatter1);
//            appointment.setAppointmentTime(appointmentTime);
//
//            //Get serviceID
//            String serviceIDStr = request.getParameter("serviceID");
//            int serviceID = Integer.parseInt(serviceIDStr);
//            Service service = new Service();
//            service.setId(serviceID);
//
//            Person customer = new Person();
//            customer.setName(name);
//            customer.setEmail(email);
//            customer.setPhone(phone);
//
//            PersonDAO personDAO = new PersonDAO();
//            Person existCustomer = personDAO.existCheck(customer);
//            if (existCustomer == null) {
//                personDAO.addPerson(customer);
//                int max = personDAO.maxID();
//                customer.setId(max);
//                appointment.setPerson(customer);
//            } else {
//                appointment.setPerson(existCustomer);
//            }
//            appointment.setCreatedDate(LocalDateTime.now());
//            appointment.setStatus("Scheduled");
//
//            appointmentDAO.addAppointment(appointment);
//
//            //Add bảng trung gian
//            AppointmentServiceDAO aSDAO = new AppointmentServiceDAO();
//            int maxID = appointmentDAO.getMaxAppointmentID();
//            aSDAO.addAppointmentService(maxID, service);
//            response.sendRedirect("appointment");
//        } catch (SQLException ex) {
//            Logger.getLogger(PickTimeServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
