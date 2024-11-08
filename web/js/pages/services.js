document.addEventListener('DOMContentLoaded', function () {
    setupModalHandlers();
    setupFilterHandlers();
});

// Modal Functions
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';

        // Reset form if opening add service modal
        if (modalId === 'serviceModal') {
            resetServiceForm();
        }
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
        document.body.style.overflow = 'auto';
    }
}

function resetServiceForm() {
    const form = document.getElementById('serviceForm');
    if (form) {
        form.reset();
        document.getElementById('modalTitle').textContent = 'Add New Service';
        document.getElementById('actionType').value = 'add';
        document.getElementById('serviceId').value = '';
        document.getElementById('statusGroup').style.display = 'none';
    }
}

function setupModalHandlers() {
    // Close modal when clicking outside
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('click', function (e) {
            if (e.target === this) {
                closeModal(this.id);
            }
        });
    });

    // Close buttons in modals
    document.querySelectorAll('.close-modal').forEach(button => {
        button.addEventListener('click', function () {
            const modal = this.closest('.modal');
            if (modal) {
                closeModal(modal.id);
            }
        });
    });
}

function setupFilterHandlers() {
    document.querySelectorAll('.filter-btn').forEach(button => {
        button.addEventListener('click', function () {
            document.querySelectorAll('.filter-btn').forEach(btn =>
                btn.classList.remove('active'));
            this.classList.add('active');

            // Submit the filter form
            document.getElementById('filterForm').submit();
        });
    });
}

// Service Actions
function openEditModal(serviceId) {
    // Set form action to edit
    const form = document.getElementById('serviceForm');
    form.action = 'staff-services?action=edit';

    // Submit form to get service details
    const hiddenInput = document.createElement('input');
    hiddenInput.type = 'hidden';
    hiddenInput.name = 'id';
    hiddenInput.value = serviceId;
    form.appendChild(hiddenInput);
    form.submit();
}

function confirmDelete(serviceId) {
    const modal = document.getElementById('deleteModal');
    if (modal) {
        modal.dataset.serviceId = serviceId;
        openModal('deleteModal');
    }
}

function deleteService() {
    const modal = document.getElementById('deleteModal');
    const serviceId = modal.dataset.serviceId;

    // Create and submit form for deletion
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'staff-services';

    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'delete';

    const idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'id';
    idInput.value = serviceId;

    form.appendChild(actionInput);
    form.appendChild(idInput);
    document.body.appendChild(form);
    form.submit();
}

function toggleStatus(serviceId, currentStatus) {
    // Create and submit form for status toggle
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'staff-services';

    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'status';

    const idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = 'id';
    idInput.value = serviceId;

    const statusInput = document.createElement('input');
    statusInput.type = 'hidden';
    statusInput.name = 'status';
    statusInput.value = currentStatus === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';

    form.appendChild(actionInput);
    form.appendChild(idInput);
    form.appendChild(statusInput);
    document.body.appendChild(form);
    form.submit();
}

// Form validation
function validateServiceForm() {
    const form = document.getElementById('serviceForm');
    const name = form.querySelector('[name="name"]').value;
    const price = form.querySelector('[name="price"]').value;
    const duration = form.querySelector('[name="duration"]').value;

    if (!name || !price || !duration) {
        alert('Please fill in all required fields');
        return false;
    }

    if (isNaN(price) || price <= 0) {
        alert('Please enter a valid price');
        return false;
    }

    if (isNaN(duration) || duration < 15) {
        alert('Duration must be at least 15 minutes');
        return false;
    }

    return true;
}

function filterServices(category) {
    window.location.href = 'staff-services?action=filter&category=' + encodeURIComponent(category);
}

// Add function to show/hide debug info
function toggleDebugInfo() {
    const debugInfo = document.querySelector('.debug-info');
    debugInfo.style.display = debugInfo.style.display === 'none' ? 'block' : 'none';
} 