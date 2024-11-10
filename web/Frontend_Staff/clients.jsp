<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Account" %>
<%@ page import="model.Person" %> <!-- Import Person class -->
<%@ page import="dal.PersonDAO" %>
<%@ page import="java.util.List" %>
<%
    // Constants for pagination
    final int CLIENTS_PER_PAGE = 8;
    int currentPage = 1;

    // Retrieve page parameter from the request if available
    String pageParam = request.getParameter("page");
    if (pageParam != null) {
        currentPage = Integer.parseInt(pageParam);
    }

    int offset = (currentPage - 1) * CLIENTS_PER_PAGE;

    // Initialize DAO and fetch the list of clients with pagination
    PersonDAO personDAO = new PersonDAO();
    List<Person> clients = personDAO.getAllCustomers(offset, CLIENTS_PER_PAGE);
%>
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
<style>
    /* General modal styling */
    .modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.6); /* Dark overlay */
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
        overflow: hidden;
    }

    .modal.active {
        display: flex;
    }

    .modal-content {
        background-color: #f9f9f9; /* Soft background */
        padding: 25px;
        border-radius: 12px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* Elegant shadow */
        width: 90%;
        max-width: 500px;
        animation: slide-down 0.3s ease; /* Smooth open animation */
    }

    @keyframes slide-down {
        from {
            transform: translateY(-20px);
            opacity: 0;
        }
        to {
            transform: translateY(0);
            opacity: 1;
        }
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #ddd;
        padding-bottom: 10px;
        margin-bottom: 15px;
    }

    .modal-header h2 {
        font-size: 1.5rem;
        color: #333; /* Primary text color */
        margin: 0;
    }

    .close-modal {
        background: none;
        border: none;
        font-size: 1.5rem;
        cursor: pointer;
        color: #888;
        transition: color 0.3s;
    }

    .close-modal:hover {
        color: #555; /* Darker on hover */
    }

    .modal-body {
        padding: 15px 0;
    }

    .form-group {
        margin-bottom: 15px;
    }

    .form-group label {
        display: block;
        font-weight: 600;
        color: #444; /* Label color */
        margin-bottom: 5px;
    }

    .form-group input,
    .form-group select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 1rem;
        transition: border-color 0.3s;
    }

    .form-group input:focus,
    .form-group select:focus {
        border-color: #007bff; /* Focus color */
        outline: none;
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.2); /* Subtle glow on focus */
    }

    .modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 20px;
    }

    .modal-footer .btn-primary {
        background-color: #007bff;
        color: #fff;
        border: none;
        padding: 10px 20px;
        border-radius: 6px;
        font-weight: 600;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .modal-footer .btn-primary:hover {
        background-color: #0056b3; /* Darker blue on hover */
    }

    .modal-footer .btn-secondary {
        background-color: #f1f1f1;
        color: #333;
        border: none;
        padding: 10px 20px;
        border-radius: 6px;
        font-weight: 600;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .modal-footer .btn-secondary:hover {
        background-color: #e2e2e2; /* Slightly darker gray on hover */
    }

</style>
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
        <link rel="stylesheet" href="Frontend_Staff/styles.css">
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sideBar.jsp" />

            <div class="main-content">
                <jsp:include page="topbar.jsp" />
                <main>
                    <div class="section-header">
                        <div class="header-content">
                            <h2>Client Management</h2>
                            <div class="client-filter">
                                <form action="clientManagement" method="get" class="d-flex justify-content-between align-items-center">
                                    <button type="submit" name="filter" value="all"
                                            class="filter-btn btn btn-outline-primary ${param.filter == null || param.filter == 'all' ? 'active' : ''}">
                                        All Clients
                                    </button>

                                    <button type="submit" name="filter" value="regular"
                                            class="filter-btn btn btn-outline-primary ${param.filter == 'regular' ? 'active' : ''}">
                                        Regular Clients
                                    </button>

                                    <button type="submit" name="filter" value="vip"
                                            class="filter-btn btn btn-outline-primary ${param.filter == 'vip' ? 'active' : ''}">
                                        VIP Clients
                                    </button>

                                    <button type="submit" name="filter" value="new"
                                            class="filter-btn btn btn-outline-primary ${param.filter == 'new' ? 'active' : ''}">
                                        New Clients
                                    </button>
                                </form>


                            </div>

                        </div>
                        <button class="btn-primary" id="newClientBtn">
                            <i class="fas fa-plus"></i> Add New Client
                        </button>
                    </div>

                    <div class="clients-grid">
                        <!-- Clients List -->
                        <div class="clients-list">
                            <c:forEach var="client" items="${customers}">
                                <c:choose>
                                    <c:when test="${client.image != null && !client.image.isEmpty()}">
                                        <c:set var="imageUrl" value="newUI/assets/img/${client.image}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="imageUrl" value="newUI/assets/img/default-avatar.jpg" />
                                    </c:otherwise>
                                </c:choose>

                                <div class="client-card ${client.tier != null ? client.tier.toLowerCase() : 'regular'}">
                                    <div class="client-header">
                                        <img src="${imageUrl}" 
                                             alt="${client.name}" class="client-avatar">
                                        <div class="client-status ${client.tier != null ? client.tier.toLowerCase() : 'regular'}">
                                            ${client.tier != null ? client.tier : 'Regular'}
                                        </div>
                                    </div>
                                    <div class="client-info">
                                        <h3>${client.name}</h3>
                                        <div class="contact-details">
                                            <p><i class="fas fa-envelope"></i> ${client.email}</p>
                                            <p><i class="fas fa-phone"></i> ${client.phone}</p>
                                        </div>
                                        <div class="preferences">
                                            <h4>Preferences</h4>
                                            <div class="preference-tags">
                                                <!-- Display preferences if available -->
                                                <span class="pref-tag">Swedish Massage</span>
                                                <span class="pref-tag">Medium Pressure</span>
                                                <span class="pref-tag">Female Therapist</span>
                                            </div>
                                        </div>
                                        <div class="visit-history">
                                            <p><i class="fas fa-history"></i> Last Visit: 2 weeks ago</p>
                                            <p><i class="fas fa-calendar-check"></i> Total Points: ${client.points}</p>
                                        </div>
                                    </div>
                                    <div class="client-actions">
                                        <button class="btn-icon" title="Book Appointment" onclick="window.location.href = 'customer-management'">
                                            <i class="fas fa-user"></i>
                                        </button>

                                        <button class="btn-icon" title="Book Appointment"><i class="fas fa-calendar-plus"></i></button>
                                        <button class="btn-icon" title="Edit" onclick="openEditModal({
                                                    name: '${client.name}',
                                                    email: '${client.email}',
                                                    phone: '${client.phone}',
                                                    tier: '${client.tier}',
                                                    points: '${client.points}'
                                                })">
                                            <i class="fas fa-edit"></i>
                                        </button>


                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                        <!-- Book Appointment Modal -->
                        <div id="bookAppointmentModal" class="modal">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h2>Book Appointment</h2>
                                    <button class="close-modal" onclick="closeBookingModal()" aria-label="Close">&times;</button>
                                </div>
                                <div class="modal-body">
                                    <form action="BookAppointmentServlet" method="post">
                                        <!-- Client Name (Read-Only) -->
                                        <div class="form-group">
                                            <label for="clientName">Client Name</label>
                                            <input type="text" id="clientName" name="clientName" readonly value="${client.name}" />
                                        </div>

                                        <!-- Appointment Date -->
                                        <div class="form-group">
                                            <label for="appointmentDate">Date</label>
                                            <input type="date" id="appointmentDate" name="appointmentDate" required />
                                        </div>

                                        <!-- Appointment Time -->
                                        <div class="form-group">
                                            <label for="appointmentTime">Time</label>
                                            <input type="time" id="appointmentTime" name="appointmentTime" required />
                                        </div>

                                        <!-- Service Type -->
                                        <div class="form-group">
                                            <label for="serviceType">Service Type</label>
                                            <select id="serviceType" name="serviceType" required>
                                                <option value="Swedish Massage">Swedish Massage</option>
                                                <option value="Deep Tissue Massage">Deep Tissue Massage</option>
                                                <option value="Aromatherapy">Aromatherapy</option>
                                            </select>
                                        </div>

                                        <!-- Therapist Preference -->
                                        <div class="form-group">
                                            <label for="therapistPreference">Therapist Preference</label>
                                            <select id="therapistPreference" name="therapistPreference">
                                                <option value="Any">Any</option>
                                                <option value="Male">Male</option>
                                                <option value="Female">Female</option>
                                            </select>
                                        </div>

                                        <!-- Submit Button -->
                                        <div class="modal-footer">
                                            <button type="submit" class="btn-primary">Confirm Appointment</button>
                                            <button type="button" class="btn-secondary" onclick="closeBookingModal()">Cancel</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Edit Client Modal -->
                        <div id="editClientModal" class="modal" role="dialog" aria-labelledby="editClientTitle" aria-hidden="true">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h2 id="editClientTitle">Edit Client</h2>
                                    <button class="close-modal" onclick="closeEditModal()" aria-label="Close">&times;</button>
                                </div>
                                <div class="modal-body">
                                    <form>
                                        <!-- Client Name -->
                                        <div class="form-group">
                                            <label for="clientName">Name</label>
                                            <input type="text" id="clientName" aria-required="true">
                                        </div>

                                        <!-- Email -->
                                        <div class="form-group">
                                            <label for="clientEmail">Email</label>
                                            <input type="email" id="clientEmail" aria-required="true">
                                        </div>

                                        <!-- Phone -->
                                        <div class="form-group">
                                            <label for="clientPhone">Phone</label>
                                            <input type="tel" id="clientPhone" aria-required="true">
                                        </div>

                                        <!-- Tier -->
                                        <div class="form-group">
                                            <label for="clientTier">Tier</label>
                                            <select id="clientTier" aria-required="true">
                                                <option value="regular">Regular</option>
                                                <option value="vip">VIP</option>
                                                <option value="new">New</option>
                                            </select>
                                        </div>

                                        <!-- Points -->
                                        <div class="form-group">
                                            <label for="clientPoints">Total Points</label>
                                            <input type="number" id="clientPoints" aria-required="true">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn-primary" onclick="saveClient()">Save Changes</button>
                                    <button class="btn-secondary" onclick="closeEditModal()">Cancel</button>
                                </div>
                            </div>
                        </div>




                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Client Statistics -->
                            <div class="summary-card">
                                <h3>Client Overview</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Clients</span>
                                        <span class="value">248</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">VIP Members</span>
                                        <span class="value">45</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">New This Month</span>
                                        <span class="value">12</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Active</span>
                                        <span class="value">180</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Recent Activity -->
                            <div class="summary-card">
                                <h3>Recent Activity</h3>
                                <div class="activity-list">
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-user-plus"></i></div>
                                        <div class="activity-details">
                                            <h4>New Client Registration</h4>
                                            <p>Emily Davis joined as a new client</p>
                                            <span class="activity-time">2 hours ago</span>
                                        </div>
                                    </div>
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-star"></i></div>
                                        <div class="activity-details">
                                            <h4>VIP Status Achieved</h4>
                                            <p>Michael Brown upgraded to VIP</p>
                                            <span class="activity-time">1 day ago</span>
                                        </div>
                                    </div>
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-comment"></i></div>
                                        <div class="activity-details">
                                            <h4>Feedback Received</h4>
                                            <p>Sarah Johnson left a 5-star review</p>
                                            <span class="activity-time">2 days ago</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Quick Actions -->
                            <div class="summary-card">
                                <h3>Quick Actions</h3>
                                <div class="quick-actions-grid">
                                    <button class="action-btn">
                                        <i class="fas fa-user-plus"></i>
                                        Add Client
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-envelope"></i>
                                        Send Email
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-tag"></i>
                                        Promotions
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-file-export"></i>
                                        Export List
                                    </button>
                                </div>
                            </div>
                            <div class="pagination">
                                <c:if test="${currentPage > 1}">
                                    <a href="clientManagement?filter=${selectedTier}&page=${currentPage - 1}" class="page-link">Previous</a>
                                </c:if>

                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <a href="clientManagement?filter=${selectedTier}&page=${i}" class="page-link ${i == currentPage ? 'active' : ''}">${i}</a>
                                </c:forEach>

                                <c:if test="${currentPage < totalPages}">
                                    <a href="clientManagement?filter=${selectedTier}&page=${currentPage + 1}" class="page-link">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script>
            function openEditModal(client) {
                // Thiết lập các giá trị vào modal
                document.getElementById('clientName').value = client.name;
                document.getElementById('clientEmail').value = client.email;
                document.getElementById('clientPhone').value = client.phone;
                document.getElementById('clientTier').value = client.tier ? client.tier.toLowerCase() : 'regular';
                document.getElementById('clientPoints').value = client.points;

                // Hiển thị modal
                const modal = document.getElementById('editClientModal');
                modal.classList.add('active');
                modal.style.display = "flex";
            }

// Đóng modal
            function closeEditModal() {
                const modal = document.getElementById('editClientModal');
                modal.classList.remove('active');
                modal.style.display = "none";
            }


// Hàm xử lý khi lưu thay đổi
            function saveClientChanges() {
                // Logic để lưu thay đổi, có thể bao gồm AJAX gửi dữ liệu đến server
                alert("Changes have been saved!");
                closeEditModal();
            }
        </script>
    </body><!-- comment -->
</html>