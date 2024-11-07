class ServicesPage {
    constructor () {
        this.initializeModals();
        this.initializeEventListeners();
        this.mockData = {
            services: [
                {
                    id: 1,
                    name: "Swedish Massage",
                    category: "massage",
                    duration: 60,
                    regularPrice: 85,
                    specialPrice: null,
                    description: "Classic relaxation massage using long, flowing strokes to reduce tension and promote wellness.",
                    therapists: 4,
                    popularity: "Popular",
                    requirements: {
                        certification: true,
                        equipment: false,
                        room: false
                    },
                    skills: ["swedish", "relaxation"],
                    products: ["Massage Oil", "Essential Oils"]
                },
                {
                    id: 2,
                    name: "Deep Tissue Massage",
                    category: "massage",
                    duration: 90,
                    regularPrice: 120,
                    specialPrice: null,
                    description: "Therapeutic massage targeting deep muscle layers to release chronic tension.",
                    therapists: 3,
                    popularity: "Best Seller",
                    requirements: {
                        certification: true,
                        equipment: false,
                        room: false
                    },
                    skills: ["deep tissue", "therapeutic"],
                    products: ["Deep Tissue Oil"]
                }
            ]
        };
    }

    initializeModals() {
        // Initialize modal elements
        this.serviceModal = document.getElementById('serviceModal');
        this.editServiceModal = document.getElementById('editServiceModal');
        this.serviceDetailsModal = document.getElementById('serviceDetailsModal');

        // Close buttons
        document.querySelectorAll('.close-modal').forEach(button => {
            button.addEventListener('click', () => this.closeAllModals());
        });

        // Close on outside click
        window.addEventListener('click', (e) => {
            if (e.target.classList.contains('modal')) {
                this.closeAllModals();
            }
        });
    }

    initializeEventListeners() {
        // New Service Button
        document.getElementById('newServiceBtn')?.addEventListener('click', () => {
            this.openNewServiceModal();
        });

        // Filter Buttons
        document.querySelectorAll('.filter-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                this.filterServices(e.target.textContent.toLowerCase());
            });
        });

        // Save Service Button
        document.getElementById('saveService')?.addEventListener('click', () => {
            this.saveNewService();
        });

        // Tag Input Handler
        const tagInput = document.querySelector('.tag-input input');
        if (tagInput) {
            tagInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    this.addTag(e.target.value);
                    e.target.value = '';
                }
            });
        }

        // Tag Remove Buttons
        document.querySelectorAll('.tag i').forEach(icon => {
            icon.addEventListener('click', (e) => {
                e.target.closest('.tag').remove();
            });
        });
    }

    openNewServiceModal() {
        this.closeAllModals();
        this.serviceModal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }

    openEditServiceModal(serviceId) {
        this.closeAllModals();
        this.editServiceModal.classList.add('active');
        const service = this.mockData.services.find(s => s.id === serviceId);
        if (service) {
            this.populateEditForm(service);
        }
        document.body.style.overflow = 'hidden';
    }

    openServiceDetailsModal(serviceId) {
        this.closeAllModals();
        this.serviceDetailsModal.classList.add('active');
        const service = this.mockData.services.find(s => s.id === serviceId);
        if (service) {
            this.populateServiceDetails(service);
        }
        document.body.style.overflow = 'hidden';
    }

    closeAllModals() {
        document.querySelectorAll('.modal').forEach(modal => {
            modal.classList.remove('active');
        });
        document.body.style.overflow = 'auto';
    }

    saveNewService() {
        const form = document.getElementById('serviceForm');
        if (form.checkValidity()) {
            // Collect form data
            const formData = new FormData(form);
            const serviceData = {
                name: formData.get('serviceName'),
                category: formData.get('category'),
                duration: formData.get('duration'),
                regularPrice: formData.get('regularPrice'),
                specialPrice: formData.get('specialPrice') || null,
                description: formData.get('description'),
                requirements: {
                    certification: formData.get('certification') === 'on',
                    equipment: formData.get('equipment') === 'on',
                    room: formData.get('room') === 'on'
                }
            };

            console.log('Saving new service:', serviceData);
            this.closeAllModals();
            // Refresh service list
            this.loadServices();
        } else {
            form.reportValidity();
        }
    }

    addTag(tagText) {
        if (!tagText.trim()) return;

        const tagsContainer = document.querySelector('.tags');
        const tag = document.createElement('span');
        tag.className = 'tag';
        tag.innerHTML = `${tagText}<i class="fas fa-times"></i>`;

        tag.querySelector('i').addEventListener('click', () => tag.remove());
        tagsContainer.appendChild(tag);
    }

    populateEditForm(service) {
        const form = document.querySelector('#editServiceModal form');
        if (form) {
            // Set form values based on service data
            form.querySelector('[name="serviceName"]').value = service.name;
            form.querySelector('[name="category"]').value = service.category;
            form.querySelector('[name="duration"]').value = service.duration;
            form.querySelector('[name="regularPrice"]').value = service.regularPrice;
            if (service.specialPrice) {
                form.querySelector('[name="specialPrice"]').value = service.specialPrice;
            }
            form.querySelector('[name="description"]').value = service.description;

            // Set checkboxes
            Object.entries(service.requirements).forEach(([key, value]) => {
                const checkbox = form.querySelector(`[name="${key}"]`);
                if (checkbox) checkbox.checked = value;
            });

            // Set tags
            const tagsContainer = form.querySelector('.tags');
            tagsContainer.innerHTML = '';
            service.products.forEach(product => this.addTag(product));
        }
    }

    populateServiceDetails(service) {
        const detailsContainer = document.querySelector('.service-details');
        if (detailsContainer) {
            // Update service details in the modal
            detailsContainer.querySelector('.service-image img').src = `service-${service.id}.jpg`;
            detailsContainer.querySelector('[data-field="category"]').textContent = service.category;
            detailsContainer.querySelector('[data-field="duration"]').textContent = `${service.duration} minutes`;
            detailsContainer.querySelector('[data-field="price"]').textContent = `$${service.regularPrice}`;
        }
    }

    filterServices(category) {
        console.log(`Filtering services by: ${category}`);
        // Implement filtering logic here
        this.loadServices(category);
    }

    loadServices(category = 'all') {
        // Filter services based on category
        let filteredServices = this.mockData.services;
        if (category !== 'all') {
            filteredServices = this.mockData.services.filter(service =>
                service.category.toLowerCase() === category
            );
        }

        // Render filtered services
        this.renderServices(filteredServices);
    }

    renderServices(services) {
        const servicesList = document.querySelector('.service-cards');
        if (servicesList) {
            servicesList.innerHTML = services.map(service => this.createServiceCard(service)).join('');
        }
    }

    createServiceCard(service) {
        return `
            <div class="service-card">
                <div class="service-header">
                    <h4>${service.name}</h4>
                    <span class="service-price">$${service.regularPrice}</span>
                </div>
                <p class="service-duration"><i class="fas fa-clock"></i> ${service.duration} minutes</p>
                <p class="service-description">${service.description}</p>
                <div class="service-meta">
                    <span><i class="fas fa-user-md"></i> ${service.therapists} Therapists</span>
                    <span><i class="fas fa-chart-line"></i> ${service.popularity}</span>
                </div>
                <div class="service-actions">
                    <button class="btn-icon" onclick="servicesPage.openServiceDetailsModal(${service.id})" title="View Details">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn-icon" onclick="servicesPage.openEditServiceModal(${service.id})" title="Edit">
                        <i class="fas fa-edit"></i>
                    </button>
                </div>
            </div>
        `;
    }
}

// Initialize the page
const servicesPage = new ServicesPage(); 