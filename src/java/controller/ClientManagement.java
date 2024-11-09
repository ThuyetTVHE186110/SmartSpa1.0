package controller;

import dal.PersonDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Person;

public class ClientManagement extends HttpServlet {

    private static final int CLIENTS_PER_PAGE = 8; // Define the number of clients per page

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("../adminLogin");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        if (account.getRole() != 3) {  // Assuming roleID = 3 is for staff
            response.sendRedirect("../adminLogin");
            return;
        }

        String filterTier = request.getParameter("filter");
        int page = 1;

// Get the page parameter from the request, if available
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // Default to page 1 if there's an error parsing the page number
            }
        }

        int CLIENTS_PER_PAGE = 8;
        int offset = (page - 1) * CLIENTS_PER_PAGE;

        PersonDAO personDAO = new PersonDAO();
        List<Person> customers;
        int totalClients;

        if (filterTier != null && !filterTier.isEmpty() && !"all".equalsIgnoreCase(filterTier)) {
            // Apply tier filter if it's specified and not "all"
            totalClients = personDAO.countCustomersByTier(filterTier);
            customers = personDAO.getCustomersByTier(filterTier, offset, CLIENTS_PER_PAGE);
        } else {
            // If "all" is selected, retrieve all customers
            totalClients = personDAO.countAllCustomers();
            customers = personDAO.getAllCustomers(offset, CLIENTS_PER_PAGE);
        }

        int totalPages = (int) Math.ceil((double) totalClients / CLIENTS_PER_PAGE);

// Set attributes for JSP
        request.setAttribute("customers", customers);
        request.setAttribute("selectedTier", filterTier);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

// Forward to the JSP page for rendering
        request.getRequestDispatcher("Frontend_Staff/clients.jsp").forward(request, response);

    }
}
