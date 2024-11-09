document.addEventListener("DOMContentLoaded", function () {
    const currentPath = window.location.pathname.split("/").pop();
    const sidebarLinks = document.querySelectorAll(".sidebar nav ul li a");
    const submenuToggles = document.querySelectorAll(".submenu-toggle");
    const sidebar = document.querySelector(".sidebar");

    // Add hover effect for expandable sidebar
    let sidebarTimeout;

    sidebar.addEventListener("mouseenter", () => {
        clearTimeout(sidebarTimeout);
        sidebar.classList.add("expanded");
    });

    sidebar.addEventListener("mouseleave", () => {
        sidebarTimeout = setTimeout(() => {
            sidebar.classList.remove("expanded");
            // Close all submenus when sidebar collapses
            document.querySelectorAll(".has-submenu").forEach(item => {
                item.classList.remove("active");
            });
        }, 300);
    });

    // Handle submenu toggles with animation
    submenuToggles.forEach(toggle => {
        toggle.addEventListener("click", function (e) {
            e.preventDefault();
            e.stopPropagation(); // Prevent event bubbling

            const parent = this.closest(".has-submenu");
            const submenu = parent.querySelector(".submenu");
            const arrow = parent.querySelector(".submenu-arrow");

            // Toggle active class
            parent.classList.toggle("active");

            // Rotate arrow
            if (parent.classList.contains("active")) {
                arrow.style.transform = "rotate(180deg)";
            } else {
                arrow.style.transform = "rotate(0)";
            }

            // Close other submenus
            document.querySelectorAll(".has-submenu").forEach(item => {
                if (item !== parent && item.classList.contains("active")) {
                    item.classList.remove("active");
                    item.querySelector(".submenu-arrow").style.transform = "rotate(0)";
                }
            });
        });
    });

    // Set active states with animation
    sidebarLinks.forEach(link => {
        const linkPath = link.getAttribute("href");

        if (linkPath === currentPath) {
            link.classList.add("active");
            // If this is a submenu item, open its parent with animation
            const submenuParent = link.closest(".has-submenu");
            if (submenuParent) {
                const submenu = submenuParent.querySelector(".submenu");
                const arrow = submenuParent.querySelector(".submenu-arrow");
                submenuParent.classList.add("active");
                arrow.style.transform = "rotate(180deg)";
                submenu.style.maxHeight = submenu.scrollHeight + "px";
            }
        }

        // Add hover animation for links
        link.addEventListener("mouseenter", () => {
            link.style.transform = "translateX(5px)";
        });

        link.addEventListener("mouseleave", () => {
            link.style.transform = "translateX(0)";
        });
    });

    // Add ripple effect to links
    function createRipple(event) {
        const link = event.currentTarget;
        const ripple = document.createElement("span");
        const rect = link.getBoundingClientRect();

        ripple.className = "ripple";
        ripple.style.left = `${event.clientX - rect.left}px`;
        ripple.style.top = `${event.clientY - rect.top}px`;

        link.appendChild(ripple);

        setTimeout(() => {
            ripple.remove();
        }, 600);
    }

    sidebarLinks.forEach(link => {
        link.addEventListener("click", createRipple);
    });
}); 