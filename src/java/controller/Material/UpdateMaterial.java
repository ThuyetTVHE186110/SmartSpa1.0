/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Material;

import dal.MaterialDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import model.Material;
import model.Supplier;

/**
 *
 * @author hotdo
 */
@MultipartConfig
@WebServlet(name = "UpdateMaterial", urlPatterns = {"/updatematerial"})
public class UpdateMaterial extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateMaterial</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateMaterial at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MaterialDAO materialDAO = new MaterialDAO();
        List<Supplier> suppliers = materialDAO.getAllSuppliers();
        request.setAttribute("suppliers", suppliers);
        request.getRequestDispatcher("/materialmanagement").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Material ID is required");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Material ID format");
            return;
        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Initialize variables with default values
        int price = 0;
        int supplierId = 0;

        // Check and parse parameters, if present
        try {
            String priceParam = request.getParameter("price");
            if (priceParam != null && !priceParam.isEmpty()) {
                price = Integer.parseInt(priceParam);
            }
            String supplierIdParam = request.getParameter("supplierId");
            if (supplierIdParam != null && !supplierIdParam.isEmpty()) {
                supplierId = Integer.parseInt(supplierIdParam);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            return;
        }

        // Handle file upload or retain existing image
        Part imagePart = request.getPart("file");
        String image = "";
        if (imagePart != null && imagePart.getSize() > 0) {
            // Validate and save the new image file
            String fileName = imagePart.getSubmittedFileName();
            if (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".png")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only JPG and PNG files are allowed");
                return;
            }
            if (imagePart.getSize() > 5 * 1024 * 1024) { // 5MB limit
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File size must be 5MB or less");
                return;
            }

            // Define upload path
            String uploadPath = getServletContext().getRealPath("img");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Create the directory if it doesn't exist
            }

            // Save the file and set the image path
            Path filePath = Paths.get(uploadPath, fileName);
            Files.copy(imagePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            image = "img/" + fileName;
        } else {
            // Use the existing image if no new file is uploaded
            image = request.getParameter("currentImage");
        }

        String status = request.getParameter("status");

        // Create MaterialDAO object and call updateMaterial method
        MaterialDAO materialDAO = new MaterialDAO();
        materialDAO.updateMaterial(id, name, description, price, image, supplierId, status);

        response.sendRedirect("materialmanagement");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
