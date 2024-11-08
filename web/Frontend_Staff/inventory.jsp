<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.Account" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // No need to declare session manually; it's already available in JSP
    // You can directly use session
    if (session == null || session.getAttribute("account") == null) {
        // Redirect to login page if session is not found or account is not in session
        response.sendRedirect("../adminLogin");
    } else {
        // Get the account object from session
        Account account = (Account) session.getAttribute("account");

        if (account.getRole() == 3) {
            // Allow access to the page (do nothing and let the JSP render)
        } else {
            // Set an error message and redirect to an error page
            request.setAttribute("errorMessage", "You do not have the required permissions to access the dashboard.");
            request.getRequestDispatcher("error").forward(request, response);
        }
    }
%>
<!DOCTYPE html>
<link rel="stylesheet" href="/SmartSpa1.0/Frontend_Staff/styles.css">
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
                    <div class="header-content">
                        <h2>Spa Inventory Management</h2>
                        <!-- Filter buttons (optional) -->
                        <div class="inventory-filter">
                            <button class="filter-btn active" data-category="all">All Items</button>
                            <c:forEach var="category" items="${productCategories}">
                                <button class="filter-btn" data-category="${category.id}">${category.name}</button>
                            </c:forEach>
                            <button class="filter-btn all-materials" data-category="materials">Materials</button>
                            <button class="filter-btn low-stock" data-category="low-stock">Low Stock</button>
                        </div>

                    </div>


                    <div class="inventory-grid">

                        <div class="inventory-list">

                            <div class="inventory-category">
                                <div class="category-header">
                                    <h3>Products</h3>
                                </div>
                                <div class="inventory-cards">
                                    <c:forEach var="product" items="${products}">
                                        <div class="inventory-card">
                                            <div class="inventory-header">
                                                <img src="${product.image}" alt="${product.name}" class="item-image">
                                            </div>
                                            <div class="inventory-info">
                                                <h4>${product.name}</h4>
                                                <div class="item-details">
                                                    <span><i class="fas fa-dollar-sign"></i> Price: $${product.price}</span>
                                                    <span><i class="fas fa-tag"></i> Category: ${product.category}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>

                                </div>
                            </div>

                            <!-- Display Materials -->
                            <div class="inventory-category">
                                <div class="category-header">
                                    <h3>Materials</h3>
                                </div>
                                <div class="inventory-cards">
                                    <c:forEach var="material" items="${materialCategories}">
                                        <div class="inventory-card">
                                            <div class="inventory-header">
                                                <img src="${material.image}" alt="${material.name}" class="item-image">
                                            </div>
                                            <div class="inventory-info">
                                                <h4>${material.name}</h4>
                                                <div class="item-details">
                                                    <span><i class="fas fa-dollar-sign"></i> Price: $${material.price}</span>

                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>


                        <div class="summary-column">

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

    <script>
        // Function to update active button
        function updateActiveButton(selectedButton) {
            document.querySelectorAll(".filter-btn").forEach(button => {
                button.classList.remove("active");
            });
            selectedButton.classList.add("active");
        }

// Add event listeners to filter buttons
        document.querySelectorAll('.filter-btn').forEach(button => {
            button.addEventListener('click', function () {
                const categoryId = this.getAttribute('data-category');
                // Check if categoryId is defined and append it to the URL correctly
                if (categoryId) {
                    window.location.href = `inventoryservlet?categoryID=${categoryId}`;
                }
            });
        });

