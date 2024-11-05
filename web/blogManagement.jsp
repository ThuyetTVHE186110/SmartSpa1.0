<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.Account" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session == null || session.getAttribute("account") == null) {
        response.sendRedirect("adminLogin.jsp");
    } else {
        Account account = (Account) session.getAttribute("account");
        if (!(account.getRole() == 1 || account.getRole() == 2 || account.getRole() == 3)) {
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
        <link href="https://fonts.googleapis.com/css?family=Open+Sans|Nunito|Poppins" rel="stylesheet">

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
        <script src="assets/js/main.js"></script>

        <style>
            .table td {
                white-space: normal;
                max-width: 300px;
                vertical-align: top;
                padding: 10px 5px;
            }

            .table .content-preview,
            .table .description-preview {
                max-height: 150px;
                overflow-y: auto;
            }

            .table tr {
                height: auto;
            }
        </style>
    </head>
    <body>
        <jsp:include page="headerHTML.jsp" />
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
            </div>

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
                                                <input type="text" class="form-control" placeholder="Search" name="search" value="${param.search}">
                                                <button class="btn btn-outline-secondary" type="submit">Search</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <!-- Table -->
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
                                                <td><img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.authorImage}" alt="${fn:escapeXml(blog.authorName)}" width="50" height="50" style="border-radius: 50%;" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/newUI/assets/img/default-author.jpg';"></td>
                                                <td>${fn:escapeXml(blog.authorName)}</td>
                                                <td title="${fn:escapeXml(blog.title)}">${fn:escapeXml(blog.title)}</td>
                                                <td class="content-preview">${fn:substring(blog.content, 0, 200)}...</td>
                                                <td class="description-preview">${fn:substring(blog.description.replaceAll('<[^>]*>', ''), 0, 200)}...</td>
                                                <td><fmt:formatDate value="${blog.datePosted}" pattern="yyyy-MM-dd" /></td>
                                                <td>${blog.views}</td>
                                                <td>${blog.commentsCount}</td>
                                                <td>${fn:escapeXml(blog.category)}</td>
                                                <td><img src="${pageContext.request.contextPath}/newUI/assets/img/${blog.image}" alt="${fn:escapeXml(blog.title)}" width="50" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/newUI/assets/img/default-blog.jpg';"></td>
                                                <td>
                                                    <c:if test="${!viewOnly}">
                                                        <a href="editBlog?id=${blog.id}" class="btn btn-outline-primary btn-sm" title="Edit ${fn:escapeXml(blog.title)}"><i class="bi bi-pencil-square"></i></a>
                                                        <form action="blogManagement" method="post" style="display:inline;">
                                                            <input type="hidden" name="action" value="delete">
                                                            <input type="hidden" name="id" value="${blog.id}">
                                                            <button type="submit" class="btn btn-outline-danger btn-sm" onclick="return confirm('Are you sure you want to delete this blog?');">
                                                                <i class="bi bi-trash"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${viewOnly}">
                                                        <a href="${pageContext.request.contextPath}/blogDetails?id=${blog.id}" class="btn btn-outline-info btn-sm" title="View ${fn:escapeXml(blog.title)}"><i class="bi bi-eye"></i></a>
                                                        </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <!-- Add New Blog Button -->
                                <div class="text-center mt-3">
                                    <a href="addBlog" class="btn btn-primary">
                                        <i class="bi bi-plus-circle me-1"></i> Add New Blog
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>

        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

        <!-- Success/Error Message Display -->
        <%
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
        %>
        <div class="header-nav messages">
            <div class="message-item alert alert-success">
                <a href="#">
                    <img src="assets/img/success-icon.png" alt="Success Icon">
                    <div>
                        <h4>Success</h4>
                        <p><%= successMessage%></p>
                    </div>
                </a>
            </div>
        </div>
        <%
                session.removeAttribute("successMessage"); // Clear message after displaying
            }
        %>

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