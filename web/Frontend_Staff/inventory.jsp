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
                            <h2>Spa Inventory Management</h2>
                            <div class="inventory-filter">
                                <button class="filter-btn active">All Items</button>
                                <button class="filter-btn">Massage Oils</button>
                                <button class="filter-btn">Essential Oils</button>
                                <button class="filter-btn">Skincare</button>
                                <button class="filter-btn">Supplies</button>
                                <button class="filter-btn low-stock">Low Stock</button>
                            </div>
                        </div>
                        <button class="btn-primary" id="newItemBtn">
                            <i class="fas fa-plus"></i> Add New Item
                        </button>
                    </div>

                    <div class="inventory-grid">
                        <!-- Inventory List -->
                        <div class="inventory-list">
                            <!-- Massage Oils Category -->
                            <div class="inventory-category">
                                <div class="category-header">
                                    <h3>Massage Oils</h3>
                                    <button class="btn-text">Manage Category</button>
                                </div>
                                <div class="inventory-cards">
                                    <!-- Regular Stock Item -->
                                    <div class="inventory-card">
                                        <div class="inventory-header">
                                            <img src="lavender-oil.jpg" alt="Lavender Massage Oil" class="item-image">
                                            <div class="stock-badge sufficient">In Stock</div>
                                        </div>
                                        <div class="inventory-info">
                                            <h4>Lavender Massage Oil</h4>
                                            <div class="item-details">
                                                <span><i class="fas fa-boxes"></i> Current Stock: 45 units</span>
                                                <span><i class="fas fa-exclamation-triangle"></i> Min Stock: 20 units</span>
                                                <span><i class="fas fa-dollar-sign"></i> Unit Cost: $15.00</span>
                                            </div>
                                            <div class="stock-progress">
                                                <div class="progress-bar" style="width: 75%"></div>
                                                <span>75% of max stock</span>
                                            </div>
                                            <div class="usage-info">
                                                <p><i class="fas fa-chart-line"></i> Monthly Usage: ~30 units</p>
                                                <p><i class="fas fa-clock"></i> Last Restocked: 2 weeks ago</p>
                                                <p><i class="fas fa-shopping-cart"></i> Next Order Due: In 3 weeks</p>
                                            </div>
                                        </div>
                                        <div class="inventory-actions">
                                            <button class="btn-icon" title="Restock"><i class="fas fa-plus-circle"></i></button>
                                            <button class="btn-icon" title="Adjust Stock"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="View History"><i class="fas fa-history"></i></button>
                                            <button class="btn-icon" title="Set Alerts"><i class="fas fa-bell"></i></button>
                                        </div>
                                    </div>

                                    <!-- Low Stock Item -->
                                    <div class="inventory-card low-stock">
                                        <div class="inventory-header">
                                            <img src="almond-oil.jpg" alt="Almond Massage Oil" class="item-image">
                                            <div class="stock-badge warning">Low Stock</div>
                                        </div>
                                        <div class="inventory-info">
                                            <h4>Almond Massage Oil</h4>
                                            <div class="item-details">
                                                <span><i class="fas fa-boxes"></i> Current Stock: 12 units</span>
                                                <span><i class="fas fa-exclamation-triangle"></i> Min Stock: 15 units</span>
                                                <span><i class="fas fa-dollar-sign"></i> Unit Cost: $18.00</span>
                                            </div>
                                            <div class="stock-progress low">
                                                <div class="progress-bar" style="width: 25%"></div>
                                                <span>25% of max stock</span>
                                            </div>
                                            <div class="usage-info">
                                                <p><i class="fas fa-chart-line"></i> Monthly Usage: ~25 units</p>
                                                <p><i class="fas fa-clock"></i> Last Restocked: 1 month ago</p>
                                                <p><i class="fas fa-shopping-cart"></i> Reorder Required!</p>
                                            </div>
                                        </div>
                                        <div class="inventory-actions">
                                            <button class="btn-icon urgent" title="Restock Now"><i class="fas fa-plus-circle"></i></button>
                                            <button class="btn-icon" title="Adjust Stock"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="View History"><i class="fas fa-history"></i></button>
                                            <button class="btn-icon" title="Set Alerts"><i class="fas fa-bell"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Essential Oils Category -->
                            <div class="inventory-category">
                                <div class="category-header">
                                    <h3>Essential Oils</h3>
                                    <button class="btn-text">Manage Category</button>
                                </div>
                                <div class="inventory-cards">
                                    <div class="inventory-card">
                                        <div class="inventory-header">
                                            <img src="eucalyptus-oil.jpg" alt="Eucalyptus Essential Oil" class="item-image">
                                            <div class="stock-badge sufficient">In Stock</div>
                                        </div>
                                        <div class="inventory-info">
                                            <h4>Eucalyptus Essential Oil</h4>
                                            <div class="item-details">
                                                <span><i class="fas fa-boxes"></i> Current Stock: 28 units</span>
                                                <span><i class="fas fa-exclamation-triangle"></i> Min Stock: 10 units</span>
                                                <span><i class="fas fa-dollar-sign"></i> Unit Cost: $22.00</span>
                                            </div>
                                            <div class="stock-progress">
                                                <div class="progress-bar" style="width: 65%"></div>
                                                <span>65% of max stock</span>
                                            </div>
                                            <div class="usage-info">
                                                <p><i class="fas fa-chart-line"></i> Monthly Usage: ~15 units</p>
                                                <p><i class="fas fa-clock"></i> Last Restocked: 1 week ago</p>
                                                <p><i class="fas fa-shopping-cart"></i> Next Order Due: In 6 weeks</p>
                                            </div>
                                        </div>
                                        <div class="inventory-actions">
                                            <button class="btn-icon" title="Restock"><i class="fas fa-plus-circle"></i></button>
                                            <button class="btn-icon" title="Adjust Stock"><i class="fas fa-edit"></i></button>
                                            <button class="btn-icon" title="View History"><i class="fas fa-history"></i></button>
                                            <button class="btn-icon" title="Set Alerts"><i class="fas fa-bell"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Summary Column -->
                        <div class="summary-column">
                            <!-- Inventory Overview -->
                            <div class="summary-card">
                                <h3>Inventory Overview</h3>
                                <div class="summary-stats">
                                    <div class="stat">
                                        <span class="label">Total Items</span>
                                        <span class="value">156</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Categories</span>
                                        <span class="value">8</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">Low Stock</span>
                                        <span class="value">3</span>
                                    </div>
                                    <div class="stat">
                                        <span class="label">To Order</span>
                                        <span class="value">5</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Recent Activity -->
                            <div class="summary-card">
                                <h3>Recent Activity</h3>
                                <div class="activity-list">
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-box"></i></div>
                                        <div class="activity-details">
                                            <h4>Stock Received</h4>
                                            <p>50 units of Lavender Oil added</p>
                                            <span class="activity-time">2 hours ago</span>
                                        </div>
                                    </div>
                                    <div class="activity-item">
                                        <div class="activity-icon warning"><i class="fas fa-exclamation-circle"></i></div>
                                        <div class="activity-details">
                                            <h4>Low Stock Alert</h4>
                                            <p>Almond Oil below minimum level</p>
                                            <span class="activity-time">1 day ago</span>
                                        </div>
                                    </div>
                                    <div class="activity-item">
                                        <div class="activity-icon"><i class="fas fa-sync"></i></div>
                                        <div class="activity-details">
                                            <h4>Stock Updated</h4>
                                            <p>Monthly inventory check completed</p>
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
                                        <i class="fas fa-plus-circle"></i>
                                        New Item
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-file-invoice"></i>
                                        Create Order
                                    </button>
                                    <button class="action-btn">
                                        <i class="fas fa-qrcode"></i>
                                        Scan Items
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
    </body>
</html>