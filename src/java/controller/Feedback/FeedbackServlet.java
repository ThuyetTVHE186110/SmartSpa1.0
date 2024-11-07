/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Feedback;

import dal.FeedbackDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Feedback;
import model.Person;
import model.Service;

/**
 *
 * @author admin
 */
public class FeedbackServlet extends HttpServlet {

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
            out.println("<title>Servlet FeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serviceIdParam = request.getParameter("serviceId");

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ServiceDAO serviceDAO = new ServiceDAO();

        ArrayList<Feedback> feedback = feedbackDAO.getFeedback();
        ArrayList<Feedback> feedbackS;
       if (serviceIdParam == null || "all".equals(serviceIdParam)) {
            // Lấy toàn bộ feedback nếu serviceId là "all" hoặc không có
            feedback = feedbackDAO.getFeedback();
        } else {
            // Lấy feedback của dịch vụ cụ thể dựa trên serviceId
            int serviceId = Integer.parseInt(serviceIdParam);
            feedbackS = feedbackDAO.getFeedbackByService(serviceId);
        }

        List<Service> service = null;
        try {
            service = serviceDAO.selectAllServices();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("service", service);
        request.setAttribute("feedback", feedback);
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
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

        HttpSession session = request.getSession();
        Account loggedInAccount = (Account) session.getAttribute("account");

        if (loggedInAccount != null) {
            String content = request.getParameter("content");
            String service = request.getParameter("service");
            int serviceId = Integer.parseInt(service);
            Person person = loggedInAccount.getPersonInfo(); // Assuming it retrieves the linked Person object
            if (person != null) {
                FeedbackDAO feedbackDAO = new FeedbackDAO();
                boolean isAdded = feedbackDAO.createFeedback(person.getId(), content, serviceId);
                if (isAdded) {
                    response.sendRedirect("feedback?status=success");
                } else {
                    response.sendRedirect("feedback?status=error");
                }
            }
        } else {
            request.setAttribute("message", "You need to login to add feedback");
            request.getRequestDispatcher("login").forward(request, response);
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
