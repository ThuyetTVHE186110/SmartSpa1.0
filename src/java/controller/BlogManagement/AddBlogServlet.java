package controller.BlogManagement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import model.Blog;

/**
 *
 * @author PC
 */
public class AddBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("addBlog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        Part imagePart = request.getPart("image");

        try {
            BlogDAO blogDAO = new BlogDAO();
            Blog newBlog = new Blog();
            newBlog.setTitle(title);
            newBlog.setContent(content);
            newBlog.setDescription(description);
            newBlog.setCategory(category);

            String image = saveFile(imagePart, request);
            if (image != null) {
                newBlog.setImage(image);
            }

            blogDAO.createBlog(newBlog);
            request.setAttribute("successMessage", "Blog added successfully.");
            response.sendRedirect("blogManagement"); // Redirect to blog management page
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while adding the blog.");
            request.getRequestDispatcher("addBlog.jsp").forward(request, response);
        }
    }

    private String saveFile(Part filePart, HttpServletRequest request) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        String imagePath = getServletContext().getRealPath("/newUI/assets/img/");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        Files.copy(filePart.getInputStream(), Paths.get(imagePath).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
