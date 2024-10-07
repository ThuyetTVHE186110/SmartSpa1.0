<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Appointment Management - NiceAdmin</title>
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
                                        <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...</p>
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
                                        <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...</p>
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
                                        <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...</p>
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

                        <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
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
                                <a class="dropdown-item d-flex align-items-center" href="users-profile.html">
                                    <i class="bi bi-person"></i>
                                    <span>My Profile</span>
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>

                            <li>
                                <a class="dropdown-item d-flex align-items-center" href="users-profile.html">
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
                    <a class="nav-link collapsed" href="index.html">
                        <i class="bi bi-grid"></i>
                        <span>Dashboard</span>
                    </a>
                </li><!-- End Dashboard Nav -->

                <!-- Admin Section -->
                <li class="nav-item">
                    <a class="nav-link collapsed" data-bs-target="#admin-nav" data-bs-toggle="collapse" href="#">
                        <i class="bi bi-person-gear"></i><span>Admin</span><i class="bi bi-chevron-down ms-auto"></i>
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
                        <i class="bi bi-briefcase"></i><span>Manager</span><i class="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="manager-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="service-management.html">
                                <i class="bi bi-circle"></i><span>Service Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="productmanagement.html">
                                <i class="bi bi-circle"></i><span>Product Management</span>
                            </a>
                        </li>
                    </ul>
                </li><!-- End Manager Nav -->

                <!-- Staff Section -->
                <li class="nav-item">
                    <a class="nav-link" data-bs-target="#staff-nav" data-bs-toggle="collapse" href="#">
                        <i class="bi bi-people"></i><span>Staff</span><i class="bi bi-chevron-down ms-auto"></i>
                    </a>
                    <ul id="staff-nav" class="nav-content collapse show" data-bs-parent="#sidebar-nav">
                        <li>
                            <a href="appoinment-management.html" class="active">
                                <i class="bi bi-circle"></i><span>Appointment Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="schedulemanagement.html">
                                <i class="bi bi-circle"></i><span>Schedule Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="feedback-management.html">
                                <i class="bi bi-circle"></i><span>Feedback Management</span>
                            </a>
                        </li>
                    </ul>
                </li><!-- End Staff Nav -->

            </ul>

        </aside><!-- End Sidebar-->

        <main id="main" class="main">
            <div class="pagetitle">
                <h1>Appointment Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Staff</li>
                        <li class="breadcrumb-item active">Appointment Management</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Appointments</h5>
                                <p>Manage appointments from this panel.</p>

                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Customer Name</th>
                                            <th scope="col">Service</th>
                                            <th scope="col">Date</th>
                                            <th scope="col">Time</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.appointmentList}" var="appointment">
                                            <tr>
                                                <th scope="row">1</th>
                                                <td>${appointment.person.name}</td>
                                                <td> 
                                                    <c:forEach items="${appointmentServicesMap[appointment.id]}" var="service">
                                                        <p>${service.name}</p>
                                                    </c:forEach>
                                                </td>
                                                <td>${appointment.appointmentDate}</td>
                                                <td>${appointment.appointmentTime}</td>
                                                <td><span class="badge bg-success">${appointment.status}</span></td>
                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                                            data-bs-target="#editAppointmentModal"
                                                            date-person-id="${appointment.person.id}"
                                                            data-name="${appointment.person.name}"
                                                            data-services="${appointmentServicesMap[appointment.id]}"
                                                            data-date="${appointment.appointmentDate}"
                                                            data-time="${appointment.appointmentTime}"
                                                            data-status="${appointment.status}">Edit</button>
                                                    <button type="button" class="btn btn-danger btn-sm">Cancel</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <!-- Add more rows as needed -->
                                    </tbody>
                                </table>
                                <!-- End Table with stripped rows -->

                                <div class="text-center mt-3">
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            data-bs-target="#addAppointmentModal">
                                        <i class="bi bi-plus-circle me-1"></i> Add New Appointment
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

        <!-- Add Appointment Modal -->
        <div class="modal fade" id="addAppointmentModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Appointment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Appointment Form -->
                        <form class="row g-3">
                            <div class="col-12">
                                <label for="inputCustomerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" id="inputCustomerName">
                            </div>
                            <div class="col-12">
                                <label for="inputService" class="form-label">Service</label>
                                <select id="inputService" class="form-select">
                                    <option selected>Choose...</option>
                                    <option>Haircut</option>
                                    <option>Hair Coloring</option>
                                    <option>Manicure</option>
                                    <option>Pedicure</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="inputDate" class="form-label">Date</label>
                                <input type="date" class="form-control" id="inputDate">
                            </div>
                            <div class="col-md-6">
                                <label for="inputTime" class="form-label">Time</label>
                                <input type="time" class="form-control" id="inputTime">
                            </div>
                        </form>
                        <!-- End Add Appointment Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Add Appointment</button>
                    </div>
                </div>
            </div>
        </div><!-- End Add Appointment Modal-->

        <!-- Edit Appointment Modal -->
        <div class="modal fade" id="editAppointmentModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Appointment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Edit Appointment Form -->
                        <form class="row g-3">
                            <div class="col-12">
                                <label for="editCustomerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" id="editCustomerName" value="Brandon Jacob">
                            </div>
                            <div class="col-12">
                                <label for="editService" class="form-label">Service</label>
                                <select id="editService" class="form-select" name="editService" multiple>
                                    <c:forEach items="${requestScope.serviceList}" var="service">
                                        <option>${service.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="editDate" class="form-label">Date</label>
                                <input type="date" class="form-control" id="editDate" name="editDate">
                            </div>
                            <div class="col-md-6">
                                <label for="editTime" class="form-label">Time</label>
                                <input type="time" class="form-control" id="editTime" name="editTime">
                            </div>
                            <div class="col-12">
                                <label for="editStatus" class="form-label">Status</label>
                                <select id="editStatus" class="form-select" name="editStatus">
                                    <option>Scheduled</option>
                                    <option>In Processing</option>
                                    <option>Cancelled</option>
                                </select>
                            </div>
                        </form>
                        <!-- End Edit Appointment Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save Changes</button>
                    </div>
                </div>
            </div>
        </div><!-- End Edit Appointment Modal-->
        
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $('#editAppointmentModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget); // Button that triggered the modal
                var personID = button.data('person-id');
                var name = button.data('name'); // Extract info from data-* attributes
                var services = button.data('services'); // Assuming this is a list of services
                var date = button.data('date');
                var time = button.data('time');
                var status = button.data('status');

                // Update the modal's content.
                var modal = $(this);
                modal.find('#personID').val(personID);
                modal.find('#editCustomerName').val(name);
                modal.find('#editService').val(services); // You may want to handle this differently if it's multiple services
                modal.find('#editDate').val(date);
                modal.find('#editTime').val(time);
                modal.find('#editStatus').val(status);
            });
        </script>
    </body>

</html>