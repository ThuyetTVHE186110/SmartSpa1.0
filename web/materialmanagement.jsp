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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Material Management - NiceAdmin</title>
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
        <!-- Include the Navbar -->
        <jsp:include page="sideBar.jsp" />

        <main id="main" class="main">

            <div class="pagetitle">
                <h1>Material Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Manager</li>
                        <li class="breadcrumb-item active">Material Management</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Materials</h5>
                                <p>Manage materials from this panel.</p>
                                <form action="materialmanagement" method="get class="mb-3">
                                    <div class="input-group">
                                        <input type="text" name="search" class="form-control" placeholder="Search materials" value="${param.search}">
                                        <button class="btn btn-primary" type="submit">Search</button>
                                    </div>
                                </form>

                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Image</th>
                                            <th scope="col">Material Name</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Status</th>
                                            <th scope="col" style="width: 15%;">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${materialList}" var="material">
                                            <tr>
                                                <th scope="row">${material.id}</th>
                                                <td><img src="<c:out value=" ${material.image}" />"
                                                         alt="${material.name}" width="50" height="50">
                                                </td>
                                                <td>${material.name}</td>
                                                <td>${material.description}</td>
                                                <td>${material.price}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${material.status == 'Available'}">
                                                            <span class="badge bg-success">Available</span>
                                                        </c:when>
                                                        <c:when test="${material.status == 'UnAvailable'}">
                                                            <span class="badge bg-danger">UnAvailable</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">Unknown</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#editMaterialModal" 
                                                            data-id="${material.id}">
                                                        Edit
                                                    </button>
                                                    <a href="deletematerial?materialID=${material.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this material?');">Delete</a>
                                                </td>
                                            </tr>

                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!-- End Table with stripped rows -->

                                <div class="text-center mt-3">
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            data-bs-target="#addMaterialModal">
                                        <i class="bi bi-plus-circle me-1"></i> Add New Material
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

        <!-- Add Material Modal -->
        <div class="modal fade" id="addMaterialModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Material</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Material Form -->
                        <form action="${pageContext.request.contextPath}/addmaterial" method="post" enctype="multipart/form-data" class="row g-3">
                            <div class="col-12">
                                <label for="name" class="form-label">Material Name</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="col-12">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                            </div>
                            <div class="col-md-6">
                                <label for="price" class="form-label">Price</label>
                                <input type="number" class="form-control" id="price" name="price" required min="0.01" step="0.01">
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

                            <div class="col-md-6">
                                <label for="supplierId" class="form-label">Supplier</label>
                                <select class="form-select" id="supplierId" name="supplierId" required>
                                    <option value="" disabled selected>Select Supplier</option>
                                    <c:forEach var="supplier" items="${suppliers}">
                                        <option value="${supplier.id}">${supplier.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status" required>
                                    <option value="" disabled selected>Select Status</option>
                                    <option value="Available">Available</option>
                                    <option value="UnAvailable">UnAvailable</option>
                                    <option value="Unknown">Unknown</option>
                                </select>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add Material</button>
                            </div>
                        </form>
                        <!-- End Add Material Form -->
                    </div>
                </div>
            </div>
        </div>
        <!-- End Add Material Modal -->

        <!-- Edit Material Modal -->

        <div class="modal fade" id="editMaterialModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Update Material</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/updatematerial" method="post" enctype="multipart/form-data" class="row g-3">
                            <input type="hidden" name="id" value="">
                            <input type="hidden" name="currentImage" value="${material.image}"> <!-- Add this line -->
                            <!-- Other form fields remain the same -->
                            <div class="col-12">
                                <label for="name" class="form-label">Material Name</label>
                                <input type="text" class="form-control" id="name" name="name" value="" required minlength="3">
                            </div>
                            <div class="col-12">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3" required minlength="10"></textarea>
                            </div>
                            <div class="col-md-6">
                                <label for="price" class="form-label">Price</label>
                                <input type="number" class="form-control" id="price" name="price" value="" required min="0.01" step="0.01">
                            </div>

                            <div class="mb-3">
                                <label for="file" class="form-label">Image</label> <!-- Changed id to "file" -->
                                <input type="file" class="form-control" id="file" name="file" accept=".jpg,.jpeg,.png">
                                <div class="invalid-feedback">
                                    Please select an image file (JPG or PNG only).
                                </div>
                                <div class="form-text">
                                    Current image: ${material.image}<br>
                                    Leave empty to keep current image. Maximum file size: 5MB
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label for="supplierId" class="form-label">Supplier</label>
                                <select class="form-select" id="supplierId" name="supplierId" required>
                                    <option value="" disabled selected>Select Supplier</option>
                                    <c:forEach var="supplier" items="${suppliers}">
                                        <option value="${supplier.id}">${supplier.name}</option>
                                    </c:forEach>
                                </select>
                            </div>


                            <div class="col-md-6">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status" required>
                                    <option value="" disabled selected>Select Status</option>
                                    <option value="Available">Available</option>
                                    <option value="UnAvailable">UnAvailable</option>
                                    <option value="Unknown">Unknown</option>
                                </select>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </div>
                        </form>

                        <!-- End Edit Material Form -->
                    </div>
                </div>
            </div>
        </div><!-- End Edit Material Modal-->

        <script>
                                                        function previewImage(event) {
                                                            const imagePreview = document.getElementById('imagePreview');
                                                            const file = event.target.files[0];
                                                            if (file) {
                                                                const reader = new FileReader();
                                                                reader.onload = function (e) {
                                                                    imagePreview.src = e.target.result;
                                                                    imagePreview.style.display = 'block';
                                                                };
                                                                reader.readAsDataURL(file);
                                                            } else {
                                                                imagePreview.src = '';
                                                                imagePreview.style.display = 'none';
                                                            }
                                                        }
                                                        var editMaterialModal = document.getElementById('editMaterialModal');
editMaterialModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var materialId = button.getAttribute('data-id');

    fetch('${pageContext.request.contextPath}/materialinformation?id=' + materialId)
        .then(response => response.json())
        .then(material => {
            var form = editMaterialModal.querySelector('form');
            form.action = form.action.split('?')[0] + "?id=" + materialId;

            // Populate fields
            form.querySelector('input[name="id"]').value = material.id;
            form.querySelector('input[name="name"]').value = material.name;
            form.querySelector('textarea[name="description"]').value = material.description;
            form.querySelector('input[name="price"]').value = material.price;

            // Supplier select
         
var supplierSelect = form.querySelector('select[name="supplierId"]');
supplierSelect.value = material.supplierInfo.id; // Kiểm tra xem id này có đúng không

            // Status select
            var statusSelect = form.querySelector('select[name="status"]');
            statusSelect.value = material.status; // Ensure this value is correctly set

            // Image preview
            var currentImageInput = form.querySelector('input[name="currentImage"]');
            currentImageInput.value = material.image;
            var imagePreviewText = form.querySelector('.form-text');
            imagePreviewText.innerHTML = `Current image: <img src="${material.image}" alt="Current Image" style="max-width: 100px;">`;
        })
        .catch(error => console.error('Error fetching material details:', error));
});

        </script>

    </body><!-- comment -->
</html>
