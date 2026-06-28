package fu.swt301.sms.servlet;

import fu.swt301.sms.dao.StaffDAO;
import fu.swt301.sms.entity.Staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff-list")
public class StaffListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchName = request.getParameter("searchName");
        String searchStatus = request.getParameter("searchStatus");

        int page = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        StaffDAO staffDAO = new StaffDAO();
        int totalRecords = staffDAO.getStaffCountByFilter(searchName, searchStatus);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;
        if (page < 1) page = 1;

        int offset = (page - 1) * pageSize;
        List<Staff> staffList = staffDAO.getStaffByFilterPaginated(searchName, searchStatus, offset, pageSize);

        request.setAttribute("staffList", staffList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchStatus", searchStatus);

        request.getRequestDispatcher("staff-list.jsp").forward(request, response);
    }
}
