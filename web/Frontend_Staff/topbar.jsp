<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>  
<%@ page import="model.Person" %>

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
            
            <img src="<%= avatarUrl %>" alt="User Avatar" style="width: 40px; height: 40px; object-fit: cover;" class="rounded-circle">
            <span><%= displayName %></span>
            <i class="fas fa-chevron-down"></i>
        </div>
    </div>
</header>
