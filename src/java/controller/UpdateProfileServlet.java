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

        // Retrieve form fields
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String newEmail = request.getParameter("email");
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        String genderStr = request.getParameter("gender");

        // Update profile details
        if (fullName != null) {
            person.setName(fullName);
        }
        if (phone != null) {
            person.setPhone(phone);
        }
        if (address != null) {
            person.setAddress(address);
        }

        // Handle email update with error checking
        try {
            if (newEmail != null && !newEmail.equals(person.getEmail())) {
                boolean emailUpdated = new PersonDAO().updateEmailAndUsername(person.getId(), newEmail);
                if (emailUpdated) {
                    person.setEmail(newEmail);
                    account.setUsername(newEmail);
                } else {
                    request.setAttribute("errorMessage", "Failed to update email.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating email. Please try again.");
        }

        // Handle date of birth
        if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
            try {
                java.sql.Date dateOfBirth = java.sql.Date.valueOf(dateOfBirthStr);
                person.setDateOfBirth(dateOfBirth);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Invalid date format.");
            }
        }

        // Handle gender
        if (genderStr != null && !genderStr.isEmpty()) {
            person.setGender(genderStr.charAt(0));
        }

        try {
            // Check if the "deleteImage" parameter is set to delete the profile image
            if ("true".equals(request.getParameter("deleteImage"))) {
                String currentImage = person.getImage();
                if (currentImage != null && !currentImage.isEmpty() && !currentImage.equals("default-avartar.jpg")) {
                    // Remove the file from the server directory
                    String uploadPath = getServletContext().getRealPath("/") + "img";
                    File file = new File(uploadPath, currentImage);

                    if (file.exists() && !file.delete()) {
                        request.setAttribute("errorMessage", "Failed to delete the image file.");
                    }
                }

                // Remove the image reference from the database using the DAO method
                new PersonDAO().deleteImage(person.getId());
                person.setImage(null); // Clear the image reference in the session object
            } else {
                // Handle profile image upload if a new file is uploaded
                Part filePart = request.getPart("profileImage");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = filePart.getSubmittedFileName();
                    String uploadPath = getServletContext().getRealPath("/") + "img";

                    // Ensure the directory exists
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    // Save the uploaded file to the server
                    filePart.write(uploadPath + File.separator + fileName);

                    // Update image in session and database
                    person.setImage(fileName);
                    new PersonDAO().updateImage(person.getId(), fileName);
                }
            }

            // Update other profile fields in the database
            new PersonDAO().updatePerson(person);

            // Update the session with the updated person info
            session.setAttribute("person", person);
            session.setAttribute("successMessage", "Profile updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating your profile. Please try again.");
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);
            return;
        }

        // Redirect back to profile page to view updated info
        response.sendRedirect("userProfile.jsp?tab=edit");
    }

}
