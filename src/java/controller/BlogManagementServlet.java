package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;


import model.Account;
import model.Blog;
import dal.BlogDAO;

@MultipartConfig
public class BlogManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Redirect to error page if session is null or account is not in session
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("error"); // Custom error page
            return;
        }
        Account account = (Account) session.getAttribute("account");

//        if (account == null || account.getRole() != 3) {
//            response.sendRedirect("roleError");
//            return;
//        }

        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs;

        try {
            blogs = blogDAO.getBlogsByStaffID(account.getPersonInfo().getId());
            System.out.println("Blogs size: " + blogs.size()); // Debug line

            request.setAttribute("blogs", blogs);
            request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve blog posts.");
            request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        Account account = (Account) session.getAttribute("account");
//        if (account.getRole() != 3) { // Assuming role 3 is for staff members
//            response.sendRedirect("roleError");
//            return;
//        }

        BlogDAO blogDAO = new BlogDAO();
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                // Handle adding a new blog
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String description = request.getParameter("description");
                String category = request.getParameter("category");
                Part imagePart = request.getPart("image");

                // Save the main blog image
                String image = saveFile(imagePart, request);

                // Retrieve author image from logged-in user's profile
                String authorImage = account.getPersonInfo().getImage(); // Ensure this is set correctly

                // Create a new Blog object with the necessary details
                Blog newBlog = new Blog();
                newBlog.setTitle(title);
                newBlog.setContent(content);
                newBlog.setDescription(description);
                newBlog.setCategory(category);
                newBlog.setStaffID(account.getPersonInfo().getId()); // Link blog to the logged-in staff
                newBlog.setImage(image);
                newBlog.setAuthorImage(authorImage); // Set the author image directly from profile

                // Insert into the database
                blogDAO.createBlog(newBlog);

                session.setAttribute("successMessage", "Blog added successfully.");
                response.sendRedirect("blogManagement");

            } else if ("edit".equals(action)) {
                // Handle editing an existing blog
                int blogId = Integer.parseInt(request.getParameter("id"));
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String description = request.getParameter("description");
                String category = request.getParameter("category");
                Part imagePart = request.getPart("image");
                Part authorImagePart = request.getPart("authorImage");

                // Save images if they exist
                String image = saveFile(imagePart, request);
                String authorImage = saveFile(authorImagePart, request);

                // Get the blog to update
                Blog updatedBlog = blogDAO.getBlogById(blogId);
                if (updatedBlog != null) {
                    updatedBlog.setTitle(title);
                    updatedBlog.setContent(content);
                    updatedBlog.setDescription(description);
                    updatedBlog.setCategory(category);

                    // Only set new images if they were uploaded
                    if (image != null) {
                        updatedBlog.setImage(image);
                    }
                    if (authorImage != null) {
                        updatedBlog.setAuthorImage(authorImage);
                    }

                    // Update blog in the database
                    blogDAO.updateBlog(updatedBlog);
                    session.setAttribute("successMessage", "Blog updated successfully.");
                } else {
                    session.setAttribute("errorMessage", "Blog not found.");
                }
                response.sendRedirect("blogManagement");

            } else if ("delete".equals(action)) {
                // Handle deleting a blog
                int blogId = Integer.parseInt(request.getParameter("id"));
                blogDAO.deleteBlog(blogId);
                session.setAttribute("successMessage", "Blog deleted successfully.");
                response.sendRedirect("blogManagement");

            } else {
                response.sendRedirect("blogManagement");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An SQL error occurred: " + e.getMessage());
            request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
        }
    }

    private String saveFile(Part filePart, HttpServletRequest request) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }

        String imagePath = getServletContext().getRealPath("/newUI/assets/img/");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, Paths.get(imagePath).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

}
