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
public class BookingServlet extends HttpServlet {

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
            out.println("<title>Servlet BookingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingServlet at " + request.getContextPath() + "</h1>");
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
        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            PersonDAO personDAO = new PersonDAO();
            List<Service> serviceList = serviceDAO.selectAllServices();
            List<Person> staffList = personDAO.getPersonByRole("Staff");
            request.setAttribute("staffList", staffList);
            request.setAttribute("serviceList", serviceList);
            request.setAttribute("today", LocalDate.now());
            request.getRequestDispatcher("booking.jsp").forward(request, response);
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
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            PersonDAO personDAO = new PersonDAO();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            Person customer = new Person();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            Person personCheck = personDAO.existCheck(customer);
            
            if (personCheck == null) {
                personDAO.insertPerson(customer);
                int nextID = personDAO.maxID();
                customer.setId(nextID);
            } else {
                customer.setId(personCheck.getId());
            }
            
            int serviceID = Integer.parseInt(request.getParameter("service"));
            ServiceDAO serviceDAO = new ServiceDAO();
            Service service = serviceDAO.selectService(serviceID);
            LocalDate date = LocalDate.parse(request.getParameter("appointmentDate"));
            LocalTime time = LocalTime.parse(request.getParameter("selectedTime"));
            LocalDateTime start = LocalDateTime.of(date, time);
            String note = request.getParameter("notes");
            Appointment appointment = new Appointment();
            appointment.setStart(start);
            appointment.setEnd(start.plusMinutes(service.getDuration()));
            appointment.setCreatedDate(LocalDateTime.now());
            appointment.setStatus("Scheduled");
            appointment.setNote(note);
            appointment.setCustomer(customer);
            appointmentDAO.addAppointment(appointment);
            int staffId = Integer.parseInt(request.getParameter("staff"));
            AppointmentServiceDAO serviceList = new AppointmentServiceDAO();
            serviceList.addAppointmentService(appointmentDAO.getMaxAppointmentID(), serviceID, staffId);
            request.setAttribute("success", "Booking successfully!");
            doGet(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BookingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
