<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>  
<%@ page import="model.Person" %>
<style>
    /* Container for user menu */
    .user-menu {
        display: inline-flex;
        align-items: center;
        position: relative;
        cursor: pointer;
    }

    /* Avatar styling */
    .user-avatar {
        width: 40px;
        height: 40px;
        object-fit: cover;
        border-radius: 50%;
        margin-right: 8px;
    }

    /* Name and icon alignment */
    .user-name, .dropdown-icon {
        font-size: 14px;
        color: #333;
    }

    /* Dropdown icon margin */
    .dropdown-icon {
        margin-left: 6px;
    }

    /* Dropdown content styling */
    .dropdown-content {
        display: none;  /* Hidden by default */
        position: absolute;
        top: 100%; /* Position below the user menu */
        right: 0;
        background-color: #ffffff;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
        z-index: 1;
        min-width: 150px;
        padding: 10px 0;
    }

    /* Links inside the dropdown */
    .dropdown-content a {
        display: block;
        padding: 10px 15px;
        color: #333;
        text-decoration: none;
        font-size: 14px;
    }

    /* Hover effect for links */
    .dropdown-content a:hover {
        background-color: #f5f5f5;
    }

    /* Show dropdown when user-menu is hovered */
    .user-menu:hover .dropdown-content {
        display: block;
    }

</style>
<header class="top-bar">
    <div class="search-bar">
        <i class="fas fa-search"></i>
        <input type="text" placeholder="Search...">
    </div>

    <div class="user-menu">
        <div class="notifications">
            <i class="fas fa-bell"></i>
            <span class="badge">3</span> <!-- Notification count -->
        </div>

        <div class="user-profile">
            <%
                Account account = (Account) session.getAttribute("account");
                Person person = null;
                String displayName = "Guest";
                String avatarUrl = "img/default-avatar.jpg"; // Default avatar

                if (account != null) {
                    person = account.getPersonInfo();
                    displayName = (person != null) ? person.getName() : "Guest";
                    avatarUrl = (person != null && person.getImage() != null && !person.getImage().isEmpty())
                            ? "img/" + person.getImage()
                            : avatarUrl;
                }
            %>

            <div class="user-menu">
                 <img src="<%= (person != null && person.getImage() != null && !person.getImage().isEmpty())
                        ? " img/" + person.getImage() : "img/default-avartar.jpg"%>" alt="Profile
                 Picture" class="rounded-circle" style="width: 20px; height:
                 20px; object-fit: cover;">
                <span class="user-name"><%= displayName%></span>
                <i class="fas fa-chevron-down dropdown-icon"></i>

                <div class="dropdown-content">
                    <a href="staffProfile">View Profile</a>
                    <a href="userProfile">Update Profile</a>
                    <a href="LogoutServlet" class="book-now-btn nav-item nav-link">Logout</a>
                </div>
            </div>


        </div>
    </div>
</header>