// Function to load items based on the selected category
        function loadItemsByCategory(category) {
            document.querySelector(".inventory-grid").innerHTML = ""; // Clear current items

            // Define URL for fetching data based on category
            let url = `/productinformation?category=${category}`;

            fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        const inventoryGrid = document.querySelector(".inventory-grid");
                        data.forEach(item => {
                            const itemElement = document.createElement("div");
                            itemElement.classList.add("inventory-card");
                            itemElement.innerHTML = `
                    <div class="inventory-header">
                        <img src="${item.imagePath}" alt="${item.name}" class="item-image">
                    </div>
                    <div class="inventory-info">
                        <h4>${item.name}</h4>
                        <span><i class="fas fa-boxes"></i> Current Stock: ${item.currentStock} units</span>
                    </div>
                `;
                            inventoryGrid.appendChild(itemElement);
                        });
                    })
                    .catch(error => console.error("Error loading items:", error));
        }

        // Xử lý sự kiện cho nút "All Items"
        document.querySelector(".filter-btn.active").addEventListener("click", function () {
            updateActiveButton(this);  // Thêm class active
            document.querySelector(".inventory-grid").innerHTML = ""; // Xóa nội dung hiện tại
            loadAllItems();  // Gọi hàm để tải tất cả các item
        });

        // Xử lý sự kiện cho nút "Materials"
        document.querySelector(".all-materials").addEventListener("click", function () {
            updateActiveButton(this);  // Thêm class active
            document.querySelector(".inventory-grid").innerHTML = ""; // Xóa nội dung hiện tại
            loadAllMaterials();  // Gọi hàm để tải tất cả nguyên liệu
        });

        // Xử lý sự kiện cho nút "Low Stock"
        document.querySelector(".low-stock").addEventListener("click", function () {
            updateActiveButton(this);  // Thêm class active
            document.querySelector(".inventory-grid").innerHTML = ""; // Xóa nội dung hiện tại
            loadLowStockItems();  // Gọi hàm để tải các item có số lượng tồn kho thấp
        });

        // Hàm tải tất cả các items (có thể bao gồm cả sản phẩm và nguyên liệu)
        function loadAllItems() {
            // Lấy tất cả sản phẩm
            fetch("/productinformation")
                    .then(response => response.json())
                    .then(data => {
                        const inventoryGrid = document.querySelector(".inventory-grid");
                        data.forEach(item => {
                            const itemElement = document.createElement("div");
                            itemElement.classList.add("inventory-card");
                            itemElement.innerHTML = `
                      <div class="inventory-header">
                          <img src="${item.imagePath}" alt="${item.name}" class="item-image">
                      </div>
                      <div class="inventory-info">
                          <h4>${item.name}</h4>
                          <span><i class="fas fa-boxes"></i> Current Stock: ${item.currentStock} units</span>
                      </div>
                  `;
                            inventoryGrid.appendChild(itemElement);
                        });
                    })
                    .catch(error => console.error("Error loading products:", error));

            // Load nguyên liệu (material)
            loadAllMaterials();
        }

        // Hàm tải tất cả nguyên liệu
        function loadAllMaterials() {
            fetch("/materialinformation")
                    .then(response => response.json())
                    .then(data => {
                        const inventoryGrid = document.querySelector(".inventory-grid");
                        data.forEach(item => {
                            const itemElement = document.createElement("div");
                            itemElement.classList.add("inventory-card");
                            itemElement.innerHTML = `
                      <div class="inventory-header">
                          <img src="${item.imagePath}" alt="${item.name}" class="item-image">
                      </div>
                      <div class="inventory-info">
                          <h4>${item.name}</h4>
                          <span><i class="fas fa-boxes"></i> Current Stock: ${item.currentStock} units</span>
                      </div>
                  `;
                            inventoryGrid.appendChild(itemElement);
                        });
                    })
                    .catch(error => console.error("Error loading materials:", error));
        }

        // Hàm tải các item có số lượng tồn kho thấp
        function loadLowStockItems() {
            fetch("/getLowStockItems") // Thay đổi URL theo yêu cầu của bạn
                    .then(response => response.json())
                    .then(data => {
                        const inventoryGrid = document.querySelector(".inventory-grid");
                        data.forEach(item => {
                            const itemElement = document.createElement("div");
                            itemElement.classList.add("inventory-card");
                            itemElement.innerHTML = `
                      <div class="inventory-header">
                          <img src="${item.imagePath}" alt="${item.name}" class="item-image">
                      </div>
                      <div class="inventory-info">
                          <h4>${item.name}</h4>
                          <span><i class="fas fa-boxes"></i> Current Stock: ${item.currentStock} units</span>
                      </div>
                  `;
                            inventoryGrid.appendChild(itemElement);
                        });
                    })
                    .catch(error => console.error("Error loading low stock items:", error));
        }

    </script>

</html>