/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PersonDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import model.Account;
import model.Person;

@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        Person person = account.getPersonInfo();

        if (person == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        // Update profile fields
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        String genderStr = request.getParameter("gender");
        // Handle other fields as needed, like phone, address, etc.
        if (fullName != null) {
            person.setName(fullName);
        }
        if (phone != null) {
            person.setPhone(phone);
        }
        if (address != null) {
            person.setAddress(address);
        }
        if (email != null) {
            person.setEmail(email);
        }
        if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            try {
                java.sql.Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthStr); // Convert String to Date
                person.setDateOfBirth(dateOfBirth);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                // Handle invalid date format here if needed
            }
        }
        if (genderStr != null && !genderStr.isEmpty()) {
            person.setGender(genderStr.charAt(0));
        }
        // Set other fields to `person` object similarly

        try {
            // Handle profile image upload
            Part filePart = request.getPart("profileImage");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String uploadPath = getServletContext().getRealPath("/") + "img";

                // Ensure the directory exists
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                // Save file to directory
                filePart.write(uploadPath + File.separator + fileName);

                // Update image in session and database
                person.setImage(fileName);
                new PersonDAO().updateImage(person.getId(), fileName);
            }

            // Handle image deletion if requested
            if ("true".equals(request.getParameter("deleteImage"))) {
                person.setImage(null);
                new PersonDAO().updateImage(person.getId(), null);
            }
            new PersonDAO().updatePerson(person);
            // Save the updated person info to the session
            session.setAttribute("person", person);

        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally add a custom error message or redirect to an error page
            request.setAttribute("errorMessage", "An error occurred while updating your profile. Please try again.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Redirect back to profile page to view updated info
        response.sendRedirect("userProfile.jsp?tab=edit");
    }

}
