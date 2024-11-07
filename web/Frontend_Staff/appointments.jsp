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
        <link rel="stylesheet" href="../styles.css">
    </head>
    <body>
        <div class="dashboard-container">
            <jsp:include page="sideBar.jsp" />

            <div class="main-content">
                <jsp:include page="topbar.jsp" />
                <main>
                    <div class="section-header">

                        <div class="header-content">
                            <h2>Appointments Management</h2>
                            <div class="date-filter">
                                <button class="date-btn active">Today</button>
                                <button class="date-btn">Tomorrow</button>
                                <button class="date-btn">This Week</button>
                                <input type="date" class="date-picker">
                            </div>
                        </div>
                        <button class="btn-primary" id="newAppointmentBtn">
                            <i class="fas fa-plus"></i> New Appointment
                        </button>
                    </div>

                    <div class="appointments-grid">
                        <!-- Timeline Column -->
                        <div class="timeline-column">
                            <div class="time-slot">
                                <span class="time">09:00</span>
                                <div class="slot-content">
                                    <div class="appointment-card booked">
                                        <div class="appointment-time">09:00 AM - 10:00 AM</div>
                                        <h4>Swedish Massage</h4>
                                        <div class="appointment-info">
                                            <span><i class="fas fa-user"></i> Sarah Johnson</span>
                                            <span><i class="fas fa-user-md"></i> Emma Wilson</span>
                                            <span><i class="fas fa-door-open"></i> Room 1</span>
                                            <span><i class="fas fa-clock"></i> 60 min</span>
                                            <span><i class="fas fa-dollar-sign"></i> $85</span>
                                        </div>
                                        <div class="appointment-actions">
                                            <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="Cancel"><i class="fas fa-times"></i></button>
                                            <button class="btn-icon" title="Complete"><i class="fas fa-check"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="time-slot">
                                <span class="time">10:00</span>
                                <div class="slot-content">
                                    <div class="appointment-card available">
                                        <div class="available-slot">Available</div>
                                    </div>
                                </div>
                            </div>

                            <div class="time-slot">
                                <span class="time">11:00</span>
                                <div class="slot-content">
                                    <div class="appointment-card pending">
                                        <div class="appointment-time">11:00 AM - 12:30 PM</div>
                                        <h4>Deep Tissue Massage</h4>
                                        <div class="appointment-info">
                                            <span><i class="fas fa-user"></i> Michael Brown</span>
                                            <span><i class="fas fa-user-md"></i> John Smith</span>
                                            <span><i class="fas fa-door-open"></i> Room 3</span>
                                            <span><i class="fas fa-clock"></i> 90 min</span>
                                            <span><i class="fas fa-dollar-sign"></i> $120</span>
                                        </div>
                                        <div class="status-badge pending">Pending Confirmation</div>
                                    </div>
                                </div>
                            </div>

                            <!-- Add more time slots -->
                            <div class="time-slot">
                                <span class="time">14:00</span>
                                <div class="slot-content">
                                    <div class="appointment-card booked">
                                        <div class="appointment-time">02:00 PM - 03:00 PM</div>
                                        <h4>Hot Stone Massage</h4>
                                        <div class="appointment-info">
                                            <span><i class="fas fa-user"></i> David Wilson</span>
                                            <span><i class="fas fa-user-md"></i> Emma Wilson</span>
                                            <span><i class="fas fa-door-open"></i> Room 4</span>
                                            <span><i class="fas fa-clock"></i> 60 min</span>
                                            <span><i class="fas fa-dollar-sign"></i> $110</span>
                                        </div>
                                        <div class="appointment-actions">
                                            <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="Cancel"><i class="fas fa-times"></i></button>
                                            <button class="btn-icon" title="Complete"><i class="fas fa-check"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Today's Summary Card -->
                            <div class="summary-card">
                                <h3>Today's Summary</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Appointments</span>
                                        <span class="value">8</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Completed</span>
                                        <span class="value">3</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Upcoming</span>
                                        <span class="value">4</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Available Slots</span>
                                        <span class="value">5</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Therapist Status Card -->
                            <div class="summary-card">
                                <h3>Therapist Status</h3>
                                <div class="therapist-list">
                                    <div class="therapist-item available">
                                        <img src="therapist1.jpg" alt="Emma Wilson">
                                        <div class="therapist-info">
                                            <h4>Emma Wilson</h4>
                                            <p>Swedish Massage, Deep Tissue</p>
                                        </div>
                                        <span class="next-slot">Next: 2:30 PM</span>
                                    </div>
                                    <div class="therapist-item busy">
                                        <img src="therapist2.jpg" alt="John Smith">
                                        <div class="therapist-info">
                                            <h4>John Smith</h4>
                                            <p>Sports Massage, Thai Massage</p>
                                        </div>
                                        <span class="next-slot">Until: 1:00 PM</span>
                                    </div>
                                    <div class="therapist-item break">
                                        <img src="therapist3.jpg" alt="Lisa Anderson">
                                        <div class="therapist-info">
                                            <h4>Lisa Anderson</h4>
                                            <p>Facial, Aromatherapy</p>
                                        </div>
                                        <span class="next-slot">Back at: 2:00 PM</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Quick Actions Card -->
                            <div class="summary-card">
                                <h3>Quick Actions</h3>
                                <div class="quick-actions-grid">
                                    <button class="action-btn">
                                        <i class="fas fa-calendar-plus"></i>
                                        New Booking
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-clock"></i>
                                        Block Time
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-user-plus"></i>
                                        Add Client
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-history"></i>
                                        View History
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Add this at the end of appointments.html -->

                    <!-- New Appointment Modal -->
                    <div class="modal" id="appointmentModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>New Appointment</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <form id="appointmentForm">
                                    <!-- Client Selection -->
                                    <div class="form-group">
                                        <label>Client</label>
                                        <select required>
                                            <option value="">Select Client</option>
                                            <option value="1">Sarah Johnson</option>
                                            <option value="2">Michael Brown</option>
                                            <option value="3">Emily Davis</option>
                                            <option value="4">David Wilson</option>
                                        </select>
                                    </div>

                                    <!-- Service Selection -->
                                    <div class="form-group">
                                        <label>Service</label>
                                        <select required>
                                            <option value="">Select Service</option>
                                            <option value="swedish">Swedish Massage (60 min) - $85</option>
                                            <option value="deep">Deep Tissue Massage (90 min) - $120</option>
                                            <option value="hot">Hot Stone Massage (75 min) - $110</option>
                                            <option value="facial">Facial Treatment (60 min) - $95</option>
                                            <option value="aromatherapy">Aromatherapy (60 min) - $90</option>
                                        </select>
                                    </div>

                                    <!-- Therapist Selection -->
                                    <div class="form-group">
                                        <label>Therapist</label>
                                        <select required>
                                            <option value="">Select Therapist</option>
                                            <option value="1">Emma Wilson (Swedish, Deep Tissue)</option>
                                            <option value="2">John Smith (Sports, Thai Massage)</option>
                                            <option value="3">Lisa Anderson (Facial, Aromatherapy)</option>
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
                                            <option value="1">Room 1 - Standard</option>
                                            <option value="2">Room 2 - Deluxe</option>
                                            <option value="3">Room 3 - Couples</option>
                                            <option value="4">Room 4 - Premium</option>
                                        </select>
                                    </div>

                                    <!-- Special Requirements -->
                                    <div class="form-group">
                                        <label>Special Requirements</label>
                                        <div class="checkbox-group">
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="allergies">
                                                Allergies/Sensitivities
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="pressure">
                                                Specific Pressure Preference
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="areas">
                                                Focus Areas
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="music">
                                                Music Preference
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Notes -->
                                    <div class="form-group">
                                        <label>Additional Notes</label>
                                        <textarea rows="3" placeholder="Enter any special requests, preferences, or notes"></textarea>
                                    </div>

                                    <!-- Payment Information -->
                                    <div class="form-group">
                                        <label>Payment Method</label>
                                        <select required>
                                            <option value="">Select Payment Method</option>
                                            <option value="cash">Cash</option>
                                            <option value="card">Credit/Debit Card</option>
                                            <option value="package">Package/Gift Card</option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="cancelAppointment">Cancel</button>
                                <button class="btn-primary" id="saveAppointment">Save Appointment</button>
                            </div>
                        </div>
                    </div>

                    <!-- Edit Appointment Modal -->
                    <div class="modal" id="editAppointmentModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Edit Appointment</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <!-- Same form as new appointment with pre-filled values -->
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="cancelEdit">Cancel</button>
                                <button class="btn-primary" id="saveEdit">Save Changes</button>
                            </div>
                        </div>
                    </div>

                    <!-- Cancel Appointment Modal -->
                    <div class="modal" id="cancelAppointmentModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Cancel Appointment</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="confirmation-message">
                                    <i class="fas fa-exclamation-circle"></i>
                                    <p>Are you sure you want to cancel this appointment?</p>
                                    <div class="appointment-details">
                                        <p><strong>Client:</strong> <span id="cancelClientName">Sarah Johnson</span></p>
                                        <p><strong>Service:</strong> <span id="cancelService">Swedish Massage</span></p>
                                        <p><strong>Date & Time:</strong> <span id="cancelDateTime">Dec 20, 2023 2:30 PM</span></p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Cancellation Reason</label>
                                    <select required>
                                        <option value="">Select Reason</option>
                                        <option value="client">Client Request</option>
                                        <option value="illness">Illness/Emergency</option>
                                        <option value="scheduling">Scheduling Conflict</option>
                                        <option value="other">Other</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Additional Notes</label>
                                    <textarea rows="2" placeholder="Enter any additional notes about the cancellation"></textarea>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="keepAppointment">Keep Appointment</button>
                                <button class="btn-danger" id="confirmCancel">Confirm Cancellation</button>
                            </div>
                        </div>
                    </div>

                    <!-- Complete Appointment Modal -->
                    <div class="modal" id="completeAppointmentModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Complete Appointment</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Service Completion</label>
                                    <div class="checkbox-group">
                                        <label class="checkbox-label">
                                            <input type="checkbox" checked>
                                            Service Completed as Scheduled
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Therapist Notes</label>
                                    <textarea rows="3" placeholder="Enter any notes about the treatment"></textarea>
                                </div>
                                <div class="form-group">
                                    <label>Follow-up Recommendation</label>
                                    <select>
                                        <option value="">Select Recommendation</option>
                                        <option value="week">Next Week</option>
                                        <option value="2weeks">In 2 Weeks</option>
                                        <option value="month">In a Month</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Products Used</label>
                                    <div class="checkbox-group">
                                        <label class="checkbox-label">
                                            <input type="checkbox">
                                            Massage Oil
                                        </label>
                                        <label class="checkbox-label">
                                            <input type="checkbox">
                                            Essential Oils
                                        </label>
                                        <label class="checkbox-label">
                                            <input type="checkbox">
                                            Hot Stones
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="backToAppointment">Back</button>
                                <button class="btn-primary" id="markComplete">Mark as Complete</button>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script src="app.js"></script>
        <script src="../js/pages/appointments.js"></script>
    </body>
</html>