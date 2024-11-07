<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ page import="model.Account" %>
            <%@ page import="model.Person" %> <!-- Import Person class -->
                <% // No need to declare session manually; it's already available in JSP // You can directly use session
                    if (session==null || session.getAttribute("account")==null) { // Redirect to login page if session
                    is not found or account is not in session response.sendRedirect("login"); } else { // Get the
                    account object from session Account account=(Account) session.getAttribute("account"); if
                    (account.getRole()==4) { response.sendRedirect("booking"); } else { // Set an error message and
                    redirect to an error page
                    request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard."
                    ); request.getRequestDispatcher("error").forward(request, response); } } %>
                    <!DOCTYPE html>
                    <html lang="en">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Book Appointment - Blushed Beauty Bar</title>
                        <link rel="stylesheet" href="newUI/assets/css/styles.css">
                        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
                        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
                        <link rel="stylesheet"
                            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
                        <link rel="stylesheet"
                            href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/5.11.3/main.min.css">
                        <style>
                            .time-options {
                                display: flex;
                                gap: 20px;
                                justify-content: space-around;
                            }

                            .time-slots h3 {
                                color: #000000;
                            }

                            .time-slots h4 {
                                color: #000000;
                            }

                            .time-column {
                                flex: 1;
                            }

                            .time-slot {
                                display: block;
                                width: 100%;
                                margin: 5px 0;
                                padding: 8px;
                                background-color: #f0f0f0;
                                border: 1px solid #ddd;
                                cursor: pointer;
                                text-align: center;
                                transition: background-color 0.3s;
                            }

                            .time-slot.selected {
                                background-color: #007bff;
                                color: white;
                                border-color: #0056b3;
                            }

                            /* Style cho ô input */
                            .summary-item input[type="text"] {
                                width: 150px;
                                padding: 10px;
                                font-size: 16px;
                                border: 1px solid #ccc;
                                border-radius: 4px;
                                transition: border-color 0.3s ease;
                            }

                            .summary-item input[type="text"]:focus {
                                border-color: #66afe9;
                                outline: none;
                                box-shadow: 0 0 5px rgba(102, 175, 233, 0.6);
                            }

                            /* Style cho thông báo mã referral */
                            .code {
                                margin-top: 10px;
                                font-size: 14px;
                                color: #555;
                                padding: 8px;
                                border-radius: 4px;
                                background-color: #f1f1f1;
                            }

                            .error-message {
                                position: fixed;
                                top: 10px;
                                right: 10px;
                                background-color: rgba(255, 0, 0, 0.8);
                                color: white;
                                padding: 10px;
                                border-radius: 5px;
                                font-size: 16px;
                                z-index: 9999;
                                /* Đảm bảo nó luôn hiển thị ở trên cùng */
                                opacity: 0;
                                /* Ẩn thông báo ban đầu */
                                transition: opacity 0.5s ease-in-out;
                                /* Hiệu ứng mờ dần */
                            }
                        </style>
                    </head>

                    <body>
                        <jsp:include page="NavBarJSP/NavBarJSP.jsp" />

                        <!-- Booking Hero Section -->
                        <section class="booking-hero">
                            <div class="hero-content">
                                <h1>Book Your Appointment</h1>
                                <p>Transform your look with our expert services</p>
                                <p>${success}</p>
                            </div>
                        </section>
                        <!--Error Message-->
                        <div id="error-message" class="error-message">
                        </div>
                        <!-- Booking Form Section -->
                        <section class="booking-section">
                            <div class="booking-container">
                                <div class="booking-steps" data-aos="fade-up">
                                    <div class="step active">
                                        <span class="step-number">1</span>
                                        <span class="step-text">Select Service</span>
                                    </div>
                                    <div class="step">
                                        <span class="step-number">2</span>
                                        <span class="step-text">Choose Date & Time</span>
                                    </div>
                                    <div class="step">
                                        <span class="step-number">3</span>
                                        <span class="step-text">Special Notes</span>
                                    </div>
                                    <div class="step">
                                        <span class="step-number">4</span>
                                        <span class="step-text">Confirmation</span>
                                    </div>
                                </div>
                                <form id="bookingForm" class="booking-form" data-aos="fade-up" action="booking"
                                    method="post">
                                    <!-- Step 1: Service Selection -->
                                    <div class="form-step active" id="step1">
                                        <h2>Select Your Service</h2>
                                        <!-- Input store selected services -->
                                        <input type="hidden" name="selectedServices" id="selectedServices">
                                        <div class="service-categories">
                                            <%--<c:forEach items="${requestScope.categoryList}" var="category">--%>
                                                <div class="service-category">
                                                    <h3>${category.name}</h3>
                                                    <div class="service-options">
                                                        <c:forEach items="${requestScope.serviceList}" var="service">
                                                            <%--<c:if test="${category.id == service.categoryID}">--%>
                                                                <label class="service-option">
                                                                    <input type="checkbox" name="service"
                                                                        value="${service.id}" class="service-checkbox"
                                                                        required>
                                                                    <span class="option-content">
                                                                        <span
                                                                            class="service-name">${service.name}</span>
                                                                        <span
                                                                            class="service-price">$${service.price}</span>
                                                                        <span
                                                                            class="service-duration">${service.duration}
                                                                            mins</span>
                                                                    </span>
                                                                </label>
                                                                <%--< /c:if>--%>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <%--< /c:forEach>--%>
                                        </div>
                                        <div class="form-navigation">
                                            <button type="button" class="next-step">Continue to Date & Time</button>
                                        </div>
                                    </div>

                                    <!-- Step 2: Date & Time Selection -->
                                    <div class="form-step" id="step2">
                                        <h2>Choose Date & Time</h2>
                                        <div class="datetime-selection">
                                            <!-- Staff Selection -->
                                            <div class="form-group">
                                                <label for="staff">Select Staff</label>
                                                <select id="staff" name="staff" required>
                                                    <option value="">Any available staff</option>
                                                    <c:forEach items="${requestScope.staffList}" var="staff">
                                                        <option value="${staff.id}">${staff.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="datetime-selection">
                                                <div class="calendar-container" style="max-height: fit-content">
                                                    <label for="appointmentDate">Choose Date</label>
                                                    <input type="date" id="appointmentDate" name="appointmentDate"
                                                        value="${today}" required>
                                                </div>
                                                <div class="time-slots">
                                                    <h3>Available Times</h3>
                                                    <div class="time-options" id="timeOptions">
                                                        <!-- Time slots will be populated via JavaScript -->
                                                        <div class="time-column">
                                                            <h4>Morning</h4>
                                                            <div id="morningSlots">
                                                                <!-- Morning slots populated by JavaScript -->
                                                            </div>
                                                        </div>
                                                        <div class="time-column">
                                                            <h4>Afternoon</h4>
                                                            <div id="afternoonSlots">
                                                                <!-- Afternoon slots populated by JavaScript -->
                                                            </div>
                                                        </div>
                                                        <div class="time-column">
                                                            <h4>Evening</h4>
                                                            <div id="eveningSlots">
                                                                <!-- Evening slots populated by JavaScript -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-navigation">
                                            <button type="button" class="prev-step">Back</button>
                                            <button id="continue-details" type="button" class="next-step">Continue to
                                                Details</button>
                                        </div>
                                    </div>

                                    <!-- Step 3: Personal Details -->
                                    <div class="form-step" id="step3">
                                        <h2>Special Notes</h2>
                                        <div class="personal-details">
                                            <!--                            <div class="form-group">
                                <label for="name">Full Name</label>
                                <input type="text" id="name" name="name" value="${account.personInfo.name}" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="email" value="${account.personInfo.email}" required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone</label>
                                <input type="tel" id="phone" name="phone" value="${account.personInfo.phone}" required>
                            </div>-->
                                            <div class="form-group">
                                                <label for="notes">Special Notes/Requests</label>
                                                <textarea id="notes" name="notes" rows="4"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-navigation">
                                            <button type="button" class="prev-step">Back</button>
                                            <button type="button" class="next-step">Review Booking</button>
                                        </div>
                                    </div>

                                    <!-- Step 4: Confirmation -->
                                    <div class="form-step" id="step4">
                                        <h2>Review Your Booking</h2>
                                        <div class="booking-summary">
                                            <div class="summary-item">
                                                <h3>Selected Service</h3>
                                                <p id="summary-service"></p>
                                            </div>
                                            <div class="summary-item">
                                                <h3>Date & Time</h3>
                                                <p id="summary-datetime"></p>
                                            </div>
                                            <div class="summary-item">
                                                <h3>Total: </h3>
                                                <p id="summary-total"></p>
                                            </div>
                                            <div class="summary-item">
                                                <h3>Promo Code: </h3>
                                                <input type="text" id="referralCode" placeholder="Enter CODE"
                                                    name="code" onchange="checkReferral()">
                                                <div class="message-code"></div>
                                            </div>
                                        </div>
                                        <div class="form-navigation">
                                            <input type="hidden" id="selectedTimeInput" name="selectedTime">
                                            <button type="button" class="prev-step">Back</button>
                                            <button id="booking" type="submit" class="confirm-booking">Confirm
                                                Booking</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </section>

                        <!-- Footer Section -->
                        <footer>
                            <!-- [Previous footer code remains the same] -->
                        </footer>
                        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js'></script>
                        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/daygrid@6.1.10/main.min.js'></script>
                        <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/timegrid@6.1.10/main.min.js'></script>
                        <script
                            src='https://cdn.jsdelivr.net/npm/@fullcalendar/interaction@6.1.10/main.min.js'></script>
                        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/main.min.js'></script>

                        <script>
                            async function checkReferral() {
                                const code = document.getElementById("referralCode").value;
                                const codeDiv = document.querySelector(".message-code");
                                if (code) {
                                    try {
                                        const response = await fetch(`getReferral?code=` + code);
                                        // Kiểm tra nếu phản hồi thành công
                                        if (response.ok) {
                                            const data = await response.json();
                                            if (data.message === "Mã không hợp lệ") {
                                                codeDiv.textContent = data.message; // Ghi thông điệp vào thẻ div
                                            } else {
                                                codeDiv.textContent = "Bạn được giảm " + data.message + "%";
                                            }
                                            codeDiv.classList.add("code");
                                        } else {
                                            codeDiv.textContent = "Lỗi kết nối đến server";
                                        }
                                    } catch (error) {
                                        console.error("Lỗi:", error);
                                        codeDiv.textContent = "Đã xảy ra lỗi khi xử lý yêu cầu";
                                    }
                                } else {
                                    codeDiv.textContent = "";
                                    codeDiv.classList.remove("code");
                                }
                            }
                        </script>
                        <script>
                            var selectedTime = null;
                            async function getAvailableTimes() {
                                const staffId = document.getElementById("staff").value;
                                const date = document.getElementById("appointmentDate").value;
                                const service = document.querySelector('input[name="service"]:checked')?.value;

                                if (staffId && date && service) {
                                    try {
                                        const response = await fetch(`getAvailableTimes?service=` + service + `&staffId=` + staffId + `&date=` + date);
                                        const availableSlots = await response.json();

                                        //                                                     Clear previous slots
                                        document.getElementById("morningSlots").innerHTML = "";
                                        document.getElementById("afternoonSlots").innerHTML = "";
                                        document.getElementById("eveningSlots").innerHTML = "";

                                        availableSlots.forEach(slot => {
                                            const button = document.createElement("button");
                                            button.type = "button";
                                            button.classList.add("time-slot");
                                            button.textContent = slot;
                                            button.onclick = () => selectTime(button, slot);

                                            // Categorize by time of day
                                            const [hour] = slot.split(":").map(Number);
                                            if (hour >= 10 && hour < 12) {
                                                document.getElementById("morningSlots").appendChild(button);
                                            } else if (hour >= 12 && hour < 16) {
                                                document.getElementById("afternoonSlots").appendChild(button);
                                            } else if (hour >= 16 && hour < 18) {
                                                document.getElementById("eveningSlots").appendChild(button);
                                            }
                                        });
                                    } catch (error) {
                                        console.error("Error fetching available times:", error);
                                    }
                                } else {
                                    console.log("Please select staff, date, and service.");
                                }
                            }

                            function selectTime(selectedButton, time) {
                                document.querySelectorAll('.time-slot').forEach(btn => btn.classList.remove('selected'));
                                selectedButton.classList.add('selected');
                                // Store the selected time in the variable
                                selectedTime = time;
                                console.log("Selected time:", selectedTime);  // Log the selected time for debugging

                                // Optional: Update a hidden input field if you need to submit it with a form
                                document.getElementById("selectedTimeInput").value = selectedTime;
                            }
                        </script>
                        <script>
                            AOS.init();
                            // Hamburger menu functionality
                            const hamburger = document.querySelector('.hamburger');
                            const navLinks = document.querySelector('.nav-links');
                            hamburger.addEventListener('click', () => {
                                navLinks.classList.toggle('active');
                                hamburger.classList.toggle('active');
                            });
                            // Form step navigation
                            let currentStep = 1;
                            const totalSteps = 4;
                            function updateSteps() {
                                document.querySelectorAll('.step').forEach((step, index) => {
                                    if (index + 1 < currentStep) {
                                        step.classList.add('completed');
                                        step.classList.remove('active');
                                    } else if (index + 1 === currentStep) {
                                        step.classList.add('active');
                                        step.classList.remove('completed');
                                    } else {
                                        step.classList.remove('active', 'completed');
                                    }
                                });
                                document.querySelectorAll('.form-step').forEach((step, index) => {
                                    if (index + 1 === currentStep) {
                                        step.classList.add('active');
                                    } else {
                                        step.classList.remove('active');
                                    }
                                });
                            }

                            document.querySelectorAll('.next-step').forEach(button => {
                                button.addEventListener('click', () => {

                                    const currentFormStep = document.querySelector('.form-step.active');
                                    const inputs = currentFormStep.querySelectorAll('input');
                                    console.log(inputs);
                                    let valid = true;
                                    // Check if each input is filled (only if required)
                                    if (currentFormStep.id === 'step1') {
                                        const serviceCheckboxes = document.querySelectorAll('.service-checkbox');
                                        const isServiceSelected = Array.from(serviceCheckboxes).some(checkbox => checkbox.checked);

                                        if (!isServiceSelected) {
                                            showError('Please select at least one service to continue.');
                                            valid = false;
                                        }
                                    } else if (currentFormStep.id === 'step2') {
                                        const selectedTimeSlots = document.querySelectorAll('.time-slot.selected');
                                        if (selectedTimeSlots.length === 0) {
                                            showError('Please select at least one available time to continue.');
                                            valid = false;
                                        }
                                    }
                                    if (valid) {
                                        // Move to the next step only if all required inputs are filled
                                        if (currentStep < totalSteps) {
                                            currentStep++;
                                            updateSteps();
                                            if (currentStep === 4) {
                                                updateSummary(); // Update summary when reaching the last step
                                            }
                                        }
                                    }
                                });
                            });
                            document.querySelectorAll('.prev-step').forEach(button => {
                                button.addEventListener('click', () => {
                                    if (currentStep > 1) {
                                        currentStep--;
                                        updateSteps();
                                    }
                                });
                            });
                            function updateSummary() {
                                // Update selected service
                                const selectedService = document.querySelector('input[name="service"]:checked');
                                const serviceName = selectedService ? selectedService.closest('.service-option').querySelector('.service-name').textContent : '';
                                const servicePrice = selectedService ? selectedService.closest('.service-option').querySelector('.service-price').textContent : '';
                                const serviceDuration = selectedService ? selectedService.closest('.service-option').querySelector('.service-duration').textContent : '';
                                document.getElementById('summary-service').textContent = serviceName + " - " + servicePrice + " (" + serviceDuration + ")";
                                // Update selected date & time
                                const date = document.getElementById("appointmentDate").value;
                                //                var selectedTime = selectTime;
                                //                const summaryDate = selectedDate.value ? selectedDate.textContent : 'No date selected';
                                //                const summaryTime = selectedTime ? selectedTime.value : 'No time selected';
                                document.getElementById('summary-datetime').textContent = date + " at " + selectedTime;
                                // Update personal details
                                const name = document.getElementById('name').value;
                                const email = document.getElementById('email').value;
                                const phone = document.getElementById('phone').value;
                                const notes = document.getElementById('notes').value;
                                document.getElementById('summary-details').innerHTML =
                                    "Name: " + name + "<br>Email: " + email + "<br>Phone: " + phone + "<br>Notes: " + (notes ? notes : 'None');
                            }

                            // Form submission
                            //            document.getElementById('bookingForm').addEventListener('submit', function (e) {
                            //                e.preventDefault();
                            //                // Add your booking submission logic here
                            //                alert('Thank you for your booking! We will confirm your appointment shortly.');
                            //                // Reset form and steps
                            //                currentStep = 1;
                            //                updateSteps();
                            //                this.reset();
                            //            }
                            //            );
                            function showError(message) {
                                const errorMessage = document.getElementById('error-message');
                                errorMessage.textContent = message;  // Thay đổi nội dung thông báo

                                // Hiển thị thông báo lỗi
                                errorMessage.style.display = 'block';

                                // Đợi 0.1 giây để cho phép browser render thông báo
                                setTimeout(() => {
                                    errorMessage.style.opacity = 1;  // Từ từ hiện lên
                                }, 100);

                                // Ẩn thông báo sau 3 giây
                                setTimeout(() => {
                                    errorMessage.style.opacity = 0;  // Dần dần mờ đi
                                    setTimeout(() => {
                                        errorMessage.style.display = 'none';  // Ẩn đi sau khi hiệu ứng kết thúc
                                    }, 500); // Thời gian để opacity chuyển thành 0 (500ms)
                                }, 3000); // Sau 3 giây, ẩn thông báo
                            }
                        </script>
                    </body>

                    </html>