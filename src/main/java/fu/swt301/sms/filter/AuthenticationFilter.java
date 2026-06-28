package fu.swt301.sms.filter;

import fu.swt301.sms.entity.Staff;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/staff-list",
        "/staff-crud"
})
public class AuthenticationFilter extends HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
public void doFilter(ServletRequest request,
                     ServletResponse response,
                     FilterChain chain)
        throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    HttpSession session = req.getSession(false);

    if (session == null || session.getAttribute("user") == null) {

        resp.sendRedirect(req.getContextPath() + "/login");
        return;
    }

    Staff staff = (Staff) session.getAttribute("user");

    String uri = req.getRequestURI();

    // Nếu truy cập CRUD
    if (uri.contains("staff-crud")) {

        // Chỉ ADMIN được phép
        if (staff.getRole() == null
                || staff.getRole().getRoleID() != 1) {

            resp.sendRedirect(req.getContextPath() + "/staff-list");
            return;
        }
    }

    chain.doFilter(request, response);
}

    @Override
    public void destroy() {
    }
}