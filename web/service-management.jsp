<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

            <!-- ======= Header ======= -->
            <header id="header" class="header fixed-top d-flex align-items-center">

                <div class="d-flex align-items-center justify-content-between">
                    <a href="index.html" class="logo d-flex align-items-center">
                        <img src="assets/img/logo.png" alt="">
                        <span class="d-none d-lg-block">NiceAdmin</span>
                    </a>
                    <i class="bi bi-list toggle-sidebar-btn"></i>
                </div><!-- End Logo -->

                <div class="search-bar">
                    <form class="search-form d-flex align-items-center" method="POST" action="#">
                        <input type="text" name="query" placeholder="Search" title="Enter search keyword">
                        <button type="submit" title="Search"><i class="bi bi-search"></i></button>
                    </form>
                </div><!-- End Search Bar -->

                <nav class="header-nav ms-auto">
                    <ul class="d-flex align-items-center">

                        <li class="nav-item d-block d-lg-none">
                            <a class="nav-link nav-icon search-bar-toggle " href="#">
                                <i class="bi bi-search"></i>
                            </a>
                        </li><!-- End Search Icon-->

                        <li class="nav-item dropdown">

                            <a class="nav-link nav-icon" href="#" data-bs-toggle="dropdown">
                                <i class="bi bi-bell"></i>
                                <span class="badge bg-primary badge-number">4</span>
                            </a><!-- End Notification Icon -->

                            <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow notifications">
                                <li class="dropdown-header">
                                    You have 4 new notifications
                                    <a href="#"><span class="badge rounded-pill bg-primary p-2 ms-2">View all</span></a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="notification-item">
                                    <i class="bi bi-exclamation-circle text-warning"></i>
                                    <div>
                                        <h4>Lorem Ipsum</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>30 min. ago</p>
                                    </div>
                                </li>

                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="notification-item">
                                    <i class="bi bi-x-circle text-danger"></i>
                                    <div>
                                        <h4>Atque rerum nesciunt</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>1 hr. ago</p>
                                    </div>
                                </li>

                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="notification-item">
                                    <i class="bi bi-check-circle text-success"></i>
                                    <div>
                                        <h4>Sit rerum fuga</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>2 hrs. ago</p>
                                    </div>
                                </li>

                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="notification-item">
                                    <i class="bi bi-info-circle text-primary"></i>
                                    <div>
                                        <h4>Dicta reprehenderit</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>4 hrs. ago</p>
                                    </div>
                                </li>

                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li class="dropdown-footer">
                                    <a href="#">Show all notifications</a>
                                </li>

                            </ul><!-- End Notification Dropdown Items -->

                        </li><!-- End Notification Nav -->

                        <li class="nav-item dropdown">

                            <a class="nav-link nav-icon" href="#" data-bs-toggle="dropdown">
                                <i class="bi bi-chat-left-text"></i>
                                <span class="badge bg-success badge-number">3</span>
                            </a><!-- End Messages Icon -->

                            <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow messages">
                                <li class="dropdown-header">
                                    You have 3 new messages
                                    <a href="#"><span class="badge rounded-pill bg-primary p-2 ms-2">View all</span></a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="message-item">
                                    <a href="#">
                                        <img src="assets/img/messages-1.jpg" alt="" class="rounded-circle">
                                        <div>
                                            <h4>Maria Hudson</h4>
                                            <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...
                                            </p>
                                            <p>4 hrs. ago</p>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="message-item">
                                    <a href="#">
                                        <img src="assets/img/messages-2.jpg" alt="" class="rounded-circle">
                                        <div>
                                            <h4>Anna Nelson</h4>
                                            <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...
                                            </p>
                                            <p>6 hrs. ago</p>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="message-item">
                                    <a href="#">
                                        <img src="assets/img/messages-3.jpg" alt="" class="rounded-circle">
                                        <div>
                                            <h4>David Muldon</h4>
                                            <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...
                                            </p>
                                            <p>8 hrs. ago</p>
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">Show all messages</a>
                                </li>

                            </ul><!-- End Messages Dropdown Items -->

                        </li><!-- End Messages Nav -->

                        <li class="nav-item dropdown pe-3">

                            <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#"
                                data-bs-toggle="dropdown">
                                <img src="assets/img/profile-img.jpg" alt="Profile" class="rounded-circle">
                                <span class="d-none d-md-block dropdown-toggle ps-2">K. Anderson</span>
                            </a><!-- End Profile Iamge Icon -->

                            <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                                <li class="dropdown-header">
                                    <h6>Kevin Anderson</h6>
                                    <span>Web Designer</span>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li>
                                    <a class="dropdown-item d-flex align-items-center" href="dashboard.html">
                                        <i class="bi bi-person"></i>
                                        <span>My Profile</span>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li>
                                    <a class="dropdown-item d-flex align-items-center" href="dashboard.html">
                                        <i class="bi bi-gear"></i>
                                        <span>Account Settings</span>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li>
                                    <a class="dropdown-item d-flex align-items-center" href="pages-faq.html">
                                        <i class="bi bi-question-circle"></i>
                                        <span>Need Help?</span>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>

                                <li>
                                    <a class="dropdown-item d-flex align-items-center" href="#">
                                        <i class="bi bi-box-arrow-right"></i>
                                        <span>Sign Out</span>
                                    </a>
                                </li>

                            </ul><!-- End Profile Dropdown Items -->
                        </li><!-- End Profile Nav -->

                    </ul>
                </nav><!-- End Icons Navigation -->

            </header><!-- End Header -->

            <!-- ======= Sidebar ======= -->
            <aside id="sidebar" class="sidebar">

                <ul class="sidebar-nav" id="sidebar-nav">

                    <li class="nav-item">
                        <a class="nav-link " href="index.html">
                            <i class="bi bi-grid"></i>
                            <span>Dashboard</span>
                        </a>
                    </li><!-- End Dashboard Nav -->

                    <!-- Admin Section -->
                    <li class="nav-item">
                        <a class="nav-link collapsed" data-bs-target="#admin-nav" data-bs-toggle="collapse" href="#">
                            <i class="bi bi-person-gear"></i><span>Admin</span><i
                                class="bi bi-chevron-down ms-auto"></i>
                        </a>
                        <ul id="admin-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <a href="accountmanagement.html">
                                    <i class="bi bi-circle"></i><span>Account Management</span>
                                </a>
                            </li>
                        </ul>
                    </li><!-- End Admin Nav -->

                    <!-- Manager Section -->
                    <li class="nav-item">
                        <a class="nav-link collapsed" data-bs-target="#manager-nav" data-bs-toggle="collapse" href="#">
                            <i class="bi bi-briefcase"></i><span>Manager</span><i
                                class="bi bi-chevron-down ms-auto"></i>
                        </a>
                        <ul id="manager-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <a href="servicemanagement">
                                    <i class="bi bi-circle"></i><span>Service Management</span>
                                </a>
                            </li>
                            <li>
                                <a href="productmanagement.html">
                                    <i class="bi bi-circle"></i><span>Product Management</span>
                                </a>
                            </li>
                            <li>
                            <a href="employee-management" class="active">
                                <i class="bi bi-circle"></i><span>Staff Management</span>
                            </a>
                        </li>
                        </ul>
                    </li><!-- End Manager Nav -->

                    <!-- Staff Section -->
                    <li class="nav-item">
                        <a class="nav-link collapsed" data-bs-target="#staff-nav" data-bs-toggle="collapse" href="#">
                            <i class="bi bi-people"></i><span>Staff</span><i class="bi bi-chevron-down ms-auto"></i>
                        </a>
                        <ul id="staff-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <a href="appoinment-management.html">
                                    <i class="bi bi-circle"></i><span>Appointment Management</span>
                                </a>
                            </li>
                            <li>
                                <a href="schedulemanagement.html">
                                    <i class="bi bi-circle"></i><span>Schedule Management</span>
                                </a>
                            </li>
                            <li>
                                <a href="feedback-management">
                                    <i class="bi bi-circle"></i><span>Feedback Management</span>
                                </a>
                            </li>
                        </ul>
                    </li><!-- End Staff Nav -->

                </ul>

            </aside><!-- End Sidebar-->

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