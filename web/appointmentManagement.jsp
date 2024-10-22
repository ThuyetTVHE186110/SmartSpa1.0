<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="model.Account" %> <!-- Add this line to import the Account class -->

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
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f4f8;
                margin: 0;
                padding: 0;
            }
            .container {
                display: flex;
                align-items: center;
                padding: 10px;
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .dropdown, .search, .button, .date-picker, .view-dropdown, .add-button {
                margin-right: 10px;
                display: flex;
                align-items: center;
                background-color: #f8f9fa;
                border: 1px solid #ced4da;
                border-radius: 4px;
                padding: 5px 10px;
            }
            .dropdown select, .view-dropdown select {
                border: none;
                background: none;
                font-size: 14px;
                outline: none;
            }
            .search input {
                border: none;
                background: none;
                font-size: 14px;
                outline: none;
            }
            .button, .add-button {
                background-color: #e9ecef;
                border: none;
                cursor: pointer;
                font-size: 14px;
                color: #28a745;
            }
            .add-button {
                background-color: #28a745;
                color: #ffffff;
                padding: 5px 15px;
            }
            .date-picker {
                display: flex;
                align-items: center;
            }
            .date-picker i {
                margin: 0 5px;
                cursor: pointer;
            }
        </style>
    </head>

    <body>

        <jsp:include page="headerHTML.jsp" />

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
                        <li>
                            <a href="employee-management" class="active">
                                <i class="bi bi-circle"></i><span>Staff Management</span>
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
                            <a href="feedback-management">
                                <i class="bi bi-circle"></i><span>Feedback Management</span>
                            </a>
                        </li>
                    </ul>
                </li><!-- End Staff Nav -->

            </ul>

        </aside><!-- End Sidebar-->
        <!-- Include the Navbar -->
        <jsp:include page="sideBar.jsp" />

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
                                <div class="container">
                                    <div class="dropdown">
                                        <select id="employee-select">
                                            <option>Choose Staff</option>
                                            <c:forEach items="${requestScope.staffList}" var="staff">
                                                <option>${staff.name}</option>>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="search">
                                        <form action="appointment-management" method="post">
                                            <i class="fas fa-search"></i>
                                            <input type="text" name="searchTerm" placeholder="Search Customer" value="${searchTerm}">
                                            <input type="hidden" name="action" value="customerSearch">
                                            <button type="submit">Search</button>
                                        </form>
                                    </div>
                                    <button class="button">Today</button>
                                    <div class="date-picker">
                                        <form action="appointment-management" method="post">
                                            <input type="date" placeholder="" id="appointment-date">
                                        </form>

                                    </div>
                                    <!--                                    <div class="view-dropdown">
                                                                            <select>
                                                                                <option>Xem theo ngày</option>
                                                                            </select>
                                                                        </div>-->
                                    <!--<button class="add-button"><i class="fas fa-plus"></i> Thêm lịch</button>-->
                                </div>
                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Customer</th>
                                            <th scope="col">Time</th>
                                            <th scope="col">Service</th>
                                            <th scope="col">Staff</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.appointmentList}" var="appointment">
                                            <tr>
                                                <th scope="row">A${appointment.id}</th>
                                                <td>${appointment.person.name}</td>
                                                <td> 
                                                    ${appointment.appointmentDate} ${appointment.appointmentTime}
                                                </td>
                                                <td>
                                                    <c:set var="size" value="${fn:length(appointmentServicesMap[appointment.id])}" />
                                                    <c:set var="count" value="0" />
                                                    <c:set var="serviceIds" value="" />
                                                    <c:forEach items="${appointmentServicesMap[appointment.id]}" var="service">
                                                        <c:set var="count" value="${count + 1}" />
                                                        <c:set var="serviceIds" value="${serviceIds}${service.id}," />
                                                        ${service.name}<c:if test="${count < size}">,</c:if>
                                                    </c:forEach>
                                                    <c:if test="${not empty appointment.note}"> 
                                                        <div style="color: gray; font-size: 12px; margin-top: 5px;">
                                                            <span style="font-style: italic;">Note:</span>
                                                            <span>${appointment.note}</span>
                                                        </div>
                                                    </c:if>
                                                </td>
                                                <td></td>
                                                <td><span class="badge bg-success">${appointment.status}</span></td>
                                                <td>
                                                    <button type="button" class="btn btn-success">Check Out</button>
                                                    <button type="button" class="btn btn-primary btn-sm edit-button" data-bs-toggle="modal"
                                                            data-bs-target="#editAppointmentModal"
                                                            date-person-id="${appointment.person.id}"
                                                            data-name="${appointment.person.name}"
                                                            data-services="${serviceIds}" 
                                                            data-date="${appointment.appointmentDate}"
                                                            data-time="${appointment.appointmentTime}"
                                                            data-status="${appointment.status}">Edit</button>
                                                    <form action="appointment-management" method="post">
                                                        <input type="hidden" name="action" value="deleteAppointment">
                                                        <input type="hidden" name="appointmentID" value="${appointment.id}">
                                                        <button type="button" class="btn btn-danger btn-sm cancel-button">
                                                            Cancel
                                                        </button>
                                                    </form>
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
                    <div class="alert" id="success-alert" style="display: none;">
                        <strong>Deleted appointment successfully!</strong>
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
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content">
                    <form action="action" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Appointment</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Edit Appointment Form -->
                            <div class="row g-3">
                                <div class="col-12">
                                    <label for="editCustomerName" class="form-label">Customer Name</label>
                                    <input type="text" class="form-control" id="editCustomerName" readonly>
                                </div>
                                <div class="col-12">
                                    <label for="editServices" class="form-label">Services</label>
                                    <div id="editServices">
                                        <!-- Dịch vụ sẽ được thêm vào đây -->
                                    </div>
                                </div>
                                <div class="col-12">
                                    <button type="button" class="btn btn-primary add-service" data-bs-toggle="modal" data-bs-target="#addServiceModal">Add Service</button>
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
                                <div class="col-12">
                                    <label for="editNote" class="form-label">Note</label>
                                    <input type="text" class="form-control" id="editNote">
                                </div>
                            </div>
                            <!-- End Edit Appointment Form -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div><!-- End Edit Appointment Modal -->

        <!-- Modal để thêm dịch vụ -->
        <div class="modal fade" id="addServiceModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Select Service</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="list-group">
                            <c:forEach items="${requestScope.serviceList}" var="service">
                                <button type="button" class="list-group-item list-group-item-action add-service-item" data-service-id="${service.id}" data-service-name="${service.name}">${service.name}</button>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- End Add Service Modal -->

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            var serviceMap = {};
            <c:forEach items="${requestScope.serviceList}" var="service">
            serviceMap["${service.id}"] = "${service.name}"; // Tạo ánh xạ giữa ID và tên dịch vụ
            </c:forEach>
            console.log(serviceMap);
        </script>
        <script>

            $('#editAppointmentModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget); // Button that triggered the modal
                var personID = button.data('person-id');
                var name = button.data('name');

                // Lấy dữ liệu các dịch vụ và loại bỏ dấu phẩy cuối cùng nếu có
                var services = button.data('services').replace(/,\s*$/, '').split(',');
                console.log("Services:", services); // Kiểm tra chuỗi services đã chia tách

                var date = button.data('date');
                var time = button.data('time');
                var status = button.data('status');

                // Update nội dung của modal
                var modal = $(this);
                modal.find('#editCustomerName').val(name);
                modal.find('#editDate').val(date);
                modal.find('#editTime').val(time);
                modal.find('#editStatus').val(status);

                // Hiển thị các dịch vụ đã chọn
                var servicesDiv = modal.find('#editServices');
                servicesDiv.empty(); // Xóa nội dung cũ

                services.forEach(function (serviceId) {
                    // Loại bỏ khoảng trắng nếu có trong serviceId
                    serviceId = serviceId.trim();

                    console.log("Processing serviceId:", serviceId); // Kiểm tra từng serviceId
                    var serviceName = serviceMap[serviceId]; // Lấy tên dịch vụ từ serviceMap

                    if (serviceName) {
                        console.log("Adding service:", serviceName); // Kiểm tra serviceName
                        servicesDiv.append('<div class="selected-service" data-service-id="' + serviceId + '">' + serviceName + ' <button type="button" class="btn btn-danger btn-sm remove-service">Remove</button></div>');
                    } else {
                        console.log("ServiceId not found in serviceMap:", serviceId);
                    }
                });

                // Thêm sự kiện click cho nút "Remove"
                servicesDiv.off('click').on('click', '.remove-service', function () {
                    var removedService = $(this).parent(); // Lấy phần tử chứa dịch vụ đã xóa
                    var serviceId = removedService.data('service-id'); // Lấy serviceId từ thuộc tính data-service-id

                    console.log("Removing service with ID:", serviceId); // In ra ID của dịch vụ bị xóa

                    removedService.remove(); // Xóa dịch vụ đã chọn
                });
            });

            // Thêm sự kiện click cho các dịch vụ
            $(document).on('click', '.add-service-item', function () {
                var serviceId = $(this).data('service-id');
                var serviceName = $(this).data('service-name');
                var servicesDiv = $('#editServices');

                // Kiểm tra xem dịch vụ đã được thêm chưa
                if (!servicesDiv.find('[data-service-id="' + serviceId + '"]').length) {
                    servicesDiv.append('<div class="selected-service" data-service-id="' + serviceId + '">' + serviceName + ' <button type="button" class="btn btn-danger btn-sm remove-service">Remove</button></div>');
                }

                $('#addServiceModal').modal('hide'); // Đóng modal thêm dịch vụ
            });

            $(document).ready(function () {
                $('.cancel-button').click(function (event) {
                    event.preventDefault(); // Ngăn chặn hành động mặc định của nút
                    var confirmation = confirm("Are you sure you want to cancel this appointment?");

                    if (confirmation) {
                        var form = $(this).closest('form'); // Lấy form gần nhất

                        // Hiển thị thông báo thành công
                        $('#success-alert').fadeIn().delay(2000).fadeOut(function () {
                            // Tải lại trang sau khi thông báo đã fade out
                            form.submit();
                        });
                    } else {
                        console.log("Cancellation aborted."); // Người dùng nhấn Cancel
                    }
                });
            });
            function setTodayDate() {
                var today = new Date(); // Lấy ngày hiện tại
                var yyyy = today.getFullYear(); // Năm
                var mm = String(today.getMonth() + 1).padStart(2, '0'); // Tháng (cộng thêm 1 vì getMonth() trả về từ 0-11)
                var dd = String(today.getDate()).padStart(2, '0'); // Ngày

                var todayFormatted = yyyy + '-' + mm + '-' + dd; // Format ngày theo chuẩn HTML date input
                document.getElementById('appointment-date').value = todayFormatted; // Gán giá trị ngày hiện tại cho input
            }

            // Gọi hàm khi trang tải xong
            window.onload = setTodayDate;
        </script>
    </body>

</html>