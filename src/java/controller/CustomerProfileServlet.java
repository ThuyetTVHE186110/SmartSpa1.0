package controller;

import dal.AccountDAO;
import dal.AppointmentDAO;
import dal.PersonDAO;
import dal.PaymentDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Paths;
import java.util.List;
import model.Account;
import model.Appointment;
import model.Person;
import model.Payment;
import org.mindrot.jbcrypt.BCrypt;

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
        String currentTab = request.getParameter("currentTab");

        // Check if action is null before proceeding
        if (action == null) {
            session.setAttribute("errorMessage", "Action parameter is missing.");
            response.sendRedirect("customerProfile?tab=settings");
            return;
        }

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        if ("cancel".equals(action)) {
            int id = Integer.parseInt(request.getParameter("appointmentID"));
            appointmentDAO.updateStatus("Cancelled", id);
            doGet(request, response);
            return;
        }

        try {
            if ("uploadImage".equals(action)) {
                // Handle image upload
                Part filePart = request.getPart("profileImage");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String imagePath = getServletContext().getRealPath("/newUI/assets/img/") + fileName;

                    // Save the file on the server
                    filePart.write(imagePath);

                    // Update the database with the new image file name
                    person.setImage(fileName);
                    PersonDAO personDAO = new PersonDAO();
                    personDAO.updateImage(person.getId(), fileName);

                    // Update the session attribute
                    session.setAttribute("person", person);
                    session.setAttribute("successMessage", "Profile picture updated successfully.");
                } else {
                    session.setAttribute("errorMessage", "Please select an image file.");
                }
                response.sendRedirect("customerProfile?tab=settings");
                return;
            } else if ("changePassword".equals(action)) {
                String currentPassword = request.getParameter("password");
                String newPassword = request.getParameter("newPassword");
                String confirmNewPassword = request.getParameter("confirmNewPassword");

                if (!newPassword.equals(confirmNewPassword)) {
                    request.setAttribute("errorMessage", "New passwords do not match.");
                    request.setAttribute("currentTab", "preferences");
                    request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                    return;
                }

                // Validate the current password with BCrypt
                if (!BCrypt.checkpw(currentPassword, account.getPassword())) {
                    request.setAttribute("errorMessage", "Current password is incorrect.");
                    request.setAttribute("currentTab", "preferences");
                    request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
                    return;
                }

                // Hash the new password using BCrypt
                String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                AccountDAO accountDAO = new AccountDAO();
                if (accountDAO.updatePassword(account.getUsername(), hashedNewPassword)) {
                    account.setPassword(hashedNewPassword); // Update the password in the session
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
        request.setAttribute("commingup", commingup);
        // Forward to customerProfile.jsp
        PaymentDAO paymentDAO = new PaymentDAO();
        List<Payment> payments = paymentDAO.getPaymentsByPersonId(person.getId());
        request.setAttribute("payments", payments);

        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
    }
}
