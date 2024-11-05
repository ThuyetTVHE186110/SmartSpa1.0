package controller.BlogManagement;

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
import java.util.ArrayList;

@MultipartConfig
public class BlogManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if session is valid and if the user is logged in
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("error"); // Redirect to a custom error page if session is invalid
            return;
        }

        Account account = (Account) session.getAttribute("account");
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs = new ArrayList<>();
        List<String> categories;

        try {
            // Check if a specific blog ID is provided for editing
            String blogIdParam = request.getParameter("id");
            if (blogIdParam != null) {
                try {
                    int blogId = Integer.parseInt(blogIdParam);
                    // Retrieve the blog by ID for editing
                    Blog blog = blogDAO.getBlogById(blogId);
                    if (blog != null) {
                        request.setAttribute("blog", blog);
                        request.getRequestDispatcher("editBlog").forward(request, response);
                        return;
                    } else {
                        request.setAttribute("error", "Blog not found.");
                    }
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Invalid blog ID.");
                }
            } else {
                // No specific blog ID provided, so continue with regular blog list retrieval

                // Retrieve all available categories
                categories = blogDAO.getAllCategories();
                request.setAttribute("categories", categories);

                // Retrieve filter, search, and category parameters from the request
                String filter = request.getParameter("filter");
                String keyword = request.getParameter("search");
                String category = request.getParameter("category");

                if (category != null && !category.equals("all")) {
                    // Filter blogs by selected category
                    blogs = blogDAO.getBlogsByCategory(category);
                    request.setAttribute("viewOnly", true);
                } else if (keyword != null && !keyword.trim().isEmpty()) {
                    // Search for blogs if a keyword is provided
                    blogs = blogDAO.searchBlogs2(keyword);
                    request.setAttribute("viewOnly", true);
                } else if ("all".equalsIgnoreCase(filter)) {
                    // Display all blogs if 'all' filter is selected
                    blogs = blogDAO.getAllBlogs();
                    request.setAttribute("viewOnly", true);
                } else {
                    // Display blogs created by the logged-in user
                    blogs = blogDAO.getBlogsByStaffID(account.getPersonInfo().getId());
                    request.setAttribute("viewOnly", false);
                }

                // Set attributes for blogs and forward to the blog management JSP page
                request.setAttribute("blogs", blogs);
                request.getRequestDispatcher("blogManagement.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            // Log the error and set an error message attribute
            e.printStackTrace();
            request.setAttribute("error", "Unable to retrieve blog posts or categories. Please try again later.");
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
                String authorImage = account.getPersonInfo().getImage();

                // Create a new Blog object with the necessary details
                Blog newBlog = new Blog();
                newBlog.setTitle(title);
                newBlog.setContent(content);
                newBlog.setDescription(description);
                newBlog.setCategory(category);
                newBlog.setStaffID(account.getPersonInfo().getId()); // Link blog to the logged-in staff
                newBlog.setImage(image);
                newBlog.setAuthorImage(authorImage);

                // Insert into the database
                blogDAO.createBlog(newBlog);

                // Redirect to blog management page with success message
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

                    // Forward to editBlog.jsp with updated blog data
                    request.setAttribute("blog", updatedBlog);
                    request.getRequestDispatcher("editBlog.jsp").forward(request, response);

                } else {
                    session.setAttribute("errorMessage", "Blog not found.");
                    response.sendRedirect("blogManagement");
                }

            } else if ("delete".equals(action)) {
                // Handle deleting a blog
                int blogId = Integer.parseInt(request.getParameter("id"));
                blogDAO.deleteBlog(blogId);
                session.setAttribute("successMessage", "Blog deleted successfully.");

                // Redirect to blog management page
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

    private void updateBlogsList(HttpServletRequest request, BlogDAO blogDAO, Account account) throws SQLException {
        List<Blog> blogs;
        blogs = blogDAO.getBlogsByStaffID(account.getPersonInfo().getId()); // Hoặc điều chỉnh tùy vào logic lọc của bạn
        request.setAttribute("blogs", blogs);
    }
}
