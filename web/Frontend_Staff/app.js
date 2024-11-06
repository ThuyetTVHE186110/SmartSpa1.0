// DOM Elements
const sections = document.querySelectorAll('section');
const navLinks = document.querySelectorAll('nav a');
const searchInput = document.querySelector('.search-bar input');
const userProfile = document.querySelector('.user-profile');
const notifications = document.querySelector('.notifications');

// Navigation Handler
document.querySelector('nav').addEventListener('click', async (e) => {
    if (e.target.closest('a')) {
        e.preventDefault();
        const link = e.target.closest('a');
        const targetId = link.getAttribute('href').substring(1);

        try {
            // Load the page content
            const response = await fetch(`/pages/${targetId}.html`);
            const content = await response.text();

            // Update the main content area
            const mainContent = document.querySelector('main');

            // Add breadcrumb if it doesn't exist
            let breadcrumb = mainContent.querySelector('.breadcrumb');
            if (!breadcrumb) {
                breadcrumb = document.createElement('div');
                breadcrumb.className = 'breadcrumb';
                breadcrumb.innerHTML = `
                    <i class="fas fa-home"></i>
                    <span>Dashboard</span>
                `;
                mainContent.insertBefore(breadcrumb, mainContent.firstChild);
            }

            mainContent.innerHTML = content;

            // Update active nav link
            navLinks.forEach(navLink => navLink.classList.remove('active'));
            link.classList.add('active');

            // Update breadcrumb
            updateBreadcrumb(targetId);

            // Load and execute page-specific JavaScript
            loadPageScript(targetId);

        } catch (error) {
            console.error('Error loading page:', error);
        }
    }
});

// Function to load page-specific JavaScript
function loadPageScript(pageId) {
    const script = document.createElement('script');
    script.src = `/js/pages/${pageId}.js`;
    script.type = 'module';
    document.body.appendChild(script);
}

// Update breadcrumb text and icon
function updateBreadcrumb(sectionId) {
    const breadcrumb = document.querySelector('.breadcrumb');
    if (!breadcrumb) return;

    const icons = {
        dashboard: 'home',
        appointments: 'calendar-check',
        services: 'spa',
        therapists: 'user-md',
        rooms: 'door-open',
        clients: 'users',
        inventory: 'pump-soap',
        packages: 'gift',
        feedback: 'comments',
        reports: 'chart-bar'
    };

    breadcrumb.innerHTML = `
        <i class="fas fa-${icons[sectionId] || 'home'}"></i>
        <span>${sectionId.charAt(0).toUpperCase() + sectionId.slice(1)}</span>
    `;
}

// Search bar interaction
searchInput?.addEventListener('input', debounce((e) => {
    // Add visual feedback for search
    const searchBar = searchInput.closest('.search-bar');
    if (e.target.value) {
        searchBar.classList.add('searching');
    } else {
        searchBar.classList.remove('searching');
    }
}, 300));

// User profile dropdown toggle
userProfile?.addEventListener('click', () => {
    userProfile.classList.toggle('active');
});

// Notifications panel toggle
notifications?.addEventListener('click', () => {
    notifications.classList.toggle('active');
});

// Close dropdowns when clicking outside
document.addEventListener('click', (e) => {
    if (!userProfile?.contains(e.target)) {
        userProfile?.classList.remove('active');
    }
    if (!notifications?.contains(e.target)) {
        notifications?.classList.remove('active');
    }
});

// Hover effects for cards
document.querySelectorAll('.card').forEach(card => {
    card.addEventListener('mouseenter', () => {
        card.style.transform = 'translateY(-5px)';
    });

    card.addEventListener('mouseleave', () => {
        card.style.transform = 'translateY(0)';
    });
});

// Utility - Debounce Function
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Initialize Application
document.addEventListener('DOMContentLoaded', async () => {
    // Load dashboard content by default
    try {
        const response = await fetch('/pages/dashboard.html');
        const content = await response.text();
        document.querySelector('main').innerHTML = content;
    } catch (error) {
        console.error('Error loading dashboard:', error);
    }

    // Set home link as active
    document.querySelector('nav a[href="#dashboard"]').classList.add('active');
}); 