<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Account" %>
<% // No need to declare session manually; it's already available in JSP // You can directly use session
    if (session == null || session.getAttribute("account") == null) { // Redirect to login page if session is not found or account is not in session 
        response.sendRedirect("adminLogin");
    } else {
        // Get the account object from session 
        Account account = (Account) session.getAttribute("account");
        if (account.getRole() == 1
                || account.getRole() == 2) { // Allow access to the page (do nothing and let the JSP render) 
        } else { //Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard."
                           );
                           request.getRequestDispatcher("roleError").forward(request, response);
                       }
                   }%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <title>Payment Management - NiceAdmin</title>
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
        <!-- Include Header -->
        <jsp:include page="headerHTML.jsp" />

        <!-- Include Sidebar -->
        <jsp:include page="sideBar.jsp" />

        <main id="main" class="main">
            <div class="pagetitle">
                <h1>Payment Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Manager</li>
                        <li class="breadcrumb-item active">Payment Management</li>
                    </ol>
                </nav>
            </div>

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h5 class="card-title">Payments Overview</h5>
                                    <div class="table-header-buttons">
                                        <button class="btn btn-primary" onclick="exportPayments()">
                                            <i class="bi bi-download"></i> Export
                                        </button>
                                    </div>
                                </div>

                                <!-- Alert Messages -->
                                <c:if test="${not empty successMessage}">
                                    <div class="alert alert-success alert-dismissible fade show"
                                         role="alert">
                                        ${successMessage}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                aria-label="Close"></button>
                                    </div>
                                </c:if>
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger alert-dismissible fade show"
                                         role="alert">
                                        ${errorMessage}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"
                                                aria-label="Close"></button>
                                    </div>
                                </c:if>

                                <!-- Search and Filter Section -->
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <form action="payment-management" method="get"
                                              class="d-flex gap-2">
                                            <input type="text" class="form-control"
                                                   placeholder="Search payments" name="search"
                                                   value="${param.search}">
                                            <button class="btn btn-primary" type="submit">
                                                <i class="bi bi-search"></i>
                                            </button>
                                        </form>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="d-flex justify-content-end gap-2">
                                            <select class="form-select w-auto" id="statusFilter"
                                                    onchange="filterByStatus(this.value)">
                                                <option value="">All Statuses</option>
                                                <option value="COMPLETED">Completed</option>
                                                <option value="PENDING">Pending</option>
                                                <option value="FAILED">Failed</option>
                                            </select>
                                            <select class="form-select w-auto" id="dateFilter"
                                                    onchange="filterByDate(this.value)">
                                                <option value="">All Time</option>
                                                <option value="today">Today</option>
                                                <option value="week">This Week</option>
                                                <option value="month">This Month</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <!-- Payments Table -->
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover datatable">
                                        <thead>
                                            <tr>
                                                <th scope="col">Transaction ID</th>
                                                <th scope="col">Order Code</th>
                                                <th scope="col">Customer</th>
                                                <th scope="col">Amount</th>
                                                <th scope="col">Status</th>
                                                <th scope="col">Date</th>
                                                <th scope="col">Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${payments}" var="payment">
                                                <tr>
                                                    <td>${payment.transactionId}</td>
                                                    <td>${payment.orderCode}</td>
                                                    <td>${payment.personId}</td>
                                                    <td>
                                                        <fmt:formatNumber value="${payment.amount}"
                                                                          type="currency"
                                                                          currencyCode="${payment.currency}" />
                                                    </td>
                                                    <td>
                                                        <span
                                                            class="badge rounded-pill bg-${payment.status == 'COMPLETED' ? 'success' : 
                                                                                           payment.status == 'PENDING' ? 'warning' : 'danger'}">
                                                                ${payment.status}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate value="${payment.createdAt}"
                                                                            pattern="dd/MM/yyyy HH:mm" />
                                                        </td>
                                                        <td>
                                                            <div class="d-flex gap-2">
                                                                <button type="button"
                                                                        class="btn btn-sm btn-primary"
                                                                        onclick="viewPayment('${payment.transactionId}')">
                                                                    <i class="bi bi-eye"></i>
                                                                </button>
                                                                <button type="button"
                                                                        class="btn btn-sm btn-warning"
                                                                        onclick="updateStatus('${payment.transactionId}')">
                                                                    <i class="bi bi-pencil"></i>
                                                                </button>
                                                                <button type="button"
                                                                        class="btn btn-sm btn-danger"
                                                                        onclick="deletePayment('${payment.transactionId}')">
                                                                    <i class="bi bi-trash"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>

                                    <!-- Add this after the table -->
                                    <div class="d-flex justify-content-between align-items-center mt-3">
                                        <div class="datatable-info">
                                            <c:set var="startEntry" value="${(currentPage-1)*10 + 1}" />
                                            <c:set var="endEntry" value="${currentPage*10}" />
                                            <c:set var="adjustedEndEntry"
                                                   value="${endEntry > totalPayments ? totalPayments : endEntry}" />
                                            Showing ${startEntry} to ${adjustedEndEntry} of ${totalPayments}
                                            entries
                                        </div>

                                        <nav aria-label="Page navigation">
                                            <ul class="pagination">
                                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                    <a class="page-link"
                                                       href="payment-management?page=${currentPage-1}&search=${search}&status=${status}&period=${period}"
                                                       ${currentPage==1
                                                         ? 'tabindex="-1" aria-disabled="true"' : ''
                                                       }>Previous</a>
                                                </li>

                                                <c:forEach begin="1" end="${totalPages}" var="i">
                                                    <li
                                                        class="page-item ${currentPage == i ? 'active' : ''}">
                                                        <a class="page-link"
                                                           href="payment-management?page=${i}&search=${search}&status=${status}&period=${period}">
                                                            ${i}
                                                        </a>
                                                    </li>
                                                </c:forEach>

                                                <li
                                                    class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                    <a class="page-link"
                                                       href="payment-management?page=${currentPage+1}&search=${search}&status=${status}&period=${period}"
                                                       ${currentPage==totalPages
                                                         ? 'tabindex="-1" aria-disabled="true"' : ''
                                                       }>Next</a>
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <!-- Status Update Modal -->
            <div class="modal fade" id="updateStatusModal" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Update Payment Status</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <form action="payment-management" method="post">
                            <div class="modal-body">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="transactionId" id="updateTransactionId">
                                <div class="mb-3">
                                    <label for="status" class="form-label">Status</label>
                                    <select class="form-select" name="status" id="status" required>
                                        <option value="PENDING">Pending</option>
                                        <option value="COMPLETED">Completed</option>
                                        <option value="FAILED">Failed</option>
                                    </select>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary"
                                        data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Update Status</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Delete Confirmation Modal -->
            <div class="modal fade" id="deleteModal" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Confirm Delete</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to delete this payment record?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Cancel</button>
                            <form action="payment-management" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="transactionId" id="deleteTransactionId">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Footer -->
            <footer id="footer" class="footer">
                <div class="copyright">
                    &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
                </div>
                <div class="credits">
                    Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
                </div>
            </footer>

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

            <script>
                                                                        function viewPayment(transactionId) {
                                                                            window.location.href = 'payment-management?action=view&transactionId=' + transactionId;
                                                                        }

                                                                        function updateStatus(transactionId) {
                                                                            document.getElementById('updateTransactionId').value = transactionId;
                                                                            new bootstrap.Modal(document.getElementById('updateStatusModal')).show();
                                                                        }

                                                                        function deletePayment(transactionId) {
                                                                            document.getElementById('deleteTransactionId').value = transactionId;
                                                                            new bootstrap.Modal(document.getElementById('deleteModal')).show();
                                                                        }

                                                                        function filterByStatus(status) {
                                                                            const url = new URL(window.location.href);
                                                                            url.searchParams.set('status', status);
                                                                            url.searchParams.set('page', '1'); // Reset to first page when filtering
                                                                            window.location.href = url.toString();
                                                                        }

                                                                        function filterByDate(period) {
                                                                            const url = new URL(window.location.href);
                                                                            url.searchParams.set('period', period);
                                                                            url.searchParams.set('page', '1'); // Reset to first page when filtering
                                                                            window.location.href = url.toString();
                                                                        }

                                                                        function exportPayments() {
                                                                            // Implement export functionality
                                                                            window.location.href = 'payment-management?action=export';
                                                                        }

                                                                        // Update status filter select to show current selection
                                                                        document.addEventListener('DOMContentLoaded', function () {
                                                                            const statusFilter = document.getElementById('statusFilter');
                                                                            const dateFilter = document.getElementById('dateFilter');

                                                                            if (statusFilter) {
                                                                                statusFilter.value = '${status}' || '';
                                                                            }
                                                                            if (dateFilter) {
                                                                                dateFilter.value = '${period}' || '';
                                                                            }
                                                                        });
            </script>
        </body>

    </html>