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
                    <div class="breadcrumb">
                        <i class="fas fa-home"></i>
                        <span>Dashboard</span>
                    </div>

                    <section id="dashboard" class="dashboard-grid">
                        <!-- Today's Overview Card -->
                        <div class="card stats-card">
                            <h3>Today's Overview</h3>
                            <div class="stats-grid">
                                <div class="stat-item">
                                    <i class="fas fa-calendar-check"></i>
                                    <h4>Today's Bookings</h4>
                                    <p>24</p>
                                    <span class="trend up">+12% vs yesterday</span>
                                </div>
                                <div class="stat-item">
                                    <i class="fas fa-user-clock"></i>
                                    <h4>Available Slots</h4>
                                    <p>8</p>
                                    <span class="status">Next at 2:30 PM</span>
                                </div>
                                <div class="stat-item">
                                    <i class="fas fa-user-md"></i>
                                    <h4>Active Therapists</h4>
                                    <p>6/8</p>
                                    <span class="status">2 on break</span>
                                </div>
                                <div class="stat-item">
                                    <i class="fas fa-coins"></i>
                                    <h4>Today's Revenue</h4>
                                    <p>$1,248</p>
                                    <span class="trend up">+8% vs yesterday</span>
                                </div>
                            </div>
                        </div>

                        <!-- Upcoming Appointments Card -->
                        <div class="card">
                            <div class="card-header">
                                <h3>Upcoming Appointments</h3>
                                <button class="btn-text">View All</button>
                            </div>
                            <div class="appointment-list">
                                <div class="appointment-item">
                                    <div class="time-slot">
                                        <span class="time">14:30</span>
                                        <span class="duration">60 min</span>
                                    </div>
                                    <div class="appointment-details">
                                        <h4>Swedish Massage</h4>
                                        <p>Client: Sarah Johnson</p>
                                        <span class="therapist">Therapist: Emma Wilson</span>
                                    </div>
                                    <div class="appointment-status confirmed">
                                        <span>Confirmed</span>
                                    </div>
                                </div>
                                <!-- More appointment items... -->
                            </div>
                        </div>

                        <!-- Quick Actions Card -->
                        <div class="card">
                            <h3>Quick Actions</h3>
                            <div class="quick-actions-grid">
                                <button class="action-btn">
                                    <i class="fas fa-calendar-plus"></i>
                                    New Booking
                                </button>
                                <button class="action-btn">
                                    <i class="fas fa-user-plus"></i>
                                    New Client
                                </button>
                                <button class="action-btn">
                                    <i class="fas fa-clock"></i>
                                    Manage Schedule
                                </button>
                                <button class="action-btn">
                                    <i class="fas fa-gift"></i>
                                    Special Offers
                                </button>
                            </div>
                        </div>

                        <!-- Room Status Card -->
                        <div class="card">
                            <div class="card-header">
                                <h3>Treatment Room Status</h3>
                                <button class="btn-text">Manage Rooms</button>
                            </div>
                            <div class="room-status-grid">
                                <div class="room-status-item occupied">
                                    <h4>Room 1</h4>
                                    <p>Swedish Massage</p>
                                    <span>Ends at 15:00</span>
                                </div>
                                <!-- More room status items... -->
                            </div>
                        </div>
                    </section>

                    <!-- Add this at the end of the dashboard section -->

                    <!-- New Booking Modal -->
                    <div class="modal" id="bookingModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>New Booking</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <form id="bookingForm">
                                    <!-- Client Information -->
                                    <div class="form-group">
                                        <label>Client</label>
                                        <select required>
                                            <option value="">Select Client</option>
                                            <option value="1">Sarah Johnson</option>
                                            <option value="2">Michael Brown</option>
                                            <option value="3">Emily Davis</option>
                                        </select>
                                    </div>

                                    <!-- Service Selection -->
                                    <div class="form-group">
                                        <label>Service</label>
                                        <select required>
                                            <option value="">Select Service</option>
                                            <option value="swedish">Swedish Massage (60 min)</option>
                                            <option value="deep">Deep Tissue Massage (90 min)</option>
                                            <option value="hot">Hot Stone Massage (75 min)</option>
                                            <option value="facial">Facial Treatment (60 min)</option>
                                        </select>
                                    </div>

                                    <!-- Therapist Selection -->
                                    <div class="form-group">
                                        <label>Therapist</label>
                                        <select required>
                                            <option value="">Select Therapist</option>
                                            <option value="1">Emma Wilson</option>
                                            <option value="2">John Smith</option>
                                            <option value="3">Lisa Anderson</option>
                                        </select>
                                    </div>

                                    <!-- Date and Time -->
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label>Date</label>
                                            <input type="date" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Time</label>
                                            <input type="time" required>
                                        </div>
                                    </div>

                                    <!-- Room Selection -->
                                    <div class="form-group">
                                        <label>Treatment Room</label>
                                        <select required>
                                            <option value="">Select Room</option>
                                            <option value="1">Room 1</option>
                                            <option value="2">Room 2</option>
                                            <option value="3">Room 3</option>
                                            <option value="4">Room 4</option>
                                        </select>
                                    </div>

                                    <!-- Special Requests -->
                                    <div class="form-group">
                                        <label>Special Requests</label>
                                        <textarea rows="3" placeholder="Enter any special requests or notes"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="cancelBooking">Cancel</button>
                                <button class="btn-primary" id="saveBooking">Save Booking</button>
                            </div>
                        </div>
                    </div>

                    <!-- New Client Modal -->
                    <div class="modal" id="clientModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Add New Client</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <form id="clientForm">
                                    <!-- Personal Information -->
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label>First Name</label>
                                            <input type="text" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Last Name</label>
                                            <input type="text" required>
                                        </div>
                                    </div>

                                    <!-- Contact Information -->
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="email" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Phone</label>
                                            <input type="tel" required>
                                        </div>
                                    </div>

                                    <!-- Preferences -->
                                    <div class="form-group">
                                        <label>Preferred Services</label>
                                        <div class="checkbox-group">
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="swedish">
                                                Swedish Massage
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="deep">
                                                Deep Tissue Massage
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="hot">
                                                Hot Stone Massage
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="facial">
                                                Facial Treatment
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Additional Notes -->
                                    <div class="form-group">
                                        <label>Additional Notes</label>
                                        <textarea rows="3" placeholder="Enter any additional notes or preferences"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="cancelClient">Cancel</button>
                                <button class="btn-primary" id="saveClient">Save Client</button>
                            </div>
                        </div>
                    </div>

                    <!-- Schedule Management Modal -->
                    <div class="modal" id="scheduleModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Manage Schedule</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="schedule-options">
                                    <div class="form-group">
                                        <label>View Type</label>
                                        <div class="button-group">
                                            <button class="btn-option active">Daily</button>
                                            <button class="btn-option">Weekly</button>
                                            <button class="btn-option">Monthly</button>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Block Time Slot</label>
                                        <div class="form-row">
                                            <input type="date" required>
                                            <select>
                                                <option>Morning</option>
                                                <option>Afternoon</option>
                                                <option>Evening</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Reason</label>
                                        <select>
                                            <option>Maintenance</option>
                                            <option>Staff Meeting</option>
                                            <option>Training</option>
                                            <option>Other</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="cancelSchedule">Cancel</button>
                                <button class="btn-primary" id="saveSchedule">Save Changes</button>
                            </div>
                        </div>
                    </div>

                    <!-- Special Offers Modal -->
                    <div class="modal" id="offersModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Special Offers</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="offers-list">
                                    <div class="offer-item">
                                        <div class="offer-details">
                                            <h4>Holiday Package</h4>
                                            <p>Includes massage, facial, and aromatherapy</p>
                                            <span class="offer-price">$199 (Save 25%)</span>
                                        </div>
                                        <button class="btn-primary">Apply</button>
                                    </div>
                                    <div class="offer-item">
                                        <div class="offer-details">
                                            <h4>First-Time Client</h4>
                                            <p>20% off any service</p>
                                            <span class="offer-price">Use code: WELCOME20</span>
                                        </div>
                                        <button class="btn-primary">Apply</button>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="closeOffers">Close</button>
                            </div>
                        </div> 
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>