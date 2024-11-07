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
                            <h2>Services Management</h2>
                            <div class="service-filter">
                                <button class="filter-btn active">All Services</button>
                                <button class="filter-btn">Massage</button>
                                <button class="filter-btn">Facial</button>
                                <button class="filter-btn">Body Treatment</button>
                            </div>
                        </div>
                        <button class="btn-primary" onclick="openModal('serviceModal')" id="newServiceBtn">
                            <i class="fas fa-plus"></i> Add New Service
                        </button>
                    </div>

                    <div class="services-grid">
                        <!-- Services List Column -->
                        <div class="services-list">
                            <!-- Massage Services -->
                            <div class="service-category">
                                <h3>Massage Treatments</h3>
                                <div class="service-cards">
                                    <div class="service-card">
                                        <div class="service-header">
                                            <h4>Swedish Massage</h4>
                                            <span class="service-price">$85</span>
                                        </div>
                                        <p class="service-duration"><i class="fas fa-clock"></i> 60 minutes</p>
                                        <p class="service-description">
                                            Classic relaxation massage using long, flowing strokes to reduce tension and promote wellness.
                                        </p>
                                        <div class="service-meta">
                                            <span><i class="fas fa-user-md"></i> 4 Therapists</span>
                                            <span><i class="fas fa-chart-line"></i> Popular</span>
                                        </div>
                                        <div class="service-actions">
                                            <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="Delete"><i class="fas fa-trash"></i></button>
                                        </div>
                                    </div>

                                    <div class="service-card">
                                        <div class="service-header">
                                            <h4>Deep Tissue Massage</h4>
                                            <span class="service-price">$120</span>
                                        </div>
                                        <p class="service-duration"><i class="fas fa-clock"></i> 90 minutes</p>
                                        <p class="service-description">
                                            Therapeutic massage targeting deep muscle layers to release chronic tension.
                                        </p>
                                        <div class="service-meta">
                                            <span><i class="fas fa-user-md"></i> 3 Therapists</span>
                                            <span><i class="fas fa-star"></i> Best Seller</span>
                                        </div>
                                        <div class="service-actions">
                                            <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="Delete"><i class="fas fa-trash"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Facial Treatments -->
                            <div class="service-category">
                                <h3>Facial Treatments</h3>
                                <div class="service-cards">
                                    <div class="service-card">
                                        <div class="service-header">
                                            <h4>Classic Facial</h4>
                                            <span class="service-price">$95</span>
                                        </div>
                                        <p class="service-duration"><i class="fas fa-clock"></i> 60 minutes</p>
                                        <p class="service-description">
                                            Deep cleansing facial with customized treatment for your skin type.
                                        </p>
                                        <div class="service-meta">
                                            <span><i class="fas fa-user-md"></i> 2 Therapists</span>
                                            <span><i class="fas fa-percentage"></i> On Sale</span>
                                        </div>
                                        <div class="service-actions">
                                            <button class="btn-icon" title="Edit"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="Delete"><i class="fas fa-trash"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Service Stats -->
                            <div class="summary-card">
                                <h3>Service Statistics</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Services</span>
                                        <span class="value">12</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Active</span>
                                        <span class="value">10</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">On Sale</span>
                                        <span class="value">3</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Categories</span>
                                        <span class="value">4</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Popular Services -->
                            <div class="summary-card">
                                <h3>Most Popular Services</h3>
                                <div class="popular-services">
                                    <div class="popular-service-item">
                                        <div class="service-info">
                                            <h4>Swedish Massage</h4>
                                            <p>Booked 48 times this month</p>
                                        </div>
                                        <div class="service-trend up">
                                            <i class="fas fa-arrow-up"></i>
                                            <span>12%</span>
                                        </div>
                                    </div>
                                    <div class="popular-service-item">
                                        <div class="service-info">
                                            <h4>Deep Tissue Massage</h4>
                                            <p>Booked 35 times this month</p>
                                        </div>
                                        <div class="service-trend up">
                                            <i class="fas fa-arrow-up"></i>
                                            <span>8%</span>
                                        </div>
                                    </div>
                                    <div class="popular-service-item">
                                        <div class="service-info">
                                            <h4>Classic Facial</h4>
                                            <p>Booked 27 times this month</p>
                                        </div>
                                        <div class="service-trend down">
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
                                        New Service
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-tags"></i>
                                        Manage Prices
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-percentage"></i>
                                        Special Offers
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-chart-bar"></i>
                                        View Reports
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Add Service Modal -->
                    <div class="modal" id="serviceModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Add New Service</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <form id="serviceForm">
                                    <!-- Basic Information -->
                                    <div class="form-group">
                                        <label>Service Name</label>
                                        <input type="text" placeholder="Enter service name" required>
                                    </div>

                                    <div class="form-row">
                                        <div class="form-group">
                                            <label>Category</label>
                                            <select required>
                                                <option value="">Select Category</option>
                                                <option value="massage">Massage</option>
                                                <option value="facial">Facial</option>
                                                <option value="body">Body Treatment</option>
                                                <option value="spa">Spa Packages</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Duration (minutes)</label>
                                            <select required>
                                                <option value="30">30 minutes</option>
                                                <option value="45">45 minutes</option>
                                                <option value="60">60 minutes</option>
                                                <option value="90">90 minutes</option>
                                                <option value="120">120 minutes</option>
                                            </select>
                                        </div>
                                    </div>

                                    <!-- Pricing -->
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label>Regular Price ($)</label>
                                            <input type="number" min="0" step="0.01" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Special Price ($)</label>
                                            <input type="number" min="0" step="0.01">
                                        </div>
                                    </div>

                                    <!-- Description -->
                                    <div class="form-group">
                                        <label>Description</label>
                                        <textarea rows="3" placeholder="Enter service description" required></textarea>
                                    </div>

                                    <!-- Requirements -->
                                    <div class="form-group">
                                        <label>Requirements</label>
                                        <div class="checkbox-group">
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="certification">
                                                Special Certification Required
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="equipment">
                                                Special Equipment Needed
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="room">
                                                Specific Room Required
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Therapist Skills -->
                                    <div class="form-group">
                                        <label>Required Therapist Skills</label>
                                        <div class="checkbox-group">
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="swedish">
                                                Swedish Massage
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="deep">
                                                Deep Tissue
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="hot">
                                                Hot Stone
                                            </label>
                                            <label class="checkbox-label">
                                                <input type="checkbox" value="facial">
                                                Facial Treatment
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Products Used -->
                                    <div class="form-group">
                                        <label>Products Used</label>
                                        <div class="tag-input">
                                            <input type="text" placeholder="Type and press Enter to add">
                                            <div class="tags">
                                                <span class="tag">Massage Oil<i class="fas fa-times"></i></span>
                                                <span class="tag">Essential Oils<i class="fas fa-times"></i></span>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Service Image -->
                                    <div class="form-group">
                                        <label>Service Image</label>
                                        <div class="file-upload">
                                            <input type="file" id="serviceImage" accept="image/*">
                                            <label for="serviceImage" class="file-upload-label">
                                                <i class="fas fa-cloud-upload-alt"></i>
                                                Choose Image
                                            </label>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="cancelService">Cancel</button>
                                <button class="btn-primary" id="saveService">Save Service</button>
                            </div>
                        </div>
                    </div>

                    <!-- Edit Service Modal -->
                    <div class="modal" id="editServiceModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Edit Service</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <!-- Same form as Add Service with pre-filled values -->
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="cancelEdit">Cancel</button>
                                <button class="btn-primary" id="saveEdit">Save Changes</button>
                            </div>
                        </div>
                    </div>

                    <!-- Service Details Modal -->
                    <div class="modal" id="serviceDetailsModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Service Details</h2>
                                <button class="close-modal"><i class="fas fa-times"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="service-details">
                                    <div class="service-image">
                                        <img src="service-image.jpg" alt="Service Image">
                                    </div>
                                    <div class="detail-section">
                                        <h3>Service Information</h3>
                                        <div class="detail-grid">
                                            <div class="detail-item">
                                                <span class="label">Category:</span>
                                                <span class="value">Massage</span>
                                            </div>
                                            <div class="detail-item">
                                                <span class="label">Duration:</span>
                                                <span class="value">60 minutes</span>
                                            </div>
                                            <div class="detail-item">
                                                <span class="label">Price:</span>
                                                <span class="value">$85.00</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="detail-section">
                                        <h3>Performance Metrics</h3>
                                        <div class="metrics-grid">
                                            <div class="metric-item">
                                                <span class="label">Monthly Bookings</span>
                                                <span class="value">45</span>
                                                <span class="trend up">+12%</span>
                                            </div>
                                            <div class="metric-item">
                                                <span class="label">Revenue</span>
                                                <span class="value">$3,825</span>
                                                <span class="trend up">+8%</span>
                                            </div>
                                            <div class="metric-item">
                                                <span class="label">Client Satisfaction</span>
                                                <span class="value">4.8/5</span>
                                                <span class="trend up">+0.2</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn-secondary" id="closeDetails">Close</button>
                                <button class="btn-primary" id="editService">Edit Service</button>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script src="../js/pages/services.js"></script>
    </body><!-- comment -->
</html>