package fu.swt301.sms.servlet;

import fu.swt301.sms.entity.Staff;
import fu.swt301.sms.service.StaffService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/staff-list")
public class StaffListServlet extends HttpServlet {
    private StaffService staffService = new StaffService();

    public void setStaffService(StaffService staffService) {
        this.staffService = staffService;
    }

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

        StaffService.PaginatedList<Staff> paginatedResult = staffService.getStaffListPaginated(searchName, searchStatus, page, pageSize);

        request.setAttribute("staffList", paginatedResult.list());
        request.setAttribute("currentPage", paginatedResult.currentPage());
        request.setAttribute("totalPages", paginatedResult.totalPages());
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchStatus", searchStatus);

        request.getRequestDispatcher("staff-list.jsp").forward(request, response);
    }
}
