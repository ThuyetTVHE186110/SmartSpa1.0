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
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Author Image</th>
                                            <th scope="col">Author</th>
                                            <th scope="col">Title</th>
                                            <th scope="col">Content</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">Date Posted</th>
                                            <th scope="col">Views</th>
                                            <th scope="col">Comments</th>
                                            <th scope="col">Category</th>
                                            <th scope="col">Image</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="blog" items="${blogs}">
                                            <tr>
                                                <td>${blog.id}</td>

                                                <!-- Display Author Image with Fallback -->
                                                <td>
                                                    <img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.authorImage}" 
                                                         alt="${blog.authorName}" width="50" height="50" style="border-radius: 50%;" 
                                                         onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/newUI/assets/img/default-author.jpg';">
                                                </td>

                                                <!-- Author Name -->
                                                <td>${blog.authorName}</td>

                                                <!-- Blog Title -->
                                                <td>${blog.title}</td>

                                                <!-- Blog Content Preview -->
                                                <td>${fn:substring(blog.content, 0, 50)}...</td>

                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty blog.description}">
                                                            ${fn:substring(blog.description, 0, 50)}...
                                                        </c:when>
                                                        <c:otherwise>
                                                            No description available.
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>

                                                <!-- Date Posted -->
                                                <td><fmt:formatDate value="${blog.datePosted}" pattern="yyyy-MM-dd" /></td>

                                                <!-- Views Count -->
                                                <td>${blog.views}</td>

                                                <!-- Comments Count -->
                                                <td>${blog.commentsCount}</td>

                                                <!-- Category -->
                                                <td>${blog.category}</td>

                                                <!-- Blog Image with Fallback -->
                                                <td>
                                                    <img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.image}" 
                                                         alt="${blog.title}" width="50" 
                                                         onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/newUI/assets/img/default-blog.jpg';">
                                                </td>

                                                <!-- Actions: Edit and Delete -->
                                                <td>
                                                    <!-- Edit Button to open modal with blog details -->
                                                    <a href="javascript:void(0);"
                                                       class="btn btn-primary btn-sm"
                                                       onclick="openEditBlogModal(
                                                                       '${blog.id}',
                                                                       '${fn:escapeXml(blog.title)}',
                                                                       '${fn:escapeXml(blog.content)}',
                                                                       '${blog.datePosted}',
                                                                       '${fn:escapeXml(blog.image)}',
                                                                       '${fn:substring(fn:escapeXml(blog.description), 0, 500)}',
                                                                       '${blog.views}',
                                                                       '${blog.commentsCount}',
                                                                       '${fn:escapeXml(blog.category)}',
                                                                       '${fn:escapeXml(blog.authorName)}',
                                                                       '${fn:escapeXml(blog.authorImage)}'
                                                                       )">Edit</a>




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


                                <%
                                    String successMessage = (String) session.getAttribute("successMessage");
                                    String errorMessage = (String) request.getAttribute("errorMessage");

                                    if (successMessage != null) {
                                %>
                                <div class="alert alert-success"><%= successMessage%></div>
                                <%
                                        session.removeAttribute("successMessage"); // Remove after displaying
                                    }

                                    if (errorMessage != null) {
                                %>
                                <div class="alert alert-danger"><%= errorMessage%></div>
                                <%
                                    }
                                %>

                                <script>
                                    setTimeout(function ()

                                    {
                                        const successAlert = document.querySelector('.alert-success');
                                        const errorAlert = document.querySelector('.alert-danger');
                                        if (successAlert)
                                            successAlert.style.display = 'none';
                                        if (errorAlert)
                                            errorAlert.style.display = 'none';
                                    }, 5000); // Auto-dismiss after 5 seconds
                                </script>

                                <!-- End Table with stripped rows -->


                                <div class="text-center mt-3">
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            data-bs-target="#addAccountModal">
                                        <i class="bi bi-plus-circle me-1"></i> Add New Blog
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
                        <h5 class="modal-title">Add New Blog</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Account Form -->
                        <form action="blogManagement" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="add">

                            <div class="mb-3">
                                <label for="inputTitle" class="form-label">Title</label>
                                <input type="text" class="form-control" id="inputTitle" name="title" required>
                            </div>

                            <div class="mb-3">
                                <label for="inputContent" class="form-label">Content</label>
                                <textarea class="form-control" id="inputContent" name="content" rows="5" required></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="inputDescription" class="form-label">Description</label>
                                <textarea class="form-control" id="inputDescription" name="description" rows="3"></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="inputCategory" class="form-label">Category</label>
                                <input type="text" class="form-control" id="inputCategory" name="category">
                            </div>

                            <div class="mb-3">
                                <label for="inputImage" class="form-label">Image</label>
                                <input type="file" class="form-control" id="inputImage" name="image">
                                <img id="imagePreview" src="#" alt="Image Preview" style="display:none; margin-top:10px; max-width:100px;">
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add Blog</button>
                            </div>
                        </form>
                        <!-- End Add Account Form -->
                    </div>
                </div>
            </div>
        </div><!-- End Add Account Modal-->

        <!-- Edit Blog Modal -->
        <!-- Edit Blog Modal -->
        <div class="modal fade" id="editBlogModal" tabindex="-1" aria-labelledby="editBlogModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editBlogModalLabel">Edit Blog</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="blogManagement" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" id="editBlogId" name="id">

                            <div class="mb-3">
                                <label for="editBlogTitle" class="form-label">Title</label>
                                <input type="text" class="form-control" id="editBlogTitle" name="title" required>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogContent" class="form-label">Content</label>
                                <textarea class="form-control" id="editBlogContent" name="content" rows="5" required></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogDatePosted" class="form-label">Date Posted</label>
                                <input type="date" class="form-control" id="editBlogDatePosted" name="datePosted" required>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogDescription" class="form-label">Description</label>
                                <textarea class="form-control" id="editBlogDescription" name="description" rows="3"></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogCategory" class="form-label">Category</label>
                                <input type="text" class="form-control" id="editBlogCategory" name="category">
                            </div>

                            <div class="mb-3">
                                <label for="editBlogViews" class="form-label">Views</label>
                                <input type="number" class="form-control" id="editBlogViews" name="views" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogCommentsCount" class="form-label">Comments Count</label>
                                <input type="number" class="form-control" id="editBlogCommentsCount" name="commentsCount" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogAuthorName" class="form-label">Author</label>
                                <input type="text" class="form-control" id="editBlogAuthorName" name="authorName" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogImage" class="form-label">Image</label>
                                <input type="file" class="form-control" id="editBlogImage" name="image">
                                <img id="editBlogImagePreview" src="#" alt="Image Preview" style="margin-top: 10px; max-width: 100px;">
                            </div>

                            <div class="mb-3">
                                <label for="editBlogAuthorImage" class="form-label">Author Image</label>
                                <input type="file" class="form-control" id="editBlogAuthorImageInput" name="authorImage">
                                <img id="editBlogAuthorImage" src="#" alt="Author Image Preview" style="margin-top: 10px; max-width: 100px;">
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Edit Blog Modal -->




        <!-- End Edit Blog Modal -->
        <script>
                                    // Function to open and populate the Edit Blog Modal
                                    function openEditBlogModal(id, title, content, datePosted, image, description, views, commentsCount, category, authorName, authorImage)
                                    {
                                        // Populate the form fields in the modal
                                        document.getElementById('editBlogId').value = id;
                                        document.getElementById('editBlogTitle').value = title;
                                        document.getElementById('editBlogContent').value = content;
                                        document.getElementById('editBlogDatePosted').value = datePosted;
                                        document.getElementById('editBlogDescription').value = description;
                                        document.getElementById('editBlogViews').value = views;
                                        document.getElementById('editBlogCommentsCount').value = commentsCount;
                                        document.getElementById('editBlogCategory').value = category;
                                        document.getElementById('editBlogAuthorName').value = authorName;

                                        // Display image previews if available
                                        const blogImagePreview = document.getElementById('editBlogImagePreview');
                                        blogImagePreview.src = image ? `${window.location.origin}/newUI/assets/img/${image}` : '#';
                                                blogImagePreview.style.display = image ? 'block' : 'none';

                                                const authorImagePreview = document.getElementById('editBlogAuthorImage');
                                                authorImagePreview.src = authorImage ? `${window.location.origin}/newUI/assets/img/${authorImage}` : '#';
                                                        authorImagePreview.style.display = authorImage ? 'block' : 'none';

                                                        // Show the modal
                                                        new bootstrap.Modal(document.getElementById('editBlogModal')).show();
                                                    }


        </script>

    </body>

</html>