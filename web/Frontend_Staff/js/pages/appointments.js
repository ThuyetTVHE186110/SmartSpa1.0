class AppointmentsPage {
    constructor () {
        this.appointmentsList = document.getElementById('appointmentsList');
        this.initializeModals();
        this.initializeEventListeners();
        this.loadAppointments();
    }

    initializeModals() {
        // Modal elements
        this.newAppointmentModal = document.getElementById('appointmentModal');
        this.editAppointmentModal = document.getElementById('editAppointmentModal');
        this.cancelAppointmentModal = document.getElementById('cancelAppointmentModal');
        this.completeAppointmentModal = document.getElementById('completeAppointmentModal');

        // Close buttons
        document.querySelectorAll('.close-modal').forEach(button => {
            button.addEventListener('click', () => {
                this.closeAllModals();
            });
        });

        // Close modal when clicking outside
        window.addEventListener('click', (e) => {
            if (e.target.classList.contains('modal')) {
                this.closeAllModals();
            }
        });
    }

    initializeEventListeners() {
        // New Appointment Button
        document.getElementById('newAppointmentBtn')?.addEventListener('click', () => {
            this.openNewAppointmentModal();
        });

        // Date filter listeners
        document.querySelectorAll('.date-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.date-btn').forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                this.filterByDate(e.target.textContent.toLowerCase());
            });
        });

        // Save Appointment Button
        document.getElementById('saveAppointment')?.addEventListener('click', () => {
            this.saveNewAppointment();
        });

        // Cancel Appointment Buttons
        document.getElementById('keepAppointment')?.addEventListener('click', () => {
            this.closeAllModals();
        });

        document.getElementById('confirmCancel')?.addEventListener('click', () => {
            this.confirmCancelAppointment();
        });

        // Complete Appointment Button
        document.getElementById('markComplete')?.addEventListener('click', () => {
            this.markAppointmentComplete();
        });
    }

    openNewAppointmentModal() {
        this.closeAllModals();
        this.newAppointmentModal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }

    openEditAppointmentModal(appointmentId) {
        this.closeAllModals();
        this.editAppointmentModal.classList.add('active');
        // Load appointment data
        const appointment = this.mockData.appointments.find(a => a.id === appointmentId);
        if (appointment) {
            this.populateEditForm(appointment);
        }
        document.body.style.overflow = 'hidden';
    }

    openCancelAppointmentModal(appointmentId) {
        this.closeAllModals();
        this.cancelAppointmentModal.classList.add('active');
        const appointment = this.mockData.appointments.find(a => a.id === appointmentId);
        if (appointment) {
            document.getElementById('cancelClientName').textContent = appointment.clientName;
            document.getElementById('cancelService').textContent = appointment.service;
            document.getElementById('cancelDateTime').textContent = appointment.time;
        }
        document.body.style.overflow = 'hidden';
    }

    openCompleteAppointmentModal(appointmentId) {
        this.closeAllModals();
        this.completeAppointmentModal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }

    closeAllModals() {
        document.querySelectorAll('.modal').forEach(modal => {
            modal.classList.remove('active');
        });
        document.body.style.overflow = 'auto';
    }

    saveNewAppointment() {
        const form = document.getElementById('appointmentForm');
        if (form.checkValidity()) {
            // Collect form data and save
            console.log('Saving new appointment...');
            this.closeAllModals();
            // Refresh appointments list
            this.loadAppointments();
        } else {
            form.reportValidity();
        }
    }

    confirmCancelAppointment() {
        const reason = document.querySelector('#cancelAppointmentModal select').value;
        if (reason) {
            console.log('Canceling appointment with reason:', reason);
            this.closeAllModals();
            // Refresh appointments list
            this.loadAppointments();
        } else {
            alert('Please select a cancellation reason');
        }
    }

    markAppointmentComplete() {
        const notes = document.querySelector('#completeAppointmentModal textarea').value;
        console.log('Completing appointment with notes:', notes);
        this.closeAllModals();
        // Refresh appointments list
        this.loadAppointments();
    }

    populateEditForm(appointment) {
        // Populate edit form with appointment data
        const form = document.querySelector('#editAppointmentModal form');
        if (form) {
            // Set form values based on appointment data
            // This would be implemented based on your form structure
        }
    }

    editAppointment(id) {
        this.openEditAppointmentModal(id);
    }

    cancelAppointment(id) {
        this.openCancelAppointmentModal(id);
    }

    completeAppointment(id) {
        this.openCompleteAppointmentModal(id);
    }

    // Existing methods remain the same
    loadAppointments() {
        setTimeout(() => {
            this.renderAppointments(this.mockData.appointments);
            this.renderTherapists(this.mockData.therapists);
        }, 300);
    }

    renderAppointments(appointments) {
        const appointmentsHTML = appointments.map(appointment => `
            <div class="appointment-card ${appointment.status}">
                <div class="appointment-time">${appointment.time}</div>
                <h4>${appointment.service}</h4>
                <div class="appointment-info">
                    <span><i class="fas fa-user"></i> ${appointment.clientName}</span>
                    <span><i class="fas fa-user-md"></i> ${appointment.therapistName}</span>
                    <span><i class="fas fa-door-open"></i> ${appointment.room}</span>
                    <span><i class="fas fa-clock"></i> ${appointment.duration}</span>
                    <span><i class="fas fa-dollar-sign"></i> ${appointment.price}</span>
                </div>
                <div class="appointment-actions">
                    <button class="btn-icon" onclick="appointmentsPage.editAppointment(${appointment.id})" title="Edit">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn-icon" onclick="appointmentsPage.cancelAppointment(${appointment.id})" title="Cancel">
                        <i class="fas fa-times"></i>
                    </button>
                    ${appointment.status === 'confirmed' ? `
                        <button class="btn-icon" onclick="appointmentsPage.completeAppointment(${appointment.id})" title="Complete">
                            <i class="fas fa-check"></i>
                        </button>
                    ` : ''}
                </div>
            </div>
        `).join('');

        this.appointmentsList.innerHTML = appointmentsHTML;
    }

    renderTherapists(therapists) {
        const therapistList = document.createElement('div');
        therapistList.className = 'therapist-list';
        therapistList.innerHTML = therapists.map(therapist => `
            <div class="therapist-item ${therapist.status}">
                <img src="${therapist.image}" alt="${therapist.name}">
                <div class="therapist-info">
                    <h4>${therapist.name}</h4>
                    <p>${therapist.specialties.join(', ')}</p>
                </div>
                <span class="next-slot">Next: ${therapist.nextSlot}</span>
            </div>
        `).join('');

        // Find or create summary column
        let summaryColumn = document.querySelector('.summary-column');
        if (!summaryColumn) {
            summaryColumn = document.createElement('div');
            summaryColumn.className = 'summary-column';
            this.appointmentsList.parentNode.appendChild(summaryColumn);
        }

        // Add therapist list to summary column
        const therapistCard = document.createElement('div');
        therapistCard.className = 'summary-card';
        therapistCard.innerHTML = '<h3>Active Therapists</h3>';
        therapistCard.appendChild(therapistList);
        summaryColumn.appendChild(therapistCard);
    }

    filterByDate(dateFilter) {
        console.log(`Filtering appointments by: ${dateFilter}`);
        this.loadAppointments();
    }
}

// Initialize the page
const appointmentsPage = new AppointmentsPage();