/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PersonDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Appointment;
import model.Person;

public class TherapistManagement extends HttpServlet {

    private static final int THERAPISTS_PER_PAGE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * THERAPISTS_PER_PAGE;
        PersonDAO personDAO = new PersonDAO();

//        // Retrieve therapists with roleID = 3
//        List<Person> therapists = personDAO.getTherapists(offset, THERAPISTS_PER_PAGE, 3); // 3 = roleID for therapists
//        for (Person therapist : therapists) {
//            // Get the status and appointments for each therapist
//            String status = personDAO.getTherapistStatus(therapist.getId());
//            therapist.setStatus(status); // Assuming `Person` class has a `status` field
//
//            // Retrieve and set each therapist's appointments
//            List<Appointment> appointments = personDAO.getAppointmentsByTherapist(therapist.getId());
//            therapist.setAppointments(appointments); // Add a setAppointments method in `Person` if needed
//        }
//
//        int totalTherapists = personDAO.countTherapistsByRole(3);
//        int totalPages = (int) Math.ceil((double) totalTherapists / THERAPISTS_PER_PAGE);
//
//        request.setAttribute("therapists", therapists);
//        request.setAttribute("currentPage", page);
//        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("Frontend_Staff/therapists.jsp").forward(request, response);
    }

}
