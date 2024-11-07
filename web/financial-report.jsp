<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Financial Report - Smart Beauty Spa</title>
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/style.css" rel="stylesheet">
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <!-- DataTables -->
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
        <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <!-- Vendor CSS Files -->
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
        <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
        <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
        <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
        <link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="headerHTML.jsp" />
        <jsp:include page="sideBar.jsp" />

        <main id="main" class="main">
            <div class="pagetitle">
                <h1>Financial Report</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="dashboard">Home</a></li>
                        <li class="breadcrumb-item active">Financial Report</li>
                    </ol>
                </nav>
            </div>

            <!-- Filters Section -->
            <section class="section">
                <div class="card">
                    <div class="card-body">
                        <form id="reportFilters" class="row g-3">
                            <div class="col-md-4">
                                <label class="form-label">Start Date</label>
                                <input type="date" class="form-control" name="startDate" value="${startDate}">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">End Date</label>
                                <input type="date" class="form-control" name="endDate" value="${endDate}">
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Year</label>
                                <select class="form-select" name="year">
                                    <c:forEach begin="2020" end="2024" var="yr">
                                        <option value="${yr}" ${yr==year ? 'selected' : '' }>${yr}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-12">
                                <button type="submit" class="btn btn-primary">Generate Report</button>
                                <div class="float-end">
                                    <button type="button" class="btn btn-success"
                                            onclick="exportReport('excel')">
                                        <i class="bi bi-file-excel"></i> Export Excel
                                    </button>
                                    <button type="button" class="btn btn-danger" onclick="exportReport('pdf')">
                                        <i class="bi bi-file-pdf"></i> Export PDF
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </section>

            <!-- Summary Cards -->
            <section class="section">
                <div class="row">
                    <div class="col-xxl-4 col-md-6">
                        <div class="card info-card revenue-card">
                            <div class="card-body">
                                <h5 class="card-title">Total Revenue</h5>
                                <div class="d-flex align-items-center">
                                    <div
                                        class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-currency-dollar"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>$
                                            <fmt:formatNumber value="${summary.totalRevenue}"
                                                              pattern="#,##0.00" />
                                        </h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-xxl-4 col-md-6">
                        <div class="card info-card sales-card">
                            <div class="card-body">
                                <h5 class="card-title">Total Transactions</h5>
                                <div class="d-flex align-items-center">
                                    <div
                                        class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-cart"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>${summary.totalTransactions}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-xxl-4 col-md-6">
                        <div class="card info-card customers-card">
                            <div class="card-body">
                                <h5 class="card-title">Average Transaction</h5>
                                <div class="d-flex align-items-center">
                                    <div
                                        class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-person"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>$
                                            <fmt:formatNumber value="${summary.averageTransaction}"
                                                              pattern="#,##0.00" />
                                        </h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Charts Section -->
            <section class="section">
                <div class="row">
                    <!-- Monthly Revenue Chart -->
                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Monthly Revenue</h5>
                                <canvas id="monthlyRevenueChart"></canvas>
                            </div>
                        </div>
                    </div>

                    <!-- Revenue by Service Chart -->
                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Revenue by Service</h5>
                                <canvas id="serviceRevenueChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Top Customers Table -->
            <section class="section">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Top Customers</h5>
                        <table class="table table-striped" id="topCustomersTable">
                            <thead>
                                <tr>
                                    <th>Customer Name</th>
                                    <th>Total Visits</th>
                                    <th>Total Spent</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${topCustomers}" var="customer">
                                    <tr>
                                        <td>${customer.customerName}</td>
                                        <td>${customer.totalVisits}</td>
                                        <td>$
                                            <fmt:formatNumber value="${customer.totalSpent}"
                                                              pattern="#,##0.00" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
        </main>
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
                                            // Initialize DataTable
                                            new DataTable('#topCustomersTable', {
                                                pageLength: 5,
                                                lengthMenu: [[5, 10, 25, -1], [5, 10, 25, "All"]]
                                            });

                                            // Monthly Revenue Chart
                                            const monthlyData = ${ monthlyRevenue };
                                            new Chart(document.getElementById('monthlyRevenueChart'), {
                                                type: 'line',
                                                data: {
                                                    labels: Object.keys(monthlyData),
                                                    datasets: [{
                                                            label: 'Monthly Revenue',
                                                            data: Object.values(monthlyData),
                                                            borderColor: '#4154f1',
                                                            tension: 0.4
                                                        }]
                                                }
                                            });

                                            // Service Revenue Chart
                                            const serviceData = ${ revenueByService };
                                            new Chart(document.getElementById('serviceRevenueChart'), {
                                                type: 'pie',
                                                data: {
                                                    labels: Object.keys(serviceData),
                                                    datasets: [{
                                                            data: Object.values(serviceData),
                                                            backgroundColor: [
                                                                '#4154f1',
                                                                '#2eca6a',
                                                                '#ff771d',
                                                                '#ff4747',
                                                                '#7460ee'
                                                            ]
                                                        }]
                                                }
                                            });
                                        });

                                        // Export Function
                                        function exportReport(format) {
                                            const form = document.createElement('form');
                                            form.method = 'POST';
                                            form.action = 'financial-report';

                                            const actionInput = document.createElement('input');
                                            actionInput.type = 'hidden';
                                            actionInput.name = 'action';
                                            actionInput.value = 'export';
                                            form.appendChild(actionInput);

                                            const formatInput = document.createElement('input');
                                            formatInput.type = 'hidden';
                                            formatInput.name = 'format';
                                            formatInput.value = format;
                                            form.appendChild(formatInput);

                                            const startDate = document.querySelector('input[name="startDate"]').value;
                                            const startDateInput = document.createElement('input');
                                            startDateInput.type = 'hidden';
                                            startDateInput.name = 'startDate';
                                            startDateInput.value = startDate;
                                            form.appendChild(startDateInput);

                                            const endDate = document.querySelector('input[name="endDate"]').value;
                                            const endDateInput = document.createElement('input');
                                            endDateInput.type = 'hidden';
                                            endDateInput.name = 'endDate';
                                            endDateInput.value = endDate;
                                            form.appendChild(endDateInput);

                                            document.body.appendChild(form);
                                            form.submit();
                                            document.body.removeChild(form);
                                        }
        </script>
    </body>

</html>