<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %> 
<%@ page import="model.Person" %>  <!-- Import Person class -->
<%
    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    Account account = (Account) session.getAttribute("account");
    if (account == null) {
        // Nếu chưa đăng nhập, chuyển hướng tới trang lỗi hoặc login
        response.sendRedirect("login");
        return;
    }

    // Lấy thông tin cá nhân từ đối tượng account
    Person person = account.getPersonInfo();

    // Kiểm tra quyền hạn (chỉ cho phép customer role)
    if (account.getRole() != 4) {
        response.sendRedirect("error");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Appointments - Blushed Beauty Bar</title>
        <link rel="stylesheet" href="newUI/assets/css/styles.css">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <!-- Add FullCalendar CSS -->
        <link href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/main.min.css' rel='stylesheet' />
    </head>

    <body>
        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

        <!-- Appointments Section -->
        <section class="appointments-section">
            <div class="appointments-container">
                <!-- Staff Filter -->
                <div class="staff-filter">
                    <h2>Appointment History</h2>
                    <div class="staff-toggle">
                        <label class="staff-checkbox">
                            <input type="checkbox" checked data-staff="all">
                            <span>All Staff</span>
                        </label>
                        <c:forEach items="${requestScope.staffList}" var="staff">
                            <label class="staff-checkbox">
                                <input type="checkbox" data-staff="${staff.id}">
                                <span>${staff.name}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <!-- Calendar Section -->
                <div class="calendar-section">
                    <div class="calendar-container">
                        <div class="calendar-header">
                            <div class="calendar-nav">
                                <button class="prev-month"><i class="fas fa-chevron-left"></i></button>
                                <h3 class="current-month"></h3>
                                <button class="next-month"><i class="fas fa-chevron-right"></i></button>
                            </div>
                            <div class="view-toggle">
                                <button class="view-btn active" data-view="month">Month</button>
                                <button class="view-btn" data-view="week">Week</button>
                                <button class="view-btn" data-view="day">Day</button>
                            </div>
                        </div>
                        <div id="calendar"></div>
                    </div>
                </div>

                <!-- Details Section -->
                <div class="details-section">
                    <div class="appointment-details">
                        <div class="details-header">
                            <h3>Appointment Details</h3>
                        </div>
                        <div class="details-content">
                            <div class="detail-item">
                                <span class="label">Client</span>
                                <span class="value" data-field="client"></span>
                            </div>
                            <div class="detail-item">
                                <span class="label">Service</span>
                                <span class="value" data-field="service"></span>
                            </div>
                            <div class="detail-item">
                                <span class="label">Date</span>
                                <span class="value" data-field="date"></span>
                            </div>
                            <div class="detail-item">
                                <span class="label">Time</span>
                                <span class="value" data-field="time"></span>
                            </div>
                            <div class="detail-item">
                                <span class="label">Staff</span>
                                <span class="value" data-field="staff"></span>
                            </div>
                            <div class="detail-item">
                                <span class="label">Status</span>
                                <span class="value" data-field="status"></span>
                            </div>
                            <div class="detail-item">
                                <span class="label">Notes</span>
                                <span class="value" data-field="notes"></span>
                            </div>
                            <div class="detail-actions">
                                <button class="edit-btn">Edit Appointment</button>
                                <button class="cancel-btn">Cancel Appointment</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Add this new section after the calendar container -->
                <div class="quick-book-container">
                    <button class="quick-book-btn" id="quickBookBtn">
                        <i class="fas fa-plus"></i> New Appointment
                    </button>
                </div>
            </div>
        </section>

        <!-- Add this new modal section before the footer -->
        <div class="booking-modal" id="bookingModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Quick Book Appointment</h2>
                    <button class="close-modal" onclick="closeBookingModal()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="quickBookForm">
                        <div class="form-group">
                            <label>Select Service</label>
                            <select required>
                                <option value="">Choose a service...</option>
                                <c:forEach items="${requestScope.serviceList}" var="service">
                                    <option value="${service.id}">${service.name} ($${service.price})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Select Staff</label>
                            <select required>
                                <option value="">Choose a staff member...</option>
                                <c:forEach items="${requestScope.staffList}" var="staff">
                                    <option value="${staff.id}">${staff.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="date" required>
                            </div>
                            <div class="form-group">
                                <label>Time</label>
                                <select required>
                                    <option value="">Select time...</option>
                                    <option>9:00 AM</option>
                                    <option>10:00 AM</option>
                                    <option>11:00 AM</option>
                                    <option>1:00 PM</option>
                                    <option>2:00 PM</option>
                                    <option>3:00 PM</option>
                                    <option>4:00 PM</option>
                                    <option>5:00 PM</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Notes (Optional)</label>
                            <textarea rows="3" placeholder="Any special requests or notes..."></textarea>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="cancel-btn" onclick="closeBookingModal()">Cancel</button>
                            <button type="submit" class="confirm-btn">Book Appointment</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Edit Appointment Modal -->
        <div class="booking-modal" id="editAppointmentModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Edit Appointment</h2>
                    <button class="close-modal" id="closeEditModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editAppointmentForm">
                        <div class="form-group">
                            <label>Client Name</label>
                            <input type="text" id="editClientName" required>
                        </div>
                        <div class="form-group">
                            <label>Service</label>
                            <select id="editService" required>
                                <option value="">Choose a service...</option>
                                <option value="classic-full">Classic Full Set ($150)</option>
                                <option value="hybrid-full">Hybrid Full Set ($170)</option>
                                <option value="volume-full">Volume Full Set ($185)</option>
                                <option value="classic-fill">Classic Fill ($65-$105)</option>
                                <option value="hybrid-fill">Hybrid Fill ($80-$130)</option>
                                <option value="volume-fill">Volume Fill ($85-$140)</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Staff Member</label>
                            <select id="editStaff" required>
                                <option value="">Choose a staff member...</option>
                                <option value="sarah">Sarah Johnson</option>
                                <option value="emily">Emily Davis</option>
                                <option value="lauren">Lauren Smith</option>
                                <option value="jessica">Jessica Chen</option>
                                <option value="rachel">Rachel Thompson</option>
                            </select>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="date" id="editDate" required>
                            </div>
                            <div class="form-group">
                                <label>Time</label>
                                <select id="editTime" required>
                                    <option value="">Select time...</option>
                                    <option>9:00 AM</option>
                                    <option>10:00 AM</option>
                                    <option>11:00 AM</option>
                                    <option>1:00 PM</option>
                                    <option>2:00 PM</option>
                                    <option>3:00 PM</option>
                                    <option>4:00 PM</option>
                                    <option>5:00 PM</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Notes</label>
                            <textarea id="editNotes" rows="3" placeholder="Any special requests or notes..."></textarea>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="cancel-btn" id="cancelEdit">Cancel</button>
                            <button type="submit" class="confirm-btn">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Footer Section -->
        <footer>
            <!-- [Previous footer code remains the same] -->
        </footer>

        <!-- Add FullCalendar JS -->
        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js'></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/daygrid@6.1.10/main.min.js'></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/timegrid@6.1.10/main.min.js'></script>
        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/interaction@6.1.10/main.min.js'></script>
        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/main.min.js'></script>
        <!-- Move this script section right after the FullCalendar JS scripts and before the main script -->
        <script>
                                var events = []; // Initialize an empty array
                                // Use JSP to loop through appointments and add each one to the events array
            <c:forEach var="appointment" items="${appointmentList}">
                                var start = "${appointment.start}";
                                var end = "${appointment.end}";
                                var note = "${appointment.note}";
                                var status = "${appointment.status}";
                                var color;
                                switch (status) {
                                    case "Cancelled":
                                        color = '#FF0000'; // Màu đỏ cho trạng thái "cancelled"
                                        break;
                                    case "In Processing":
                                        color = '#4CAF50'; // Màu xanh lá cho trạng thái "confirmed"
                                        break;
                                    case "pending":
                                        color = '#FFC107'; // Màu vàng cho trạng thái "pending"
                                        break;
                                    case "rescheduled":
                                        color = '#2196F3'; // Màu xanh dương cho trạng thái "rescheduled"
                                        break;
                                    default:
                                        color = '#rgb(108 117 125)'; // Màu mặc định nếu trạng thái không khớp
                                }
                                var services = [];
                                var staffs = [];
                <c:forEach var="info" items="${appointment.services}">
                                var service = "${info.service.name}";
                                services.push(service);
                                var staff = "${info.staff.name}";
                                if (staff && staff.trim().length > 0) {
                                    staffs.push(staff);
                                }
                </c:forEach>
                                console.log("Services: ", services);
                                console.log("Staffs: ", staffs);
                                events.push({
                                    title: services,
                                    start: start,
                                    end: end,
                                    staff: staffs,
                                    color: color,
                                    extendedProps: {
                                        client: '${appointment.customer.name}',
                                        staff: staffs,
                                        service: services,
                                        status: status,
                                        notes: note
                                    }
                                });
            </c:forEach>;

                                console.log(events); // Check if events are correctly added
        </script>
        <script>
            AOS.init();
            // Initialize FullCalendar
            document.addEventListener('DOMContentLoaded', function () {
                const calendarEl = document.getElementById('calendar');
                const calendar = new FullCalendar.Calendar(calendarEl, {
                    initialView: 'dayGridMonth',
                    headerToolbar: false,
                    events: events,
                    eventClick: function (info) {
                        // Update the details content with event information
                        const event = info.event;
                        // Update the details content
                        document.querySelector('.detail-item .value[data-field="client"]').textContent = event.extendedProps.client;
                        document.querySelector('.detail-item .value[data-field="service"]').textContent = event.extendedProps.service;
                        document.querySelector('.detail-item .value[data-field="date"]').textContent = event.start.toLocaleDateString();
                        document.querySelector('.detail-item .value[data-field="time"]').textContent =
                                new Date(event.start).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'}) + " - " +
                                new Date(event.end).toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
                        document.querySelector('.detail-item .value[data-field="staff"]').textContent = event.extendedProps.staff;
                        document.querySelector('.detail-item .value[data-field="status"]').textContent = event.extendedProps.status;
                        document.querySelector('.detail-item .value[data-field="notes"]').textContent = event.extendedProps.notes;
                    }
                });
                calendar.render();
                // View toggle functionality
                document.querySelectorAll('.view-btn').forEach(btn => {
                    btn.addEventListener('click', () => {
                        document.querySelectorAll('.view-btn').forEach(b => b.classList.remove('active'));
                        btn.classList.add('active');
                        const view = btn.dataset.view;
                        calendar.changeView(
                                view === 'month' ? 'dayGridMonth' :
                                view === 'week' ? 'timeGridWeek' : 'timeGridDay'
                                );
                    });
                });
                // Month navigation
                document.querySelector('.prev-month').addEventListener('click', () => {
                    calendar.prev();
                    updateMonthTitle();
                });
                document.querySelector('.next-month').addEventListener('click', () => {
                    calendar.next();
                    updateMonthTitle();
                });
                function updateMonthTitle() {
                    const date = calendar.getDate();
                    document.querySelector('.current-month').textContent =
                            date.toLocaleString('default', {month: 'long', year: 'numeric'});
                }
                updateMonthTitle();
                // Modal functionality
                const quickBookBtn = document.getElementById('quickBookBtn');
                const bookingModal = document.getElementById('bookingModal');
                quickBookBtn.addEventListener('click', () => {
                    bookingModal.classList.add('active');
                    document.body.style.overflow = 'hidden';
                });
                // Form submission handling
                document.getElementById('quickBookForm').addEventListener('submit', function (e) {
                    e.preventDefault();
                    alert('Appointment booked successfully!');
                    bookingModal.classList.remove('active');
                    document.body.style.overflow = '';
                });
                // Close modal when clicking outside
                bookingModal.addEventListener('click', (e) => {
                    if (e.target === bookingModal) {
                        bookingModal.classList.remove('active');
                        document.body.style.overflow = '';
                    }
                });
                // Close modal button
                const closeModalBtn = document.querySelector('.close-modal');
                if (closeModalBtn) {
                    closeModalBtn.addEventListener('click', () => {
                        bookingModal.classList.remove('active');
                        document.body.style.overflow = '';
                    });
                }
            }
            );
            // Hamburger menu functionality
            const hamburger = document.querySelector('.hamburger');
            const navLinks = document.querySelector('.nav-links');
            hamburger.addEventListener('click', () => {
                navLinks.classList.toggle('active');
                hamburger.classList.toggle('active');
            });
            // Add this to your existing script section
            document.addEventListener('DOMContentLoaded', function () {
                // Get modal elements
                const editModal = document.getElementById('editAppointmentModal');
                const editBtn = document.querySelector('.edit-btn');
                const closeEditBtn = document.getElementById('closeEditModal');
                const cancelEditBtn = document.getElementById('cancelEdit');
                const editForm = document.getElementById('editAppointmentForm');
                // Function to populate form with current appointment details
                function populateEditForm(appointmentDetails) {
                    document.getElementById('editClientName').value = appointmentDetails.client;
                    document.getElementById('editService').value = appointmentDetails.service.toLowerCase().replace(/ /g, '-');
                    document.getElementById('editStaff').value = appointmentDetails.staff.toLowerCase().split(' ')[0];
                    document.getElementById('editDate').value = appointmentDetails.date;
                    document.getElementById('editTime').value = appointmentDetails.time.split(' - ')[0];
                    document.getElementById('editNotes').value = appointmentDetails.notes;
                }

                // Open edit modal
                editBtn.addEventListener('click', () => {
                    // Get current appointment details from the details panel
                    const appointmentDetails = {
                        client: document.querySelector('.value[data-field="client"]').textContent,
                        service: document.querySelector('.value[data-field="service"]').textContent,
                        staff: document.querySelector('.value[data-field="staff"]').textContent,
                        date: document.querySelector('.value[data-field="date"]').textContent,
                        time: document.querySelector('.value[data-field="time"]').textContent,
                        notes: document.querySelector('.value[data-field="notes"]').textContent
                    };
                    // Populate form with current details
                    populateEditForm(appointmentDetails);
                    // Show modal
                    editModal.classList.add('active');
                    document.body.style.overflow = 'hidden';
                });
                // Close modal functions
                function closeEditModal() {
                    editModal.classList.remove('active');
                    document.body.style.overflow = '';
                    editForm.reset();
                }

                closeEditBtn.addEventListener('click', closeEditModal);
                cancelEditBtn.addEventListener('click', closeEditModal);
                // Close modal when clicking outside
                editModal.addEventListener('click', (e) => {
                    if (e.target === editModal) {
                        closeEditModal();
                    }
                });
                // Handle form submission
                editForm.addEventListener('submit', function (e) {
                    e.preventDefault();
                    // Update appointment details in the details panel
                    document.querySelector('.value[data-field="client"]').textContent = document.getElementById('editClientName').value;
                    document.querySelector('.value[data-field="service"]').textContent = document.getElementById('editService').options[document.getElementById('editService').selectedIndex].text;
                    document.querySelector('.value[data-field="staff"]').textContent = document.getElementById('editStaff').options[document.getElementById('editStaff').selectedIndex].text;
                    document.querySelector('.value[data-field="date"]').textContent = document.getElementById('editDate').value;
                    document.querySelector('.value[data-field="time"]').textContent = document.getElementById('editTime').value;
                    document.querySelector('.value[data-field="notes"]').textContent = document.getElementById('editNotes').value;
                    // Show success message
                    alert('Appointment updated successfully!');
                    // Close modal
                    closeEditModal();
                });
            });
        </script>
    </body>
</html>