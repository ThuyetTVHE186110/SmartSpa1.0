<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
            <%@ page import="model.Account" %>
                <%@page contentType="text/html" pageEncoding="UTF-8" %>
                    <!DOCTYPE html>
                    <html lang="en">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Bill History - Spa Management</title>
                        <link rel="stylesheet"
                            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
                        <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/styles.css">
                        <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/billing.css">
                    </head>

                    <body>
                        <div class="dashboard-container">
                            <jsp:include page="sideBar.jsp" />

                            <div class="main-content">
                                <jsp:include page="topbar.jsp" />

                                <main class="billing-container">
                                    <div class="billing-header">
                                        <h1>Bill History</h1>
                                        <div class="bill-actions">
                                            <button class="btn-secondary" onclick="exportBills()">
                                                <i class="fas fa-download"></i> Export
                                            </button>
                                            <button class="btn-primary"
                                                onclick="window.location.href='${pageContext.request.contextPath}/staff/createBill'">
                                                <i class="fas fa-plus"></i> Create New Bill
                                            </button>
                                        </div>
                                    </div>

                                    <div class="history-filters">
                                        <div class="filter-group">
                                            <label for="dateRange">Date Range:</label>
                                            <select id="dateRange" onchange="filterBills()">
                                                <option value="all">All Time</option>
                                                <option value="today">Today</option>
                                                <option value="week">This Week</option>
                                                <option value="month">This Month</option>
                                                <option value="custom">Custom Range</option>
                                            </select>
                                        </div>

                                        <div class="filter-group">
                                            <label for="status">Status:</label>
                                            <select id="status" onchange="filterBills()">
                                                <option value="all">All Status</option>
                                                <option value="paid">Paid</option>
                                                <option value="pending">Pending</option>
                                                <option value="cancelled">Cancelled</option>
                                            </select>
                                        </div>

                                        <div class="filter-group">
                                            <input type="text" id="searchBill" placeholder="Search bills..."
                                                oninput="searchBills(this.value)">
                                        </div>
                                    </div>

                                    <div class="bills-table">
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Bill #</th>
                                                    <th>Date</th>
                                                    <th>Customer</th>
                                                    <th>Services</th>
                                                    <th>Amount</th>
                                                    <th>Status</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${bills}" var="bill">
                                                    <tr>
                                                        <td>${bill.billCode}</td>
                                                        <td>
                                                            <fmt:formatDate value="${bill.createdAt}"
                                                                pattern="dd/MM/yyyy HH:mm" />
                                                        </td>
                                                        <td>
                                                            <div>
                                                                <strong>${bill.customerName}</strong><br>
                                                                <small>${bill.customerPhone}</small>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="services-list">
                                                                <c:forEach items="${bill.billDetails}" var="detail"
                                                                    varStatus="status">
                                                                    ${detail.serviceName}${!status.last ? ', ' : ''}
                                                                </c:forEach>
                                                            </div>
                                                        </td>
                                                        <td>$
                                                            <fmt:formatNumber value="${bill.total}"
                                                                pattern="#,##0.00" />
                                                        </td>
                                                        <td>
                                                            <span class="status-badge ${fn:toLowerCase(bill.status)}">
                                                                ${bill.status}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <div class="action-buttons">
                                                                <button class="btn-icon"
                                                                    onclick="viewBill('${bill.billCode}')"
                                                                    title="View Bill">
                                                                    <i class="fas fa-eye"></i>
                                                                </button>
                                                                <button class="btn-icon"
                                                                    onclick="printBill('${bill.billCode}')"
                                                                    title="Print Bill">
                                                                    <i class="fas fa-print"></i>
                                                                </button>
                                                                <button class="btn-icon"
                                                                    onclick="downloadBill('${bill.billCode}')"
                                                                    title="Download PDF">
                                                                    <i class="fas fa-download"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>

                                    <!-- Pagination -->
                                    <div class="pagination">
                                        <button class="btn-icon" onclick="previousPage()" ${currentPage==1 ? 'disabled'
                                            : '' }>
                                            <i class="fas fa-chevron-left"></i>
                                        </button>
                                        <span>Page ${currentPage} of ${totalPages}</span>
                                        <button class="btn-icon" onclick="nextPage()" ${currentPage==totalPages
                                            ? 'disabled' : '' }>
                                            <i class="fas fa-chevron-right"></i>
                                        </button>
                                    </div>
                                </main>
                            </div>
                        </div>

                        <!-- View Bill Modal -->
                        <div id="viewBillModal" class="modal">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h2>Bill Details</h2>
                                    <button class="close-modal" onclick="closeViewModal()">&times;</button>
                                </div>
                                <div class="modal-body" id="billDetails">
                                    <!-- Bill details will be loaded here -->
                                </div>
                            </div>
                        </div>

                        <script src="${pageContext.request.contextPath}/Frontend_Staff/js/billHistory.js"></script>
                    </body>

                    </html>