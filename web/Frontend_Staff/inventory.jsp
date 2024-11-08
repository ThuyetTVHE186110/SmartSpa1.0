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
                            <button class="filter-btn active">All Items</button>
                            <c:forEach var="category" items="${productCategories}">
                                <button class="filter-btn">${category.name}</button>
                            </c:forEach>
                            <button class="filter-btn all-materials">Materials</button>
                            <button class="filter-btn low-stock">Low Stock</button>
                        </div>
                    </div>

                    <div class="inventory-grid">
                        <!-- Inventory List -->
                        <div class="inventory-list">
                            <!-- Display Products -->
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
                    </div>
                </main>
            </div>
        </div>
    </body>

    <script>
        // Hàm để thay đổi class active cho các nút filter
        function updateActiveButton(selectedButton) {
            // Lấy tất cả các button filter
            const buttons = document.querySelectorAll(".filter-btn");
            buttons.forEach(button => {
                // Xóa class active khỏi tất cả các button
                button.classList.remove("active");
            });
            // Thêm class active vào button được chọn
            selectedButton.classList.add("active");
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