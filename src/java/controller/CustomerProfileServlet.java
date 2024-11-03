package controller;

import dal.AccountDAO;
import dal.AppointmentDAO;
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
import java.util.List;
import model.Account;
import model.Appointment;
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
        String currentTab = request.getParameter("currentTab"); // Get the current tab parameter

        try {
            if ("changePassword".equals(action)) {
                String currentPassword = request.getParameter("password");
                String newPassword = request.getParameter("newPassword");
                String confirmNewPassword = request.getParameter("confirmNewPassword");

                if (!newPassword.equals(confirmNewPassword)) {
                    request.setAttribute("errorMessage", "New passwords do not match.");
                    request.setAttribute("currentTab", "preferences");
                    request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                    return;
                }

                if (!account.getPassword().equals(currentPassword)) {
                    request.setAttribute("errorMessage", "Current password is incorrect.");
                    request.setAttribute("currentTab", "preferences");
                    request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                    return;
                }

                AccountDAO accountDAO = new AccountDAO();
                if (accountDAO.updatePassword(account.getUsername(), newPassword)) {
                    account.setPassword(newPassword);
                    session.setAttribute("successMessage", "Password changed successfully.");
                    response.sendRedirect("customerProfile?tab=preferences");
                } else {
                    request.setAttribute("errorMessage", "Failed to update password.");
                    request.setAttribute("currentTab", "preferences");
                    request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                }
            } else {
                // Handle profile update
                String fullName = request.getParameter("fullName");
                String dateOfBirth = request.getParameter("dateOfBirth");
                String gender = request.getParameter("gender");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");

                person.setName(fullName);
                if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                    try {
                        person.setDateOfBirth(java.sql.Date.valueOf(dateOfBirth));
                    } catch (IllegalArgumentException e) {
                        request.setAttribute("errorMessage", "Invalid date format. Please enter a valid date.");
                        request.setAttribute("currentTab", "settings");
                        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                        return;
                    }
                } else {
                    person.setDateOfBirth(null);
                }
                if (gender != null && !gender.isEmpty()) {
                    person.setGender(gender.charAt(0));
                } else {
                    person.setGender('U');
                }
                person.setPhone(phone);
                person.setEmail(email);
                person.setAddress(address);

                PersonDAO personDAO = new PersonDAO();
                personDAO.updatePerson(person);
                session.setAttribute("person", person);

                session.setAttribute("successMessage", "Profile updated successfully.");
                response.sendRedirect("customerProfile?tab=" + (currentTab != null ? java.net.URLEncoder.encode(currentTab, "UTF-8") : "settings"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating your information.");
            request.setAttribute("currentTab", currentTab);
            request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();
        List<Appointment> history = appointmentDAO.getHistoryCustomer(person.getId());
        request.setAttribute("history", history);

        List<Appointment> commingup = appointmentDAO.getCommingCustomer(person.getId());
//        commingup.get(0).getStart().toLocalTime();
        request.setAttribute("coming-up", commingup);
        // Forward to customerProfile.jsp
        PaymentDAO paymentDAO = new PaymentDAO();
        List<Payment> payments = paymentDAO.getPaymentsByPersonId(person.getId());
        request.setAttribute("payments", payments);

        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
    }
}
