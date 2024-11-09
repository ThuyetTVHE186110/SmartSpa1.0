/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Appointment_Staff;

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
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
 *
 * @author ADMIN
 */
public class AppointmentManagementServlet extends HttpServlet {

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
            out.println("<title>Servlet AppointmentManagementServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AppointmentManagementServlet at " + request.getContextPath() + "</h1>");
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
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            List<Appointment> appointments = appointmentDAO.getByDate(LocalDate.now());
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
            List<Person> customerList = customerList();
            request.setAttribute("customerList", customerList);
            List<Appointment> upcoming = upcoming();
            request.setAttribute("upcoming", upcoming);
            PersonDAO personDAO = new PersonDAO();
            List<Person> staffList = personDAO.getPersonByRole("staff");
            request.setAttribute("searchDate", LocalDate.now());
            request.setAttribute("staffList", staffList);
            request.setAttribute("serviceList", serviceList);
            request.setAttribute("appointmentServicesMap", appointmentServicesMap);
            request.getRequestDispatcher("Frontend_Staff\\appointments.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error");
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
            List<Appointment> upcoming = upcoming();
            
            PersonDAO personDAO = new PersonDAO();
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            String action = request.getParameter("action");
            List<Appointment> appointments = null;
            ServiceDAO serviceDAO = new ServiceDAO();
            AppointmentServiceDAO appointmentServiceDAO = new AppointmentServiceDAO();
            HashMap<Integer, List<Service>> appointmentServicesMap = new HashMap<>();
            List<Service> serviceList = serviceDAO.selectAllServices();
            List<AppointmentService> appointmentServiceList = appointmentServiceDAO.getAllAppointmentServices();
            List<Person> staffList = personDAO.getPersonByRole("staff");
            request.setAttribute("staffList", staffList);
            switch (action) {
                case "customerSearch":
                    String searchTerm = request.getParameter("searchTerm").trim();
                    request.setAttribute("searchTerm", searchTerm);
                    appointments = appointmentDAO.getByCustomer(searchTerm);
                    break;
                case "cancel":
                    String delAppointment = request.getParameter("appointmentID");
                    int appointmentID = Integer.parseInt(delAppointment);
                    Appointment appointment = appointmentDAO.getAppointmentByID(appointmentID);
                    appointment.setStatus("Cancelled");
                    appointmentDAO.updateAppointment(appointment);
                    doGet(request, response);
                    break;
                case "edit":
//                    String editAppointment = request.getParameter("appointmentID");
//                    int editID = Integer.parseInt(editAppointment);
//                    String editDatetemp = request.getParameter("editDate");
//                    LocalDateTime editStart = LocalDateTime.parse(editDatetemp);
//                    String editTimetemp = request.getParameter("editTime");
//                    LocalDateTime editEnd = LocalDateTime.parse(editTimetemp);
//                    String editStatus = request.getParameter("editStatus");
//                    String editNote = request.getParameter("editNote");
//                    String personIDtp = request.getParameter("personID");
//                    int personID = Integer.parseInt(personIDtp);
//                    Person person = personDAO.getPersonByID(personID);
////                    Appointment appointment = new Appointment(editID, editStart, editEnd, LocalDateTime.MAX, editStatus, editNote, person, appointmentServiceList);
////                    appointmentDAO.updateAppointment(appointment);
//                    doGet(request, response);
//                    break;
                case "today":
                    appointments = appointmentDAO.getByDate(LocalDate.now());
                    request.setAttribute("searchDate", LocalDate.now());
                    break;
                case "tomorrow":
                    LocalDate tomorrow = LocalDate.now().plusDays(1);
                    appointments = appointmentDAO.getByDate(tomorrow);
                    request.setAttribute("searchDate", tomorrow);
                    break;
                case "this-week":
                    LocalDate week = LocalDate.now();
                    appointments = appointmentDAO.getByThisWeek(week);
                    request.setAttribute("searchDate", week);
                    break;
                case "searchDate":
                    String searchDateTemp = request.getParameter("searchDate");
                    if (!searchDateTemp.isBlank()) {
                        LocalDate searchDate = LocalDate.parse(searchDateTemp);
                        appointments = appointmentDAO.getByDate(searchDate);
                        request.setAttribute("searchDate", searchDate);
                    }
                    break;
                case "chooseStaff":
                    String selectedStaff = request.getParameter("selectedStaff");
                    if ("all-staff".equals(selectedStaff)) {
                        appointments = appointmentDAO.getAll();
                        request.setAttribute("selectedStaff", selectedStaff);
                    } else {
                        int staffID = Integer.parseInt(selectedStaff);
                        request.setAttribute("selectedStaff", staffID);
                    }
                    break;
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
            List<Person> customerList = customerList();
            request.setAttribute("customerList", customerList);
            request.setAttribute("upcoming", upcoming);
            request.setAttribute("serviceList", serviceList);
            request.setAttribute("appointmentServicesMap", appointmentServicesMap);
            request.getRequestDispatcher("Frontend_Staff\\appointments.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(controller.AppointmentManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DateTimeParseException e) {
            Logger.getLogger(controller.AppointmentManagementServlet.class.getName()).log(Level.SEVERE, null, e);
            doGet(request, response);
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
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private List<Appointment> upcoming(){
        return appointmentDAO.getCommingToday();
    }
    private final PersonDAO personDAO = new PersonDAO();
    private List<Person> customerList(){
        return  personDAO.getPersonByRole("Customer");
    }

}
