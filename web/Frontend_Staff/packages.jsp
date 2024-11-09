<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Account" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // No need to declare session manually; it's already available in JSP
    // You can directly use session
    if (session == null || session.getAttribute("account") == null) {
        // Redirect to login page if session is not found or account is not in session
        response.sendRedirect("../adminLogin");
    } else {
        // Get the account object from session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 3) {
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
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Staff Spa Dashboard</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="./styles.css">
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sideBar.jsp" />

            <div class="main-content">
                <jsp:include page="topbar.jsp" />
                <main>
                    <div class="section-header">
                        <div class="header-content">
                            <h2>Packages & Promotions</h2>
                            <div class="package-filter">
                                <button class="filter-btn active">All Packages</button>
                                <button class="filter-btn">Active</button>
                                <button class="filter-btn">Upcoming</button>
                                <button class="filter-btn">Expired</button>
                            </div>
                        </div>
                        <button class="btn-primary" id="newPackageBtn">
                            <i class="fas fa-plus"></i> Create New Package
                        </button>
                    </div>

                    <div class="packages-grid">
                        <!-- Packages List -->
                        <div class="packages-list">
                            <!-- Active Package -->
                            <div class="package-card active">
                                <div class="package-header">
                                    <div class="package-info">
                                        <h3>Relaxation Bundle</h3>
                                        <div class="package-price">
                                            <span class="original-price">$250</span>
                                            <span class="discounted-price">$199</span>
                                        </div>
                                    </div>
                                    <div class="status-badge">Active</div>
                                </div>
                                <div class="package-content">
                                    <div class="package-services">
                                        <h4>Included Services:</h4>
                                        <ul>
                                            <li><i class="fas fa-check"></i> 60min Swedish Massage</li>
                                            <li><i class="fas fa-check"></i> Facial Treatment</li>
                                            <li><i class="fas fa-check"></i> Aromatherapy Session</li>
                                        </ul>
                                    </div>
                                    <div class="package-details">
                                        <div class="detail-item">
                                            <i class="fas fa-calendar"></i>
                                            <span>Valid until: Dec 31, 2024</span>
                                        </div>
                                        <div class="detail-item">
                                            <i class="fas fa-users"></i>
                                            <span>Sold: 45 packages</span>
                                        </div>
                                        <div class="detail-item">
                                            <i class="fas fa-percentage"></i>
                                            <span>20% Savings</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="package-actions">
                                    <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                    <button class="btn-icon" title="Pause"><i class="fas fa-pause"></i></button>
                                    <button class="btn-icon" title="Analytics"><i class="fas fa-chart-line"></i></button>
                                </div>
                            </div>

                            <!-- Upcoming Package -->
                            <div class="package-card upcoming">
                                <div class="package-header">
                                    <div class="package-info">
                                        <h3>Holiday Special</h3>
                                        <div class="package-price">
                                            <span class="original-price">$350</span>
                                            <span class="discounted-price">$275</span>
                                        </div>
                                    </div>
                                    <div class="status-badge">Upcoming</div>
                                </div>
                                <div class="package-content">
                                    <div class="package-services">
                                        <h4>Included Services:</h4>
                                        <ul>
                                            <li><i class="fas fa-check"></i> 90min Deep Tissue Massage</li>
                                            <li><i class="fas fa-check"></i> Hot Stone Treatment</li>
                                            <li><i class="fas fa-check"></i> Foot Reflexology</li>
                                            <li><i class="fas fa-gift"></i> Complimentary Face Mask</li>
                                        </ul>
                                    </div>
                                    <div class="package-details">
                                        <div class="detail-item">
                                            <i class="fas fa-calendar-plus"></i>
                                            <span>Starts: Dec 1, 2024</span>
                                        </div>
                                        <div class="detail-item">
                                            <i class="fas fa-calendar-minus"></i>
                                            <span>Ends: Jan 31, 2025</span>
                                        </div>
                                        <div class="detail-item">
                                            <i class="fas fa-percentage"></i>
                                            <span>25% Savings</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="package-actions">
                                    <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                    <button class="btn-icon" title="Preview"><i class="fas fa-eye"></i></button>
                                    <button class="btn-icon" title="Delete"><i class="fas fa-trash"></i></button>
                                </div>
                            </div>
                        </div>

                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Package Statistics -->
                            <div class="summary-card">
                                <h3>Package Overview</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Active Packages</span>
                                        <span class="value">5</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Total Sales</span>
                                        <span class="value">128</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Revenue</span>
                                        <span class="value">$25.4K</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Upcoming</span>
                                        <span class="value">3</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Top Selling Packages -->
                            <div class="summary-card">
                                <h3>Top Selling Packages</h3>
                                <div class="top-packages">
                                    <div class="top-package-item">
                                        <div class="package-rank">#1</div>
                                        <div class="package-info">
                                            <h4>Relaxation Bundle</h4>
                                            <p>45 sold this month</p>
                                        </div>
                                        <div class="package-trend up">
                                            <i class="fas fa-arrow-up"></i>
                                            <span>15%</span>
                                        </div>
                                    </div>
                                    <div class="top-package-item">
                                        <div class="package-rank">#2</div>
                                        <div class="package-info">
                                            <h4>Couples Retreat</h4>
                                            <p>32 sold this month</p>
                                        </div>
                                        <div class="package-trend up">
                                            <i class="fas fa-arrow-up"></i>
                                            <span>8%</span>
                                        </div>
                                    </div>
                                    <div class="top-package-item">
                                        <div class="package-rank">#3</div>
                                        <div class="package-info">
                                            <h4>Wellness Package</h4>
                                            <p>28 sold this month</p>
                                        </div>
                                        <div class="package-trend down">
                                            <i class="fas fa-arrow-down"></i>
                                            <span>3%</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Quick Actions -->
                            <div class="summary-card">
                                <h3>Quick Actions</h3>
                                <div class="quick-actions-grid">
                                    <button class="action-btn">
                                        <i class="fas fa-plus-circle"></i>
                                        New Package
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-tags"></i>
                                        Promotions
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-chart-pie"></i>
                                        Analytics
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-bullhorn"></i>
                                        Marketing
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>