<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.Account" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // No need to declare session manually; it's already available in JSP
    // You can directly use session
    if (session == null || session.getAttribute("account") == null) {
        // Redirect to login page if session is not found or account is not in session
        response.sendRedirect("adminLogin.jsp");
    } else {
        // Get the account object from session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 1 || account.getRole() == 2 || account.getRole() == 3) {
            // Allow access to the page (do nothing and let the JSP render)
        } else {
            // Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            request.getRequestDispatcher("error").forward(request, response);
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Account Detail - SmartBeautySpa</title>
        <meta content="" name="description">
        <meta content="" name="keywords">

        <!-- Favicons -->
        <link href="assets/img/favicon.png" rel="icon">
        <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

        <!-- Google Fonts -->
        <link href="https://fonts.gstatic.com" rel="preconnect">
        <link
            href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
            rel="stylesheet">

        <!-- Vendor CSS Files -->
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
        <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
        <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
        <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
        <link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">

        <!-- Template Main CSS File -->
        <link href="assets/css/style.css" rel="stylesheet">
        <!-- Vendor JS Files -->
        <script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/chart.js/chart.umd.js"></script>
        <script src="assets/vendor/echarts/echarts.min.js"></script>
        <script src="assets/vendor/quill/quill.min.js"></script>
        <script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
        <script src="assets/vendor/tinymce/tinymce.min.js"></script>
        <script src="assets/vendor/php-email-form/validate.js"></script>

        <!-- Template Main CSS File -->
        <link href="assets/css/style.css" rel="stylesheet">
        <!-- Vendor JS Files -->
        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/main.js"></script>
        <script src="assets/ckeditor/ckeditor.js"></script>
    </head>
    <body>
        <jsp:include page="headerHTML.jsp" />

        <!-- Sidebar -->
        <jsp:include page="sideBar.jsp" />

        <div class="container mt-4">
            <h1>Edit Blog</h1>

            <form action="blogManagement" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="id" value="${blog.id}">

                <div class="mb-3">
                    <label for="editBlogTitle" class="form-label">Title</label>
                    <input type="text" class="form-control" id="editBlogTitle" name="title" value="${blog.title}" required>
                </div>

                <div class="mb-3">
                    <label for="editBlogContent" class="form-label">Content</label>
                    <textarea class="form-control" id="editBlogContent" name="content" rows="5">${blog.content}</textarea>
                </div>

                <div class="mb-3">
                    <label for="editBlogDatePosted" class="form-label">Date Posted</label>
                    <input type="date" class="form-control" id="editBlogDatePosted" name="datePosted" value="${blog.datePosted}" required>
                </div>

                <div class="mb-3">
                    <label for="editBlogDescription" class="form-label">Description</label>
                    <textarea class="form-control" id="editBlogDescription" name="description" rows="3">${blog.description}</textarea>
                </div>

                <div class="mb-3">
                    <label for="editBlogCategory" class="form-label">Category</label>
                    <input type="text" class="form-control" id="editBlogCategory" name="category" value="${blog.category}">
                </div>

                <div class="mb-3">
                    <label for="editBlogViews" class="form-label">Views</label>
                    <input type="number" class="form-control" id="editBlogViews" name="views" value="${blog.views}" readonly>
                </div>

                <div class="mb-3">
                    <label for="editBlogCommentsCount" class="form-label">Comments Count</label>
                    <input type="number" class="form-control" id="editBlogCommentsCount" name="commentsCount" value="${blog.commentsCount}" readonly>
                </div>

                <div class="mb-3">
                    <label for="editBlogAuthorName" class="form-label">Author</label>
                    <input type="text" class="form-control" id="editBlogAuthorName" name="authorName" value="${blog.authorName}" readonly>
                </div>

                <div class="mb-3">
                    <label for="editBlogImage" class="form-label">Image</label>
                    <input type="file" class="form-control" id="editBlogImage" name="image">
                    <img id="editBlogImagePreview" src="assets/img/${blog.image}" alt="Image Preview" style="display:block; margin-top:10px; max-width:100px;">
                </div>

                <div class="mb-3">
                    <label for="editBlogAuthorImage" class="form-label">Author Image</label>
                    <input type="file" class="form-control" id="editBlogAuthorImageInput" name="authorImage">
                    <img id="editBlogAuthorImage" src="assets/img/${blog.authorImage}" alt="Author Image Preview" style="display:block; margin-top:10px; max-width:100px;">
                </div>

                <button type="submit" class="btn btn-primary">Save Changes</button>
                <a href="blogManagement" class="btn btn-secondary">Cancel</a>
            </form>
        </div>

        <!-- CKEditor Initialization Script -->
        <script>
            CKEDITOR.replace('editBlogDescription');
        </script>
        <%
            String successMessage = (String) session.getAttribute("successMessage");
            String errorMessage = (String) request.getAttribute("errorMessage");

            if (successMessage != null) {
        %>
        <div class="alert alert-success"><%= successMessage%></div>
        <%
                session.removeAttribute("successMessage");
            }

            if (errorMessage != null) {
        %>
        <div class="alert alert-danger"><%= errorMessage%></div>
        <%
            }
        %>
        <script>
            <script>
            // Dismiss messages after 5 seconds
            setTimeout(function () {
                    const successAlert = document.querySelector('.alert-success');
                    const errorAlert = document.querySelector('.alert-danger');
                    if (successAlert)
                    successAlert.style.display = 'none';
                    if (errorAlert)
                    errorAlert.style.display = 'none';
}, 5000); // Auto-dismiss after 5 seconds
        </script>
    </body>
</html>
