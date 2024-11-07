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
                <a href="services.jsp">
                    <i class="fas fa-spa"></i>
                    <span>Services</span>
                </a>
            </li>
            <li>
                <a href="therapists.jsp">
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
                <a href="inventory.jsp">
                    <i class="fas fa-pump-soap"></i>
                    <span>Inventory</span>
                </a>
            </li>
            <li>
                <a href="packages.jsp">
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

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const currentPath = window.location.pathname.split("/").pop();
        const sidebarLinks = document.querySelectorAll(".sidebar nav ul li a");

        // Smoothly add 'active' to the matching link and remove from others
        sidebarLinks.forEach(link => {
            const linkPath = link.getAttribute("href");

            if (linkPath === currentPath) {
                link.classList.add("active");
            } else {
                link.classList.remove("active");
            }
        });
    });
</script>
