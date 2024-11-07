/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.PersonDAO;
import dal.ReferralDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Person;
import jakarta.servlet.http.HttpSession;
import model.Account;
import java.sql.SQLException;
import model.Referral;

/**
 *
 * @author PC
 */
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("adminLogin.jsp"); // Redirect to login if not logged in
            return;
        }

        // Fetch the account and person info
        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();

        ReferralDAO referralDAO = new ReferralDAO();
        Referral referral = referralDAO.getReferralByStaff(person.getId());
        request.setAttribute("referral", referral);
        // Set attributes for display in JSP
        request.setAttribute("person", person);
        request.setAttribute("displayName", person.getName());

        // Forward to JSP or render directly in response (choose one)
        request.getRequestDispatcher("userProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        // Retrieve and update the person info from the form data
        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();
        person.setName(request.getParameter("fullName"));
        person.setEmail(request.getParameter("email"));
        person.setPhone(request.getParameter("phone"));
        person.setAddress(request.getParameter("address"));
        person.setGender(request.getParameter("gender").charAt(0));
        // Add any additional fields here

        // Update the database
        try {
            new PersonDAO().updatePerson(person);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating your profile.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Set a success message
        session.setAttribute("successMessage", "Profile updated successfully.");

        // Redirect to profile page or stay on edit page
        response.sendRedirect("userProfile");
    }
}
