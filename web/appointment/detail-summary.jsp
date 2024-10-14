<%-- 
    Document   : detail-summary
    Created on : Oct 8, 2024, 8:53:28 AM
    Author     : ADMIN
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>
            Willow Spa
        </title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f8f8;
                margin: 0;
                padding: 0;
            }
            .header {
                display: flex;
                align-items: center;
                padding: 10px 20px;
                background-color: white;
                border-bottom: 1px solid #ddd;
            }
            .header img {
                height: 50px;
            }
            .header .title {
                flex-grow: 1;
                margin-left: 10px;
            }
            .header .title h1 {
                margin: 0;
                font-size: 24px;
            }
            .header .title p {
                margin: 0;
                font-size: 12px;
                color: #666;
            }
            .header .nav {
                display: flex;
                align-items: center;
            }
            .header .nav a {
                margin-left: 20px;
                text-decoration: none;
                color: #333;
                font-size: 14px;
            }
            .header .nav a i {
                margin-right: 5px;
            }
            .container {
                padding: 20px;
            }
            .back-link {
                display: flex;
                align-items: center;
                color: #a67c52;
                text-decoration: none;
                margin-bottom: 20px;
            }
            .back-link i {
                margin-right: 5px;
            }
            .content {
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .content h2 {
                margin-top: 0;
                font-size: 24px;
            }
            .content .details {
                font-size: 14px;
                color: #666;
                margin-bottom: 20px;
            }
            .content .details span {
                margin-right: 10px;
            }
            .content .description {
                font-size: 14px;
                color: #666;
                margin-bottom: 20px;
            }
            .content .form-group {
                margin-bottom: 20px;
            }
            .content .form-group label {
                display: block;
                font-size: 14px;
                margin-bottom: 5px;
            }
            .content .form-group select {
                width: 100%;
                padding: 10px;
                font-size: 14px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            .content .form-group p {
                font-size: 12px;
                color: #666;
                margin-top: 5px;
            }
            .content .addons {
                margin-bottom: 20px;
            }
            .content .addons .addon {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }
            .content .addons .addon input {
                margin-right: 10px;
            }
            .content .addons .addon label {
                font-size: 14px;
            }
            .content .addons .addon .price {
                margin-left: auto;
                font-size: 14px;
                color: #666;
            }
            .content .buttons {
                display: flex;
                justify-content: space-between;
            }
            .content .buttons button {
                padding: 10px 20px;
                font-size: 14px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .content .buttons .add-services {
                background-color: white;
                color: #a67c52;
                border: 1px solid #a67c52;
            }
            .content .buttons .continue {
                background-color: #a67c52;
                color: white;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <img alt="Spa Logo" height="50" src="" width="50"/>
            <div class="title">
                <h1>
                    Name Spa
                </h1>
                <p>
                    slogan of spa
                </p>
            </div>
            <div class="nav">
                <a href="#">
                    <i class="fas fa-user">
                    </i>
                    Sign in
                </a>
                <a href="#">
                    <i class="fas fa-calendar-alt">
                    </i>
                    Book
                </a>
                <a href="#">
                    <i class="fas fa-tag">
                    </i>
                    Buy
                </a>
                <a href="#">
                    <i class="fas fa-shopping-cart">
                    </i>
                    Cart
                </a>
            </div>
        </div>
        <div class="container">
            <a class="back-link" href="#">
                <i class="fas fa-chevron-left">
                </i>
                Back
            </a>
            <div class="content">
                <h2>
                    ${service.name} - ${service.duration} min
                </h2>
                <div class="details">
                    <span>
                        ${service.duration}
                    </span>
                    ·
                    <span>
                        $${service.price}
                    </span>
                </div>
                <div class="description">
                    ${service.description}
                </div>
                <div class="form-group">
                    <label for="guests">
                        Add Guests
                    </label>
                    <!--                    <select id="guests">
                                            <option>
                                                Just me
                                            </option>
                                        </select>-->
                    <p>
                        Add-ons are not available when booking this service for 2 or more people.
                    </p>
                </div>
                <!--                <div class="addons">
                                    <div class="addon">
                                        <input id="addon1" type="checkbox"/>
                                        <label for="addon1">
                                            Willow Foot Bath Ritual - 30 min add on
                                        </label>
                                        <span class="price">
                                            30 min · $75.00
                                        </span>
                                    </div>
                                    <div class="addon">
                                        <input id="addon2" type="checkbox"/>
                                        <label for="addon2">
                                            Collagen Eye Treatment Add On
                                        </label>
                                        <span class="price">
                                            $15.00
                                        </span>
                                    </div>
                                    <div class="addon">
                                        <input id="addon3" type="checkbox"/>
                                        <label for="addon3">
                                            Willow Hand Scrub
                                        </label>
                                        <span class="price">
                                            $15.00
                                        </span>
                                    </div>
                                    <div class="addon">
                                        <input id="addon4" type="checkbox"/>
                                        <label for="addon4">
                                            Willow Foot Scrub
                                        </label>
                                        <span class="price">
                                            $15.00
                                        </span>
                                    </div>
                                </div>-->
                <div class="buttons">
                    <form action="action" method="post">
                        <button class="add-services" type="submit" >
                            Add services
                        </button>
                        <button class="continue" type="">
                            Continue
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>