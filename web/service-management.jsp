<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> <!-- Add this line to import the Account class -->
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Service Management - NiceAdmin</title>
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
                <h1>Service Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Manager</li>
                        <li class="breadcrumb-item active">Service Management</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Services</h5>
                                <p>Manage services from this panel.</p>

                                <!-- Add this near the top of the body, after the header -->
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger" role="alert">
                                        ${errorMessage}
                                    </div>
                                </c:if>

                                <!-- Search Form -->
                                <form action="servicemanagement" method="get" class="mb-3">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Search services"
                                               name="search" value="${param.search}">
                                        <button class="btn btn-primary" type="submit">Search</button>
                                    </div>
                                </form>

                                <!-- Table with services -->
                                <table class="table datatable">
                                    <thead>
                                        <!-- Table with services -->
                                    <table class="table datatable">
                                        <thead>
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">Image</th>
                                                <th scope="col">Service Name</th>
                                                <th scope="col">Description</th>
                                                <th scope="col">Price</th>
                                                <th scope="col">Duration</th>
                                                <th scope="col">Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="service" items="${services}">
                                                >>>>>>> ae03ce95e8572fb14b7c98f2935d0cdb4654b2cb
                                                <tr>
                                                    <th scope="row">
                                                        <c:out value="${service.id}" />
                                                    </th>
                                                    <td><img src="<c:out value=" ${service.image}" />"
                                                             alt="${service.name}" width="50" height="50"></td>
                                                    <td>
                                                        <c:out value="${service.name}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${service.description}" />
                                                    </td>
                                                    <td>$
                                                        <c:out value="${service.price}" />
                                                    </td>
                                                    <td>
                                                        <c:out value="${service.duration}" /> min
                                                    </td>
                                                    <td>
                                                        <button type="button" class="btn btn-primary btn-sm"
                                                                data-bs-toggle="modal"
                                                                data-bs-target="#editServiceModal${service.id}">Edit</button>
                                                        <a href="servicemanagement?action=delete&id=${service.id}"
                                                           class="btn btn-danger btn-sm"
                                                           onclick="return confirm('Are you sure you want to delete this service?')">Delete</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <!-- End Table with services -->

                                    <!-- Pagination -->
                                    <nav aria-label="Page navigation">
                                        <ul class="pagination justify-content-center">
                                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                <a class="page-link"
                                                   href="servicemanagement?page=${currentPage - 1}&search=${param.search}"
                                                   tabindex="-1">Previous</a>
                                            </li>
                                            <c:forEach begin="1" end="${totalPages}" var="i">
                                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                    <a class="page-link"
                                                       href="servicemanagement?page=${i}&search=${param.search}">${i}</a>
                                                </li>
                                            </c:forEach>
                                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                <a class="page-link"
                                                   href="servicemanagement?page=${currentPage + 1}&search=${param.search}">Next</a>
                                            </li>
                                        </ul>
                                    </nav>
                                    <!-- End Pagination -->

                                    <div class="text-center mt-3">
                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                                data-bs-target="#addServiceModal">
                                            <i class="bi bi-plus-circle me-1"></i> Add New Service
                                        </button>
                                    </div>

                            </div>
                        </div>

                    </div>
                </div>
            </section>

        </main><!-- End #main -->

        <!-- ======= Footer ======= -->
        <footer id="footer" class="footer">
            <div class="copyright">
                &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
            </div>
            <div class="credits">
                Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
            </div>
        </footer><!-- End Footer -->

        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
                class="bi bi-arrow-up-short"></i></a>

        <!-- Add Service Modal -->
        <div class="modal fade" id="addServiceModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Service</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Service Form -->
                        <form action="${pageContext.request.contextPath}/servicemanagement" method="post"
                              enctype="multipart/form-data">
                            <div class="mb-3">
                                <label for="name" class="form-label">Service Name</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="mb-3">
                                <label for="price" class="form-label">Price</label>
                                <input type="number" class="form-control" id="price" name="price" required>
                            </div>
                            <div class="mb-3">
                                <label for="duration" class="form-label">Duration (minutes)</label>
                                <input type="number" class="form-control" id="duration" name="duration" required>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3"
                                          required></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="file" class="form-label">Image</label>
                                <input type="file" class="form-control" id="file" name="file" required>
                            </div>
                            <input type="hidden" name="action" value="insert">
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary"
                                        data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add Service</button>
                            </div>
                        </form>
                        <!-- End Add Service Form -->
                    </div>
                </div>
            </div>
        </div><!-- End Add Service Modal-->

        <!-- Edit Service Modals -->
        <c:forEach var="service" items="${services}">
            <div class="modal fade" id="editServiceModal${service.id}" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Service</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Edit Service Form -->
                            <form action="servicemanagement?action=update" method="post"
                                  enctype="multipart/form-data">
                                <input type="hidden" name="id" value="${service.id}">
                                <div class="mb-3">
                                    <label for="name${service.id}" class="form-label">Service Name</label>
                                    <input type="text" class="form-control" id="name${service.id}" name="name"
                                           value="${service.name}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="description${service.id}" class="form-label">Description</label>
                                    <textarea class="form-control" id="description${service.id}" name="description"
                                              rows="3" required>${service.description}</textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="price${service.id}" class="form-label">Price</label>
                                    <input type="number" class="form-control" id="price${service.id}" name="price"
                                           value="${service.price}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="duration${service.id}" class="form-label">Duration (minutes)</label>
                                    <input type="number" class="form-control" id="duration${service.id}"
                                           name="duration" value="${service.duration}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="file${service.id}" class="form-label">Image</label>
                                    <input type="file" class="form-control" id="file${service.id}" name="file">
                                    <small class="form-text text-muted">Leave empty to keep the current
                                        image.</small>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                </div>
                            </form>
                            <!-- End Edit Service Form -->
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <!-- End Edit Service Modals -->

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

    </body>

</html>