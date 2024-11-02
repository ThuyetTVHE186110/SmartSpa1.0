<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<style>
    .filter-container {
        display: flex;
        gap: 20px; /* Space between the filter groups */
        align-items: center; /* Center the elements vertically */
    }
    .filter-group {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
    }
    .filter-group label {
        display: inline-block;
        margin-bottom: 5px;
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
        <script>
            function filterAccounts() {
                const selectedRole = document.getElementById("roleFilter").value;
                const rows = document.querySelectorAll(".account-row");

                rows.forEach(row => {
                    const role = row.getAttribute("data-role");
                    if (selectedRole === "all" || role === selectedRole) {
                        row.style.display = "";
                    } else {
                        row.style.display = "none";
                    }
                });
            }
        </script>
        <script>
            function filterAccounts() {
                const selectedRole = document.getElementById("roleFilter").value.toLowerCase();
                const selectedStatus = document.getElementById("statusFilter").value.toLowerCase();
                const rows = document.querySelectorAll(".account-row");

                rows.forEach(row => {
                    const role = row.getAttribute("data-role").toLowerCase();
                    const status = row.getAttribute("data-status").toLowerCase();

                    const matchesRole = selectedRole === "all" || role === selectedRole;
                    const matchesStatus = selectedStatus === "all" || status === selectedStatus;

                    if (matchesRole && matchesStatus) {
                        row.style.display = "";
                    } else {
                        row.style.display = "none";
                    }
                });
            }
        </script>

        <main id="main" class="main">

            <div class="pagetitle">
                <h1>Account Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="dashboard">Home</a></li>
                        <li class="breadcrumb-item active">Account</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Account Details</h5>
                                <div class="row mb-3">
                                    <!-- Filter Form -->
                                    <form id="filterForm" action="accountManagement" method="get" class="filter-container">
                                        <div class="filter-group">
                                            <label for="roleFilter">Role:</label>
                                            <select id="roleFilter" name="roleFilter" class="form-select" onchange="submitFilterForm()">
                                                <option value="all" ${roleFilter == null || roleFilter == 'all' ? 'selected' : ''}>All Roles</option>
                                                <option value="Admin" ${roleFilter == 'Admin' ? 'selected' : ''}>Admin</option>
                                                <option value="Manager" ${roleFilter == 'Manager' ? 'selected' : ''}>Manager</option>
                                                <option value="Staff" ${roleFilter == 'Staff' ? 'selected' : ''}>Staff</option>
                                            </select>
                                        </div>

                                        <div class="filter-group">
                                            <label for="statusFilter">Status:</label>
                                            <select id="statusFilter" name="statusFilter" class="form-select" onchange="submitFilterForm()">
                                                <option value="all" ${statusFilter == null || statusFilter == 'all' ? 'selected' : ''}>All</option>
                                                <option value="Active" ${statusFilter == 'Active' ? 'selected' : ''}>Active</option>
                                                <option value="Suspended" ${statusFilter == 'Suspended' ? 'selected' : ''}>Suspended</option>
                                            </select>
                                        </div>
                                    </form>


                                    <script>
                                        function submitFilterForm() {
                                            // Submit the form to apply filters and retain the selected filter states
                                            document.getElementById('filterForm').submit();
                                        }
                                    </script>


                                </div>
                            </div>
                            <!-- Table with stripped rows -->
                            <table class="table datatable">
                                <thead>
                                    <tr>
                                        <th scope="col">No</th>
                                        <th scope="col">Image</th> <!-- New column for image -->
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
                                        <tr class="account-row" data-role="${account.roleName}" data-status="${account.status}">
                                            <th scope="row">${status.index + 1}</th>  <!-- Auto-incremented index -->
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty account.personInfo.image}">
                                                        <img src="newUI/assets/img/${account.personInfo.image}" alt="Image" style="width: 150px; height: 200px; object-fit: cover;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="newUI/assets/img/default-avartar.jpg" alt="Default Image" style="width: 150px; height: 200px; object-fit: cover;">
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

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
                                                <button type="button" class="btn btn-primary btn-sm view-account-btn"
                                                        data-bs-toggle="modal" data-bs-target="#editAccountModal"
                                                        data-id="${account.id}"
                                                        data-name="${account.personInfo.name}"
                                                        data-email="${account.personInfo.email}"
                                                        data-role="${account.roleName}"
                                                        data-status="${account.status}">
                                                    View
                                                </button>
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
                        <!-- Pagination Controls -->
                        <c:if test="${totalPages > 1}">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="accountManagement?page=${currentPage - 1}&roleFilter=${param.roleFilter}&statusFilter=${param.statusFilter}" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                    </c:if>

                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="accountManagement?page=${i}&roleFilter=${param.roleFilter}&statusFilter=${param.statusFilter}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="accountManagement?page=${currentPage + 1}&roleFilter=${param.roleFilter}&statusFilter=${param.statusFilter}" aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </c:if>
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
                        <form id="addAccountForm" class="row g-3" action="accountManagement" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="col-12">
                                <label for="inputName" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="inputName" name="name" required>
                            </div>
                            <div class="col-12">
                                <label for="inputEmail" class="form-label">Email (Username)</label>
                                <input type="email" class="form-control" id="inputEmail" name="username" required>
                            </div>
                            <div class="col-12">
                                <label for="inputPassword" class="form-label">Password</label>
                                <input type="password" class="form-control" id="inputPassword" name="password" required>
                            </div>
                            <div class="col-12">
                                <label for="confirmPassword" class="form-label">Confirm Password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            </div>
                            <div class="col-12">
                                <label for="inputRole" class="form-label">Role</label>
                                <select id="inputRole" name="role" class="form-select" required>
                                    <option value="">Choose...</option>
                                    <option value="1">Admin</option>
                                    <option value="2">Manager</option>
                                    <option value="3">Staff</option>
                                </select>
                            </div>
                           
                        </form>
                        <!-- End Add Account Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" form="addAccountForm" class="btn btn-primary">Add Account</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Add Account Modal -->


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
                        <form id="editAccountForm" class="row g-3" method="post" action="accountManagement">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" id="editAccountId" name="id"> <!-- Account ID -->

                            <div class="mb-3">
                                <label for="editPersonName" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="editPersonName" name="name" required>
                            </div>

                            <div class="mb-3">
                                <label for="editUsername" class="form-label">Username</label>
                                <input type="text" class="form-control" id="editUsername" name="username" required>
                            </div>

                            <div class="col-12">
                                <label for="newPassword" class="form-label">New Password</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword">
                                <button type="button" onclick="togglePasswordVisibility('newPassword')">Show</button>
                            </div>
                            <div class="col-12">
                                <label for="confirmPassword" class="form-label">Confirm New Password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
                                <button type="button" onclick="togglePasswordVisibility('confirmPassword')">Show</button>
                            </div>

                            <div class="mb-3">
                                <label for="editRole" class="form-label">Role</label>
                                <select id="editRole" name="role" class="form-select">
                                    <option value="1">Admin</option>
                                    <option value="2">Manager</option>
                                    <option value="3">Staff</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="editStatus" class="form-label">Status</label>
                                <select id="editStatus" name="status" class="form-select">
                                    <option value="Active">Active</option>
                                    <option value="Suspended">Suspended</option>
                                </select>
                            </div>
                        </form>
                        <!-- End Edit Account Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" form="editAccountForm" class="btn btn-primary">Save Changes</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const viewButtons = document.querySelectorAll(".view-account-btn");

                viewButtons.forEach(button => {
                    button.addEventListener("click", function () {
                        const id = button.getAttribute("data-id");
                        const name = button.getAttribute("data-name");
                        const username = button.getAttribute("data-email");
                        const role = button.getAttribute("data-role");
                        const status = button.getAttribute("data-status");

                        // Set modal fields
                        document.getElementById("editAccountId").value = id;
                        document.getElementById("editPersonName").value = name;
                        document.getElementById("editUsername").value = username;
                        document.getElementById("editStatus").value = status;

                        // Set role dropdown by value
                        const roleDropdown = document.getElementById("editRole");
                        roleDropdown.value = role;

                        // Debugging: Log to console
                        console.log("ID:", id, "Name:", name, "Username:", username, "Role:", role, "Status:", status);

                        // Ensure the correct role is selected in dropdown
                        Array.from(roleDropdown.options).forEach(option => {
                            option.selected = option.text === role;
                        });
                    });
                });
            });

            function togglePasswordVisibility(inputId) {
                const input = document.getElementById(inputId);
                const button = input.nextElementSibling;
                if (input.type === "password") {
                    input.type = "text";
                    button.textContent = "Hide";
                } else {
                    input.type = "password";
                    button.textContent = "Show";
                }
            }

            document.getElementById("editAccountForm").addEventListener("submit", function (event) {
                const newPassword = document.getElementById("newPassword").value;
                const confirmPassword = document.getElementById("confirmPassword").value;

                if (newPassword !== confirmPassword) {
                    event.preventDefault(); // Stop form submission
                    alert("Passwords do not match. Please make sure both fields are identical.");
                }
            });

            document.addEventListener("DOMContentLoaded", function () {
                const viewButtons = document.querySelectorAll(".view-account-btn");
                const newPasswordField = document.getElementById("newPassword");
                const confirmPasswordField = document.getElementById("confirmPassword");

                viewButtons.forEach(button => {
                    button.addEventListener("click", function () {
                        // Clear new password fields
                        newPasswordField.value = "";
                        confirmPasswordField.value = "";
                    });
                });
            });

            document.addEventListener("DOMContentLoaded", function () {
                const addAccountModal = document.getElementById('addAccountModal');
                const addAccountForm = document.getElementById('addAccountForm');

                // Clear form fields when the modal is shown
                addAccountModal.addEventListener('show.bs.modal', function () {
                    addAccountForm.reset(); // This will clear all input fields in the form
                });
            });
        </script>
        <%-- Success and Error Message Display --%>
        <script>
            setTimeout(function () {
                const successAlert = document.querySelector('.alert-success');
                const errorAlert = document.querySelector('.alert-danger');
                if (successAlert)
                    successAlert.style.display = 'none';
                if (errorAlert)
                    errorAlert.style.display = 'none';
            }, 5000); // Auto-dismiss after 5 seconds
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

    </body>

</html>