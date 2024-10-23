<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Account" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
                <h1>Account Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="dashboard.jsp">Home</a></li>
                        <li class="breadcrumb-item active">Account Management</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">User Accounts</h5>
                                <p>Manage user accounts from this panel.</p>

                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Email</th>
                                            <th scope="col">Role</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- Iterate over the 'accounts' list passed from the servlet -->
                                        <c:forEach var="account" items="${accounts}" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>  <!-- Auto-incremented index -->
                                                <td>${account.personInfo.name}</td>  <!-- Display person's name -->
                                                <td>${account.personInfo.email}</td>  <!-- Display person's email -->
                                                <td>${account.roleName}</td>  <!-- Display role name -->
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${account.status == 'Active'}">
                                                            <span class="badge bg-success">Active</span>
                                                        </c:when>
                                                        <c:when test="${account.status == 'Suspended'}">
                                                            <span class="badge bg-danger">Suspended</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">Unknown</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <!-- Buttons for actions such as edit and delete -->
                                                    <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                                            data-bs-target="#editAccountModal">View</button>
                                                    <button type="button" class="btn btn-danger btn-sm">Delete</button>
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

        <!-- ======= Footer ======= -->
        <footer id="footer" class="footer">
            <div class="copyright">
                &copy; Copyright <strong><span>SmartBeautySpa</span></strong>. All Rights Reserved
            </div>
            <div class="credits">
                Designed by Chien</a>
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

        <!-- Edit Account Modal -->
        <div class="modal fade" id="editAccountModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Account</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Edit Account Form -->
                        <form class="row g-3">
                            <div class="col-12">
                                <label for="editName" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="editName" value="Brandon Jacob">
                            </div>
                            <div class="col-12">
                                <label for="editEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="editEmail" value="brandon@example.com">
                            </div>
                            <div class="col-12">
                                <label for="editRole" class="form-label">Role</label>
                                <select id="editRole" class="form-select">
                                    <option>Admin</option>
                                    <option>Manager</option>
                                    <option>Staff</option>
                                </select>
                            </div>
                            <div class="col-12">
                                <label for="editStatus" class="form-label">Status</label>
                                <select id="editStatus" class="form-select">
                                    <option>Active</option>
                                    <option>Pending</option>
                                    <option>Suspended</option>
                                </select>
                            </div>
                        </form>
                        <!-- End Edit Account Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save Changes</button>
                    </div>
                </div>
            </div>
        </div><!-- End Edit Account Modal-->

    </body>

</html>