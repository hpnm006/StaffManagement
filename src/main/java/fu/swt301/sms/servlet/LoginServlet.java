package fu.swt301.sms.servlet;

import fu.swt301.sms.service.AuthService;
import fu.swt301.sms.service.AuthException;
import fu.swt301.sms.entity.Staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Tạo Service dưới dạng thuộc tính
    private AuthService authService = new AuthService();

    // Setter dùng cho Unit Test
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Staff staff = authService.login(email, password);
            HttpSession session = request.getSession();
            session.setAttribute("user", staff);
            response.sendRedirect("staff-list");
        } catch (AuthException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp")
                .forward(request, response);
    }
}