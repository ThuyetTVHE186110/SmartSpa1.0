<%-- Document : service Created on : Sep 30, 2024, 3:17:34 AM Author : Asus --%>

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta charset="UTF-8" />
                <title>Trang quản lý dzịch vụ</title>
                <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
                <link rel="icon" href="/assets/img/icon.ico" type="image/x-icon" />

                <!-- Fonts and icons -->
                <script src="/assets/js/plugin/webfont/webfont.min.js"></script>
                <script>
                    WebFont.load({
                        google: { "families": ["Lato:300,400,700,900"] },
                        custom: { "families": ["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands", "simple-line-icons"], urls: ['/assets/css/fonts.min.css'] },
                        active: function () {
                            sessionStorage.fonts = true;
                        }
                    });
                </script>

                <!-- CSS Files -->
                <link rel="stylesheet" href="./assets/css/bootstrap.min.css">
                <link rel="stylesheet" href="./assets/css/atlantis.min.css">
                <!-- CSS Just for demo purpose, don't include it in your project -->
                <link rel="stylesheet" href="./assets/css/demo.css">
            </head>

            <body>
                <div class="wrapper">


                    <div class="main-panel">
                        <div class="content">
                            <div class="page-inner">
                                <div class="page-header">
                                    <h4 class="page-title">Quản lý service</h4>
                                    <ul class="breadcrumbs">
                                        <li class="nav-home">
                                            <a href="#">
                                                <i class="flaticon-home"></i>
                                            </a>
                                        </li>
                                        <li class="separator">
                                            <i class="flaticon-right-arrow"></i>
                                        </li>
                                        <li class="nav-item">
                                            <a href="${pageContext.request.contextPath}/.">Trang chủ</a>
                                        </li>
                                        <li class="separator">
                                            <i class="flaticon-right-arrow"></i>
                                        </li>
                                        <li class="nav-item">
                                            <a href="#">Quản lý service</a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <div class="d-flex align-items-center">
                                                <h4 class="card-title">Quản lý service</h4>
                                            </div>
                                        </div>
                                        <div class="card-header">
                                            <section class="row">
                                                <div class="col-9 offset-1">
                                                    <form action="${pageContext.request.contextPath}/servicemanagement"
                                                        method="post" enctype="multipart/form-data">
                                                        <c:if test="${not empty errorMessage}">
                                                            <div class="alert alert-danger" role="alert">
                                                                ${errorMessage}
                                                            </div>
                                                        </c:if>
                                                        <div class="row">
                                                            <div class="col-sm-12">
                                                                <div class="form-group form-group-default">
                                                                    <label for="name">Tên dịch vụ</label>
                                                                    <input name="name" id="name" type="text"
                                                                        class="form-control"
                                                                        placeholder="Tên dịch vụ..." required>
                                                                    <small class="text-danger">${errors.name}</small>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6 pr-0">
                                                                <div class="form-group form-group-default">
                                                                    <label>Đơn giá</label>
                                                                    <input name="price" id="price" min="0" type="number"
                                                                        class="form-control" placeholder="Đơn giá"
                                                                        required>
                                                                    <small class="text-danger">${errors.price}</small>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6 pr-0">
                                                                <div class="form-group form-group-default">
                                                                    <label>Thời gian thực hiện</label>
                                                                    <input name="duration" id="duration" min="0"
                                                                        type="number" class="form-control"
                                                                        placeholder="Thời gian thực hiện" required>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-12">
                                                                <div class="form-group form-group-default">
                                                                    <label>Images</label>
                                                                    <input type="file" id="productImage" name="file"
                                                                        class="form-control">
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-12">
                                                                <div class="form-group form-group-default">
                                                                    <label>Mô tả dịch vụ</label>
                                                                    <textarea name="description" id="description"
                                                                        class="form-control"
                                                                        placeholder="Mô tả dịch vụ"></textarea>
                                                                </div>
                                                            </div>
                                                            <input type="hidden" name="action" value="insert">
                                                        </div>
                                                        <div class="modal-footer no-bd">
                                                            <button type="submit" class="btn btn-primary">Cập
                                                                nhật</button>
                                                            <a href="${pageContext.request.contextPath}/servicemanagement"
                                                                class="btn btn-danger">Hủy</a>
                                                        </div>
                                                    </form>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--   Core JS Files   -->
                <script src="./assets/js/core/jquery.3.2.1.min.js"></script>
                <script src="./assets/js/core/popper.min.js"></script>
                <script src="./assets/js/core/bootstrap.min.js"></script>
                <!-- jQuery UI -->
                <script src="./assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
                <script src="./assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>

                <!-- jQuery Scrollbar -->
                <script src="./assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
                <!-- Datatables -->
                <script src="./assets/js/plugin/datatables/datatables.min.js"></script>
                <!-- Atlantis JS -->
                <script src="./assets/js/atlantis.min.js"></script>
                <!-- Atlantis DEMO methods, don't include it in your project! -->
                <script src="./assets/js/setting-demo2.js"></script>
            </body>

            </html>