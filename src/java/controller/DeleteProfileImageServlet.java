/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteProfileImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tên file từ session (hoặc database)
        String fileName = (String) request.getSession().getAttribute("uploadedImage");

        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("/") + "img";
            File file = new File(uploadPath, fileName);

            // Xóa file
            if (file.exists()) {
                file.delete();
            }

            // Xóa thông tin file ảnh từ session và đặt lại ảnh mặc định
            request.getSession().setAttribute("uploadedImage", "default-avartar.jpg");

            // Redirect về trang profile
            response.sendRedirect("userProfile.jsp?tab=edit");
        } else {
            response.sendRedirect("userProfile.jsp?error=FileDeleteFailed");
        }
    }
}
