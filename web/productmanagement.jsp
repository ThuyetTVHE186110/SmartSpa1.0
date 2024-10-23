<%-- 
    Document   : productmanagement
    Created on : Oct 4, 2024, 8:35:40 AM
    Author     : Dell Alienware
--%>
<%@ page import="model.Account" %> <!-- Add this line to import the Account class -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Product Management - NiceAdmin</title>
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
                <h1>Product Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Manager</li>
                        <li class="breadcrumb-item active">Product Management</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Products</h5>
                                <p>Manage products from this panel.</p>

                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Image</th>
                                            <th scope="col">Product Name</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Quantity</th>
                                            <th scope="col" style="width: 15%;">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${productList}" var="product">
                                            <tr>
                                                <th scope="row">${product.id}</th>
                                                <td><img src="${pageContext.request.contextPath}/img/${product.image}" alt="${product.name}" width="50" height="50"></td>
                                                <td>${product.name}</td>
                                                <td>${product.description}</td>
                                                <td>${product.price}</td>
                                                <td>${product.quantity}</td>
                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#editProductModal" 
                                                            data-id="${product.id}">
                                                        Edit
                                                    </button>
                                                    <a href="deleteproduct?productID=${product.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
                                                </td>
                                            </tr>

                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!-- End Table with stripped rows -->

                                <div class="text-center mt-3">
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            data-bs-target="#addProductModal">
                                        <i class="bi bi-plus-circle me-1"></i> Add New Product
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

        <!-- Add Product Modal -->
        <div class="modal fade" id="addProductModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Product</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Product Form -->
                        <form action="${pageContext.request.contextPath}/addproduct" method="get" enctype="multipart/form-data" class="row g-3">
                            <div class="col-12">
                                <label for="name" class="form-label">Product Name</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="col-12">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                            </div>
                            <div class="col-md-6">
                                <label for="price" class="form-label">Price</label>
                                <input type="number" class="form-control" id="price" name="price" required>
                            </div>
                            <div class="col-md-6">
                                <label for="quantity" class="form-label">Quantity</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" required>
                            </div>
                            <div class="col-12">
                                <label for="image" class="form-label">Product Image</label>
                                <input type="file" class="form-control" id="image" name="image" accept="image/*" required>
                            </div>
                            <div class="col-md-6">
                                <label for="categoryId" class="form-label">Category ID</label>
                                <input type="number" class="form-control" id="categoryId" name="categoryId" required>
                            </div>
                            <div class="col-md-6">
                                <label for="supplierId" class="form-label">Supplier ID</label>
                                <input type="number" class="form-control" id="supplierId" name="supplierId" required>
                            </div>
                            <div class="col-md-6">
                                <label for="discountId" class="form-label">Discount ID</label>
                                <input type="number" class="form-control" id="discountId" name="discountId" required>
                            </div>
                            <div class="col-md-6">
                                <label for="branchName" class="form-label">Branch Name</label>
                                <input type="text" class="form-control" id="branchName" name="branchName" required>
                            </div>
                            <div class="modal-footer" href="addproduct">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add Product</button>
                            </div>
                        </form>
                        <!-- End Add Product Form -->
                    </div>
                </div>
            </div>
        </div>
        <!-- End Add Product Modal -->

        <!-- Edit Product Modal -->

        <div class="modal fade" id="editProductModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Update Product</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/updateproduct" method="get" enctype="multipart/form-data" class="row g-3">
                            <input type="hidden" name="id" value="">
                            <!-- Other form fields remain the same -->
                            <div class="col-12">
                                <label for="name" class="form-label">Product Name</label>
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
                            <div class="col-md-6">
                                <label for="quantity" class="form-label">Quantity</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" value="" required min="1">
                            </div>
                            <div class="col-12">
                                <label for="image" class="form-label">Product Image</label>
                                <input type="file" class="form-control" id="image" name="image" accept="image/*" required>
                            </div>
                            <div class="col-md-6">
                                <label for="categoryId" class="form-label">Category ID</label>
                                <input type="number" class="form-control" id="categoryId" name="categoryId" value="" required min="1">
                            </div>
                            <div class="col-md-6">
                                <label for="supplierId" class="form-label">Supplier ID</label>
                                <input type="number" class="form-control" id="supplierId" name="supplierId" required min="1">
                            </div>
                            <div class="col-md-6">
                                <label for="discountId" class="form-label">Discount ID</label>
                                <input type="number" class="form-control" id="discountId" name="discountId" min="1">
                            </div>
                            <div class="col-md-6">
                                <label for="branchName" class="form-label">Branch Name</label>
                                <input type="text" class="form-control" id="branchName" name="branchName" required minlength="3">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </div>
                        </form>

                        <!-- End Edit Product Form -->
                    </div>
                </div>
            </div>
        </div><!-- End Edit Product Modal-->

        <script>
                                                        var editProductModal = document.getElementById('editProductModal');
                                                        editProductModal.addEventListener('show.bs.modal', function (event) {
                                                            // Lấy nút đã kích hoạt modal (nút Edit)
                                                            var button = event.relatedTarget;
                                                            // Lấy giá trị id từ thuộc tính data-id của nút
                                                            var productId = button.getAttribute('data-id');
                                                            // Gửi yêu cầu AJAX tới server để lấy chi tiết sản phẩm
                                                            fetch('${pageContext.request.contextPath}/detail?id=' + productId)
                                                                    .then(response => response.json())  // Parse dữ liệu JSON
                                                                    .then(product => {
                                                                        // Tìm form và cập nhật action URL với id sản phẩm
                                                                        var form = editProductModal.querySelector('form');
                                                                        form.action = form.action.split('?')[0] + "?id=" + productId;

                                                                        // Cập nhật giá trị cho các trường trong modal
                                                                        form.querySelector('input[name="id"]').value = product.id;
                                                                        form.querySelector('input[name="name"]').value = product.name;
                                                                        form.querySelector('textarea[name="description"]').value = product.description;
                                                                        form.querySelector('input[name="price"]').value = product.price;
                                                                        form.querySelector('input[name="quantity"]').value = product.quantity;
                                                                        form.querySelector('input[name="categoryId"]').value = product.categoryId;
                                                                        form.querySelector('input[name="supplierId"]').value = product.supplierId;
                                                                        form.querySelector('input[name="discountId"]').value = product.discountId;
                                                                        form.querySelector('input[name="branchName"]').value = product.branchName;
                                                                        // Bạn có thể bổ sung thêm các field khác như hình ảnh nếu cần
                                                                    })
                                                                    .catch(error => console.error('Error fetching product details:', error));
                                                            x
                                                        });

        </script>

    </body><!-- comment -->
</html>
