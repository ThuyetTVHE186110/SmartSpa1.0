<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Staff Spa Dashboard - Services Management</title>
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/Frontend_Staff/styles.css">
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
            </head>

            <body>
                <div class="dashboard-container">
                    <jsp:include page="sideBar.jsp" />

                    <div class="main-content">
                        <jsp:include page="topbar.jsp" />
                        <main>
                            <!-- Section Header -->
                            <div class="section-header">
                                <div class="header-content">
                                    <h2>Services Management</h2>
                                    <div class="service-filter">
                                        <button class="filter-btn ${empty selectedCategory ? 'active' : ''}"
                                            onclick="filterServices('')">All Services</button>
                                        <c:forEach items="${categories}" var="category">
                                            <button class="filter-btn ${category eq selectedCategory ? 'active' : ''}"
                                                onclick="filterServices('${category}')">${category}</button>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- <button class="btn-primary" onclick="openModal('addServiceModal')">
                                    <i class="fas fa-plus"></i> Add New Service
                                </button> -->
                            </div>

                            <!-- Services Grid -->
                            <div class="services-grid">
                                <!-- Services List -->
                                <div class="services-list">
                                    <c:forEach items="${services}" var="service">
                                        <div class="service-card" data-id="${service.id}">
                                            <div class="service-header">
                                                <h4>${service.name}</h4>
                                                <span class="service-price">$${service.price}</span>
                                            </div>
                                            <p class="service-duration">
                                                <i class="fas fa-clock"></i> ${service.duration} minutes
                                            </p>
                                            <p class="service-description">${service.description}</p>
                                            <div class="service-meta">
                                                <span class="category-badge">${service.category}</span>
                                                <span
                                                    class="status-badge ${service.status.toLowerCase()}">${service.status}</span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Summary Column -->
                                <div class="summary-column">
                                    <!-- Service Statistics -->
                                    <div class="summary-card">
                                        <h3>Service Statistics</h3>
                                        <div class="summary-stats">
                                            <div class="stat">
                                                <span class="label">Total Services</span>
                                                <span class="value">${stats.total}</span>
                                            </div>
                                            <div class="stat">
                                                <span class="label">Active</span>
                                                <span class="value">${stats.active}</span>
                                            </div>
                                            <div class="stat">
                                                <span class="label">Inactive</span>
                                                <span class="value">${stats.inactive}</span>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Popular Services -->
                                    <div class="summary-card">
                                        <h3>Most Popular Services</h3>
                                        <div class="popular-services">
                                            <c:forEach items="${popularServices}" var="service">
                                                <div class="popular-service-item">
                                                    <div class="service-info">
                                                        <h4>${service.name}</h4>
                                                        <p>Booked ${service.bookingCount != null ? service.bookingCount
                                                            : 0} times this month</p>
                                                    </div>
                                                    <c:if test="${service.trend != null}">
                                                        <div
                                                            class="service-trend ${service.trend >= 0 ? 'up' : 'down'}">
                                                            <i
                                                                class="fas fa-arrow-${service.trend >= 0 ? 'up' : 'down'}"></i>
                                                            <span>${service.trend >= 0 ? service.trend :
                                                                -service.trend}%</span>
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <!-- Revenue & Booking Trends -->
                                    <div class="summary-card">
                                        <h3>Revenue & Booking Trends</h3>
                                        <div class="trends-chart">
                                            <canvas id="trendsChart"></canvas>
                                        </div>
                                        <div class="trends-summary">
                                            <div class="trend-item">
                                                <span class="label">Revenue Trend</span>
                                                <span
                                                    class="value ${monthlyStats.revenueTrend != null && monthlyStats.revenueTrend >= 0 ? 'positive' : 'negative'}">
                                                    <i
                                                        class="fas fa-arrow-${monthlyStats.revenueTrend != null && monthlyStats.revenueTrend >= 0 ? 'up' : 'down'}"></i>
                                                    <c:choose>
                                                        <c:when test="${monthlyStats.revenueTrend != null}">
                                                            ${monthlyStats.revenueTrend >= 0 ? monthlyStats.revenueTrend
                                                            : -monthlyStats.revenueTrend}%
                                                        </c:when>
                                                        <c:otherwise>
                                                            0%
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                            <div class="trend-item">
                                                <span class="label">Booking Trend</span>
                                                <span
                                                    class="value ${monthlyStats.bookingTrend != null && monthlyStats.bookingTrend >= 0 ? 'positive' : 'negative'}">
                                                    <i
                                                        class="fas fa-arrow-${monthlyStats.bookingTrend != null && monthlyStats.bookingTrend >= 0 ? 'up' : 'down'}"></i>
                                                    <c:choose>
                                                        <c:when test="${monthlyStats.bookingTrend != null}">
                                                            ${monthlyStats.bookingTrend >= 0 ? monthlyStats.bookingTrend
                                                            : -monthlyStats.bookingTrend}%
                                                        </c:when>
                                                        <c:otherwise>
                                                            0%
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                        </div>

                                        <div class="monthly-breakdown">
                                            <h4>Monthly Breakdown</h4>
                                            <div class="breakdown-list">
                                                <c:forEach items="${monthlyStats.monthlyData}" var="month">
                                                    <div class="breakdown-item">
                                                        <div class="month-info">
                                                            <span class="month">${month.monthName}</span>
                                                            <span class="revenue">$${month.revenue != null ?
                                                                month.revenue : '0.00'}</span>
                                                        </div>
                                                        <div class="trends">
                                                            <c:if test="${month.revenueTrend != null}">
                                                                <span
                                                                    class="trend ${month.revenueTrend >= 0 ? 'up' : 'down'}">
                                                                    ${month.revenueTrend >= 0 ? month.revenueTrend :
                                                                    -month.revenueTrend}%
                                                                </span>
                                                            </c:if>
                                                            <span class="bookings">${month.bookings != null ?
                                                                month.bookings : 0} bookings</span>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Service Performance Details -->
                            <!-- <c:if test="${not empty performance}">
                                <div class="performance-metrics">
                                    <div class="metric-card">
                                        <h3>Service Performance</h3>
                                        <div class="metrics-grid">
                                            <div class="metric-item">
                                                <i class="fas fa-calendar-check"></i>
                                                <span class="metric-label">Total Appointments</span>
                                                <span class="metric-value">${performance.totalAppointments}</span>
                                            </div>
                                            <div class="metric-item">
                                                <i class="fas fa-users"></i>
                                                <span class="metric-label">Unique Customers</span>
                                                <span class="metric-value">${performance.uniqueCustomers}</span>
                                            </div>
                                            <div class="metric-item">
                                                <i class="fas fa-user-md"></i>
                                                <span class="metric-label">Assigned Staff</span>
                                                <span class="metric-value">${performance.assignedStaff}</span>
                                            </div>
                                            <div class="metric-item">
                                                <i class="fas fa-dollar-sign"></i>
                                                <span class="metric-label">Total Revenue</span>
                                                <span class="metric-value">$${performance.totalRevenue}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if> -->

                            <!-- Staff Performance Section -->
                            <!-- <c:if test="${not empty staffPerformance}">
                                <div class="staff-performance">
                                    <h3>Staff Performance</h3>
                                    <div class="staff-grid">
                                        <c:forEach items="${staffPerformance}" var="staff">
                                            <div class="staff-card">
                                                <div class="staff-info">
                                                    <div class="staff-details">
                                                        <h4>${staff.staffName}</h4>
                                                        <p>${staff.serviceCount} services completed</p>
                                                    </div>
                                                    <div class="staff-stats">
                                                        <div class="stat">
                                                            <span class="label">Completion Rate</span>
                                                            <span class="value">
                                                                <fmt:formatNumber value="${staff.completionRate}"
                                                                    type="number" maxFractionDigits="1" />%
                                                            </span>
                                                        </div>
                                                        <div class="stat">
                                                            <span class="label">Unique Customers</span>
                                                            <span class="value">${staff.uniqueCustomers}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if> -->

                            <!-- Resource Requirements Section
                            <c:if test="${not empty resources}">
                                <div class="resource-requirements">
                                    <h3>Resource Management</h3>
                                    <div class="resources-grid">
                                        <div class="products-list">
                                            <h4>Required Products</h4>
                                            <div class="resource-items">
                                                <c:forEach items="${resources.products}" var="product">
                                                    <div class="resource-item">
                                                        <div class="resource-info">
                                                            <h5>${product.name}</h5>
                                                            <small>Required: ${product.required}</small>
                                                        </div>
                                                        <span
                                                            class="stock-status ${product.available >= product.required ? 'in-stock' : 'low-stock'}">
                                                            Available: ${product.available}
                                                        </span>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="materials-list">
                                            <h4>Required Materials</h4>
                                            <div class="resource-items">
                                                <c:forEach items="${resources.materials}" var="material">
                                                    <div class="resource-item">
                                                        <div class="resource-info">
                                                            <h5>${material.name}</h5>
                                                            <span
                                                                class="status-badge ${material.status.toLowerCase()}">${material.status}</span>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if> -->





                            <!-- Add Service Modal -->
                            <div class="modal" id="addServiceModal">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h2 id="modalTitle">Add New Service</h2>
                                        <button class="close-modal" onclick="closeModal('addServiceModal')">
                                            <i class="fas fa-times"></i>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="serviceForm" onsubmit="handleServiceSubmit(event)">
                                            <input type="hidden" id="serviceId" name="id">
                                            <input type="hidden" id="actionType" name="action" value="add">

                                            <div class="form-group">
                                                <label for="serviceName">Service Name</label>
                                                <input type="text" id="serviceName" name="name" required>
                                            </div>

                                            <div class="form-row">
                                                <div class="form-group">
                                                    <label for="servicePrice">Price ($)</label>
                                                    <input type="number" id="servicePrice" name="price" min="0"
                                                        required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="serviceDuration">Duration (minutes)</label>
                                                    <input type="number" id="serviceDuration" name="duration" min="15"
                                                        step="15" required>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="serviceCategory">Category</label>
                                                <select id="serviceCategory" name="category" required>
                                                    <option value="">Select Category</option>
                                                    <c:forEach items="${categories}" var="category">
                                                        <option value="${category}">${category}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label for="serviceDescription">Description</label>
                                                <textarea id="serviceDescription" name="description" rows="3"
                                                    required></textarea>
                                            </div>

                                            <div class="form-group">
                                                <label for="serviceImage">Image URL</label>
                                                <input type="text" id="serviceImage" name="image">
                                            </div>

                                            <div id="statusGroup" class="form-group" style="display: none;">
                                                <label for="serviceStatus">Status</label>
                                                <select id="serviceStatus" name="status">
                                                    <option value="ACTIVE">Active</option>
                                                    <option value="INACTIVE">Inactive</option>
                                                </select>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn-secondary"
                                            onclick="closeModal('addServiceModal')">Cancel</button>
                                        <button type="submit" form="serviceForm" class="btn-primary">Save
                                            Service</button>
                                    </div>
                                </div>
                            </div>


                        </main>
                    </div>
                </div>

                <script src="${pageContext.request.contextPath}/js/pages/services.js"></script>
                <script>
                    // Initialize chart if data exists
                    <c:if test="${not empty monthlyStats}">
                        const trendsCtx = document.getElementById('trendsChart').getContext('2d');
                        const monthlyData = ${ monthlyStats.chartData };

                        new Chart(trendsCtx, {
                            type: 'line',
                        data: {
                            labels: monthlyData.map(data => {
                                    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                        'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
                        return monthNames[data.month - 1];
                                }),
                        datasets: [
                        {
                            label: 'Revenue',
                                        data: monthlyData.map(data => data.revenue),
                        borderColor: '#4F46E5',
                        backgroundColor: 'rgba(79, 70, 229, 0.1)',
                        yAxisID: 'y-revenue',
                        fill: true
                                    },
                        {
                            label: 'Bookings',
                                        data: monthlyData.map(data => data.bookings),
                        borderColor: '#10B981',
                        backgroundColor: 'rgba(16, 185, 129, 0.1)',
                        yAxisID: 'y-bookings',
                        fill: true
                                    }
                        ]
                            },
                        options: {
                            responsive: true,
                        interaction: {
                            intersect: false,
                        mode: 'index'
                                },
                        plugins: {
                            legend: {
                            position: 'top'
                                    },
                        tooltip: {
                            callbacks: {
                            label: function(context) {
                            let label = context.dataset.label || '';
                        if (label) {
                            label += ': ';
                                                }
                        if (context.dataset.label === 'Revenue') {
                            label += new Intl.NumberFormat('en-US', {
                                style: 'currency',
                                currency: 'USD'
                            }).format(context.raw);
                                                } else {
                            label += context.raw;
                                                }
                        return label;
                                            }
                                        }
                                    }
                                },
                        scales: {
                            'y-revenue': {
                            type: 'linear',
                        display: true,
                        position: 'left',
                        title: {
                            display: true,
                        text: 'Revenue ($)'
                                        }
                                    },
                        'y-bookings': {
                            type: 'linear',
                        display: true,
                        position: 'right',
                        title: {
                            display: true,
                        text: 'Number of Bookings'
                                        },
                        grid: {
                            drawOnChartArea: false
                                        }
                                    }
                                }
                            }
                        });
                    </c:if>
                </script>
            </body>

            </html>