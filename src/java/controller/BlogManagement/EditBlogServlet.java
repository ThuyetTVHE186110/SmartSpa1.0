/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.BlogManagement;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import model.Blog;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class EditBlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String blogIdParam = request.getParameter("id");
        if (blogIdParam != null) {
            try {
                int blogId = Integer.parseInt(blogIdParam);
                BlogDAO blogDAO = new BlogDAO();
                Blog blog = blogDAO.getBlogById(blogId);
                if (blog != null) {
                    request.setAttribute("blog", blog);
                    request.getRequestDispatcher("editBlog.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Blog not found.");
                    request.getRequestDispatcher("blogManagement").forward(request, response);
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Invalid blog ID.");
                request.getRequestDispatcher("blogManagement").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        int blogId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        Part imagePart = request.getPart("image");

        try {
            BlogDAO blogDAO = new BlogDAO();
            Blog updatedBlog = blogDAO.getBlogById(blogId);

            if (updatedBlog != null) {
                updatedBlog.setTitle(title);
                updatedBlog.setContent(content);
                updatedBlog.setDescription(description);
                updatedBlog.setCategory(category);

                // Save the image if present
                String image = saveFile(imagePart, request);
                if (image != null) {
                    updatedBlog.setImage(image);
                }

                // Update the blog in the database
                blogDAO.updateBlog(updatedBlog);

                // Use session for redirect message
                session.setAttribute("successMessage", "Blog updated successfully.");
                response.sendRedirect("blogManagement"); // Redirect to management page
            } else {
                request.setAttribute("errorMessage", "Blog not found.");
                request.getRequestDispatcher("editBlog.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the blog.");
            request.getRequestDispatcher("editBlog.jsp").forward(request, response);
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
