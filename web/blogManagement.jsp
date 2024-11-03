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
<style>
    .table td {
        white-space: normal; /* Allow text to wrap */
        max-width: 300px; /* Optional: set a max width to control column width */
        vertical-align: top; /* Align content to the top of the cell */
        padding: 10px 5px; /* Add padding for readability */
    }

    .table .content-preview, .table .description-preview {
        max-height: 150px; /* Optional: limit the height of content preview */
        overflow-y: auto; /* Add vertical scroll if content exceeds the height */
    }

    /* Increase row height */
    .table tr {
        height: auto; /* Let rows grow based on content */
    }
</style>

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
                                <!-- Filter Dropdowns -->
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <form action="blogManagement" method="get">
                                            <label for="categoryFilter" class="form-label">Filter by Category:</label>
                                            <select id="categoryFilter" name="category" class="form-select" onchange="this.form.submit()">
                                                <option value="all">All</option>
                                                <c:forEach var="category" items="${categories}">
                                                    <option value="${category}" ${param.category == category ? 'selected' : ''}>${category}</option>
                                                </c:forEach>
                                            </select>
                                        </form>
                                    </div>

                                    <div class="col-md-6">
                                        <form action="blogManagement" method="get">
                                            <label for="filter">Filter Blogs:</label>
                                            <select name="filter" class="form-select" id="filter" onchange="this.form.submit()">
                                                <option value="myBlogs" ${param.filter == 'myBlogs' ? 'selected' : ''}>My Blogs</option>
                                                <option value="all" ${param.filter == 'all' ? 'selected' : ''}>All Blogs</option>
                                            </select>
                                        </form>
                                    </div>
                                    <div class="col-md-6">
                                        <form action="blogManagement" method="get">
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" placeholder="Search by Author, Name, Title, Content, or Description" name="search" value="${param.search}">
                                                <button class="btn btn-outline-secondary" type="submit">Search</button>
                                            </div>
                                        </form>
                                    </div>
                                    <script>
                                        function sortBlogs() {
                                            const sortOrder = document.getElementById('sortOrder').value;
                                            const urlParams = new URLSearchParams(window.location.search);
                                            urlParams.set('sortOrder', sortOrder);
                                            window.location.search = urlParams.toString();
                                        }
                                    </script>
                                </div>
                                <!-- Table with stripped rows -->
                                <table class="table table-striped table-responsive">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Author</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Title</th>
                                            <th scope="col">Content</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">Date</th>
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
                                                         alt="${fn:escapeXml(blog.authorName)}" width="50" height="50" style="border-radius: 50%;" 
                                                         title="${fn:escapeXml(blog.authorName)}"
                                                         onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/newUI/assets/img/default-author.jpg';">
                                                </td>

                                                <!-- Author Name -->
                                                <td>${fn:escapeXml(blog.authorName)}</td>

                                                <!-- Blog Title -->
                                                <td title="${fn:escapeXml(blog.title)}">${fn:escapeXml(blog.title)}</td>

                                                <!-- Blog Content Preview -->
                                                <td class="content-preview" title="${fn:escapeXml(blog.content)}">
                                                    ${fn:substring(blog.content, 0, 200)}...
                                                </td>

                                                <!-- Blog Description Preview -->
                                                <td class="description-preview">
                                                    <c:choose>
                                                        <c:when test="${not empty blog.description}">
                                                            ${fn:substring(blog.description, 0, 200)}...
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
                                                <td>${fn:escapeXml(blog.category)}</td>

                                                <!-- Blog Image with Fallback -->
                                                <td>
                                                    <img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.image}" 
                                                         alt="${fn:escapeXml(blog.title)}" width="50" 
                                                         title="${fn:escapeXml(blog.title)}"
                                                         onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/newUI/assets/img/default-blog.jpg';">
                                                </td>

                                                <!-- Actions: Edit and Delete -->
                                                <td class="action-buttons">
                                                    <c:if test="${!viewOnly}">
                                                        <a href="javascript:void(0);" class="btn btn-outline-primary btn-sm"
                                                           title="Edit ${fn:escapeXml(blog.title)}"
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
                                                                           )">
                                                            <i class="bi bi-pencil-square"></i>
                                                        </a>
                                                        <form action="blogManagement" method="post" style="display:inline;">
                                                            <input type="hidden" name="action" value="delete">
                                                            <input type="hidden" name="id" value="${blog.id}">
                                                            <button type="submit" class="btn btn-outline-danger btn-sm" 
                                                                    title="Delete ${fn:escapeXml(blog.title)}" 
                                                                    onclick="return confirm('Are you sure you want to delete this blog?');">
                                                                <i class="bi bi-trash"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${viewOnly}">
                                                        <a href="${pageContext.request.contextPath}/blogDetails?id=${blog.id}" class="btn btn-outline-info btn-sm" class="btn btn-outline-secondary btn-sm" 
                                                           title="View ${fn:escapeXml(blog.title)}">
                                                            <i class="bi bi-eye"></i>
                                                        </a>
                                                    </c:if>
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

        <!-- Add Modal -->
        <div class="modal fade" id="addAccountModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Blog</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Blog Form -->
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
                                <select class="form-select" id="inputCategory" name="category" required>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category}">${category}</option>
                                    </c:forEach>
                                </select>
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
                        <!-- End Add Blog Form -->
                    </div>
                </div>
            </div>
        </div><!-- End Add Account Modal -->


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
                                <input type="text" class="form-control" id="editBlogTitle" name="title" required readonly>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogContent" class="form-label">Content</label>
                                <textarea class="form-control" id="editBlogContent" name="content" rows="5" required readonly></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogDatePosted" class="form-label">Date Posted</label>
                                <input type="date" class="form-control" id="editBlogDatePosted" name="datePosted" required readonly>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogDescription" class="form-label">Description</label>
                                <textarea class="form-control" id="editBlogDescription" name="description" rows="3" readonly></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="editBlogCategory" class="form-label">Category</label>
                                <input type="text" class="form-control" id="editBlogCategory" name="category" readonly>
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
                                <input type="file" class="form-control" id="editBlogImage" name="image" disabled>
                                <img id="editBlogImagePreview" src="#" alt="Image Preview" style="margin-top: 10px; max-width: 100px;">
                            </div>

                            <div class="mb-3">
                                <label for="editBlogAuthorImage" class="form-label">Author Image</label>
                                <input type="file" class="form-control" id="editBlogAuthorImageInput" name="authorImage" disabled>
                                <img id="editBlogAuthorImage" src="#" alt="Author Image Preview" style="margin-top: 10px; max-width: 100px;">
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary" id="saveChangesButton">Save Changes</button>
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
                                    function openEditBlogModal(blogId, title, content, datePosted, image, description, views, commentsCount, category, authorName, authorImage, viewOnly) {
                                        document.getElementById('editBlogId').value = blogId;
                                        document.getElementById('editBlogTitle').value = title;
                                        document.getElementById('editBlogContent').value = content;
                                        document.getElementById('editBlogDatePosted').value = datePosted;
                                        document.getElementById('editBlogDescription').value = description;
                                        document.getElementById('editBlogCategory').value = category;
                                        document.getElementById('editBlogViews').value = views;
                                        document.getElementById('editBlogCommentsCount').value = commentsCount;
                                        document.getElementById('editBlogAuthorName').value = authorName;
                                        document.getElementById('editBlogImagePreview').src = image ? `path/to/images/${image}` : 'path/to/default-image.jpg';
                                        document.getElementById('editBlogAuthorImage').src = authorImage ? `path/to/images/${authorImage}` : 'path/to/default-author.jpg';

                                        const inputs = document.querySelectorAll('#editBlogModal input, #editBlogModal textarea');
                                        const saveButton = document.getElementById('saveChangesButton');

                                        if (viewOnly) {
                                            inputs.forEach(input => input.setAttribute('readonly', true));
                                            document.getElementById('editBlogImage').disabled = true;
                                            document.getElementById('editBlogAuthorImageInput').disabled = true;
                                            saveButton.style.display = 'none'; // Hide save button when viewing
                                        } else {
                                            inputs.forEach(input => input.removeAttribute('readonly'));
                                            document.getElementById('editBlogImage').disabled = false;
                                            document.getElementById('editBlogAuthorImageInput').disabled = false;
                                            saveButton.style.display = 'inline-block'; // Show save button when editing
                                        }

                                        new bootstrap.Modal(document.getElementById('editBlogModal')).show();
                                    }



        </script>
        <script>
            function filterBlogs() {
                const selectedCategory = document.getElementById("categoryFilter").value;
                const rows = document.querySelectorAll(".blog-row");

                rows.forEach(row => {
                    const category = row.getAttribute("data-category");
                    if (selectedCategory === "all" || category === selectedCategory) {
                        row.style.display = "";
                    } else {
                        row.style.display = "none";
                    }
                });
            }
        </script>
        <script>
            function filterBlogs() {
                const selectedRole = document.getElementById("roleFilter").value;
                const selectedAuthor = document.getElementById("authorFilter").value;
                const rows = document.querySelectorAll(".account-row");

                rows.forEach(row => {
                    const role = row.getAttribute("data-role");
                    const author = row.getAttribute("data-author");

                    if ((selectedRole === "all" || role === selectedRole) &&
                            (selectedAuthor === "all" || author === selectedAuthor)) {
                        row.style.display = "";
                    } else {
                        row.style.display = "none";
                    }
                });
            }
        </script>


    </body>

</html>