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
                            <h2>Client Management</h2>
                            <div class="client-filter">
                                <button class="filter-btn active">All Clients</button>
                                <button class="filter-btn">Regular</button>
                                <button class="filter-btn">VIP</button>
                                <button class="filter-btn">New</button>
                            </div>
                        </div>
                        <button class="btn-primary" id="newClientBtn">
                            <i class="fas fa-plus"></i> Add New Client
                        </button>
                    </div>

                    <div class="clients-grid">
                        <!-- Clients List -->
                        <div class="clients-list">
                            <!-- Regular Client -->
                            <div class="client-card">
                                <div class="client-header">
                                    <img src="client1.jpg" alt="Sarah Johnson" class="client-avatar">
                                    <div class="client-status regular">Regular Client</div>
                                </div>
                                <div class="client-info">
                                    <h3>Sarah Johnson</h3>
                                    <div class="contact-details">
                                        <p><i class="fas fa-envelope"></i> sarah.j@email.com</p>
                                        <p><i class="fas fa-phone"></i> (555) 123-4567</p>
                                    </div>
                                    <div class="preferences">
                                        <h4>Preferences</h4>
                                        <div class="preference-tags">
                                            <span class="pref-tag">Swedish Massage</span>
                                            <span class="pref-tag">Medium Pressure</span>
                                            <span class="pref-tag">Female Therapist</span>
                                        </div>
                                    </div>
                                    <div class="visit-history">
                                        <p><i class="fas fa-history"></i> Last Visit: 2 weeks ago</p>
                                        <p><i class="fas fa-calendar-check"></i> Total Visits: 12</p>
                                    </div>
                                </div>
                                <div class="client-actions">
                                    <button class="btn-icon" title="View Profile"><i class="fas fa-user"></i></button>
                                    <button class="btn-icon" title="Book Appointment"><i class="fas fa-calendar-plus"></i></button>
                                    <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                </div>
                            </div>

                            <!-- VIP Client -->
                            <div class="client-card vip">
                                <div class="client-header">
                                    <img src="client2.jpg" alt="Michael Brown" class="client-avatar">
                                    <div class="client-status vip">VIP Member</div>
                                </div>
                                <div class="client-info">
                                    <h3>Michael Brown</h3>
                                    <div class="contact-details">
                                        <p><i class="fas fa-envelope"></i> michael.b@email.com</p>
                                        <p><i class="fas fa-phone"></i> (555) 234-5678</p>
                                    </div>
                                    <div class="preferences">
                                        <h4>Preferences</h4>
                                        <div class="preference-tags">
                                            <span class="pref-tag">Deep Tissue</span>
                                            <span class="pref-tag">Hot Stone</span>
                                            <span class="pref-tag">Room 3</span>
                                        </div>
                                    </div>
                                    <div class="visit-history">
                                        <p><i class="fas fa-history"></i> Last Visit: 3 days ago</p>
                                        <p><i class="fas fa-calendar-check"></i> Total Visits: 45</p>
                                    </div>
                                </div>
                                <div class="client-actions">
                                    <button class="btn-icon" title="View Profile"><i class="fas fa-user"></i></button>
                                    <button class="btn-icon" title="Book Appointment"><i class="fas fa-calendar-plus"></i></button>
                                    <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                </div>
                            </div>

                            <!-- New Client -->
                            <div class="client-card new">
                                <div class="client-header">
                                    <img src="client3.jpg" alt="Emily Davis" class="client-avatar">
                                    <div class="client-status new">New Client</div>
                                </div>
                                <div class="client-info">
                                    <h3>Emily Davis</h3>
                                    <div class="contact-details">
                                        <p><i class="fas fa-envelope"></i> emily.d@email.com</p>
                                        <p><i class="fas fa-phone"></i> (555) 345-6789</p>
                                    </div>
                                    <div class="preferences">
                                        <h4>Preferences</h4>
                                        <div class="preference-tags">
                                            <span class="pref-tag">Aromatherapy</span>
                                            <span class="pref-tag">Light Pressure</span>
                                        </div>
                                    </div>
                                    <div class="visit-history">
                                        <p><i class="fas fa-history"></i> First Visit: Today</p>
                                        <p><i class="fas fa-calendar-check"></i> Total Visits: 1</p>
                                    </div>
                                </div>
                                <div class="client-actions">
                                    <button class="btn-icon" title="View Profile"><i class="fas fa-user"></i></button>
                                    <button class="btn-icon" title="Book Appointment"><i class="fas fa-calendar-plus"></i></button>
                                    <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
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
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </body><!-- comment -->
</html>