<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Account" %> <!-- Import your Account model -->
<%
    // No need to declare session manually; it's already available in JSP
    // You can directly use session
    if (session == null || session.getAttribute("account") == null) {
        // Redirect to login page if session is not found or account is not in session
        response.sendRedirect("adminLogin.jsp");
    } else {
        // Get the account object from session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 1 || account.getRole() == 2) {
            // Allow access to the page (do nothing and let the JSP render)
        } else {
            // Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            request.getRequestDispatcher("roleError").forward(request, response);
        }
    }
%>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Customer Management - NiceAdmin</title>
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
                <h1>Customer Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Manager</li>
                        <li class="breadcrumb-item active">Customer Management</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Customer</h5>
                                <p>Manage customers from this panel.</p>

                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Image</th>
                                            <th scope="col">Customer Name</th>
                                            <th scope="col">DOB</th>
                                            <th scope="col">Gender</th>
                                            <th scope="col">Email</th>
                                            <th scope="col">Actions</th>

                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach items="${customerList}" var="e" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td><img src="data:image/png;base64,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" alt="Shampoo" width="50"
                                                         height="50"></td>
                                                <td>${e.name}</td>
                                                <td>${e.dateOfBirth}</td>
                                                <td>${e.gender}</td>
                                                <td>${e.email}</td>
                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#viewCustomerModal" 
                                                            onclick="setModalData('${e.name}', '${e.dateOfBirth}', '${e.gender}', '${e.email}', '${e.phone}', '${e.address}')">
                                                        View
                                                    </button>
                                                    <button type="button" class="btn btn-success btn-sm" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#editCustomerModal"
                                                            data-id="${e.id}"
                                                            data-name="${e.name}"
                                                            data-dob="${e.dateOfBirth}"
                                                            data-gender="${e.gender}"
                                                            data-email="${e.email}"
                                                            data-phone="${e.phone}"
                                                            data-address="${e.address}"
                                                            >
                                                        Edit
                                                    </button>

                                                    <button class="btn btn-sm btn-danger" onclick="confirmDelete(${e.id})">Delete</button>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                                <!-- End Table with stripped rows -->

                                <div class="text-center mt-3">
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            data-bs-target="#addCustomerModal">
                                        <i class="bi bi-plus-circle me-1"></i> Add New Customer
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

        <!-- Add Customer Modal -->
        <div class="modal fade" id="addCustomerModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Customer</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Customer Form -->
                        <form class="row g-3">
                            <div class="col-12">
                                <label for="inputCustomerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" id="inputCustomerName">
                            </div>
                            <div class="col-12">
                                <label for="inputDescription" class="form-label">Description</label>
                                <textarea class="form-control" id="inputDescription" rows="3"></textarea>
                            </div>
                            <div class="col-md-6">
                                <label for="inputPrice" class="form-label">Price</label>
                                <input type="number" class="form-control" id="inputPrice">
                            </div>
                            <div class="col-md-6">
                                <label for="inputStock" class="form-label">Stock</label>
                                <input type="number" class="form-control" id="inputStock">
                            </div>
                            <div class="col-12">
                                <label for="inputImage" class="form-label">Customer Image</label>
                                <input type="file" class="form-control" id="inputImage" accept="image/*">
                            </div>
                        </form>
                        <!-- End Add Customer Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Add Customer</button>
                    </div>
                </div>
            </div>
        </div><!-- End Add Customer Modal-->

        <!-- Edit Customer Modal -->
        <div class="modal fade" id="editCustomerModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Customer</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <!-- Edit Customer Form -->
                        <form class="row g-3" action="customer-management" method="post">
                            <input type="hidden" name="id" id="inputCustomerId"> <!-- Hidden field for Customer ID -->
                            <div class="col-12">
                                <label for="inputCustomerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" id="inputName" name="name">
                            </div>

                            <div class="col-md-6">
                                <label for="inputDateOfBirth" class="form-label">Date of Birth</label>
                                <input type="date" class="form-control" id="inputDateOfBirth" name="dateOfBirth">
                            </div>
                            <div class="col-md-6">
                                <label for="inputGender" class="form-label">Gender</label>
                                <select class="form-select" id="inputGender" name="gender">
                                    <option value="M">Male</option>
                                    <option value="F">Female</option>
                                    <option value="O">Other</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="inputPhone" class="form-label">Phone</label>
                                <input type="text" class="form-control" id="inputPhone" name="phone">
                            </div>
                            <div class="col-md-6">
                                <label for="inputEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="inputEmail" name="email">
                            </div>
                            <div class="col-12">
                                <label for="inputAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" id="inputAddress" name="address">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </div>
                        </form>
                        <!-- End Edit Customer Form -->
                    </div>


                </div>
            </div>
        </div><!-- End Edit Customer Modal-->

        <!-- View Customer Modal -->
        <div class="modal fade" id="viewCustomerModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">View Customer</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- View Customer Form -->
                        <form class="row g-3">
                            <div class="col-12">
                                <label for="viewCustomerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" id="viewCustomerName" readonly>
                            </div>
                            <div class="col-12">
                                <label for="viewCustomerDOB" class="form-label">Date of Birth</label>
                                <input type="text" class="form-control" id="viewCustomerDOB" readonly>
                            </div>
                            <div class="col-12">
                                <label for="viewCustomerGender" class="form-label">Gender</label>
                                <input type="text" class="form-control" id="viewCustomerGender" readonly>
                            </div>
                            <div class="col-12">
                                <label for="viewCustomerEmail" class="form-label">Email</label>
                                <input type="text" class="form-control" id="viewCustomerEmail" readonly>
                            </div>
                            <div class="col-12">
                                <label for="viewCustomerPhone" class="form-label">Phone</label>
                                <input type="text" class="form-control" id="viewCustomerPhone" readonly>
                            </div>
                            <div class="col-12">
                                <label for="viewCustomerAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" id="viewCustomerAddress" readonly>
                            </div>
                        </form>
                        <!-- End View Customer Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div><!-- End View Customer Modal -->

        <!-- Notification Modal -->
        <div class="modal fade" id="editSuccessModal" tabindex="-1" aria-labelledby="editSuccessModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editSuccessModalLabel">Success</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Edit Successful!
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Notification Modal -->

    </body>
    <script>
                                                        function confirmDelete(id) {
                                                            if (confirm("Are you sure you want to delete this customer?")) {
                                                                // AJAX call to delete feedback
                                                                $.post('customer-management', {action: 'delete', id: id}, function () {
                                                                    location.reload(); // Reload the page to reflect changes
                                                                });
                                                            }
                                                        }

                                                        function confirmDelete(id) {
                                                            // display modal confirm
                                                            const isConfirmed = confirm("Are you sure you want to delete this customer?");

                                                            if (isConfirmed) {
                                                                // If user confirm delete customer => delete customer
                                                                // Send requirment delete to Servet
                                                                fetch(`customer-management/delete?id=${e.id}`, {
                                                                    method: 'DELETE'
                                                                })
                                                                        .then(response => {
                                                                            if (response.ok) {
                                                                                // delete successful and update table
                                                                                alert("Customer deleted successfully.");
                                                                                window.location.reload(); // Reload page to update
                                                                            } else {
                                                                                // Handle errors if necessary
                                                                                alert("Failed to delete customer. Please try again.");
                                                                            }
                                                                        })
                                                                        .catch(error => {
                                                                            console.error('Error:', error);
                                                                            alert("An error occurred. Please try again.");
                                                                        });
                                                            }
                                                        }
                                                        //===============================================
                                                        document.addEventListener('DOMContentLoaded', function () {
                                                            const editButtons = document.querySelectorAll('button[data-bs-target="#editCustomerModal"]');

                                                            editButtons.forEach(button => {
                                                                button.addEventListener('click', function () {

                                                                    const id = this.getAttribute('data-id');
                                                                    const name = this.getAttribute('data-name');
                                                                    const dob = this.getAttribute('data-dob');
                                                                    const gender = this.getAttribute('data-gender');
                                                                    const email = this.getAttribute('data-email');
                                                                    const phone = this.getAttribute('data-phone');
                                                                    const address = this.getAttribute('data-address');
                                                                    document.getElementById('inputCustomerId').value = id;
                                                                    document.getElementById('inputName').value = name;
                                                                    document.getElementById('inputDateOfBirth').value = dob;
                                                                    document.getElementById('inputGender').value = gender;
                                                                    document.getElementById('inputEmail').value = email;
                                                                    document.getElementById('inputPhone').value = phone;
                                                                    document.getElementById('inputAddress').value = address;

                                                                });
                                                            });
                                                        });

                                                        //View Customer Modal
                                                        function setModalData(name, dob, gender, email, phone, address) {
                                                            document.getElementById('viewCustomerName').value = name;
                                                            document.getElementById('viewCustomerDOB').value = dob;
                                                            document.getElementById('viewCustomerGender').value = gender;
                                                            document.getElementById('viewCustomerEmail').value = email;
                                                            document.getElementById('viewCustomerPhone').value = phone;
                                                            document.getElementById('viewCustomerAddress').value = address;
                                                        }


                                                        // Event when form sended
                                                        document.querySelector('.modal-body form').addEventListener('submit', function (event) {
                                                            event.preventDefault(); // prevent send form default 

                                                            // get infomation from form
                                                            const formData = new FormData(this);

                                                            // send requirement AJAX
                                                            fetch('customer-management', {
                                                                method: 'POST',
                                                                body: formData
                                                            })
                                                                    .then(response => response.json())
                                                                    .then(data => {
                                                                        if (data.success) {
                                                                            // display modal announce if update success 
                                                                            const editSuccessModal = new bootstrap.Modal(document.getElementById('editSuccessModal'));
                                                                            editSuccessModal.show();

                                                                            // Close modal
                                                                            const editCustomerModal = bootstrap.Modal.getInstance(document.getElementById('editCustomerModal'));
                                                                            editCustomerModal.hide();
                                                                        } else {
                                                                            // Handle errors if necessary
                                                                            alert('Failed to update customer. Please try again.');
                                                                        }
                                                                    })
                                                                    .catch(error => {
                                                                        console.error('Error:', error);
                                                                        alert('An error occurred. Please try again.');
                                                                    });
                                                        });


    </script>
</html>