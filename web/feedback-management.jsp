<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Account" %>
<%
    // No need to declare session manually; it's already available in JSP
    // You can directly use session
    if (session == null || session.getAttribute("account") == null) {
        // Redirect to login page if session is not found or account is not in session
        response.sendRedirect("error");
    } else {
        // Get the account object from session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 1 || account.getRole() == 2 || account.getRole() == 3) {
            // Allow access to the page (do nothing and let the JSP render)
        } else {
            // Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            request.getRequestDispatcher("error").forward(request, response);
        }
    }
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Feedback Management - NiceAdmin</title>
        <title>Feedback</title>
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
                <h1>Feedback Management</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Staff</li>
                        <li class="breadcrumb-item active">Feedback Management</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-12">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Feedback</h5>
                                <p>Manage customer feedback from this panel.</p>
                                <!-- Search Bar and Filter Menu -->
                                <div class="d-flex justify-content-between mb-3">
                                    <input style="width: 30% !important" type="text" class="form-control w-50" id="searchFeedback" placeholder="Search for customers..." onkeyup="filterTable()">

                                    <select class="form-select w-25" id="serviceFilter" onchange="filterTable()">
                                        <option value="">All Service</option>
                                        <option value="Stone Therapy">Stone Therapy</option>
                                        <option value="Body Massage">Body Massage</option>
                                        <option value="Facial Therapy">Facial Therapy</option>
                                        <option value="Skin Care">Skin Care</option>
                                    </select>
                                </div>
                                <!-- Table with stripped rows -->
                                <table class="table datatable">
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Customer Name</th>
                                            <th scope="col">Content</th>
                                            <th scope="col">Service</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${feedback}" var="feedback" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td>${feedback.customer.name}</td>
                                                <td>${feedback.content}</td>
                                                <td>${feedback.service.name}</td>

                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                                            data-bs-target="#viewFeedbackModal" onclick="setModalData('${feedback.customer.name}', '${feedback.service.name}', '${feedback.content}')">View</button>
                                                    <!--<button type="button" class="btn btn-danger btn-sm">Delete</button>-->
                                                </td>
                                            </tr>
                                        </c:forEach>

                                </table>
                                <!-- End Table with stripped rows -->
                                <div id="paginationControls" class="mt-3 d-flex justify-content-center"></div>
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

        <!-- View Feedback Modal -->
        <div class="modal fade" id="viewFeedbackModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">View Feedback</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- View Feedback Form -->
                        <form class="row g-3">
                            <div class="col-12">
                                <label for="viewCustomerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" id="viewCustomerName" readonly>
                            </div>
                            <div class="col-12">
                                <label for="viewService" class="form-label">Service</label>
                                <input type="text" class="form-control" id="viewService" readonly>
                            </div>
                            <div class="col-12">
                                <label for="viewComment" class="form-label">Comment</label>
                                <textarea class="form-control" id="viewComment" rows="3" readonly></textarea>
                            </div>
                            
                            <div class="col-12">
                                <label for="viewRating" class="form-label">Rating</label>
                                <input type="text" class="form-control" id="viewRating" value="4.5" readonly>
                            </div>
                            

                        </form>
                        <!-- End View Feedback Form -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div><!-- End View Feedback Modal-->


    </body>
    <script>
                                                                function setModalData(customerName, serviceName, content) {
                                                                    document.getElementById('viewCustomerName').value = customerName;
                                                                    document.getElementById('viewService').value = serviceName;
                                                                    document.getElementById('viewComment').value = content;
                                                                }

                                                                function filterTable() {
                                                                    const input = document.getElementById('searchFeedback').value.toLowerCase();
                                                                    const serviceFilter = document.getElementById('serviceFilter').value;
                                                                    const table = document.querySelector('.datatable tbody');
                                                                    const rows = table.getElementsByTagName('tr');
                                                                    let filteredRows = [];

                                                                    for (let i = 0; i < rows.length; i++) {
                                                                        const cells = rows[i].getElementsByTagName('td');
                                                                        let textMatch = false;
                                                                        let serviceMatch = true; // Default to true when "All Service" is selected

                                                                        // Check if the row matches the search text
                                                                        for (let j = 0; j < cells.length; j++) {
                                                                            if (cells[j]) {
                                                                                const cellText = cells[j].textContent || cells[j].innerText;

                                                                                // Check for text match
                                                                                if (cellText.toLowerCase().indexOf(input) > -1) {
                                                                                    textMatch = true;
                                                                                }

                                                                                // Check for service match (assuming service is in the 3rd column, adjust if necessary)
                                                                                if (j === 2 && serviceFilter && serviceFilter !== '') {
                                                                                    if (cells[j].textContent.trim() !== serviceFilter) {
                                                                                        serviceMatch = false;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }

                                                                        // Display the row if both conditions are satisfied
                                                                        if (textMatch && serviceMatch) {
                                                                            filteredRows.push(rows[i]);
                                                                        }
                                                                    }

                                                                    // Hide all rows first
                                                                    for (let i = 0; i < rows.length; i++) {
                                                                        rows[i].style.display = 'none';
                                                                    }

                                                                    // Show only filtered rows with pagination
                                                                    const rowsPerPage = 5;
                                                                    const totalPages = Math.ceil(filteredRows.length / rowsPerPage);
                                                                    let currentPage = 1;

                                                                    function displayPage(page) {
                                                                        currentPage = page;
                                                                        const start = (page - 1) * rowsPerPage;
                                                                        const end = Math.min(start + rowsPerPage, filteredRows.length);

                                                                        for (let i = 0; i < filteredRows.length; i++) {
                                                                            filteredRows[i].style.display = (i >= start && i < end) ? '' : 'none';
                                                                        }

                                                                        renderPaginationControls();
                                                                    }

                                                                    function renderPaginationControls() {
                                                                        const paginationControls = document.getElementById('paginationControls');
                                                                        paginationControls.innerHTML = '';

                                                                        const prevButton = document.createElement('button');
                                                                        prevButton.textContent = 'Previous';
                                                                        prevButton.classList.add('btn', 'btn-secondary', 'm-1');
                                                                        prevButton.disabled = currentPage === 1;
                                                                        prevButton.addEventListener('click', function () {
                                                                            if (currentPage > 1) {
                                                                                displayPage(currentPage - 1);
                                                                            }
                                                                        });
                                                                        paginationControls.appendChild(prevButton);

                                                                        for (let i = 1; i <= totalPages; i++) {
                                                                            const button = document.createElement('button');
                                                                            button.textContent = i;
                                                                            button.classList.add('btn', 'btn-secondary', 'm-1');
                                                                            if (i === currentPage) {
                                                                                button.classList.add('active');
                                                                            }
                                                                            button.addEventListener('click', function () {
                                                                                displayPage(i);
                                                                            });
                                                                            paginationControls.appendChild(button);
                                                                        }

                                                                        const nextButton = document.createElement('button');
                                                                        nextButton.textContent = 'Next';
                                                                        nextButton.classList.add('btn', 'btn-secondary', 'm-1');
                                                                        nextButton.disabled = currentPage === totalPages;
                                                                        nextButton.addEventListener('click', function () {
                                                                            if (currentPage < totalPages) {
                                                                                displayPage(currentPage + 1);
                                                                            }
                                                                        });
                                                                        paginationControls.appendChild(nextButton);
                                                                    }

                                                                    displayPage(1);
                                                                }





    </script>
</html>
