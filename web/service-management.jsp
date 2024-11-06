<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ page import="model.Account" %>
            <% // No need to declare session manually; it's already available in JSP // You can directly use session
                if(session==null || session.getAttribute("account")==null) { // Redirect to login page if session is not found or account is not in session 
                response.sendRedirect("adminLogin.jsp"); 
                } else { 
                // Get the account object from session 
                Account account=(Account) session.getAttribute("account"); 
                if(account.getRole()==1||
                account.getRole()==2) { // Allow access to the page (do nothing and let the JSP render) 
                } else { //Set an error message and redirect to an error page
                request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard."
                ); request.getRequestDispatcher("roleError").forward(request, response); } } %>

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
                                                    <input type="text" class="form-control"
                                                        placeholder="Search services" name="search"
                                                        value="${param.search}">
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
                                                                <th scope="col">Status</th>
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
                                                                        alt="${service.name}" width="50" height="50">
                                                                    </td>
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
                                                                        <span
                                                                            class="badge bg-${service.status == 'ACTIVE' ? 'success' : 'warning'}">
                                                                            ${service.status}
                                                                        </span>
                                                                    </td>
                                                                    <td>
                                                                        <div class="d-flex gap-2">
                                                                            <button type="button"
                                                                                class="btn btn-sm btn-primary"
                                                                                data-bs-toggle="modal"
                                                                                data-bs-target="#editServiceModal${service.id}">
                                                                                <i class="bi bi-pencil"></i>
                                                                            </button>
                                                                            <button type="button"
                                                                                class="btn btn-sm ${service.status eq 'ACTIVE' ? 'btn-warning' : 'btn-success'}"
                                                                                onclick="toggleStatus(${service.id}, '${service.status}')">
                                                                                <i
                                                                                    class="bi ${service.status eq 'ACTIVE' ? 'bi-pause-fill' : 'bi-play-fill'}"></i>
                                                                                ${service.status eq 'ACTIVE' ?
                                                                                'Deactivate' : 'Activate'}
                                                                            </button>
                                                                        </div>
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
                                                                <li
                                                                    class="page-item ${currentPage == i ? 'active' : ''}">
                                                                    <a class="page-link"
                                                                        href="servicemanagement?page=${i}&search=${param.search}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li
                                                                class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                                <a class="page-link"
                                                                    href="servicemanagement?page=${currentPage + 1}&search=${param.search}">Next</a>
                                                            </li>
                                                        </ul>
                                                    </nav>
                                                    <!-- End Pagination -->

                                                    <div class="text-center mt-3">
                                                        <button type="button" class="btn btn-primary"
                                                            data-bs-toggle="modal" data-bs-target="#addServiceModal">
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
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="${pageContext.request.contextPath}/servicemanagement" method="post"
                                        enctype="multipart/form-data" class="needs-validation" novalidate>
                                        <div class="mb-3">
                                            <label for="name" class="form-label">Service Name <span
                                                    class="text-danger">*</span></label>
                                            <input type="text" class="form-control" id="name" name="name" required
                                                maxlength="100" pattern="^[a-zA-Z0-9\s\-&]+$">
                                            <div class="invalid-feedback">
                                                Please enter a valid service name (max 100 characters).
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="price" class="form-label">Price <span
                                                    class="text-danger">*</span></label>
                                            <input type="number" class="form-control" id="price" name="price" required
                                                min="0" max="999999">
                                            <div class="invalid-feedback">
                                                Please enter a valid price (0 or greater).
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="duration" class="form-label">Duration (minutes) <span
                                                    class="text-danger">*</span></label>
                                            <input type="number" class="form-control" id="duration" name="duration"
                                                required min="1" max="480">
                                            <div class="invalid-feedback">
                                                Please enter a valid duration (1-480 minutes).
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="description" class="form-label">Description <span
                                                    class="text-danger">*</span></label>
                                            <textarea class="form-control" id="description" name="description" rows="3"
                                                required maxlength="500"></textarea>
                                            <div class="invalid-feedback">
                                                Please enter a description (max 500 characters).
                                            </div>
                                            <div class="form-text">
                                                <span id="descriptionCount">0</span>/500 characters
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="file" class="form-label">Image <span
                                                    class="text-danger">*</span></label>
                                            <input type="file" class="form-control" id="file" name="file" required
                                                accept=".jpg,.jpeg,.png">
                                            <div class="invalid-feedback">
                                                Please select an image file (JPG or PNG only).
                                            </div>
                                            <div class="form-text">
                                                Maximum file size: 5MB
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="category" class="form-label">Category <span
                                                    class="text-danger">*</span></label>
                                            <select class="form-select" id="category" name="category" required>
                                                <option value="">Select Category</option>
                                                <option value="Extensions">Extensions</option>
                                                <option value="Lifts & Tints">Lifts & Tints</option>
                                            </select>
                                            <div class="invalid-feedback">
                                                Please select a category.
                                            </div>
                                        </div>
                                        <input type="hidden" name="action" value="insert">
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Close</button>
                                            <button type="submit" class="btn btn-primary">Add Service</button>
                                        </div>
                                    </form>
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
                                        <form action="servicemanagement?action=update" method="post"
                                            enctype="multipart/form-data" class="needs-validation" novalidate>
                                            <input type="hidden" name="id" value="${service.id}">
                                            <input type="hidden" name="currentImage" value="${service.image}">

                                            <div class="mb-3">
                                                <label for="name${service.id}" class="form-label">Service Name <span
                                                        class="text-danger">*</span></label>
                                                <input type="text" class="form-control" id="name${service.id}"
                                                    name="name" value="${service.name}" required maxlength="100"
                                                    pattern="^[a-zA-Z0-9\s\-&]+$">
                                                <div class="invalid-feedback">
                                                    Please enter a valid service name (max 100 characters).
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <label for="price${service.id}" class="form-label">Price <span
                                                        class="text-danger">*</span></label>
                                                <input type="number" class="form-control" id="price${service.id}"
                                                    name="price" value="${service.price}" required min="0" max="999999">
                                                <div class="invalid-feedback">
                                                    Please enter a valid price (0 or greater).
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <label for="duration${service.id}" class="form-label">Duration (minutes)
                                                    <span class="text-danger">*</span></label>
                                                <input type="number" class="form-control" id="duration${service.id}"
                                                    name="duration" value="${service.duration}" required min="1"
                                                    max="480">
                                                <div class="invalid-feedback">
                                                    Please enter a valid duration (1-480 minutes).
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <label for="description${service.id}" class="form-label">Description
                                                    <span class="text-danger">*</span></label>
                                                <textarea class="form-control" id="description${service.id}"
                                                    name="description" rows="3" required
                                                    maxlength="500">${service.description}</textarea>
                                                <div class="invalid-feedback">
                                                    Please enter a description (max 500 characters).
                                                </div>
                                                <div class="form-text">
                                                    <span id="descriptionCount${service.id}">0</span>/500 characters
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <label for="file${service.id}" class="form-label">Image</label>
                                                <input type="file" class="form-control" id="file${service.id}"
                                                    name="file" accept=".jpg,.jpeg,.png">
                                                <div class="invalid-feedback">
                                                    Please select an image file (JPG or PNG only).
                                                </div>
                                                <div class="form-text">
                                                    Current image: ${service.image}<br>
                                                    Leave empty to keep current image. Maximum file size: 5MB
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <label for="category${service.id}" class="form-label">Category <span
                                                        class="text-danger">*</span></label>
                                                <select class="form-select" id="category${service.id}" name="category"
                                                    required>
                                                    <option value="">Select Category</option>
                                                    <option value="Extensions" ${service.category eq 'Extensions'
                                                        ? 'selected' : '' }>Extensions</option>
                                                    <option value="Lifts & Tints" ${service.category eq 'Lifts & Tints'
                                                        ? 'selected' : '' }>Lifts & Tints</option>
                                                </select>
                                                <div class="invalid-feedback">
                                                    Please select a category.
                                                </div>
                                            </div>

                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                            </div>
                                        </form>
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

                    <script>
                        document.addEventListener('DOMContentLoaded', function () {
                            // Initialize all modals
                            var modals = document.querySelectorAll('.modal');
                            modals.forEach(function (modal) {
                                new bootstrap.Modal(modal);
                            });
                        });

                        function toggleStatus(serviceId, currentStatus) {
                            if (!serviceId) {
                                console.error('Service ID is missing');
                                return;
                            }

                            const newStatus = currentStatus === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
                            const actionText = currentStatus === 'ACTIVE' ? 'deactivate' : 'activate';

                            if (confirm(`Are you sure you want to ${actionText} this service?`)) {
                                window.location.href = 'servicemanagement?action=updateStatus&id=' + serviceId + '&status=' + newStatus;
                            }
                        }

                        function filterByStatus(status) {
                            window.location.href = 'servicemanagement?statusFilter=' + status;
                        }

                        // Make sure Bootstrap is loaded
                        if (typeof bootstrap === 'undefined') {
                            console.error('Bootstrap is not loaded!');
                        } else {
                            console.log('Bootstrap is loaded successfully');
                        }
                    </script>

                    <!-- Add this JavaScript for form validation -->
                    <script>
                        // Form validation
                        document.addEventListener('DOMContentLoaded', function () {
                            'use strict'

                            // Fetch all forms that need validation
                            const forms = document.querySelectorAll('.needs-validation')

                            // Loop over them and prevent submission
                            Array.prototype.slice.call(forms).forEach(function (form) {
                                form.addEventListener('submit', function (event) {
                                    if (!form.checkValidity()) {
                                        event.preventDefault()
                                        event.stopPropagation()
                                    }

                                    form.classList.add('was-validated')
                                }, false)
                            })

                            // Character counter for description
                            const descriptionTextarea = document.getElementById('description')
                            const descriptionCount = document.getElementById('descriptionCount')

                            if (descriptionTextarea && descriptionCount) {
                                descriptionTextarea.addEventListener('input', function () {
                                    const remaining = this.value.length
                                    descriptionCount.textContent = remaining

                                    if (remaining > 500) {
                                        this.value = this.value.substring(0, 500)
                                        descriptionCount.textContent = 500
                                    }
                                })
                            }

                            // File size validation
                            const fileInput = document.getElementById('file')
                            if (fileInput) {
                                fileInput.addEventListener('change', function () {
                                    if (this.files[0]) {
                                        const fileSize = this.files[0].size / 1024 / 1024 // in MB
                                        if (fileSize > 5) {
                                            this.setCustomValidity('File size must be less than 5MB')
                                            this.reportValidity()
                                        } else {
                                            this.setCustomValidity('')
                                        }
                                    }
                                })
                            }
                        })
                    </script>

                    <script>
                        document.addEventListener('DOMContentLoaded', function () {
                            // Character counter for description in edit forms
                            document.querySelectorAll('[id^="description"]').forEach(function (textarea) {
                                const countSpan = document.getElementById(textarea.id + 'Count');
                                if (countSpan) {
                                    // Update initial count
                                    countSpan.textContent = textarea.value.length;

                                    // Add input listener
                                    textarea.addEventListener('input', function () {
                                        const remaining = this.value.length;
                                        countSpan.textContent = remaining;

                                        if (remaining > 500) {
                                            this.value = this.value.substring(0, 500);
                                            countSpan.textContent = 500;
                                        }
                                    });
                                }
                            });

                            // File size validation for edit forms
                            document.querySelectorAll('[id^="file"]').forEach(function (input) {
                                input.addEventListener('change', function () {
                                    if (this.files[0]) {
                                        const fileSize = this.files[0].size / 1024 / 1024; // in MB
                                        if (fileSize > 5) {
                                            this.setCustomValidity('File size must be less than 5MB');
                                            this.reportValidity();
                                        } else {
                                            this.setCustomValidity('');
                                        }
                                    }
                                });
                            });
                        });
                    </script>

                </body>

                </html>