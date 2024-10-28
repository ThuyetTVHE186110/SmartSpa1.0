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

        if (account.getRole() == 1) {
            // Allow access to the page (do nothing and let the JSP render)
        } else {
            // Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            request.getRequestDispatcher("error").forward(request, response);
        }
    }
%>
<!DOCTYPE html>
<html lang="en">

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
    </head>

    <body>

        <jsp:include page="headerHTML.jsp" />

        <!-- ======= Sidebar ======= -->
        <jsp:include page="sideBar.jsp" />

        <main id="main" class="main">

            <div class="pagetitle">
                <h1>Blog Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="dashboard.jsp">Home</a></li>
                        <li class="breadcrumb-item active">Blog</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Blog Details</h5>
                                <div class="row mb-3">

                                </div>
                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Title</th>
                                            <th scope="col">Content</th>
                                            <th scope="col">Author</th>
                                            <th scope="col">Date</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- Iterate over the 'accounts' list passed from the servlet -->
                                        <c:forEach var="blog" items="${blogs}">
                                            <tr>
                                                <td>${blog.id}</td>
                                                <td>${blog.title}</td>
                                                <td>${fn:substring(blog.content, 0, 50)}...</td> <!-- Truncate content for preview -->
                                                <td>${blog.authorName}</td>
                                                <td><fmt:formatDate value="${blog.datePosted}" pattern="yyyy-MM-dd" /></td>
                                                <td>
                                                    <!-- Edit Button -->
                                                    <a href="javascript:void(0);" class="btn btn-primary btn-sm" onclick="openEditBlogModal(${blog.id}, '${blog.title}', '${blog.content}', '${blog.authorName}', '${blog.datePosted}', '${blog.image}')">Edit</a>


                                                    <!-- Delete Form -->
                                                    <form action="blogManagement" method="post" style="display:inline;">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="id" value="${blog.id}">
                                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this blog?');">Delete</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <!-- End Table with stripped rows -->


                                <div class="text-center mt-3">
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            data-bs-target="#addAccountModal">
                                        <i class="bi bi-plus-circle me-1"></i> Add New Account
                                    </button>
                                </div>

                            </div>
                        </div>

                    </div>
                </div>
            </section>

        </main><!-- End #main -->


        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
                class="bi bi-arrow-up-short"></i></a>

        <!-- Vendor JS Files -->
        <script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/vendor/chart.js/chart.umd.js"></script>
        <script src="assets/vendor/echarts/echarts.min.js"></script>
        <script src="assets/vendor/quill/quill.min.js"></script>
        <script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
        <script src="assets/vendor/tinymce/tinymce.min.js"></script>
        <script src="assets/vendor/php-email-form/validate.js"></script>

        <!-- Template Main JS File -->
        <script src="assets/js/main.js"></script>

        <!-- Add Account Modal -->
        <div class="modal fade" id="addAccountModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Account</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Account Form -->
                        <form class="row g-3">
                            <div class="col-12">
                                <label for="inputName" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="inputName">
                            </div>
                            <div class="col-12">
                                <label for="inputEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="inputEmail">
                            </div>
                            <div class="col-12">
                                <label for="inputPassword" class="form-label">Password</label>
                                <input type="password" class="form-control" id="inputPassword">
                            </div>
                            <div class="col-12">
                                <label for="inputRole" class="form-label">Role</label>
                                <select id="inputRole" class="form-select">
                                    <option selected>Choose...</option>
                                    <option>Admin</option>
                                    <option>Manager</option>
                                    <option>Staff</option>
                                </select>
                            </div>
                        </form>
                        <!-- End Add Account Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Add Account</button>
                    </div>
                </div>
            </div>
        </div><!-- End Add Account Modal-->

        <!-- Edit Blog Modal -->
        <div class="modal fade" id="editBlogModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Blog</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Edit Blog Form -->
                        <form id="editBlogForm" class="row g-3" method="post" enctype="multipart/form-data">
                            <div class="col-12">
                                <label for="editTitle" class="form-label">Title</label>
                                <input type="text" class="form-control" id="editTitle" name="title" required>
                            </div>
                            <div class="col-12">
                                <label for="editContent" class="form-label">Content</label>
                                <textarea class="form-control" id="editContent" name="content" rows="4" required></textarea>
                            </div>
                            <div class="col-12">
                                <label for="editAuthor" class="form-label">Author</label>
                                <input type="text" class="form-control" id="editAuthor" name="author" readonly>
                            </div>
                            <div class="col-12">
                                <label for="editDate" class="form-label">Date</label>
                                <input type="date" class="form-control" id="editDate" name="datePosted" required>
                            </div>
                            <div class="col-12">
                                <label for="editImage" class="form-label">Current Image</label>
                                <img id="currentImagePreview" src="" alt="Current Blog Image" style="width: 100%; max-height: 200px; object-fit: cover; margin-bottom: 10px;">
                                <input type="file" class="form-control" id="editImage" name="image">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" form="editBlogForm" class="btn btn-primary">Save Changes</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- End Edit Blog Modal -->
        <script>
                                                            function openEditBlogModal(id, title, content, authorName, datePosted, image) {
                                                                const imagePath = image ? `newUI/assets/img/${image}` : 'newUI/assets/img/default-blog.jpg';
                                                                document.getElementById("editTitle").value = title;
                                                                document.getElementById("editContent").value = content;
                                                                document.getElementById("editAuthor").value = authorName;
                                                                document.getElementById("editDate").value = datePosted;
                                                                document.getElementById("currentImagePreview").src = imagePath;

                                                                // Set form action to update blog by ID
                                                                document.getElementById("editBlogForm").action = `/updateBlog?id=${id}`;

                                                                // Open the modal
                                                                new bootstrap.Modal(document.getElementById("editBlogModal")).show();
                                                            }

// Function to handle saving blog changes (AJAX or form submission)
                                                            function saveBlogChanges() {
                                                                document.getElementById("editBlogForm").submit(); // Or use AJAX to submit form data
                                                            }

        </script>

    </body>

</html>