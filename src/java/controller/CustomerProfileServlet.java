package controller;

import dal.AccountDAO;
import dal.PersonDAO;
import dal.PaymentDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Person;
import model.Payment;

public class CustomerProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();
        String action = request.getParameter("action");

        try {
            if ("changePassword".equals(action)) {
                // Handle password change
                String currentPassword = request.getParameter("currentPassword");
                String newPassword = request.getParameter("newPassword");

                if (!account.getPassword().equals(currentPassword)) {
                    request.setAttribute("error", "Current password is incorrect.");
                    request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                    return;
                }

                // Update password if valid
                AccountDAO accountDAO = new AccountDAO();
                accountDAO.updatePassword(account.getUsername(), newPassword);
                account.setPassword(newPassword);  // Update password in session

                request.setAttribute("successMessage", "Password changed successfully.");
                response.sendRedirect("customerProfile");

            } else {
                // Handle profile update
                String fullName = request.getParameter("fullName");
                String dateOfBirth = request.getParameter("dateOfBirth");
                String gender = request.getParameter("gender");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");

                person.setName(fullName);
                // Check if dateOfBirth is valid before setting
                if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                    try {
                        person.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth));
                    } catch (IllegalArgumentException e) {
                        request.setAttribute("error", "Invalid date format. Please enter a valid date.");
                        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                        return;
                    }
                } else {
                    // Handle the case where dateOfBirth might be optional or not provided
                    person.setDateOfBirth(null);
                }
                // Set gender only if it is not null and not empty
                if (gender != null && !gender.isEmpty()) {
                    person.setGender(gender.charAt(0));
                } else {
                    person.setGender('U'); // Default 'U' for unknown or retain the current gender if null isn't intended to reset
                }
                person.setPhone(phone);
                person.setEmail(email);
                person.setAddress(address);

                PersonDAO personDAO = new PersonDAO();
                personDAO.updatePerson(person);
                session.setAttribute("person", person);

                request.setAttribute("successMessage", "Profile updated successfully.");
                response.sendRedirect("customerProfile");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating your information.");
            request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        // Get payment history
        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();
        PaymentDAO paymentDAO = new PaymentDAO();
        List<Payment> payments = paymentDAO.getPaymentsByPersonId(person.getId());
        request.setAttribute("payments", payments);

        // Forward to customerProfile.jsp
        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
    }
}
