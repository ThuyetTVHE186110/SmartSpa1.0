<aside class="sidebar">
    <div class="sidebar-header">
        <img src="../img/logo.png" alt="Company Logo" class="logo">
        <h1>Staff Portal</h1>
    </div>
    <nav>
        <ul>
            <li>
                <a href="./index.jsp">
                    <i class="fas fa-home"></i>
                    <span>Dashboard</span>
                </a>
            </li>
            <li>
                <a href="./appointments.jsp">
                    <i class="fas fa-calendar-check"></i>
                    <span>Appointments</span>
                </a>
            </li>
            <li>
                <a href="staff-services">
                    <i class="fas fa-spa"></i>
                    <span>Services</span>
                </a>
            </li>
            <li class="has-submenu">
                <a href="#" class="submenu-toggle">
                    <i class="fas fa-file-invoice-dollar"></i>
                    <span>Billing</span>
                    <i class="fas fa-chevron-down submenu-arrow"></i>
                </a>
                <ul class="submenu">
                    <li>
                        <a href="${pageContext.request.contextPath}/staff/createBill">
                            <i class="fas fa-plus-circle"></i>
                            <span>Create Bill</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/staff/billHistory">
                            <i class="fas fa-history"></i>
                            <span>Bill History</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="therapist">
                    <i class="fas fa-user-md"></i>
                    <span>Therapists</span>
                </a>
            </li>
            <li>
                <a href="rooms.jsp">
                    <i class="fas fa-door-open"></i>
                    <span>Treatment Rooms</span>
                </a>
            </li>
            <li>
                <a href="clientManagement">
                    <i class="fas fa-users"></i>
                    <span>Clients</span>
                </a>
            </li>
            <li>
                <a href="../inventoryservlet">
                    <i class="fas fa-pump-soap"></i>
                    <span>Inventory</span>
                </a>
            </li>
            <li>
                <a href="staff-packages">
                    <i class="fas fa-gift"></i>
                    <span>Packages & Promos</span>
                </a>
            </li>
            <li>
                <a href="feedback.jsp">
                    <i class="fas fa-comments"></i>
                    <span>Feedback</span>
                </a>
            </li>
            <li>
                <a href="reports.jsp">
                    <i class="fas fa-chart-bar"></i>
                    <span>Reports</span>
                </a>
            </li>
        </ul>
    </nav>
</aside>

<script src="${pageContext.request.contextPath}/Frontend_Staff/js/sidebar.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const currentPath = window.location.pathname.split("/").pop();
        const sidebarLinks = document.querySelectorAll(".sidebar nav ul li a");
        const submenuToggles = document.querySelectorAll(".submenu-toggle");

        // Handle submenu toggles
        submenuToggles.forEach(toggle => {
            toggle.addEventListener("click", function (e) {
                e.preventDefault();
                const parent = this.closest(".has-submenu");
                parent.classList.toggle("active");

                // Close other open submenus
                document.querySelectorAll(".has-submenu").forEach(item => {
                    if (item !== parent) {
                        item.classList.remove("active");
                    }
                });
            });
        });

        // Set active states
        sidebarLinks.forEach(link => {
            const linkPath = link.getAttribute("href");

            if (linkPath === currentPath) {
                link.classList.add("active");
                // If this is a submenu item, open its parent
                const submenuParent = link.closest(".has-submenu");
                if (submenuParent) {
                    submenuParent.classList.add("active");
                }
            } else {
                link.classList.remove("active");
            }
        });

        // Keep submenu open if child is active
        document.querySelectorAll(".submenu a").forEach(submenuLink => {
            if (submenuLink.classList.contains("active")) {
                submenuLink.closest(".has-submenu").classList.add("active");
            }
        });
    });
</script>

<!-- Add this at the bottom of your sideBar.jsp, after the existing script tag -->
<script src="${pageContext.request.contextPath}/Frontend_Staff/js/sidebar.js"></script>